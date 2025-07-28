package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.req.AppUpdateManageReq;
import com.palwy.common.req.AppVersionQueryReq;
import com.palwy.common.service.AppUpdateManageService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
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
    public ResultVO create(@RequestBody AppUpdateManageReq record) {
        int flag = service.create(record);
        if (flag > 0) {
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("创建失败");
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新应用更新记录")
    public ResultVO update(@RequestBody AppUpdateManageReq record) {
        int flag = service.update(record);
        if (flag > 0) {
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("修改失败");
        }
    }

    @GetMapping("/deleteById")
    @ApiOperation("删除应用更新记录")
    public ResultVO delete(@RequestParam("id") Integer id) {
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
        return ResultVOUtil.success(service.listByPage(req.getPageNum(), req.getPageSize(), req.getChannel()));
    }

    @GetMapping("/checkForceUpdate")
    @ApiOperation("检查强制更新状态")
    public ResultVO<AppUpdateManage> checkForceUpdate(
            @RequestHeader("versionCode") String versionCode,
            @RequestHeader("platform") String platform) {
        AppUpdateManage appUpdateManage = service.checkForceUpdate(versionCode, platform);
        return ResultVOUtil.success(appUpdateManage);
    }
}
