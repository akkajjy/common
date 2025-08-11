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
@RequestMapping("/v1/app-update")
@CrossOrigin(
        origins = {
                "http://one-in.shhpalwy.com",
                "http://one-in-test.shhpalwy.com",
                "http://10.32.0.2:8080"
        },
        allowCredentials = "true"
)
@Api(tags = "应用更新管理接口")
public class AppUpdateManageController {
    @Autowired
    private AppUpdateManageService service;
    @Autowired
    private AppUpdateManageMapper mapper;

    @PostMapping("/page")
    @ApiOperation("分页查询应用更新信息")
    public ResultVO<PageInfo<AppUpdateManage>> page(@RequestBody AppUpdateReq req) {
        try {
            return ResultVOUtil.success(service.selectPage(req, req.getPageNum(), req.getPageSize()));
        } catch (Exception e) {
            return ResultVOUtil.fail("查询失败");
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询应用更新详情")
    public ResultVO<AppUpdateManage> getById(@PathVariable Long id) {
        return ResultVOUtil.success(service.getById(id));
    }

    @GetMapping("/checkIsUpdate")
    @ApiOperation("根据版本号和平台查询应用更新")
    public ResultVO<AppUpdateManage> checkIsUpdate(
            @RequestParam("appName") String appName,
            @RequestParam("versionCode") String versionCode,
            @RequestParam("platform") String platform
    ) {
        AppUpdateManage appUpdateManage = service.getByVersionAndPlatform(appName,versionCode, platform);
        if(appUpdateManage==null){
            return ResultVOUtil.fail("未找到版本");
        }
        return ResultVOUtil.success(appUpdateManage);
    }

    @PostMapping("/updateById")
    @ApiOperation("根据Id更新")
    public ResultVO updateById(@RequestBody AppUpdateManage manage) {
        try {
            int code = Integer.parseInt(manage.getVersionCode()); // String转整型
            if (code <= 0) {
                return ResultVOUtil.fail("版本号必须为正整数"); // 禁止0或负值
            }
        } catch (NumberFormatException e) {
            return ResultVOUtil.fail("版本号只能填整数"); // 非数字捕获
        }
        AppUpdateManage appUpdateManage = mapper.isMax(manage.getAppName(), manage.getPlatform());
        if(appUpdateManage==null||"0".equals(manage.getForceUpdateType())||!appUpdateManage.getVersionCode().equals(manage.getVersionCode())){
            service.updateById(manage);
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("最大版本不允许修改是否强制更新！");
        }
    }
}
