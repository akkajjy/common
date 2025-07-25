package com.palwy.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.mapper.AppInfoDOMapper;
import com.palwy.common.mapper.AppVersionDOMapper;
import com.palwy.common.req.AppInfoReq;
import com.palwy.common.resp.AppVersionResp;
import com.palwy.common.service.AppVersionService;
import com.palwy.common.utils.TOSUpFileUtil;
import com.palwy.common.utils.TosUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppVersionServiceImpl implements AppVersionService {
    private static final int BATCH_SIZE = 500;
    private static final int DEFAULT_URL_EXPIRY_MINUTES = 30;

    @Resource
    private TOSUpFileUtil tosUpFileUtil;
    @Resource
    private TosUtils tosUtils;
    @Autowired
    private AppVersionDOMapper appVersionDOMapper;

    @Autowired
    private AppInfoDOMapper appInfoDOMapper;

    @Autowired
    private Executor asyncExecutor;

    @Override
    public List<AppVersionDO> getAllAppVersions() {
        return appVersionDOMapper.getAllAppVersions();
    }

    @Override
    public AppVersionResp getAppVersionById(Long id) {
        AppVersionResp resp = appVersionDOMapper.getAppVersionById(id);
        if (resp != null && StringUtils.isNotBlank(resp.getDownloadUrl())) {
            resp.setDownloadUrl(generatePresignedUrlSafe(resp.getDownloadUrl()));
        }
        return resp;
    }

    @Override
    public int saveAppVersion(AppInfoDO appInfoDO, AppInfoReq appInfoReq) {
        if (appInfoDO == null || appInfoReq == null) {
            log.warn("无效参数: appInfoDO={}, appInfoReq={}", appInfoDO, appInfoReq);
            return 0;
        }
        log.info("开始插入APP版本信息, appId={}", appInfoDO.getId());

        String channel = appInfoReq.getChannel();
        if (StringUtils.isBlank(channel)) {
            log.warn("缺少渠道信息, appId={}", appInfoDO.getId());
            return 0;
        }

        AppVersionDO version = buildAppVersion(appInfoDO, appInfoReq, channel);
        return saveBatchAppVersion(Collections.singletonList(version));
    }

    // 对象构建方法
    private AppVersionDO buildAppVersion(AppInfoDO appInfoDO, AppInfoReq req, String channel) {
        AppVersionDO entity = new AppVersionDO();
        entity.setAppId(appInfoDO.getId());
        entity.setVersionCode(req.getVersionCode());
        entity.setVersionName(req.getVersionName());
        entity.setDownloadUrl(req.getDownloadUrl());
        entity.setFilePath(req.getFilePath());
        entity.setChannel(channel);
        entity.setDeviceFilter(req.getDeviceFilter());
        entity.setShowCashLoan(req.getShowCashLoan());
        entity.setForceUpdateType(req.getForceUpdateType());
        entity.setUpdateDesc(req.getUpdateDesc());
        return entity;
    }

    // 批量插入 + 异常处理
    private int batchInsertWithFallback(List<AppVersionDO> dataList) {
        if (CollectionUtils.isEmpty(dataList)) return 0;

        int totalCount = 0;
        for (int i = 0; i < dataList.size(); i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, dataList.size());
            List<AppVersionDO> batchList = dataList.subList(i, endIndex);

            try {
                int count = appVersionDOMapper.saveBatchAppVersion(batchList);
                totalCount += count;
            } catch (DataAccessException e) {
                log.error("批次插入失败: 批次[{}-{}], 原因: {}", i, endIndex, e.getMessage());
            }
        }
        return totalCount;
    }

    @Override
    public int updateAppVersion(AppVersionDO appVersion) {
        if (appVersion == null || appVersion.getId() == null) {
            log.warn("更新APP版本失败: 无效参数");
            return 0;
        }
        return appVersionDOMapper.updateAppVersion(appVersion);
    }

    @Override
    public int deleteAppVersion(Long id) {
        if (id == null) {
            log.warn("删除APP版本失败: ID为空");
            return 0;
        }

        AppVersionDO appVersionDO = appVersionDOMapper.getAppInfo(id);
        if (appVersionDO == null) {
            log.warn("找不到要删除的APP版本, id={}", id);
            return 0;
        }

        try {
            AppInfoDO infoDO = appInfoDOMapper.getAppInfoById(appVersionDO.getAppId());
            if (infoDO != null) {
                appInfoDOMapper.deleteAppInfo(infoDO.getId());
            }
            return appVersionDOMapper.deleteAppVersion(id);
        } catch (Exception e) {
            log.error("删除APP版本及相关信息失败, id={}", id, e);
            return 0;
        }
    }

    @Override
    public int deleteVersionsByAppId(Long appId) {
        if (appId == null) {
            log.warn("按AppID删除版本失败: appId为空");
            return 0;
        }
        return appVersionDOMapper.deleteVersionsByAppId(appId);
    }

    @Override
    public int saveBatchAppVersion(List<AppVersionDO> versions) {
        return batchInsertWithFallback(versions);
    }

    @Override
    public PageInfo<AppVersionResp> listAppVersionsByCondition(
            String appName,
            String osType,
            String channel,
            int pageNum,
            int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<AppVersionResp> list = appVersionDOMapper.listByCondition(appName, osType, channel);

        // 并行生成预签名URL（使用线程池）
        if (!CollectionUtils.isEmpty(list)) {
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (AppVersionResp resp : list) {
                futures.add(CompletableFuture.runAsync(() -> {
                    String signedUrl = tosUpFileUtil.generatePresignedUrl(resp.getDownloadUrl(),60);
                    if (signedUrl != null) {
                        resp.setDownloadUrl(signedUrl);
                    }
                }, asyncExecutor));
            }

            // 等待所有异步任务完成
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }
        PageInfo<AppVersionResp> pageList = new PageInfo<>(list);
        pageList.setTotal(list.size());
        return pageList;
    }

    // 安全生成预签名URL（带空值检查）
    private String generatePresignedUrlSafe(String downloadUrl) {
        if (StringUtils.isBlank(downloadUrl)) {
            return null;
        }
        try {
            return tosUpFileUtil.generatePresignedUrl(downloadUrl, DEFAULT_URL_EXPIRY_MINUTES);
        } catch (Exception e) {
            log.error("生成预签名URL失败: url={}", downloadUrl, e);
            return downloadUrl; // 返回原始URL避免功能中断
        }
    }

    @Override
    public boolean checkForceUpdate(String versionCode) {
        if (StringUtils.isBlank(versionCode)) {
            log.warn("检查强制更新失败: 版本号为空");
            return false;
        }

        AppVersionDO version = appVersionDOMapper.getVersionByAppIdAndChannel(versionCode);
        return version != null && version.getForceUpdateType() > 0;
    }
}