package com.palwy.common.risk.utils.hy;

import com.alibaba.fastjson.JSON;
import com.palwy.common.risk.utils.hy.utils.*;
import org.apache.commons.lang.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ChannelSignTest {
    public static void main(String[] args) {
        try {
            String appId = "41dd7548510c4ab7083c9df493d02f5f";
            String tokenId = "2a27093b2920a703645e0da3ffc0a8c4";
            String productUri = "creditInsight";
            //第三方标识,如果商户有需要回调,必传。否则回调会失败.
            String thirdUserId = "用户标识";
            String productVersion = "";
            Enums.PayType payType = null;
            String userDefine = "用户自定义内容,没有可以空";
            //接口地址枚举
            Enums.RequestAddress requestAddress = Enums.RequestAddress.UNION_LOGIN_INDEX;

            generateUrl(true, appId, tokenId, productUri, thirdUserId
                    , productVersion, requestAddress, payType, userDefine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateUrl(boolean isTest, String appId,
                                     String tokenId, String productUri,
                                     String thirdUserId,
                                     String productVersion, Enums.RequestAddress requestAddress,
                                     Enums.PayType payType, String userDefine) throws Exception {
        String url = "https://pcrh.tcredit.com/xygj/creditInsight/" + requestAddress.getValue();
        if (isTest) {
            url = "https://xyh5test.tcredit.com/xygj/creditInsight/" + requestAddress.getValue();
        }
        //1、放置业务参数
        HashMap<String, String> bizzParam = new HashMap<>();
        bizzParam.put("appId", appId);
        bizzParam.put("productUri", productUri);
        if (StringUtils.isNotBlank(userDefine)) {
            bizzParam.put("userDefine", userDefine);
        }
        //Todo thirdUserId是第三方用户标识,渠道商需要使用回调接口时，必传。
        if (StringUtils.isNotBlank(thirdUserId)) {
            bizzParam.put("thirdUserId", thirdUserId);
        }

        if (requestAddress == Enums.RequestAddress.PAY_INDEX) {
            if (payType == null) {
                throw new NullPointerException("渠道商未确定支付方式");
            }
            bizzParam.put("payType", payType.getValue());
        }

        String keyIn32 = AESKeyGenerator.generator(tokenId);
        String encode = AESUtil2.encrypt(keyIn32, JSON.toJSONString(bizzParam));
        System.out.println("参数加密：" + encode + ":结束");
        //2、生成验签signature
        HashMap<String, String> signParam = new HashMap<>();
        signParam.put("appId", appId);
        String subEncode = encode;
        if (StringUtils.isNotBlank(subEncode) && subEncode.length() > SignConstant.REQUEST_PARAM_SIGN_LENGTH) {
            subEncode = subEncode.substring(0, SignConstant.REQUEST_PARAM_SIGN_LENGTH);
        }
        signParam.put("requestParam", subEncode);

        String paramStr = ParamUtil.sortMap(signParam, "&");
        String signature = HMACSHA1.encrypt(paramStr, keyIn32);
        System.out.println("签名值:" + signature);

        //3、组装请求参数
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("requestParam", encode);
        requestParam.put("appId", appId);
        if (StringUtils.isNotBlank(productVersion)) {
            requestParam.put("productVersion", productVersion);
        }
        requestParam.put("signature", signature);
        //生成带参数url
        StringBuilder urlBuilder = new StringBuilder(url + "?");
        for (String key : requestParam.keySet()) {
            urlBuilder.append(key).append("=").append(URLEncoder.encode(requestParam.get(key), StandardCharsets.UTF_8.name())).append("&");
        }
        String substring = urlBuilder.substring(0, urlBuilder.length() - 1);
        System.out.println("app地址：" + substring);
        return substring;
    }
}

