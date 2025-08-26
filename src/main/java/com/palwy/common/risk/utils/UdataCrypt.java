

/**
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package com.palwy.common.risk.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 1.生成渠道链接联登加密参数
 * 2.解密优监回调数据
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * 	<li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 *      http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html</li>
 * 	<li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * 	<li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * 	<li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
public class UdataCrypt {
	static Charset CHARSET = Charset.forName("utf-8");
	byte[] appSecretKey;
	String appToken;
	String appId;

	/**
	 * 构造函数
	 * @param appToken
	 * @param appSecret
	 * @param appId
	 * 
	 */
	public UdataCrypt(String appToken, String appSecret, String appId) {
		if (appSecret.length() != 35) {
			//TODO 请自行处理异常
		}

		this.appToken = appToken;
		this.appId = appId;
		appSecretKey = Base64.decodeBase64(appSecret + "udatatec=");
	}

	// 生成4个字节的网络字节序
	byte[] getNetworkBytesOrder(int sourceNumber) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}

	// 还原4个字节的网络字节序
	int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}

	// 随机生成16位字符串
	String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 对明文进行加密.
	 * 
	 * @param text 需要加密的明文
	 * @return 加密后base64编码的字符串
	 */
	String encrypt(String randomStr, String text) {
		ByteGroup byteCollector = new ByteGroup();
		byte[] randomStrBytes = randomStr.getBytes(CHARSET);
		byte[] textBytes = text.getBytes(CHARSET);
		byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
		byte[] appidBytes = appId.getBytes(CHARSET);

		// randomStr + networkBytesOrder + text + appid
		byteCollector.addBytes(randomStrBytes);
		byteCollector.addBytes(networkBytesOrder);
		byteCollector.addBytes(textBytes);
		byteCollector.addBytes(appidBytes);

		// ... + pad: 使用自定义的填充方式对明文进行补位填充
		byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
		byteCollector.addBytes(padBytes);

		// 获得最终的字节流, 未加密
		byte[] unencrypted = byteCollector.toBytes();

		try {
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(appSecretKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(appSecretKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);

			// 使用BASE64对加密后的字符串进行编码
			return Base64.encodeBase64URLSafeString(encrypted);
		} catch (Exception e) {
			// 请自行处理异常情况
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对密文进行解密.
	 * 
	 * @param text 需要解密的密文
	 * @return 解密得到的明文
	 */
	String decrypt(String text) {
		byte[] original;
		try {
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(appSecretKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(appSecretKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(text);

			// 解密
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			// 请自行处理异常情况
			e.printStackTrace();
			return null;
		}

		String data, fromAppId;
		try {
			// 去除补位字符
			byte[] bytes = PKCS7Encoder.decode(original);

			// 分离16位随机字符串,网络字节序和AppId
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

			int length = recoverNetworkBytesOrder(networkOrder);

			data = new String(Arrays.copyOfRange(bytes, 20, 20 + length), CHARSET);
			fromAppId = new String(Arrays.copyOfRange(bytes, 20 + length, bytes.length),
					CHARSET);
		} catch (Exception e) {
			// 请自行处理异常情况
			e.printStackTrace();
			return null;
		}

		// appid不相同的情况
		if (!fromAppId.equals(appId)) {
			// 请自行处理异常情况
			return null;
		}
		return data;

	}

	/**
	 * 消息加密打包
	 * @param data 待加密的json字符串
	 * @param timeStamp 时间戳，
	 * @param nonce 随机串
	 * 
	 * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的json格式的字符串
	 */
	public String encryptMsg(String data, String timeStamp, String nonce) {
		// 加密
		String encrypt = encrypt(getRandomStr(), data);

		// 生成安全签名
		if (timeStamp == "") {
			timeStamp = Long.toString(System.currentTimeMillis());
		}

		String signature = SHA.getSHA1(appToken, timeStamp, nonce, encrypt);

		return JSONParse.generate(encrypt, signature, timeStamp, nonce);
	}

	public String encryptOnce(String data) {
		// 加密
		return encrypt(getRandomStr(), data);
	}


	/**
	 * 检验消息的真实性，并且获取解密后的明文.
	 *
	 * @param msgSignature 签名串，对应URL参数的msgsignature
	 * @param timeStamp 时间戳，对应URL参数的udtimestamp
	 * @param nonce 随机串，对应URL参数的nonce
	 * 
	 * @return 解密后的原文
	 */
	public String decryptMsg(String msgSignature, String timeStamp, String nonce, String encrypt)
			{


		// 验证安全签名
		String signature = SHA.getSHA1(appToken, timeStamp, nonce, encrypt);

		// 和URL中的签名比较是否相等
		if (!signature.equals(msgSignature)) {
			// 请自行处理异常情况
			return null;
		}

		// 解密
		return decrypt(encrypt);
	}


	public String decryptOnce(String encrypt)
	{
		// 解密
		return decrypt(encrypt);
	}


	public static Map<String, String> extract(String jsonText) {
		return JSONParse.extract(jsonText);
	}

	class ByteGroup {
		ArrayList<Byte> byteContainer = new ArrayList<Byte>();

		public byte[] toBytes() {
			byte[] bytes = new byte[byteContainer.size()];
			for (int i = 0; i < byteContainer.size(); i++) {
				bytes[i] = byteContainer.get(i);
			}
			return bytes;
		}

		public ByteGroup addBytes(byte[] bytes) {
			for (byte b : bytes) {
				byteContainer.add(b);
			}
			return this;
		}

		public int size() {
			return byteContainer.size();
		}
	}
}