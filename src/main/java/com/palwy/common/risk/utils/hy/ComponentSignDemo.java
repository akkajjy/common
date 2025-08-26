package com.palwy.common.risk.utils.hy;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.*;

/**
 * 组件模式签名demo
 *
 */

public class ComponentSignDemo {

    // ------------------------------------天创持有------------------------------------------------------------------------------
    // 天创持有，合作方公钥（合作方颁发给天创）
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJLq/lubm+6naw7HDN25E+PUpVM2Fem5FDMWndu//wZtzRTnoqlFxde8l+ZdA6SlgANo1NbjUbRf8KxsVwlzOEuqwejQaD80cVL0bDmhCYGUza/X6745lGjrQF//hnEJs1XcXfyqHMGaIF3WHVNU1zts04bWaOwtbsTwSLPVnaBQIDAQAB";
    // 天创私钥
    public static final String tcPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANUV0Muqvcy+k9UiwGhSTfLTEAwGCQBFDyc9pIN8FHtMutNQaZbThnuXXgrvn3rAeoGLEX2q657pqM7vuDnD/b1yrFVQK1LtKK2xLt1HhMunPoZYxdd8IRCCiFEXybK1bwBiJEoj6gUsA0XpZtoXGnIVDx0FOjWwaPLFzNKPmQP7AgMBAAECgYA4PgPYXf6u1faLIRsL7f1pBFCN++HQUmoHf3u2wL9fNAdz0xurlbU/VdqapFjBu6AaJSy+sKh0T1QhGqDTjmmgZiJ786W/3vSBOUI7WMpyu7skBFsY7ez8YzrJNaPFqq6ulp6mmyRQJZRLQ8D2xSH2NBBrwjH1MeLToJA2w+iFMQJBAOqXj3evWVwKxpdDStjkAFG7PXtFCJiQ8rMAP5ra8kjIsoH/gHSMHifFfG5mFpUanCnAKkoNQF+Oj9ubZZ0D6C8CQQDoh9Jz6XfLmTVsVND9JjuR7QPoUeAC8F36SM8mm6YD+62MmPl9eq7W8twoF+b3OrerN77ORWLklJHtPn0TvmH1AkEAnsnHynNeQPbqRSi/WN+7d5cNnoqzrtEt9guNAfyBUtLGQUf4YusU3kbGWtOvJW3Fll0EQuuFTyjoJvWgag9i8QJAYQwOsYHHFZojycq8TmsegZDCCLvjGlbnYgXxLtPXPeJUVFGkDtKdFejWKYWQR66h6TuizsP2uh+np7Rkz1Ct4QJBAKBt3/dED1m04aw0PCAvMy+oLOBUuJ9dDi9iVM3ijP0Jh7rM8MYauijEeB2SL/XhZ/eJh4AGVwD145G68BfOe5E=";
    // ------------------------------------天创持有------------------------------------------------------------------------------

    // ------------------------------------合作方持有------------------------------------------------------------------------------
    // 合作方持有，天创的公钥（天创颁发给合作方）
    public static final String tcPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVFdDLqr3MvpPVIsBoUk3y0xAMBgkARQ8nPaSDfBR7TLrTUGmW04Z7l14K7596wHqBixF9quue6ajO77g5w/29cqxVUCtS7SitsS7dR4TLpz6GWMXXfCEQgohRF8mytW8AYiRKI+oFLANF6WbaFxpyFQ8dBTo1sGjyxczSj5kD+wIDAQAB";
    // 合作方自持私钥
    public static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIkur+W5ub7qdrDscM3bkT49SlUzYV6bkUMxad27//Bm3NFOeiqUXF17yX5l0DpKWAA2jU1uNRtF/wrGxXCXM4S6rB6NBoPzRxUvRsOaEJgZTNr9frvjmUaOtAX/+GcQmzVdxd/KocwZogXdYdU1TXO2zThtZo7C1uxPBIs9WdoFAgMBAAECgYAe4HpI67/KH/3tHprZSuVrK+JyQOlWoQ9WH2YjuXAHDTAAkHhg4xnTFMyqfYIbzKX4Dk9JsVeUtvGL3GeOSwoWLY2q1vrOfercGjpbThCxycBxbYAKcuUfJvjalGR/47iDFUV9QNfJ3Ejfu2v4UKO7YuALEx0dcmNRtCHjTiCOgQJBANqQlDJO2qRiutpjozY9fXXX5uzC8asMV2GHutPh8whUL8GLtk4DDB5Wt+YProfo3Sf2IbF30pPB69fnTEZZhWECQQCgrbrm2IpaIbx9EqbmOm/TQ+NsUuYPHWT6meWOTAFQRnzBIX3pc3jpobI8mzOqWuLoihqebRpKPw0BQ+Ms33MlAkEAoRwT8aWIq8YPQG5T3+ahtZ0pnvrwU4qsWrtHb2pN0KTl8wB+qLC8XRzFQDPtDkgLxao8GepecgZvR946rlzM4QJAcE29KogC+0hFI+4bdSM28aSrmhS+86WJ5JYYGkMJUABURfCQvtCUnEcF+tJHZDQuEHYmcMC09VvoTRAUAlMTeQJAG0rr/uT51nHaS6Ime++p7kf33ZQBWWP7ORoTOGDja7XHZg7K5RWxoqfxL0AuIPANCYStVI7Up2ZLU7KkQJ7bLQ==";
    // ------------------------------------合作方持有------------------------------------------------------------------------------

