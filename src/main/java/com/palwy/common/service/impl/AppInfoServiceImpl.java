package com.palwy.common.service.impl;

import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.mapper.AppInfoDOMapper;
import com.palwy.common.req.AppInfoReq;
import com.palwy.common.service.AppInfoService;
import com.palwy.common.service.AppVersionService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private AppInfoDOMapper appInfoDOMapper;
    @Autowired
    private AppVersionService appVersionService;
    @Override
    public List<AppInfoDO> getAllAppInfos() {
        return appInfoDOMapper.getAllAppInfos();
    }

    @Override
    public int addAppInfo(AppInfoDO appInfoDO) {
        return appInfoDOMapper.saveAppInfo(appInfoDO);
    }

    @Override
    public AppInfoDO getAppInfoById(Long id) {
        return appInfoDOMapper.getAppInfoById(id);
    }

    @Override
    public int updateAppInfo(AppInfoDO appInfoDO) {
        return appInfoDOMapper.updateAppInfo(appInfoDO);
    }

    @Override
    @Transactional
    public ResultVO addAppAndVersion(AppInfoReq appInfoReq) {
        log.debug("新增应用: {}", appInfoReq.getAppName());
        try {
            boolean nameExists = appInfoDOMapper.getAppInfoByNameAndVersion(appInfoReq.getAppName(),
                    appInfoReq.getVersionCode(),appInfoReq.getChannel(),appInfoReq.getOsType())>0;
            if (nameExists) {
                return ResultVOUtil.fail("应用保存失败，该应用名称已存在");
            }
            AppInfoDO appInfoDO = AppInfoDO.builder()
                    .appName(appInfoReq.getAppName())
                    .osType(appInfoReq.getOsType())
                    .extraInfo(appInfoReq.getExtraInfo())
                    .build();

            if (appInfoDOMapper.saveAppInfo(appInfoDO) <= 0) {
                return ResultVOUtil.fail("应用保存失败");
            }

            saveAppVersionInNewTransaction(appInfoDO, appInfoReq);
            return ResultVOUtil.success();
        } catch (Exception e) {
            log.error("系统异常: {}", e.getMessage(), e);
            return ResultVOUtil.fail("系统繁忙");
        }
    }

    @Override
    @Transactional
    public ResultVO updateAppAndVersion(AppInfoReq appInfoReq) {
        log.debug("更新应用: {}", appInfoReq.getAppName());
        try {
            AppInfoDO existingApp = appInfoDOMapper.getAppInfoById(appInfoReq.getId());
            if (existingApp == null || "Y".equals(existingApp.getIsDeleted())) {
                return ResultVOUtil.fail("应用不存在或已被删除");
            }

            AppInfoDO updatedApp = AppInfoDO.builder()
                    .id(appInfoReq.getId())
                    .appName(appInfoReq.getAppName())
                    .osType(appInfoReq.getOsType())
                    .extraInfo(appInfoReq.getExtraInfo())
                    .build();

            if (appInfoDOMapper.updateAppInfo(updatedApp) <= 0) {
                return ResultVOUtil.fail("应用更新失败");
            }

            updateAppVersionsInNewTransaction(existingApp, appInfoReq);
            return ResultVOUtil.success();
        } catch (Exception e) {
            log.error("系统异常: {}", e.getMessage(), e);
            return ResultVOUtil.fail("系统繁忙");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAppVersionInNewTransaction(AppInfoDO appInfoDO, AppInfoReq req) {
        String channel = req.getChannel();
        if (channel != null && !channel.isEmpty()) {
            appVersionService.saveAppVersion(appInfoDO, req); // 直接传递整个请求对象
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAppVersionsInNewTransaction(AppInfoDO appInfoDO, AppInfoReq req) {
        if (req.getVersionCode() != null) {
            appVersionService.deleteVersionsByAppId(appInfoDO.getId());
            String channel = req.getChannel();
            if (channel != null && !channel.isEmpty()) {
                AppVersionDO newVersion = buildAppVersionDO(appInfoDO, req, channel);
                List<AppVersionDO> newVersions = new ArrayList<>();
                newVersions.add(newVersion);
                appVersionService.saveBatchAppVersion(newVersions);
            }
        }
    }

    private AppVersionDO buildAppVersionDO(AppInfoDO appInfoDO, AppInfoReq req, String channel) {
        AppVersionDO version = new AppVersionDO();

        version.setAppId(appInfoDO.getId());                   // 关联应用ID
        version.setVersionCode(req.getVersionCode());          // 版本号
        version.setVersionName(req.getVersionName());          // 版本名称
        version.setDownloadUrl(req.getDownloadUrl());          // 下载地址
        version.setFilePath(req.getFilePath());                // 文件路径
        version.setChannel(channel);                           // 渠道标识
        version.setDeviceFilter(req.getDeviceFilter());        // 设备过滤规则
        version.setShowCashLoan(req.getShowCashLoan());         // 是否显示现金贷
        version.setForceUpdateType(req.getForceUpdateType());  // 强制更新类型
        version.setUpdateDesc(req.getUpdateDesc());            // 更新描述

        return version;
    }
}
