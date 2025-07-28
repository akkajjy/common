package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppInfo;
import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.req.AppReq;
import com.palwy.common.service.AppService;
import com.palwy.common.service.ClrDictService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apps")
@Api(tags = "应用管理接口")
public class AppController {
    @Autowired
    private AppService appService;
    @Resource
    private ClrDictService clrDictService;
    //查询应用
    @ApiOperation(value = "查询应用")
    @GetMapping("/getAppList")
    public ResultVO<List<ClrDictDO>> getAppList() {
        return ResultVOUtil.success(clrDictService.getClrDictListByDictType("AppNameEnum"));
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

    @PostMapping("/getApps")
    @ApiOperation("分页查询应用列表")
    public ResultVO<PageInfo<AppInfo>> getApps(@RequestBody AppReq appReq) {
        PageInfo<AppInfo> pageInfo = appService.getAppPage(appReq);
        return ResultVOUtil.success(pageInfo);
    }

    @PostMapping("/create")
    @ApiOperation("创建应用并初始化更新配置")
    public ResultVO<Long> createApp(@RequestBody AppInfo appInfo) {
        appService.createAppWithDefaultUpdate(appInfo);
        return ResultVOUtil.success();
    }

    @GetMapping("/deleteById")
    @ApiOperation("软删除应用")
    public ResultVO<Void> deleteApp(@RequestParam("id") Long id) {
        appService.deleteAppInfo(id);
        return ResultVOUtil.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新应用信息")
    public ResultVO<Void> updateApp(@RequestBody AppInfo appInfo) {
        AppInfo existing = appService.getAppById(appInfo.getId());
        if(existing == null || "Y".equals(existing.getIsDeleted())) {
            return ResultVOUtil.fail("记录不存在或已被删除");
        }
        appService.updateAppInfo(appInfo);
        appInfo.setModifier("system"); // 设置更新人
        appService.updateAppInfo(appInfo);
        return ResultVOUtil.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询应用详情")
    public ResultVO<AppInfo> getAppDetail(@PathVariable Long id) {
        AppInfo appInfo = appService.getAppById(id);
        return ResultVOUtil.success(appInfo);
    }
}
