package com.palwy.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.mapper.AppInfoMapper;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppReq;
import com.palwy.common.utils.TOSUpFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class AppService {
    @Autowired
    private AppInfoMapper appInfoMapper;
    @Autowired
    private TOSUpFileUtil tosUpFileUtil;
    @Autowired
    private AppUpdateManageMapper updateManageMapper;
    @Autowired
    private Executor asyncExecutor;
    @Autowired
    @Resource
    private ClrDictService clrDictService;
    public List<AppInfo> getAllAppInfos() {
        return appInfoMapper.getAllAppInfos();
    }
    @Transactional
    public void createAppWithDefaultUpdate(AppInfo appInfo) {
        // 设置默认值
        appInfo.setCreator("system");
        appInfo.setModifier("system");

        // 插入AppInfo
        appInfoMapper.insertAppInfo(appInfo);

        // 创建默认的更新管理记录
        AppUpdateManage updateManage = new AppUpdateManage();
        updateManage.setAppName(appInfo.getAppName());
        updateManage.setOsType(appInfo.getOsType());
        updateManage.setAppId(appInfo.getId());
        updateManage.setVersionCode(appInfo.getVersionCode());
        updateManage.setVersionName(appInfo.getVersionName());
        updateManage.setPlatform(appInfo.getPlatform());
        updateManage.setForceUpdateType("0"); // 默认不更新
        updateManage.setCreator("system");
        updateManage.setModifier("system");
        updateManage.setUpdateDesc(appInfo.getUpdateDesc());
        updateManageMapper.insertAppUpdateManage(updateManage);
    }

    public PageInfo<AppInfo> getAppPage(AppReq appReq) {
        // 使用PageHelper实现分页
        // 设置分页参数
        try {
            PageHelper.startPage(appReq.getPageNum(), appReq.getPageSize());
            List<AppInfo> list = appInfoMapper.selectAppInfoByCondition(appReq);
            List<ClrDictDO> appNameList = clrDictService.getClrDictListByDictType("AppNameEnum");
            Map<String, String> dictMap = appNameList.stream()
                    .collect(Collectors.toMap(ClrDictDO::getDictValue, ClrDictDO::getDictLabel));

            // 并行生成预签名URL（使用线程池）替换应用名称
            if (!CollectionUtils.isEmpty(list)) {
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                for (AppInfo resp : list) {
                    String dictLabel = dictMap.get(resp.getAppName());
                    if (dictLabel != null) {
                        resp.setAppName(dictLabel);
                    }
                    futures.add(CompletableFuture.runAsync(() -> {
                        String signedUrl = tosUpFileUtil.generatePresignedUrl(resp.getFilePath(),6000);
                        if (signedUrl != null) {
                            resp.setDownloadUrl(signedUrl);
                        }
                    }, asyncExecutor));
                }

                // 等待所有异步任务完成
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            }
            PageInfo<AppInfo> pageInfo = new PageInfo<>(list);
            pageInfo.setTotal(list.size());
            log.info("查询结果返回");
            return pageInfo;
        } catch (Exception e) {
            log.info("查询失败：{}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public void updateAppInfo(AppInfo appInfo) {
        if(appInfo.getModifier() == null) {
            appInfo.setModifier("system"); // 默认更新人
        }
        appInfo.setGmtModified(new Date());
        int rows = appInfoMapper.updateAppInfo(appInfo);
        if(rows > 0) {
            AppUpdateManage manage = new AppUpdateManage();
            manage.setAppName(appInfo.getAppName());
            manage.setOsType(appInfo.getOsType());
            manage.setAppId(appInfo.getId());
            manage.setVersionCode(appInfo.getVersionCode());
            manage.setVersionName(appInfo.getVersionName());
            manage.setPlatform(appInfo.getPlatform());
            manage.setForceUpdateType("0"); // 默认不更新
            manage.setCreator("system");
            manage.setModifier("system");
            manage.setUpdateDesc(appInfo.getUpdateDesc());
            updateManageMapper.updateByAppId(manage);
        }
    }

    @Transactional
    public void deleteAppInfo(Long id) {
        appInfoMapper.deleteAppInfo(id);
        updateManageMapper.updateIsDeletedByAppId(id);
    }

    public AppInfo getAppById(Long id) {
        return appInfoMapper.selectById(id);
    }
}
