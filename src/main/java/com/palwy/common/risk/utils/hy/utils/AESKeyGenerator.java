package com.palwy.common.risk.utils.hy.utils;
import org.apache.commons.lang.StringUtils;

import java.util.Base64;

/**
 * @description:返回32位的加密key
 * @author: zl.T
 * @since: 2021-11-03 11:26
 * @updatedUser: zl.T
 * @updatedDate: 2021-11-03 11:26
 * @updatedRemark:
 * @version:
 */
public class AESKeyGenerator {
    private static final String SALT = "150aecbc09d34422910a50d69bab938a";


    /**
     * 根据tokenId生成32位长度的加密key
     *
     * @param secretKey
     * @return
     */
    public static String generator(String secretKey) {
        try {
            if (StringUtils.isNotBlank(secretKey)) {
                String key = "";
                secretKey = secretKey.replaceAll("-", "");
                String secretKeyInBase64 = new String(Base64.getEncoder().encode(secretKey.getBytes("UTF-8")));
                if (secretKeyInBase64.length() > 32) {
                    key = secretKeyInBase64.substring(0, 32);
                } else {
                    int needSupply = 32 - secretKeyInBase64.length();
                    key = secretKeyInBase64 + SALT.substring(0, needSupply);
                }
                return key;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
