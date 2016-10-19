package com.tys.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MPropertiesUtil {

	private static Properties property = null;

	private static String propertyFile = "/properties/util.properties";

	static {
		if (property == null)
			loadFile(true);
	}

	public static void loadFile(boolean flag) {
		MUtil.log("load property file is ---" + propertyFile);
		InputStream inStream = null;
		property = new Properties();
		try {
			if (flag)
				inStream = MPropertiesUtil.class.getResourceAsStream(propertyFile);
			else {
				inStream = new FileInputStream(propertyFile);
			}
			property.load(inStream);
		} catch (Exception e) {
			if (flag)
				loadFile(false);
			else
				e.printStackTrace();
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (IOException localIOException1) {
			}
		}
	}

	public static String getProperty(String key) {
		return MStrUtil.encodeStr(MStrUtil.trimStr(property.getProperty(key)), "UTF-8");
	}

	public static void setProperty(String key, String value) {
		FileOutputStream fos = null;
		property.setProperty(key, value);
		try {
			fos = new FileOutputStream(propertyFile);
			property.store(fos, null);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Properties getProperty() {
		return property;
	}

}