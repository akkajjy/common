package com.palwy.common.risk.controller;

import com.palwy.common.risk.service.YjRiskService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class UnionLoginController {
    @Autowired
    private YjRiskService yjRiskService;

    /**
     * 生成优鉴联合登录URL并重定向
     */
    @GetMapping("/yj/unionLogin")
    public ResultVO<String> unionLogin(@RequestParam String uuid,
                               @RequestParam(required = false) String mobile) {
        log.info("生成优鉴联合登录URL并请求参数，{}，{}",uuid,mobile);
        String redirectUrl = yjRiskService.generateUnionLoginUrl(uuid, mobile);
        log.info("请求结果，{}",redirectUrl);
        return ResultVOUtil.success(redirectUrl);
    }

    /**
     * 处理优鉴的回调通知
     */
    @GetMapping("/yj/callback")
    public ResultVO<String> handleCallback(@RequestParam String encrypt,
                                 @RequestParam String msgsignature, // 注意参数名与图片一致
                                 @RequestParam String udtimestamp,
                                 @RequestParam String nonce) {
        // 解密优鉴回调数据
        log.info("优鉴的回调通知请求参数，{}，{}，{}，{}",encrypt,msgsignature,udtimestamp,nonce);
        String decryptedData = yjRiskService.decryptCallback(msgsignature, udtimestamp, nonce, encrypt);
        log.info("收到优鉴回调数据: {}" , decryptedData);

        //返回成功响应
        return ResultVOUtil.success(decryptedData);
    }
}