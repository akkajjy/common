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
            @ApiParam(value = "用户唯一标识", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @RequestParam String uuid,

            @ApiParam(value = "用户手机号（可选）", example = "13800138000")
            @RequestParam(required = false) String mobile) {

        log.info("生成优鉴联合登录URL并请求参数，{}，{}",uuid,mobile);
        String redirectUrl = yjRiskService.generateUnionLoginUrl(uuid, mobile);
        log.info("请求结果，{}",redirectUrl);
        return ResultVOUtil.success(redirectUrl);
    }

    @ApiOperation(value = "处理回调通知",
            notes = "接收优鉴回调数据并解密返回结果")
    @GetMapping("/yj/callback")
    public ResultVO<String> handleCallback(
            @ApiParam(value = "加密数据", required = true, example = "xYzAbCd...")
            @RequestParam String encrypt,

            @ApiParam(value = "消息签名", required = true, example = "a1b2c3d4e5")
            @RequestParam String msgsignature,

            @ApiParam(value = "时间戳", required = true, example = "1672531200000")
            @RequestParam String udtimestamp,

            @ApiParam(value = "随机数", required = true, example = "5e8g9f0a")
            @RequestParam String nonce) {

        log.info("优鉴的回调通知请求参数，{}，{}，{}，{}",encrypt,msgsignature,udtimestamp,nonce);
        String decryptedData = yjRiskService.decryptCallback(msgsignature, udtimestamp, nonce, encrypt);
        log.info("收到优鉴回调数据: {}", decryptedData);
        return ResultVOUtil.success(decryptedData);
    }
}