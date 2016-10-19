package com.tys.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MFileUtil {

	/**
	 * 读取小文件，把所有内容存到String返回
	 * 
	 * @param fileName
	 *            文件路径与文件名
	 * @return
	 */
	public static String readToStr(String fileName) {
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		try {
			InputStream inputStream = new FileInputStream(fileName);
			inputReader = new InputStreamReader(inputStream);
			bufferReader = new BufferedReader(inputReader);

			// 读取一行
			String line = null;
			StringBuffer strBuffer = new StringBuffer();

			while ((line = bufferReader.readLine()) != null) {
				strBuffer.append(line).append("\r\n");
			}
			return strBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputReader != null)
					inputReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bufferReader != null)
					bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "";

	}

}
