package com.palwy.common.risk.controller;

import com.palwy.common.risk.service.YjRiskService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "优鉴风控联合登录接口") // 添加类级描述
public class UnionLoginController {
    @Autowired
    private YjRiskService yjRiskService;

    @ApiOperation(value = "生成联合登录URL",
            notes = "根据用户标识和手机号生成优鉴联合登录链接")
    @GetMapping("/yj/unionLogin")
    public ResultVO<String> unionLogin(
            @ApiParam(value = "用户唯一标识（可选）", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @RequestParam String uuid,

            @ApiParam(value = "用户手机号", example = "13800138000")
            @RequestParam(required = true) String mobile) {

        log.info("生成优鉴联合登录URL并请求参数，{}，{}",uuid,mobile);
        String redirectUrl = yjRiskService.generateUnionLoginUrl(uuid, mobile);
        log.info("请求结果，{}",redirectUrl);
        return ResultVOUtil.success(redirectUrl);
    }
}