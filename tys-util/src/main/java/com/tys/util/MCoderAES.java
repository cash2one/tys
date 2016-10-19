package com.tys.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class MCoderAES {

	private static final String KEY_ALGORITHM = "AES";

	private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

	private static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";

	
	
	
	
	
	public static void main(String[] args) throws Exception {
		byte[] tmp;
//		tmp = encryptEcb("123456".getBytes(), "0102030405123456");
//		System.out.println("encryptEcbPkcs5-加密：" + Arrays.toString(tmp));
//		System.out.println("method1-解密后：" + new String(decryptEcb(tmp, "0102030405123456")));
		
		String aeskey = "0102030405123457";
		tmp = encryptCbcNopadding("1234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456".getBytes(), aeskey, "0102030405123456");
		System.out.println("encryptEcbPkcs5-加密：" + tmp.length);
		System.out.println("method1-解密后：" + new String(decryptCbcNopadding(tmp, aeskey, "0102030405123456")));
	}
	
	
	
	
	
	/**
	 * AES算法加密，默认模式 AES/ECB
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encryptEcb(byte[] content, String password) {
		
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 使用加密模式初始化 密钥
			return cipher.doFinal(content); // 按单部分操作加密或解密数据，或者结束一个多部分操作。
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	/**
	 * AES算法解密，默认模式 AES/ECB
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decryptEcb(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);// 使用解密模式初始化 密钥
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	
	
	
	
	
	/**
	 * AES算法加密，模式 AES/ECB/PKCS5Padding
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encryptEcbPkcs5(byte[] content, String password) {
		
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 使用加密模式初始化 密钥
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	/**
	 * AES算法解密，模式 AES/ECB/PKCS5Padding
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decryptEcbPkcs5(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);// 使用解密模式初始化 密钥
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	/**
	 * AES算法加密，模式  AES/CBC/PKCS5Padding
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encryptCbcPkcs5(byte[] content, String password, String iv) {
		
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));// 使用加密模式初始化 密钥
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	/**
	 * AES算法解密，模式 AES/CBC/PKCS5Padding
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decryptCbcPkcs5(byte[] content, String password, String iv) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));// 使用解密模式初始化 密钥
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * AES算法加密，模式  AES/CBC/NoPadding
	 * 要求 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
	 * 
	 * 由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 * 
	 * 可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 * 其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 * 除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 * 
	 * @param content
	 * @param password
	 * @param iv
	 * @return
	 */
	public static byte[] encryptCbcNopadding(byte[] content, String password, String iv) {
		
		try {
//			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
//			kgen.init(128, new SecureRandom(password.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
			
			SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC_NoPadding);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));// 使用加密模式初始化 密钥
			int left = content.length % 16;
			if(left != 0){
				//需要补0
				ByteBuffer bb = ByteBuffer.allocate(content.length + 16 - left);
				bb.put(content);
				content = bb.array();
			}
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	/**
	 * AES算法解密，模式 AES/CBC/NoPadding
	 * 
	 * 要求 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
	 * 
	 * 由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 * 
	 * 可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 * 其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 * 除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 * 
	 * @param content
	 * @param password
	 * @param iv
	 * @return
	 */
	public static byte[] decryptCbcNopadding(byte[] content, String password, String iv) {
		try {
//			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
//			kgen.init(128, new SecureRandom(password.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
			SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC_NoPadding);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));// 使用解密模式初始化 密钥
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
