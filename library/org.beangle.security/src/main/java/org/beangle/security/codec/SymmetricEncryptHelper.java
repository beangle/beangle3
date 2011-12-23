/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.codec;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class SymmetricEncryptHelper {
	private Cipher enCipher;
	private Cipher deCipher;
	private String algorithm;

	public SymmetricEncryptHelper(byte[] key) {
		this(key, "DES");
	}

	public SymmetricEncryptHelper(byte[] key, String algorithm) {
		this.algorithm = algorithm;
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		try {
			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
			SecretKey secretKey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			enCipher = Cipher.getInstance(algorithm);
			deCipher = Cipher.getInstance(algorithm);
			// 用密匙初始化Cipher对象
			enCipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
			deCipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public byte[] encypt(byte[] value) {
		try {
			return enCipher.doFinal(value);
		} catch (Exception e) {
			return new byte[0];
		}
	}

	public byte[] decrypt(byte[] value) {
		try {
			return deCipher.doFinal(value);
		} catch (Exception e) {
			return new byte[0];
		}
	}

	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * 16进制显示数据
	 * 
	 * @param value
	 *            字节数组
	 * @return
	 */
	public static String toHexString(byte[] value) {
		String newString = "";
		for (int i = 0; i < value.length; i++) {
			byte b = value[i];
			String str = Integer.toHexString(b);
			if (str.length() > 2) {
				str = str.substring(str.length() - 2);
			}
			if (str.length() < 2) {
				str = "0" + str;
			}
			newString += str;
		}
		return newString.toUpperCase();
	}

	/**
	 * 16进制显示数据
	 * 
	 * @param value
	 *            字节数组
	 * @return
	 */
	public static byte[] fromHexString(String value) {
		byte[] rs = new byte[value.length() / 2];
		for (int i = 0; i < value.length(); i += 2) {
			String b = value.substring(i, i + 2);
			rs[i / 2] = Integer.valueOf(b, 16).byteValue();
		}
		return rs;
	}

	public static byte[] encypt(byte[] key, byte[] value) {
		return new SymmetricEncryptHelper(key).encypt(value);
	}

	public static byte[] decrypt(byte[] key, byte[] value) {
		return new SymmetricEncryptHelper(key).decrypt(value);
	}
}
