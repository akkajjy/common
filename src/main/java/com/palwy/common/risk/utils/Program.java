package com.palwy.common.risk.utils;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class Program {

    public static void main(String[] args) throws Exception {

        receive();
    }

    /**
     * 产生优鉴联登链接
     */
    private static void udataUrl() {
        // 需要加密的明文
        String appSecret = "hZxKK2QmZ7QFtZfUh8NdVX8xv7AyL9FQAWV";
        String appToken = "u9UAGCSd";
        String appId = "1000126";

        String udtimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = "xxxxxx";

        String uuid = "1000126";
        String channelUrl = "https://m-stga.gzzdcredit.com?udchl=UvPbBaPD&ut=3";

        Map<String, String> data = new HashMap<String, String>();
        data.put("uuid", uuid);
        String msg = new Gson().toJson(data);

        UdataCrypt pc = new UdataCrypt(appToken, appSecret, appId);
        String encryptMsg = pc.encryptMsg(msg, udtimestamp, nonce);
        System.out.println("加密后: " + encryptMsg);

        Map<String, String> extract = UdataCrypt.extract(encryptMsg);
        System.out.println(extract);

        String result2 = pc.decryptMsg(extract.get("msgsignature"), udtimestamp, nonce, extract.get("encrypt"));
        System.out.println("解密后明文: " + result2);

        String findlUrl =
            channelUrl + "&appId=" + appId + "&encrypt=" + extract.get("encrypt") + "&msgsignature=" + extract.get(
                "msgsignature") + "&udtimestamp=" + udtimestamp + "&nonce=" + nonce;
        System.out.println(findlUrl);
    }

    /**
     * 合作方调用优鉴接口
     */
    private static void send(){
        // 准备业务数据
        Map<String, Object> data = new HashMap<>();
        data.put("uuid", "123456");
        data.put("mobile", "12345678922");
        data.put("freeInd", "0");
        String msg = new Gson().toJson(data);


        // 业务数据加密加签
        String appSecret = "hZxKK2QmZ7QFtZfUh8NdVX8xv7AyL9FQAWV";
        String appToken = "u9UAGCSd";
        String appId = "1000126";
        String udtimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = "xxxxxx";

        UdataCrypt pc = new UdataCrypt(appToken, appSecret, appId);
        String encryptMsg = pc.encryptMsg(msg, udtimestamp, nonce);
        System.out.println("加密后: " + encryptMsg);

        Map<String, String> extract = UdataCrypt.extract(encryptMsg);

        // 组建请求 并发送
        Map<String, String> params = new HashMap<>(extract);
        params.put("appId", appId);

        String requestBody = new Gson().toJson(data);


        // 接收优鉴返回
        String responseBody = "{\"code\":\"0\",\"msg\":\"成功\",\"encrypt\":\"OLmXiYBWcusSakcHXP6m2a18eBNuzDxsOtMevXUb_RW1Lqdxw2aCVepovDRLDi131BQH481RThC4k-mCH8xyF6UVr9BVQbqCALMMnxlKmLXUwi6HUIbrq4nI0BmDlX-1\",\"udtimestamp\":\"1741416214\",\"nonce\":\"xxxxxx\",\"msgsignature\":\"95755b2595c4590689ced4b2844ecef45cbba99a\"}";

        // 解密返回内容
        Map<String, String> response = UdataCrypt.extract(responseBody);
        String result2 = pc.decryptMsg(response.get("msgsignature"), response.get("udtimestamp"), response.get("nonce"), response.get("encrypt"));
        System.out.println("解密后明文: " + result2);
    }


    /**
     * 合作方接收优鉴请求
     */
    private static void receive(){

        // 优鉴请求体
        String requestBody = "{\"encrypt\":\"dYWkA8gcAO3cWZfAkiVRa5IT98qKK0IchXBNHDGvB_MMjQLEQJcATYYm80pIxwy29q6VgeXzAWT4OENP5jLbYEd2YRGxOh28ZxkTbrVopg7Ui-LcJftHAj1tpqL2ilzEFHxBc6EfmLOKDOsoX5fws7aogVQD1kPWsNBWfRPOfVysZjatRpu6rwJol6T1KufgYN9sHfVnBbuWDtjngv086izy4j6mcPnZdld6Gn97ZH_AOZIU4KECmKUhORObdhQh\",\"udtimestamp\":\"1741416663\",\"nonce\":\"xxxxxx\",\"msgsignature\":\"2840e291c4a6ecb74973d00d07ab1a94c8d06139\"}";

        // 解密请求内容
        String appSecret = "hZxKK2QmZ7QFtZfUh8NdVX8xv7AyL9FQAWV";
        String appToken = "u9UAGCSd";
        String appId = "1000126";
        UdataCrypt pc = new UdataCrypt(appToken, appSecret, appId);
        Map<String, String> req = UdataCrypt.extract(requestBody);
        String decryptMsg = pc.decryptMsg(req.get("msgsignature"), req.get("udtimestamp"), req.get("nonce"), req.get("encrypt"));
        System.out.println("解密后明文: " + decryptMsg);

        // 处理业务数据业务数据




        // 组建响应数据返回
        Map<String, String> params = new HashMap<>();
        params.put("returnSomething", "returnSomething");

        String data = new Gson().toJson(params);
        String udtimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = "xxxxxx";
        String encryptMsg = pc.encryptMsg(data, udtimestamp, nonce);
        Map<String, String> response = new HashMap<>(UdataCrypt.extract(encryptMsg));
        response.put("code", "0");
        response.put("msg", "成功");
        // 返回给优鉴
        String responseBody = new Gson().toJson(response);

        // 如果无需返回data内容 直接返回 {"code":"0","msg":"成功"} 即可

    }



}
