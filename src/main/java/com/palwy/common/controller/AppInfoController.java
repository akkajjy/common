package com.palwy.common.controller;

import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.req.AppInfoReq;
import com.palwy.common.service.AppInfoService;
import com.palwy.common.service.ClrDictService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/app-info")
public class AppInfoController {
    @Resource
    private AppInfoService appInfoService;
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
    public ResultVO getVersionPlatformList() {
        List<ClrDictDO> clrDictDOList = clrDictService.getClrDictListByDictType("VersionPlatformEnum");

        // 转换为Map: dictValue -> dictLabel
        Map<String, String> platformMap = clrDictDOList.stream()
                .collect(Collectors.toMap(
                        ClrDictDO::getDictValue,
                        ClrDictDO::getDictLabel,
                        (existing, replacement) -> existing // 重复键处理策略
                ));

        return ResultVOUtil.success(platformMap);
    }

    @ApiOperation(value = "新增应用及版本")
    @PostMapping("/add")
    public ResultVO add(@Valid @RequestBody AppInfoReq appInfoReq) {
        return appInfoService.addAppAndVersion(appInfoReq);
    }

    @ApiOperation(value = "更新应用及版本")
    @PostMapping("/update")
    public ResultVO update(@Valid @RequestBody AppInfoReq appInfoReq) {
        return appInfoService.updateAppAndVersion(appInfoReq);
    }
}
