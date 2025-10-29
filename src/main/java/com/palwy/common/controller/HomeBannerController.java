package com.palwy.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.banner.dto.HomeBannerRequest;
import com.palwy.common.banner.dto.HomeBannerStatusRequest;
import com.palwy.common.banner.entity.HomeBannerBackground;
import com.palwy.common.banner.service.HomeBannerBackgroundService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(
        origins = {
                "https://installment.shhpalwy.com",
                "https://h5-installment-shop-test.shhpalwy.com",
                "http://h5-installment-shop-test.shhpalwy.com",
                "http://localhost:8080",
                "http://localhost:9529","http://localhost:9528",
                "https://zy-shop-test02.palwy.com",
                "http://zy-shop-test02.palwy.com",
                "https://common-test.shhpalwy.com",
                "http://192.168.1.172:9094",
                "https://zy-admin.shhpalwy.com",
                "http://zy-shop-test03.palwy.com", "https://zy-shop-test03.palwy.com",
                "http://zy-shop-test03.palwy.com", "https://zy-shop-test03.palwy.com",
                "http://zy-shop-test.shhpalwy.com",
                "https://zy-shop-test.shhpalwy.com",
                "http://common-test.shhpalwy.com","https://zy.shhpalwy.com"
        },
        allowCredentials = "true"
)
@Api(tags = "首页背景图管理接口")
@RestController
@RequestMapping("/v1/homeBanners")
public class HomeBannerController {
    @Autowired
    private HomeBannerBackgroundService homeBannerBackgroundService;
    // 特别注意：为POST接口添加OPTIONS映射
    @ApiOperation(value = "预检请求处理", hidden = true)
    @RequestMapping(
            value = "/create",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity<?> handleCreatePreflight() {
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "添加首页背景图配置")
    @PostMapping("/create")
    public ResultVO<Long> createHomeBanner(
            @ApiParam(value = "背景图请求参数", required = true)
            @Valid @RequestBody HomeBannerRequest request) {
        try {
            HomeBannerBackground result = homeBannerBackgroundService.addHomeBanner(request);
            return ResultVOUtil.success(result.getId());
        } catch (Exception e) {
            return ResultVOUtil.fail(e.getMessage());
        }
    }
    // 为所有POST接口添加OPTIONS映射 (复制上述模式)
    @ApiOperation(value = "预检请求处理", hidden = true)
    @RequestMapping(
            value = "/getList",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity<?> handleGetListPreflight() {
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "分页查询首页背景图列表")
    @PostMapping("/getList")
    public ResultVO<PageInfo<HomeBannerBackground>> getHomeBanners(
            @ApiParam(value = "分页查询参数", required = true)
            @RequestBody HomeBannerRequest request) {

        // 注意：这里需要根据实际需求实现分页查询
        // 由于原需求中没有分页参数，这里返回所有数据
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<HomeBannerBackground> allBanners = homeBannerBackgroundService.getAllHomeBanners();
        PageInfo<HomeBannerBackground> pageInfo = new PageInfo<>(allBanners);
        return ResultVOUtil.success(pageInfo);
    }

    @ApiOperation(value = "获取所有上架的首页背景图")
    @GetMapping("/getOnline")
    public ResultVO<HomeBannerBackground> getOnlineHomeBanner() {
        try {
            HomeBannerBackground result = homeBannerBackgroundService.getOnlineHomeBanner();
            return ResultVOUtil.success(result);
        } catch (Exception e) {
            return ResultVOUtil.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "根据ID获取首页背景图详情")
    @GetMapping("/{id}")
    public ResultVO<HomeBannerBackground> getHomeBannerById(
            @ApiParam(value = "背景图ID", required = true, example = "1")
            @PathVariable Long id) {
        try {
            HomeBannerBackground result = homeBannerBackgroundService.getHomeBannerById(id);
            return ResultVOUtil.success(result);
        } catch (Exception e) {
            return ResultVOUtil.fail(e.getMessage());
        }
    }
    // 为所有POST接口添加OPTIONS映射 (复制上述模式)
    @ApiOperation(value = "预检请求处理", hidden = true)
    @RequestMapping(
            value = "/update",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity<?> handleUpdatePreflight() {
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "更新首页背景图信息")
    @PostMapping("/update")
    public ResultVO<Void> updateHomeBanner(
            @ApiParam(value = "背景图请求参数", required = true)
            @Valid @RequestBody HomeBannerRequest request) {
        try {
            // 这里需要ID字段，需要调整HomeBannerRequest包含ID
            homeBannerBackgroundService.updateHomeBanner(request.getId(), request);
            return ResultVOUtil.success();
        } catch (Exception e) {
            return ResultVOUtil.fail(e.getMessage());
        }
    }
    // 为所有POST接口添加OPTIONS映射 (复制上述模式)
    @ApiOperation(value = "预检请求处理", hidden = true)
    @RequestMapping(
            value = "/updateStatus",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity<?> handleUpdateStatusPreflight() {
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "更新首页背景图状态（上架/下架）")
    @PostMapping("/updateStatus")
    public ResultVO<Void> updateHomeBannerStatus(
            @ApiParam(value = "状态更新请求参数", required = true)
            @Valid @RequestBody HomeBannerStatusRequest request) {

            return homeBannerBackgroundService.updateHomeBannerStatus(request);
    }

    @ApiOperation(value = "删除首页背景图配置")
    @GetMapping("/delete")
    public ResultVO<Void> deleteHomeBanner(
            @ApiParam(value = "背景图ID", required = true, example = "1")
            @RequestParam Long id) {
        try {
            homeBannerBackgroundService.deleteHomeBanner(id);
            return ResultVOUtil.success();
        } catch (Exception e) {
            return ResultVOUtil.fail(e.getMessage());
        }
    }
}
