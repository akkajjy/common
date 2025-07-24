package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.req.AppVersionQueryReq;
import com.palwy.common.resp.AppVersionResp;
import com.palwy.common.service.AppVersionService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-version")
public class AppVersionController {
    @Autowired
    private AppVersionService appVersionService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/query")
    public ResultVO<PageInfo<AppVersionResp>> listAppVersions(@RequestBody AppVersionQueryReq queryReq) {

        // 验证分页参数
        if (queryReq.getPageNum() == null || queryReq.getPageNum() < 1) {
            queryReq.setPageNum(1);
        }
        if (queryReq.getPageSize() == null || queryReq.getPageSize() < 1) {
            queryReq.setPageSize(10);
        } else if (queryReq.getPageSize() > 100) {
            queryReq.setPageSize(100); // 最大100条/页
        }

        PageInfo<AppVersionResp> pageInfo = appVersionService.listAppVersionsByCondition(
                queryReq.getAppName(),
                queryReq.getOsType(),
                queryReq.getChannels(),
                queryReq.getPageNum(),
                queryReq.getPageSize());

        // 自定义响应结构
        return ResultVOUtil.success(pageInfo);
    }

    @ApiOperation(value = "软删除应用版本", notes = "通过版本ID逻辑删除记录")
    @GetMapping("/deleteById")
    public ResultVO softDeleteVersion(@RequestParam("id") Long id) {
        int flag = appVersionService.deleteAppVersion(id);
        if(flag>0){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.fail("删除失败");
        }
    }

    @ApiOperation(value = "根据ID查询版本详情", notes = "获取指定版本的详细信息")
    @GetMapping("/{id}")
    public ResultVO<AppVersionResp> getVersionById(@PathVariable Long id) {
        AppVersionResp detail = appVersionService.getAppVersionById(id);
        if (detail != null) {
            return ResultVOUtil.success(detail);
        } else {
            return ResultVOUtil.fail("未找到该版本信息");
        }
    }
  /*  @ApiOperation(value = "查询版本强制更新状态")
    @GetMapping("/checkForceUpdate")
    public ResultVO<Boolean> checkForceUpdate(@RequestHeader("versionCode") String versionCode) {


        boolean isForceUpdate = appVersionService.checkForceUpdate(versionCode);
        return ResultVOUtil.success(isForceUpdate);
    }*/
}
