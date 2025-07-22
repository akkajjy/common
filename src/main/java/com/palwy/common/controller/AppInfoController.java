package com.palwy.common.controller;

import com.palwy.common.Enum.AppTypeEnum;
import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.req.AppInfoReq;
import com.palwy.common.service.AppInfoService;
import com.palwy.common.service.AppVersionService;
import com.palwy.common.service.ClrDictService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/app-info")
public class AppInfoController {
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private ClrDictService clrDictService;

    //查询应用
    @ApiOperation(value = "查询应用")
    @GetMapping("/getAppList")
    public ResultVO<List<ClrDictDO>> getAppList() {
        return ResultVOUtil.success(appInfoService.getAllAppInfos());
    }

    //查询发版平台
    @ApiOperation(value = "查询发版平台")
    @GetMapping("/getVersionPlatformList")
    public ResultVO<List<ClrDictDO>> getVersionPlatformList() {
        List<ClrDictDO> clrDictDOList = clrDictService.getClrDictListByDictType("VersionPlatformEnum");
        return ResultVOUtil.success(clrDictDOList);
    }

    @ApiOperation(value = "新增应用及版本")
    @PostMapping("/add")
    @Transactional
    public ResultVO add(@Valid @RequestBody AppInfoReq appInfoReq) {
        log.debug("新增应用: {}", appInfoReq.getAppName());
        try {
            // 对象转换
            AppInfoDO appInfoDO = AppInfoDO.builder()
                    .appName(appInfoReq.getAppName())
                    .appType(AppTypeEnum.fromValue(Integer.parseInt(appInfoReq.getAppType())).getDescription())
                    .osType(AppTypeEnum.fromValue(Integer.parseInt(appInfoReq.getOsType())).getDescription())
                    .extraInfo(appInfoReq.getExtraInfo())
                    .build();
            // 保存应用
            if (appInfoService.addAppInfo(appInfoDO) <= 0) {
                return ResultVOUtil.fail("应用保存失败");
            }
            // 独立事务保存版本
            saveAppVersionIfNeeded(appInfoDO, appInfoReq);
            return ResultVOUtil.success();
        } catch (Exception e) {
            log.error("系统异常: {}", e.getMessage(), e);
            return ResultVOUtil.fail("系统繁忙");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAppVersionIfNeeded(AppInfoDO appInfoDO, AppInfoReq req) {
        Optional.ofNullable(req.getVersionCode())
                .ifPresent(v -> appVersionService.saveAppVersion(appInfoDO, req));
    }
    @ApiOperation(value = "更新应用及版本")
    @PostMapping("/update")
    @Transactional
    public ResultVO update(@Valid @RequestBody AppInfoReq appInfoReq) {
        log.debug("更新应用: {}", appInfoReq.getAppName());
        try {
            // 1. 获取现有应用信息
            AppInfoDO existingApp = appInfoService.getAppInfoById(appInfoReq.getId());
            if (existingApp == null || "Y".equals(existingApp.getIsDeleted())) {
                return ResultVOUtil.fail("应用不存在或已被删除");
            }

            // 2. 更新应用基本信息
            AppInfoDO updatedApp = AppInfoDO.builder()
                    .id(appInfoReq.getId())
                    .appName(appInfoReq.getAppName())
                    .appType(AppTypeEnum.fromValue(Integer.parseInt(appInfoReq.getAppType())).getDescription())
                    .osType(AppTypeEnum.fromValue(Integer.parseInt(appInfoReq.getOsType())).getDescription())
                    .extraInfo(appInfoReq.getExtraInfo())
                    .build();

            // 3. 执行应用更新
            if (appInfoService.updateAppInfo(updatedApp) <= 0) {
                return ResultVOUtil.fail("应用更新失败");
            }

            // 4. 处理版本更新（独立事务）
            updateAppVersions(existingApp, appInfoReq);

            return ResultVOUtil.success();
        } catch (Exception e) {
            log.error("系统异常: {}", e.getMessage(), e);
            return ResultVOUtil.fail("系统繁忙");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAppVersions(AppInfoDO appInfoDO, AppInfoReq req) {
        if (req.getVersionCode() != null) {
            // 4.1 删除现有版本
            appVersionService.deleteVersionsByAppId(appInfoDO.getId());

            // 4.2 重新插入新版本
            List<AppVersionDO> newVersions = req.getChannelList().stream()
                    .filter(channel -> !channel.isEmpty())
                    .map(channel -> buildAppVersionDO(appInfoDO, req, channel))
                    .collect(Collectors.toList());

            appVersionService.saveBatchAppVersion(newVersions);
        }
    }

    private AppVersionDO buildAppVersionDO(AppInfoDO appInfoDO, AppInfoReq req, String channel) {
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
}
