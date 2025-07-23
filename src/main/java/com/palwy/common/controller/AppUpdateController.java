package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.req.AppVersionQueryReq;
import com.palwy.common.service.AppUpdateManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-update")
@Api(tags = "应用更新管理接口")
public class AppUpdateController {
    @Autowired
    private AppUpdateManageService service;

    @PostMapping
    @ApiOperation("创建应用更新记录")
    public int create(@RequestBody AppUpdateManage record) {
        return service.create(record);
    }

    @PostMapping
    @ApiOperation("更新应用更新记录")
    public int update(@RequestBody AppUpdateManage record) {
        return service.update(record);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除应用更新记录")
    public int delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID获取应用更新记录")
    public AppUpdateManage getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/page")
    @ApiOperation("分页查询应用更新记录")
    public PageInfo<AppUpdateManage> listByPage(
            @RequestBody AppVersionQueryReq req) {
        return service.listByPage(req.getPageNum(), req.getPageSize(), req.getAppName(), req.getOsType());
    }

    @GetMapping("/force-update")
    @ApiOperation("检查强制更新状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "osType", value = "系统类型(1:Android 2:iOS)", required = true),
            @ApiImplicitParam(name = "versionCode", value = "当前版本号", required = true),
            @ApiImplicitParam(name = "platform", value = "平台标识", required = true)
    })
    public AppUpdateManage checkForceUpdate(
            @RequestHeader("osType") String osType,
            @RequestHeader("versionCode") String versionCode,
            @RequestHeader("platform") String platform) {
        return service.checkForceUpdate(osType, versionCode, platform);
    }
}
