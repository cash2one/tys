package com.tys.util;

import org.json.JSONObject;

/**
 * 项目协议工具类
 * @author Administrator
 *
 */
public class MPtoUtil {
	
	
	/**
	 * 获取数据指令类型 例:getCmdType("SZLNET:2,XXX")返回值为2
	 * 
	 * @param msg
	 * @return
	 */
	public static String getCmdType(String msg) {
		String type = "";
		int b = msg.indexOf(":");
		int e = msg.indexOf(",");
		if (b == -1 || e == -1) {
			return type;
		}
		type = msg.substring(b + 1, e);
		return type;
	}

	/**
	 * 分解参数公共方法, 例:格式:XX=1&YY=2,则getParamValue("XX")返回值为1,默认返回空字串
	 * 
	 * @param name
	 * @param params
	 * @return
	 */
	public static String getParamValue(String name, String params) {
		String paramValue = "";
		if (params.indexOf(name + "=") != -1) {
			String[] tmp = params.split(name + "=");
			if (tmp.length > 1) {
				paramValue = tmp[1].split("&")[0].trim();
			}
		}
		return paramValue;
	}
	
	/**
	 * 分解参数公共方法, 例:格式:XX=1&YY=2,则getParamValue("XX")返回值为1
	 * @param name
	 * @param params
	 * @param defaul
	 * @return
	 */
	public static String getParamValue(String name, String params, String defaul) {
		String paramValue = defaul;
		if (params.indexOf(name + "=") != -1) {
			String[] tmp = params.split(name + "=");
			if (tmp.length > 1) {
				paramValue = tmp[1].split("&")[0].trim();
			} else if (tmp.length == 1){
				paramValue = "";
			}
		}
		return paramValue;
	}

	/**
	 * 分解参数公共方法, 例:格式:XX=1&YY=2,则getParamValue("XX")返回值为1
	 * 
	 * @param name
	 * @param params
	 * @return
	 */
	public static int getParamInt(String name, String params) {
		int value = 0;
		String paramValue = "";
		if (params.indexOf(name + "=") != -1) {
			String[] tmp = params.split(name + "=");
			if (tmp.length > 1) {
				paramValue = tmp[1].split("&")[0].trim();
			}
			try {
				value = Integer.parseInt(paramValue);
			} catch (NumberFormatException e) {
			}
		}
		return value;
	}

	/**
	 * 分解参数公共方法, 例:格式:XX=1&YY=2,则getParamValue("XX")返回值为1
	 * 
	 * @param name
	 * @param params
	 * @return
	 */
	public static float getParamFloat(String name, String params) {
		float value = 0;
		String paramValue = "";
		if (params.indexOf(name + "=") != -1) {
			String[] tmp = params.split(name + "=");
			if (tmp.length > 1) {
				paramValue = tmp[1].split("&")[0].trim();
			}
			try {
				value = Float.parseFloat(paramValue);
			} catch (NumberFormatException e) {
			}
		}
		return value;
	}

	/**
	 * 分解参数公共方法, 例:格式:XX=1&YY=2,则getParamValue("XX")返回值为1
	 * 
	 * @param name
	 * @param params
	 * @return
	 */
	public static double getParamDouble(String name, String params) {
		double value = 0;
		String paramValue = "";
		if (params.indexOf(name + "=") != -1) {
			String[] tmp = params.split(name + "=");
			if (tmp.length > 1) {
				paramValue = tmp[1].split("&")[0].trim();
			}
			try {
				value = Double.parseDouble(paramValue);
			} catch (NumberFormatException e) {
			}
		}
		return value;
	}

	/**
	 * 提取数据中JSON
	 * 
	 * @param msg
	 * @return
	 */
	public static JSONObject getParamJson(String msg) {
		int b = msg.indexOf("{");
		int e = msg.lastIndexOf("}");
		if (b == -1 || e == -1) {
			return null;
		}
		JSONObject json = new JSONObject(msg.substring(b, e + 1));
		return json;
	}

	/**
	 * 获取JSON String数据
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getJsonString(JSONObject json, String key) {
		String ret = "";
		if (json.has(key)) {
			ret = json.getString(key);
		}
		return ret;
	}

	/**
	 * 获取JSON int数据
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static int getJsonInt(JSONObject json, String key) {
		int ret = -1;
		if (json.has(key)) {
			ret = json.getInt(key);
		}
		return ret;
	}

	/**
	 * 获取JSON long数据
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static long getJsonLong(JSONObject json, String key) {
		long ret = -1;
		if (json.has(key)) {
			ret = json.getLong(key);
		}
		return ret;
	}
	
	public static boolean isVirtualCar(String boxCode){
		return boxCode.matches("usercar[0-9]*");
	}
	
	
}
