package com.gyf.os.security.aes;
import java.security.Key;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
public class AESSecurityUtil {
	// 加密算法
	private static final String ALGORITHM = "AES";

	/**
	 * 用来进行加密的操作
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String keyString, String data, String charset) throws Exception {
		Key key = new SecretKeySpec(keyString.getBytes(charset), ALGORITHM);
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes(charset));
		String encryptedValue = new String(Base64.encodeBase64(encVal), charset);
		return encryptedValue;
	}

	/**
	 * 用来进行解密的操作
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String keyString, String encryptedData, String charset) throws Exception {
		Key key = new SecretKeySpec(keyString.getBytes(charset), ALGORITHM);
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.decodeBase64(encryptedData.getBytes(charset));
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue, charset);
		return decryptedValue;
	}

	/**
	 * 生成16位uuid用来做加密的key
	 * 
	 * @return
	 * @see
	 */
	public static String generateKeyString() {
		// 必须长度为16
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
	}
}
