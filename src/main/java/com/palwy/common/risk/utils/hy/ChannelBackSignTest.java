package com.palwy.common.risk.utils.hy;

import com.alibaba.fastjson.JSON;
import com.palwy.common.risk.utils.hy.utils.*;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @description: 用户提额签名
 * @author: juyang.liang
 * @since: 2023-07-14 16:07
 * @updatedUser:
 * @updatedDate:
 * @updatedRemark:
 * @version:
 */
public class ChannelBackSignTest {
    public static void main(String[] args) {
        String tokenId = "03f44cc3d1d311ed8fc2000c29b682ff";
        String orderNo = "TC202307101650574120000003";
        String thirdUserId = "48b3cc998f7712d6f35360e277a54a99d8fb6d1c00c9fe28574f01033068613c";
        // 业务参数
        HashMap<String, String> bizzParam = new HashMap<>();
        bizzParam.put("orderNo", orderNo);
        bizzParam.put("thirdUserId", thirdUserId);
        bizzParam.put("increaseLimitStatus", "2");

        String secretKey = AESKeyGenerator.generator(tokenId);
        String encryptRequestParam = AESUtil2.encrypt(secretKey, JSON.toJSONString(bizzParam));
        // 生成签名
        HashMap<String, String> signParam = new HashMap<>();
        signParam.put("orderNo", orderNo);
        String subEncode = encryptRequestParam;
        if (StringUtils.isNotBlank(subEncode) && subEncode.length() > SignConstant.REQUEST_PARAM_SIGN_LENGTH) {
            subEncode = subEncode.substring(0, SignConstant.REQUEST_PARAM_SIGN_LENGTH);
        }
        System.out.println("requestParam:" + subEncode);
        signParam.put("requestParam", subEncode);
        String paramStr = ParamUtil.sortMap(signParam, "&");
        String signature = HMACSHA1.encrypt(paramStr, secretKey);
        System.out.println("签名值:" + signature);
        System.out.println("加密参数:" + encryptRequestParam);
        // 3、组装请求参数
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("requestParam", encryptRequestParam);
        requestParam.put("orderNo", orderNo);
        requestParam.put("signature", signature);
        // 生成带参数url
        StringBuilder urlBuilder = new StringBuilder("?");
        for (String key : requestParam.keySet()) {
            try {
                urlBuilder.append(key).append("=")
                        .append(URLEncoder.encode(requestParam.get(key), StandardCharsets.UTF_8.name())).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String substring = urlBuilder.substring(0, urlBuilder.length() - 1);
        System.out.println("参数：https://xyh5test.tcredit.com/xygj/merchant/increaseLimit" + substring);
    }
}