    public static final String PARTNER_CODE = "partnerCodeExample";

    public static final String url = "";

    private final static Random random = new Random();

    public static void main(String[] args) throws Exception {

        // 模拟合作方请求天创
        String requestBody = mockRequest();
        System.out.println("请求报文：" + requestBody);

        // 模拟生成接口响应内容
        JSONObject responseJson = genResponse();

        // 验签
        boolean verifySign = verifySign(responseJson);
        System.out.println("验签结果：" + verifySign);

        // 解密
        String decryptData = decryptData(responseJson.getString("data"), responseJson.getString("randomKey"), privateKey);
        System.out.println("解密后响应：" + decryptData);
    }

    private static String mockRequest() throws Exception {
        // 生成对称密钥
        String randomKey = getAESRandomKey();

        // 加密-生成AES对称密钥
        String encryptRandomKey = encryptByRSA(randomKey, tcPublicKey);

        //业务参数
        JSONObject bizContent = new JSONObject();
        // 客户用户标识id
        bizContent.put("uuid", "123456");
        // 资源位唯一标识(天创提供)
        bizContent.put("appId", "80c29faf70d811efa902b2375916c98b");
        // 跳转方式，1：收银联登跳转；2：有效订单跳转
        bizContent.put("routeType", "1");
        // 合作方订单号
        bizContent.put("tradeNo", "pn111111");
        // 支付金额，单位元
        bizContent.put("payAmount", "29.9");
        // ⽀付完成时间，格式：yyyy-MM-dd HH:mm:ss
        bizContent.put("paySuccessTime", "2024-10-15 00:00:00");
        // 支付方式，银⾏卡：BANK
        bizContent.put("paymentMethodType", "BANK");
        // 支付渠道，7：通联；8：宝付
        bizContent.put("payType", "8");
        // 是否自动续费，1：是；0：否
        bizContent.put("autoRenew", "1");
        // 三方流水号
        bizContent.put("capNum", "tn222222");
        // 支付通道商户号
        bizContent.put("merchantNo", "210225");
        // 合作方拓展字段（对象）
        bizContent.put("ext", "预留拓展字段");

        // AES加密data
        String encryptBizContent = encryptByAES(bizContent.toJSONString(), randomKey);

        // 时间戳
        String timestamp = String.valueOf(Instant.now().toEpochMilli());

        // 待签名数据-发送请求
        String signData = buildRequestSignData(encryptBizContent, encryptRandomKey, timestamp);

        // 签名
        String sign = signByPrivateKey(signData, privateKey);

        // 组建请求body
        String requestBody = buildRequsetBody(sign, encryptBizContent, encryptRandomKey, timestamp);
        return requestBody;
    }

    private static JSONObject genResponse() throws Exception {
        // 生成对称密钥
        String randomKey = getAESRandomKey();

        // 加密-生成AES对称密钥
        String encryptRandomKey = encryptByRSA(randomKey, publicKey);

        // 业务响应参数
        JSONObject data = new JSONObject();
        // 1代表当前存在有效订单，0代表无
        data.put("orderFlag", "1");
        // 合作方订单号
        data.put("tradeNo", "pn111111");
        // 天创订单号，orderFlag为1的情况下存在
        data.put("orderNo", "TC123456");

        // AES加密data
        String encryptBizContent = encryptByAES(data.toJSONString(), randomKey);

        // 待签名数据-发送请求
        String signData = buildResponseSignData(encryptBizContent, encryptRandomKey);
        // 签名
        String sign = signByPrivateKey(signData, tcPrivateKey);

        // 组建响应body
        JSONObject body = new JSONObject();
        body.put("code", "0");
        body.put("signature", sign);
        body.put("randomKey", encryptRandomKey);
        body.put("data", encryptBizContent);

        return body;
    }

