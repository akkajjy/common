package com.palwy.common.risk.utils.hy;

import com.alibaba.fastjson.JSON;
import com.palwy.common.risk.utils.hy.utils.AESKeyGenerator;
import com.palwy.common.risk.utils.hy.utils.AESUtil2;
import com.palwy.common.risk.utils.hy.utils.HMACSHA1;
import com.palwy.common.risk.utils.hy.utils.ParamUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 解析验证
 * @author: juyang.liang
 * @since: 2023-05-10 11:36
 * @updatedUser:
 * @updatedDate:
 * @updatedRemark:
 * @version:
 */
public class ChannelParseTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String appId = "41dd7548510c4ab7083c9df493d02f5f";
        String tokenId = "2a27093b2920a703645e0da3ffc0a8c4";
        String signature = URLDecoder.decode("cCqFmZpLh0Lw6OEnFq5M2qZevFA%3D", StandardCharsets.UTF_8.name());
        System.out.println(signature);
        String requestParam = URLDecoder.decode(
                "kyJriKKRqGtsSAVXGdChqjZlBB36g9zyKe4f5h1Whwts2oSOJlfOkZrxb3rIP%2FNb6RveHtg5CSxm%0ANCrtc2zXIT9J2OSDePOIBLfYxDnl1GXRoJPpnnUiE1ic5lpIt6n%2Bm9I560t3V50wK5Yf7YLyfLZt%0Ay24%2BNfqVmGWUelJGlDG8LCbk5I3mnBFj%2BNE%2F1Lk0DHACvB5FESdFUcaIrTqGr9jAe47kbDgbQTi3%0AmIbrbulVFWZ6EpSsX9KBy4AjAIQW"
                , StandardCharsets.UTF_8.name());
        String secretKey = AESKeyGenerator.generator(tokenId);
        HashMap<String, String> signParam = new HashMap<>();
        signParam.put("appId", appId);
        // 验签 - requestParam截取长度为100的字符串
        String requestParam100 = requestParam;
        if (requestParam.length() > 100) {
            requestParam100 = requestParam100.substring(0, 100);
        }
        signParam.put("requestParam", requestParam100);
        // 验签 - 按参数key名升序排列，参数以k=v格式化，参数间以”&”拼接得到待签名明文。
        String signParamStr = ParamUtil.sortMap(signParam, "&");
        String decodeSignature = HMACSHA1.encrypt(signParamStr, secretKey);
        System.out.println(decodeSignature);
        System.out.println("signature:" + signature.equals(decodeSignature));
        String requestParamsDecrypt = AESUtil2.decrypt(secretKey, requestParam);
        System.out.println(requestParamsDecrypt);
        Map<String, String> requestParamsMap = JSON.parseObject(requestParamsDecrypt, Map.class);
        System.out.println("requestResult:" + JSON.toJSONString(requestParamsMap));
    }
}
