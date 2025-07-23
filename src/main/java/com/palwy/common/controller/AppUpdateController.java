package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.req.AppVersionQueryReq;
import com.palwy.common.service.AppUpdateManageService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/app-update")
@Api(tags = "应用更新管理接口")
public class AppUpdateController {
    @Autowired
    private AppUpdateManageService service;

    @ApiOperation(value = "创建应用更新记录")
    @PostMapping("/create")
    public ResultVO create(@RequestBody AppUpdateManage record) {
        int flag = service.create(record);
        if (flag > 0) {
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("创建失败");
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新应用更新记录")
    public ResultVO update(@RequestBody AppUpdateManage record) {
        int flag = service.update(record);
        if (flag > 0) {
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("修改失败");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除应用更新记录")
    public ResultVO delete(@PathVariable Integer id) {
        int flag = service.delete(id);
        if (flag > 0) {
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("删除失败");
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID获取应用更新记录")
    public ResultVO<AppUpdateManage> getById(@PathVariable Integer id) {
        return ResultVOUtil.success(service.getById(id));
    }

    @PostMapping("/page")
    @ApiOperation("分页查询应用更新记录")
    public ResultVO<PageInfo<AppUpdateManage>> listByPage(
            @RequestBody AppVersionQueryReq req) {
        return ResultVOUtil.success(service.listByPage(req.getPageNum(), req.getPageSize(), req.getAppName(), req.getOsType()));
    }

    @GetMapping("/force-update")
    @ApiOperation("检查强制更新状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "osType", value = "系统类型(1:Android 2:iOS)", required = true),
            @ApiImplicitParam(name = "versionCode", value = "当前版本号", required = true),
            @ApiImplicitParam(name = "platform", value = "平台标识", required = true)
    })
    public ResultVO<AppUpdateManage> checkForceUpdate(
            @RequestHeader("osType") String osType,
            @RequestHeader("versionCode") String versionCode,
            @RequestHeader("platform") String platform) {
        return ResultVOUtil.success(service.checkForceUpdate(osType, versionCode, platform));
    }
}