    public static String decryptData(String data, String encryptedRandomKey, String privateKey) {
        try {
            String randomKey = decryptByRSA(encryptedRandomKey, privateKey);
            return decryptByAES(data, randomKey);
        } catch (Exception e) {
            System.out.println("解密异常");
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptByAES(String inputStr, String password) throws Exception {
        byte[] byteData = Base64.getDecoder().decode(inputStr);
        byte[] bytePassword = password.getBytes();
        return new String(decryptByAES(byteData, bytePassword));
    }

    public static byte[] decryptByAES(byte[] data, byte[] pwd) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(pwd, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] ret = cipher.doFinal(data);
        return ret;
    }

    public static String decryptByRSA(String inputStr, String privateKey) throws Exception {
        PrivateKey key = getPrivateKeyFromString(privateKey);
        return new String(decryptByRSA(Base64.getDecoder().decode(inputStr), key));
    }

    public static byte[] decryptByRSA(byte[] input, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] output = cipher.doFinal(input);
        return output;
    }


    public static boolean verifySign(JSONObject responseJson) {
        // 解析返回结果
        if (!"0".equals(responseJson.getString("code"))) {
            System.out.println("响应码错误");
            return false;
        }
        // 依据返回的结果，构建待验证签名数据
        String signData = buildResponseSignData(responseJson.getString("data"), responseJson.getString("randomKey"));
        try {
            return verifySignByPublicKey(signData, tcPublicKey, responseJson.getString("signature"));
        } catch (Exception e) {
            System.out.println("验签失败");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifySignByPublicKey(String inputStr, String publicKey,
                                                String base64SignStr) throws Exception {
        return verifyByPublicKey(inputStr.getBytes(), getPublicKeyFromString(publicKey), Base64.getDecoder().decode(base64SignStr));
    }

    public static boolean verifyByPublicKey(byte[] data, PublicKey publicKey,
                                            byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        boolean ret = sig.verify(signature);
        return ret;
    }

    public static String buildResponseSignData(String data, String randomKey) {
        Map<String, String> signDataMap = new HashMap<>();
        signDataMap.put("randomKey", randomKey);
        signDataMap.put("data", data);
        return createLinkString(signDataMap);
    }

    public static String getAESRandomKey() {
        long longValue = random.nextLong();
        return String.format("%016x", longValue);
    }

    public static String encryptByRSA(String inputStr, String publicKey) throws Exception {
        PublicKey key = getPublicKeyFromString(publicKey);
        return Base64.getEncoder().encodeToString(encryptByRSA(inputStr.getBytes(), key));
    }

    public static PublicKey getPublicKeyFromString(String base64String)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bt = Base64.getDecoder().decode(base64String);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bt);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    public static byte[] encryptByRSA(byte[] input, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] output = cipher.doFinal(input);
        return output;
    }

    public static String encryptByAES(String inputStr, String password) throws Exception {
        byte[] byteData = inputStr.getBytes();
        byte[] bytePassword = password.getBytes();
        return Base64.getEncoder().encodeToString(encryptByAES(byteData, bytePassword));
    }

    public static byte[] encryptByAES(byte[] data, byte[] pwd) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(pwd, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] ret = cipher.doFinal(data);
        return ret;
    }

    public static String signByPrivateKey(String inputStr, String privateKey) throws Exception {
        return Base64.getEncoder().encodeToString(signByPrivateKey(inputStr.getBytes(), getPrivateKeyFromString(privateKey)));
    }

    public static PrivateKey getPrivateKeyFromString(String base64String)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bt = Base64.getDecoder().decode(base64String);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bt);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }

    public static byte[] signByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        byte[] ret = sig.sign();
        return ret;
    }

    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer linkStr = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (value == null) {
                continue;
            }
            if (i == keys.size() - 1) {
                linkStr.append(key).append("=").append(value);
            } else {
                linkStr.append(key).append("=").append(value).append("&");
            }
        }

        return linkStr.toString();
    }

    public static String buildRequestSignData(String encryptBizContent, String encryptRandomKey, String timestamp) {
        Map<String, String> signMap = new HashMap<>();
        signMap.put("bizContent", encryptBizContent);
        signMap.put("randomKey", encryptRandomKey);
        signMap.put("timestamp", timestamp);
        signMap.put("version", "v1.0");
        return createLinkString(signMap);
    }

    public static String buildRequsetBody(String sign, String encryptBizContent, String encryptRandomKey, String timestamp) {
        JSONObject body = new JSONObject();
        body.put("bizContent", encryptBizContent);
        body.put("signature", sign);
        body.put("randomKey", encryptRandomKey);
        body.put("timestamp", timestamp);
        body.put("version", "v1.0");
        // 不参与签名的合作方标识
        body.put("partnerCode", PARTNER_CODE);
        return body.toJSONString();
    }

    public static Map<String, String> initHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
