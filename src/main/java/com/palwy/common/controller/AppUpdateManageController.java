package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppUpdateReq;
import com.palwy.common.service.AppUpdateManageService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
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
    @Autowired
    private AppUpdateManageMapper mapper;

    @PostMapping("/page")
    @ApiOperation("分页查询应用更新信息")
    public ResultVO<PageInfo<AppUpdateManage>> page(@RequestBody AppUpdateReq req) {
        return ResultVOUtil.success(service.selectPage(req, req.getPageNum(), req.getPageSize()));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询应用更新详情")
    public ResultVO<AppUpdateManage> getById(@PathVariable Long id) {
        return ResultVOUtil.success(service.getById(id));
    }

    @GetMapping("/checkIsUpdate")
    @ApiOperation("根据版本号和平台查询应用更新")
    public ResultVO<AppUpdateManage> checkIsUpdate(
            @RequestParam String versionCode,
            @RequestParam String platform
    ) {
        return ResultVOUtil.success(service.getByVersionAndPlatform(versionCode, platform));
    }

    @PostMapping("/updateById")
    @ApiOperation("根据Id更新")
    public ResultVO updateById(@RequestBody AppUpdateManage manage) {
        AppUpdateManage appUpdateManage = mapper.isMax(manage.getAppName(), manage.getPlatform());
        if(appUpdateManage==null){
            service.updateById(manage);
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("最大版本不允许修改！");
        }
    }
}
