package com.palwy.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.mapper.AppInfoMapper;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppReq;
import com.palwy.common.utils.TOSUpFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
        updateManage.setAppId(appInfo.getId());
        updateManage.setVersionCode(appInfo.getVersionCode());
        updateManage.setVersionName(appInfo.getVersionName());
        updateManage.setPlatform(appInfo.getPlatform());
        updateManage.setForceUpdateType("0"); // 默认不更新
        updateManage.setCreator("system");
        updateManage.setModifier("system");

        updateManageMapper.insertAppUpdateManage(updateManage);
    }

    public PageInfo<AppInfo> getAppPage(AppReq appReq) {
        // 使用PageHelper实现分页
        // 设置分页参数
        PageHelper.startPage(appReq.getPageNum(), appReq.getPageSize());
        List<AppInfo> list = appInfoMapper.selectAppInfoByCondition(appReq);
        // 并行生成预签名URL（使用线程池）
        if (!CollectionUtils.isEmpty(list)) {
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (AppInfo resp : list) {
                futures.add(CompletableFuture.runAsync(() -> {
                    String signedUrl = tosUpFileUtil.generatePresignedUrl(resp.getDownloadUrl(),6000);
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
        return pageInfo;
    }

    @Transactional
    public void updateAppInfo(AppInfo appInfo) {
        appInfoMapper.updateAppInfo(appInfo);
        AppUpdateManage updateManage = new AppUpdateManage();
        updateManage.setAppId(appInfo.getId()); // 关联ID
        updateManage.setAppName(appInfo.getAppName());
        updateManage.setVersionCode(appInfo.getVersionCode());
        updateManage.setVersionName(appInfo.getVersionName());
        updateManage.setPlatform(appInfo.getPlatform());
        updateManageMapper.updateByAppId(updateManage);
    }

    @Transactional
    public void deleteAppInfo(Long id) {
        appInfoMapper.deleteAppInfo(id);
        updateManageMapper.updateIsDeletedByAppId(id); // 需实现此Mapper方法
    }

    public AppInfo getAppById(Long id) {
        // 实现根据ID查询逻辑，注意过滤已删除数据（isDeleted='N'）
        return appInfoMapper.selectById(id);
    }
}
