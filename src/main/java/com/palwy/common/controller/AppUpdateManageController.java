package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.service.AppUpdateManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-update")
@Api(tags = "应用更新管理接口")
public class AppUpdateManageController {
    @Autowired
    private AppUpdateManageService service;


    @GetMapping("/page")
    @ApiOperation("分页查询应用更新信息")
    public PageInfo<AppUpdateManage> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            AppUpdateManage query
    ) {
        return service.selectPage(query, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询应用更新详情")
    public AppUpdateManage getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/checkIsUpdate")
    @ApiOperation("根据版本号和平台查询应用更新")
    public AppUpdateManage checkIsUpdate(
            @RequestParam String versionCode,
            @RequestParam String platform
    ) {
        return service.getByVersionAndPlatform(versionCode, platform);
    }
}
