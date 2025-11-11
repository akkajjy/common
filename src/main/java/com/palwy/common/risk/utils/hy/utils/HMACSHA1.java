package com.palwy.common.risk.utils.hy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @description:
 * @author: zl.T
 * @since: 2021-10-27 17:10
 * @updatedUser: zl.T
 * @updatedDate: 2021-10-27 17:10
 * @updatedRemark:
 * @version:
 */
public class HMACSHA1 {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(HMACSHA1.class);

  private static final String ENCRYPT_NAME = "HmacSHA1";
  private static final String ENCODING = "UTF-8";

  public static String encrypt(String encryptText, String encryptKey) {
    try {
      String codeInBase64EncryptKey = new String(Base64.getEncoder().encode(encryptKey.getBytes(ENCODING)));
      byte[] encodingData = codeInBase64EncryptKey.getBytes(ENCODING);
      SecretKeySpec secretKey = new SecretKeySpec(encodingData, ENCRYPT_NAME);
      Mac mac = Mac.getInstance(ENCRYPT_NAME);
      mac.init(secretKey);
      byte[] text = encryptText.getBytes(ENCODING);
      byte[] digest = mac.doFinal(text);
      return new String(Base64.getEncoder().encode(digest));
    } catch (Exception e) {
      LOGGER.error("生成验签错误:" + encryptText + ",验签key:" + encryptKey + ",错误信息：" + e.getMessage());
    }
    return null;

  }

}
