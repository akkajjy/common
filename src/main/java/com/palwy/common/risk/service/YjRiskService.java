package com.palwy.common.risk.service;

import com.palwy.common.risk.utils.UdataCrypt;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class YjRiskService {

    // 从nacos注入配置
    @Value("${yj.appid}")
    private String appId;

    @Value("${yj.appToken}")
    private String appToken;

    @Value("${yj.appSecret}")
    private String appSecret;

    @Value("${yj.baseUrl}") // 基础链接需要配置
    private String baseUrl;

    /**
     * 生成优鉴联合登录URL
     * @param uuid 用户唯一标识 (必填)
     * @param mobile 用户手机号 (可选)
     * @return 完整的联合登录URL
     */
    public String generateUnionLoginUrl(String uuid, String mobile) {
        // 准备业务数据
        Map<String, String> data = new HashMap<>();
        data.put("uuid", uuid);
        if (mobile != null && !mobile.isEmpty()) {
            data.put("mobile", mobile);
        }
        String jsonData = new Gson().toJson(data);

        // 生成加密参数
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000); // 10位时间戳
        String nonce = generateRandomString(8); // 8位随机字符串

        // 使用优鉴加密工具
        UdataCrypt crypt = new UdataCrypt(appToken, appSecret, appId);
        String encryptedMsg = crypt.encryptMsg(jsonData, timestamp, nonce);

        //  解析加密结果
        Map<String, String> result = UdataCrypt.extract(encryptedMsg);
        String encryptParam = result.get("encrypt");
        String signature = result.get("msgsignature");

        // 构建完整URL
        return buildFinalUrl(timestamp, nonce, encryptParam, signature);
    }

    /**
     * 构建最终URL
     */
    private String buildFinalUrl(String timestamp,
                                 String nonce,
                                 String encryptParam,
                                 String signature) {
        // 确保基础URL格式正确
        StringBuilder url = new StringBuilder(baseUrl);
        url.append(baseUrl.contains("?") ? "&" : "?");

        // 拼接必要参数
        url.append("appId=").append(appId)
                .append("&encrypt=").append(encryptParam)
                .append("&msgsignature=").append(signature)
                .append("&udtimestamp=").append(timestamp)
                .append("&nonce=").append(nonce);

        return url.toString();
    }

    /**
     * 生成随机字符串
     * @param length 需要生成的字符串长度
     */
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 解密优鉴回调数据
     *
     * @param msgSignature 消息签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param encrypt 加密数据
     * @return 解密后的原始数据
     */
    public String decryptCallback(String msgSignature, String timestamp, String nonce, String encrypt) {
        UdataCrypt crypt = new UdataCrypt(appToken, appSecret, appId);
        return crypt.decryptMsg(msgSignature, timestamp, nonce, encrypt);
    }
}