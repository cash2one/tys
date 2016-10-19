package com.tys.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 * 
 */
public abstract class MCoderRSA {
	public static final String KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String KEY_ALGORITHM2 = "RSA/ECB/NoPadding";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = MCoder.decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return MCoder.encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = MCoder.decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(MCoder.decryptBASE64(sign));
	}

	
	
	/**
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key	BASE64编码的KEY
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKeyB64(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = MCoder.decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}
	
	
	
	/**
	 * 用私钥解密
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
		
		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	

	/**
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key	BASE64编码的KEY
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKeyB64(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = MCoder.decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	
	/**
	 * 用公钥解密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, RSAPublicKey publicKey) throws Exception {
		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}

	
	
	
	
	/**
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key		BASE64编码的KEY
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKeyB64(byte[] data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = MCoder.decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	
	/**
	 * 用公钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey) throws Exception {
		
		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	
	
	

	/**
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key	BASE64编码的KEY
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKeyB64(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = MCoder.decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}
	
	/**
	 * 用私钥加密
	 * 
	 * @param data
	 * @param privateKey	
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
		
		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	
	
	

	/**
	 * 使用模和指数生成RSA公钥
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(BigInteger modulus, BigInteger exponent) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(BigInteger modulus, BigInteger exponent) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取得私钥,BASE64编码
	 * 
	 * @see	initRsaKey
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKeyB64(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return MCoder.encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥,BASE64编码
	 * 
	 * @see	initRsaKey
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKeyB64(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return MCoder.encryptBASE64(key.getEncoded());
	}

	/**
	 * 初始化RSA密钥
	 * 
	 * @param keysize
	 *            RSA (1024, 2048)
	 * @param publicExponent
	 *            公共模数,一个大质数
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initRsaKey(int keysize, BigInteger publicExponent) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		if (keysize != 1024 && keysize != 2048) {
			keysize = 1024;
		}
		RSAKeyGenParameterSpec params = new RSAKeyGenParameterSpec(keysize, publicExponent);
		keyPairGen.initialize(params);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
}
