package com.tys.util;

import java.nio.ByteBuffer;

public class MByteUtil {

	
	/**
	 * 数字转为字节数组,0x01020304->01020304
	 * 
	 * @param num
	 * @param len
	 * @return
	 */
	public static byte[] numToBytes(long num, int len) {
		if (len > 8)
			return null;
		long temp = num;
		byte[] b = new byte[len];
		for (int i = 0; i < len; i++) {
			b[len-i-1] = Long.valueOf(temp & 0xff).byteValue();
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}
	
	/**
	 * 数字转为字节数组,0x01020304->04030201
	 * 
	 * @param num
	 * @param len
	 * @return
	 */
	public static byte[] numToBytes2(long num, int len) {
		if (len > 8)
			return null;
		long temp = num;
		byte[] b = new byte[len];
		for (int i = 0; i < len; i++) {
			b[i] = Long.valueOf(temp & 0xff).byteValue();
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/**
	 * 字节数组转为无符号数字
	 * 
	 * @param bytes
	 * @return
	 */
	public static long bytesToNum(byte[] bytes) {
		Long tmp = 0L;
		int len = bytes.length;
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.put(bytes);
		bb.flip();

		if (len == 8) {
			tmp = bb.getLong();
		} else if (len >= 4) {
			tmp = bb.getInt() & 0x0FFFFFFFFl;
		} else if (len >= 2) {
			tmp = bb.getShort() & 0x0FFFFl;
		} else if (len == 1) {
			tmp = bb.get() & 0x0FFl;
		}
		return tmp;
	}
}
