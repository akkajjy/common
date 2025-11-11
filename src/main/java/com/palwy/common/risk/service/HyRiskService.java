package com.palwy.common.risk.service;

import com.alibaba.fastjson.JSON;
import com.palwy.common.risk.domain.req.PayReq;
import com.palwy.common.risk.domain.req.UnionLoginReq;
import com.palwy.common.risk.utils.hy.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class HyRiskService {

    @Value("${hy.loginUrl}")
    private String loginUrl;

    @Value("${hy.payUrl}")
    private String payUrl;

    @Value("${hy.APPID}")
    private String APPID;

    @Value("${hy.TOKENID}")
    private String TOKENID;

    @Value("${hy.productUri}")
    private String productUri;

    // 联合登录参数加密和签名方法
    public Map<String, String> encryptAndSignUnionLogin(UnionLoginReq req) throws Exception {
        //生成AES密钥
        String secretKey = AESKeyGenerator.generator(TOKENID);
        if (secretKey == null) {
            throw new RuntimeException("AES密钥生成失败");
        }

        //转换请求参数为JSON并加密
        String jsonReq = JSON.toJSONString(req);
        String encryptedParam = AESUtil2.encrypt(secretKey, jsonReq);
        if (encryptedParam == null) {
            throw new RuntimeException("参数加密失败");
        }

        //准备签名参数
        Map<String, String> signParams = new HashMap<>();
        signParams.put("appId", APPID);

        // 截取前100个字符用于签名
        String signParamValue = encryptedParam.length() > SignConstant.REQUEST_PARAM_SIGN_LENGTH ?
                encryptedParam.substring(0, SignConstant.REQUEST_PARAM_SIGN_LENGTH) :
                encryptedParam;
        signParams.put("requestParam", signParamValue);

        //生成签名
        String sortedParams = ParamUtil.sortMap(signParams, "&");
        String signature = HMACSHA1.encrypt(sortedParams, secretKey);
        if (signature == null) {
            throw new RuntimeException("签名生成失败");
        }

        //构建最终请求参数
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("appId", APPID);
        requestParams.put("requestParam", encryptedParam);
        requestParams.put("signature", signature);

        // 6. 添加其他可选参数
        if (StringUtils.isNotBlank(req.getCusDefineReturnUrl())) {
            requestParams.put("cusDefineReturnUrl", req.getCusDefineReturnUrl());
        }
        if (StringUtils.isNotBlank(req.getUserDefine())) {
            requestParams.put("userDefine", req.getUserDefine());
        }

        return requestParams;
    }

    // 生成完整的联登URL
    public String generateUnionLoginUrl(UnionLoginReq req) throws Exception {
        Map<String, String> params = encryptAndSignUnionLogin(req);

        StringBuilder urlBuilder = new StringBuilder(loginUrl)
                .append("/xygj/")
                .append(productUri)
                .append("/")
                .append(Enums.RequestAddress.UNION_LOGIN_INDEX.getValue())
                .append("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.append(entry.getKey())
                    .append("=")
                    .append(java.net.URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()))
                    .append("&");
        }

        // 移除最后一个多余的&
        return urlBuilder.substring(0, urlBuilder.length() - 1);
    }


    /**
     * 处理商户APP跳转H5支付
     * @param payReq 支付请求参数
     * @param signature 请求签名（从URL获取）
     * @return 支付页面跳转URL
     */
    /*public String processH5Payment(PayReq payReq, String signature) throws Exception {
        // 参数校验
        validateParams(payReq, signature);

        // 构造加密的业务参数（实际项目中需实现加密逻辑）
        String encryptedParam = encryptBusinessParams(payReq);

        // 生成支付页面URL
        return buildPaymentUrl(payReq.getAppId(), encryptedParam, signature);
    }

    private void validateParams(PayReq payReq, String signature) {
        if (payReq.getAppId() == null || payReq.getAppId().isEmpty()) {
            throw new IllegalArgumentException("appId不能为空");
        }
        if (payReq.getThirdUserId() == null || payReq.getThirdUserId().isEmpty()) {
            throw new IllegalArgumentException("thirdUserId不能为空");
        }
        if (payReq.getPayType() == null || payReq.getPayType().isEmpty()) {
            throw new IllegalArgumentException("payType不能为空");
        }
        if (signature == null || signature.isEmpty()) {
            throw new IllegalArgumentException("签名不能为空");
        }

        // 可选参数校验
        if (payReq.getProductPrice() != null) {
            try {
                Double.parseDouble(payReq.getProductPrice());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("产品价格格式错误");
            }
        }
    }

    private String encryptBusinessParams(PayReq payReq) throws Exception {
        //生成AES密钥（与联合登录逻辑一致）
        String secretKey = AESKeyGenerator.generator(TOKENID);
        if (secretKey == null) {
            throw new RuntimeException("AES密钥生成失败");
        }

        //将支付参数对象转换为JSON字符串
        String jsonReq = JSON.toJSONString(payReq);

        //使用AES加密JSON数据
        String encryptedParam = AESUtil2.encrypt(secretKey, jsonReq);
        if (encryptedParam == null) {
            throw new RuntimeException("支付参数加密失败");
        }

        // 4. 返回Base64编码的加密结果
        return encryptedParam;
    }

    private String buildPaymentUrl(String appId, String encryptedParam, String signature) {
        return new StringBuilder()
                .append("https://your-domain.com/creditInsight/payIndex")
                .append("?requestParam=").append(encryptedParam)
                .append("&signature=").append(signature)
                .append("&appId=").append(appId)
                .toString();
    }*/
}