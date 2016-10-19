package com.tys.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MStrUtil {

	/**
	 * 字串是否为空
	 * 
	 * @param checkStr
	 * @return
	 */
	public static boolean isNull(String checkStr) {
		return (checkStr == null) || (checkStr.trim().length() == 0) || (checkStr.trim().equalsIgnoreCase("null"));
	}
	
	/**
	 * 字串是否不为空
	 * 
	 * @param checkStr
	 * @return
	 */
	public static boolean isNotNull(String checkStr) {
		return !isNull(checkStr);
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNullOrEmpty(Object o) {
		return (o == null || o.toString().equals(""));
	}

	/**
	 * 去除字串中空格、回车、换行符、制表符
	 * 
	 * @param str
	 * @return
	 */
	public static String removeBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 字串转整形数
	 * 
	 * @param intStr
	 * @param defaultInt
	 *            默认值
	 * @return
	 */
	public static int parseInt(String intStr, int defaultInt) {
		try {
			return Integer.parseInt(intStr);
		} catch (Exception e) {
		}
		return defaultInt;
	}

	/**
	 * 字串转整形数
	 * 
	 * @param intStr
	 * @return
	 */
	public static int parseInt(String intStr) {
		return parseInt(intStr, 0);
	}

	/**
	 * 字串转长整形数
	 * 
	 * @param longStr
	 * @param defaultLong
	 *            默认值
	 * @return
	 */
	public static long parseLong(String longStr, long defaultLong) {
		try {
			return Long.parseLong(longStr);
		} catch (Exception e) {
		}
		return defaultLong;
	}

	/**
	 * 字串转长整形数
	 * 
	 * @param longStr
	 * @return
	 */
	public static long parseLong(String longStr) {
		return parseLong(longStr, 0);
	}

	/**
	 * 字串转double数
	 * 
	 * @param str
	 * @param defaultInt
	 * @return
	 */
	public static double parseDouble(String str, double defaultInt) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}
		return defaultInt;
	}

	/**
	 * 字串转double数
	 * 
	 * @param str
	 * @return
	 */
	public static double parseDouble(String str) {
		return parseDouble(str, 0);
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		Pattern pattern = Pattern.compile("(0|[1-9][0-9]*)");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 转换编码
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String encodeStr(String str, String charset) {
		if (isNull(str))
			return str;
		try {
			return new String(str.getBytes(charset));
		} catch (Exception e) {
		}
		return str;
	}

	/**
	 * 去空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimStr(String str) {
		if (str != null) {
			return str.trim();
		}
		return str;
	}

	/**
	 * 从源字串中以指定字串头截取前半部分字串
	 * 
	 * @param src
	 * @param term
	 * @return
	 */
	public static String subBefore(String src, String term) {
		if ((src == null) || (term == null)) {
			return null;
		}
		int index = src.indexOf(term);
		return (index >= 0) ? src.substring(0, index) : src;
	}

	/**
	 * 从源字串中以指定字串头截取后半部分字串
	 * 
	 * @param src
	 * @param term
	 * @return
	 */
	public static String subAfter(String src, String term) {
		if ((src == null) || (term == null)) {
			return null;
		}
		int index = src.indexOf(term);
		return (index >= 0) ? src.substring(index + term.length()) : src;
	}

	/**
	 * 从源字串中查找最后的指定字串头以截取前半部分字串
	 * 
	 * @param src
	 * @param term
	 * @return
	 */
	public static String subLastBefore(String src, String term) {
		if ((src == null) || (term == null)) {
			return null;
		}
		int index = src.lastIndexOf(term);
		return (index >= 0) ? src.substring(0, index) : src;
	}

	/**
	 * 从源字串中查找最后的指定字串头以截取后半部分字串
	 * 
	 * @param src
	 * @param term
	 * @return
	 */
	public static String subLastAfter(String src, String term) {
		if ((src == null) || (term == null)) {
			return null;
		}
		int index = src.lastIndexOf(term);
		return (index >= 0) ? src.substring(index + term.length()) : src;
	}

	/**
	 * 查找字串是否存在指定字串
	 * 
	 * @param src
	 * @param term
	 * @return
	 */
	public static boolean exist(String src, String term) {
		if ((src == null) || (term == null)) {
			return false;
		}
		int index = src.indexOf(term);
		return index >= 0;
	}

	/**
	 * URL编码
	 * 
	 * @param src
	 * @return
	 */
	public static String encodeURL(String src) {
		String result = null;
		try {
			result = URLEncoder.encode(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * URL解码
	 * 
	 * @param src
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURL(String src) throws UnsupportedEncodingException {
		return URLDecoder.decode(src, "UTF-8");
	}

	/**
	 * 正则表达式
	 * 
	 * @param str
	 * @param rex
	 * @return
	 */
	public static boolean rexp(String str, String rex) {
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 *            生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		final String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 生成随机数字字符串
	 * 
	 * @param length
	 *            生成字符串的长度
	 * @return
	 */
	public static String getRandomNumString(int length) {
		final String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 从字节数组到十六进制字符串转换
	 * 
	 * @param b
	 * @return
	 */
	public static String bytes2HexString(byte[] b) {
		final byte[] hex = "0123456789ABCDEF".getBytes();
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	/**
	 * 二进制转为字符串
	 * 
	 * @param in
	 * @param len
	 * @return
	 */
	public static String bin2Str(long in, int len) {
		int i = 0;
		char[] buf = new char[len];

		for (i = 0; i < len; i++) {
			buf[len - i - 1] = ((in & (0x01 << i)) > 0) ? '1' : '0';
		}
		// buf[len-1] = 0;
		return new String(buf);
	}

	/**
	 * 二进制字符串转为int, 最大32位
	 * 
	 * @param str
	 * @return
	 */
	public static int str2Bin(String str) {
		int i = 0;
		int len = 0;
		int temp = 0;
		char[] buf = str.toCharArray();

		len = buf.length;
		if (len > 32)
			len = 32;

		for (i = 0; i < len - 1; i++) {
			if (buf[i] == '1')
				temp++;
			temp = temp << 1;
		}
		if (buf[len - 1] == '1')
			temp++;
		return temp;
	}

	/**
	 * 十六进制字串转字串byte
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] str2Bytes(String str) {
		int i = 0;
		int byteLen = str.length()/2;
		byte[] destBytes = new byte[byteLen];

		byte[] srcBytes = str.getBytes();
		for (i = 0; i < byteLen*2; i++) {
			if (i % 2 == 0) {
				if (srcBytes[i / 2] >= '0' && srcBytes[i] <= '9') {
					destBytes[i / 2] |= ((srcBytes[i] - '0') << 4) & 0xf0;
				} else if (srcBytes[i] >= 'A' && srcBytes[i] <= 'F') {
					destBytes[i / 2] |= ((srcBytes[i] - 'A' + 10) << 4) & 0xf0;
				} else if (srcBytes[i] >= 'a' && srcBytes[i] <= 'f') {
					destBytes[i / 2] |= ((srcBytes[i] - 'a' + 10) << 4) & 0xf0;
				}
			} else {
				if (srcBytes[i] >= '0' && srcBytes[i] <= '9') {
					destBytes[i / 2] |= ((srcBytes[i] - '0')) & 0x0f;
				} else if (srcBytes[i] >= 'A' && srcBytes[i] <= 'F') {
					destBytes[i / 2] |= ((srcBytes[i] - 'A' + 10)) & 0x0f;
				} else if (srcBytes[i] >= 'a' && srcBytes[i] <= 'f') {
					destBytes[i / 2] |= ((srcBytes[i] - 'a' + 10)) & 0x0f;
				}
			}
		}

		return destBytes;
	}

	/**
	 * MD5加密
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Map字典序排序
	 * 
	 * @param unsort_map
	 * @return
	 */
	public static SortedMap<String, String> mapSortByKey(Map<String, String> unsort_map) {
		TreeMap<String, String> result = new TreeMap<String, String>();
		Object[] unsort_key = unsort_map.keySet().toArray();
		Arrays.sort(unsort_key);
		for (int i = 0; i < unsort_key.length; i++) {
			result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
		}
		return result.tailMap(result.firstKey());
	}

	/**
	 * BASE64编码
	 * 
	 * @param b
	 * @return
	 */
	public static String getBASE64(String b) {
		String s = null;
		if (b != null) {
			s = new BASE64Encoder().encode(b.getBytes());
		}
		return s;
	}

	/**
	 * BASE64解码
	 * @param s
	 * @return
	 */
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 生成MD5
	 * @param text
	 * @param salt
	 * @return
	 */
	public static String getMD5(String text, String salt) {
		try {
			Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
			return md5PasswordEncoder.encodePassword(text, salt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}