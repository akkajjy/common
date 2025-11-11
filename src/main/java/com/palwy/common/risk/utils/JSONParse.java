package com.palwy.common.risk.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class JSONParse {
    /**
     * 提取出数据包中的加密消息
     * @param jsonText 待提取的json字符串
     * @return 提取出的加密消息字符串
     */
    public static Map<String, String> extract(String jsonText) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonText, new com.google.gson.reflect.TypeToken<Map<String, String>>(){}.getType());
        } catch (Exception e) {
            // 请自行处理异常情况
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成json
     * @param encrypt 加密后的消息密文
     * @param signature 安全签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     */
    public static String generate(String encrypt, String signature, String timestamp, String nonce) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("encrypt", encrypt);
        map.put("msgsignature", signature);
        map.put("udtimestamp", timestamp);
        map.put("nonce", nonce);


        return gson.toJson(map);

    }
}
