package org.linlinjava.litemall.core.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/***
 * DES ECB对称加密 解密
 * 
 *
 */
public class DesUtil {

	/**
	 * AES加密操作
	 *
	 * @param sSrc 待加密内容
	 * @param sKey 加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public String encryptAES(String sSrc, String sKey) {
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(sKey.substring(0,16).getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

			java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
			return encoder.encodeToString(encrypted);// 此处使用BASE64做转码
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * AES解密操作
	 *
	 * @param sSrc 待解密内容
	 * @param sKey 加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public String decryptAES(String sSrc, String sKey) {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(sKey.substring(0,16).getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
			byte[] encrypted1 = decoder.decode(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	public static String encodeBytes(byte[] bytes) {
		StringBuffer strBuf = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
			strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
		}

		return strBuf.toString();
	}

	/**
	 * DES加密操作
	 *
	 * @param plaintext 待加密内容
	 * @param key       加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public static String encryptDES(String plaintext, String key) {
		try {
			// 首先，DES算法要求有一个可信任的随机数源，可以通过 SecureRandom类,内置两种随机数算法，NativePRNG和SHA1PRNG
			SecureRandom random = new SecureRandom();
			// 创建一个DESKeySpec对象
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(cipher.ENCRYPT_MODE, securekey, random);
			// 加密生成密文byte数组
			byte[] cipherBytes = cipher.doFinal(plaintext.getBytes());
			// 将密文byte数组转化为16进制密文
			String ciphertext = byteToHex(cipherBytes);
			return ciphertext;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * DES解密操作
	 *
	 * @param ciphertext 加密的密文
	 * @param key        解密的密钥
	 * @return
	 */
	public static String decryptDES(String ciphertext, String key) {
		try {
			// DES算法要求有一个可信任的随机数源，SecureRandom内置两种随机数算法，NativePRNG和SHA1PRNG,
			SecureRandom random = new SecureRandom();
			// 创建一个DESKeySpec对象
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(cipher.DECRYPT_MODE, securekey, random);
			// 将16进制密文转化为密文byte数组
			byte[] cipherBytes = hexToByte(ciphertext);
			// 真正开始解密操作
			String plaintext = new String(cipher.doFinal(cipherBytes));
			return plaintext;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	// 将byte转化为16进制
	public static String byteToHex(byte[] bs) {
		if (0 == bs.length) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < bs.length; i++) {
				String s = Integer.toHexString(bs[i] & 0xFF);
				if (1 == s.length()) {
					sb.append("0");
				}
				sb = sb.append(s.toUpperCase());
			}
			return sb.toString();
		}
	}

	// 将16进制转化为byte
	public static byte[] hexToByte(String ciphertext) {
		byte[] cipherBytes = ciphertext.getBytes();
		if ((cipherBytes.length % 2) != 0) {
			throw new IllegalArgumentException("长度不为偶数");
		} else {
			byte[] result = new byte[cipherBytes.length / 2];
			for (int i = 0; i < cipherBytes.length; i += 2) {
				String item = new String(cipherBytes, i, 2);
				result[i / 2] = (byte) Integer.parseInt(item, 16);
			}
			return result;
		}
	}

}