package com.palwy.common.controller;

import com.ksvip.next.components.core.exception.BusinessException;
import com.palwy.common.Enum.AppTypeEnum;
import com.palwy.common.entity.AppInfoDO;
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

}
