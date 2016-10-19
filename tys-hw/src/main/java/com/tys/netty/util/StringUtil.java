package com.tys.netty.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.tys.util.MStrUtil;



public class StringUtil  {

	/**
	 * 返回一个对象的toString()
	 * 
	 * @param obj
	 *            被处理的对象
	 * @return 如果obj!=null 返回 obj.toString(),如果obj==null 返回 "";
	 */
	public static String notNullString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 返回一个对象的toString()
	 * 
	 * @param obj
	 *            被处理的对象
	 * @param dft
	 *            当obj为null时的默认值
	 * @return 如果obj!=null 返回 obj.toString(),如果obj==null 返回 notNullString(dft);
	 */
	public static String notNullString(Object obj, Object dft) {
		return obj == null ? StringUtil.notNullString(dft) : obj.toString();
	}

	/**
	 * 去掉以 suffix 结尾的部分
	 * 
	 * @param original
	 *            原字符串
	 * @param suffix
	 *            后缀
	 * @return 返回去掉后缀的字符串
	 */
	public static String cutSuffix(String original, String suffix) {
		if (original == null) {
			return null;
		}
		if (original.endsWith(suffix)) {
			int pos = original.lastIndexOf(suffix);
			return original.substring(0, pos);
		}
		return original;
	}

	/**
	 * 根据文本框的显示长度来输出相应的字符串,文本显示长度是中文1个占位,西文0.5个占位
	 * 
	 * @param original
	 *            原字串
	 * @param size
	 *            文本框的长度
	 * @return 结果字串
	 */
	public static String cutWithTextSize(String original, int size) {
		if (original == null) {
			return null;
		}
		if (size < 0) {
			return original;
		}
		if (size == 0) {
			return "";
		}

		if (original.length() > size * 2) {
			original = original.substring(0, size * 2);
		}
		int lenofByte = original.getBytes().length;
		char[] chars = original.toCharArray();
		int len = chars.length;
		while (lenofByte > size * 2) {
			if (chars[--len] > 256) {
				lenofByte -= 2;
			} else {
				lenofByte -= 1;
			}
		}
		return original.substring(0, len);
	}

	/**
	 * 字符串的首字符大写
	 * 
	 * @param original
	 *            原字符串
	 * @return 结果字串
	 */
	public static String upperCaseFirstCharacter(String original) {
		if (original == null) {
			return original;
		}
		if (original.equals("")) {
			return original;
		}
		char[] chrs = original.toCharArray();
		chrs[0] = Character.toUpperCase(chrs[0]);
		return new String(chrs);
	}

	/**
	 * 字符串的首字符小写
	 * 
	 * @param original
	 *            原字符串
	 * @return 结果字串
	 */
	public static String lowerCaseFirstCharacter(String original) {
		if (original == null) {
			return original;
		}
		if (original.equals("")) {
			return original;
		}
		char[] chrs = original.toCharArray();
		chrs[0] = Character.toLowerCase(chrs[0]);
		return new String(chrs);
	}

	/**
	 * 得到数据库中用来表识true和false的值
	 * 
	 * @param value
	 *            Boolean
	 * @return String
	 */
	public static String toSQLBooleanChar(Boolean value) {
		if (value == null) {
			return null;
		}
		if (value.booleanValue()) {
			return "Y";
		}
		return "N";
	}

	/**
	 * 解析字符串为boolean值
	 * 
	 * @param s
	 *            原字符串
	 * @param defaultValue
	 *            默认值
	 * @return 当 s=="true" 或 s=="yes" 时, 返回 true; 当 s==null, 返回 defaultValue;
	 *         否则, 返回 false;
	 */
	public static boolean parseBoolean(String s, boolean defaultValue) {
		boolean b = defaultValue;
		if (s != null) {
			if ("true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s)
					|| "y".equalsIgnoreCase(s)) {
				b = true;
			} else {
				b = false;
			}
		}
		return b;
	}

	/**
	 * 解析字符串为int值
	 * 
	 * @param s
	 *            原字符串
	 * @param defaultValue
	 *            默认值
	 * @return 当s可以被解析时,返回Integer.parseInt(s) 否则, 返回 defaultValue;
	 */
	public static int parseInt(String s, int defaultValue) {
		int i = defaultValue;
		try {
			i = Integer.parseInt(s);
		} catch (Exception ex) {
		}
		return i;
	}

	/**
	 * 解析字符串为long值
	 * 
	 * @param s
	 *            原字符串
	 * @param defaultValue
	 *            默认值
	 * @return 当s可以被解析时,返回Integer.parseInt(s) 否则, 返回 defaultValue;
	 */
	public static long parseLong(String s, long defaultValue) {
		long i = defaultValue;
		try {
			i = Long.parseLong(s);
		} catch (Exception ex) {
		}
		return i;
	}

	/**
	 * 解析字串为日期
	 * 
	 * @param s
	 *            String
	 * @return java.util.Date
	 * @throws java.text.ParseException
	 */
	public static java.util.Date parseDate(String s)
			throws java.text.ParseException {
		if (s == null) {
			return null;
		}
		java.text.DateFormat f = java.text.DateFormat
				.getDateInstance(java.text.DateFormat.DEFAULT);
		return f.parse(s);
	}

	/**
	 * 解析字符串为float值
	 * 
	 * @param s
	 *            原字符串
	 * @param defaultValue
	 *            默认值
	 * @return 当s可以被解析时,返回Float.parseFloat(s) 否则, 返回 defaultValue;
	 */
	public static float parseFloat(String s, float defaultValue) {
		float i = defaultValue;
		try {
			i = Float.parseFloat(s);
		} catch (Exception ex) {
		}
		return i;
	}

	/**
	 * 解析字符串为double值
	 * 
	 * @param s
	 *            原字符串
	 * @param defaultValue
	 *            默认值
	 * @return 当s可以被解析时,返回Integer.parseInt(s) 否则, 返回 defaultValue;
	 */
	public static double parseDouble(String s, double defaultValue) {
		double i = defaultValue;
		try {
			i = Double.parseDouble(s);
		} catch (Exception ex) {
		}
		return i;
	}

	/**
	 * 将字符串转换为Integer对象
	 * 
	 * @param s
	 *            原字符串
	 * @return 当符合10进制整数,并且值不越界时,返回相应对象,否则返回null
	 */
	public static Integer toInteger(String s) {
		try {
			return Integer.valueOf(s);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转换为Double对象
	 * 
	 * @param s
	 *            原字符串
	 * @return 当符合10进制浮点数,并且值不越界时,返回相应对象,否则返回null
	 */
	public static Double toDouble(String s) {
		try {
			return Double.valueOf(s);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转换为Float对象
	 * 
	 * @param s
	 *            原字符串
	 * @return 当符合10进制浮点数,并且值不越界时,返回相应对象,否则返回null
	 */
	public static Float toFloat(String s) {
		try {
			return Float.valueOf(s);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转换为Long对象
	 * 
	 * @param s
	 *            原字符串
	 * @return 当符合10进制整数,并且值不越界时,返回相应对象,否则返回null
	 */
	public static Long toLong(String s) {
		try {
			return Long.valueOf(s);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转换为Boolean对象
	 * 
	 * @param s
	 *            原字符串
	 * @return 当 s=="true" 或 s=="yes" 时, 返回 Boolean.True; 否则, 返回 Boolean.False;
	 */
	public static Boolean toBoolean(String s) {
		if (s == null) {
			return null;
		}
		if ("true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s)
				|| "y".equalsIgnoreCase(s)) {
			return Boolean.TRUE;
		}
		Integer i = toInteger(s);
		if (i != null && i.intValue() > 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static Timestamp toTimestamp(String str) {
		if ((str == null) || (str.trim().length() < 1)) {
			return null;
		}
		if (str.indexOf(' ') != -1) {
			str = str.substring(0, str.indexOf(' '));
		}
		StringTokenizer st = new StringTokenizer(str, "-");
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());

		return DateUtil.createTimestamp(year, month, day);

	}

	public static Timestamp toFullTimestamp(String str) {
		if ((str == null) || (str.trim().length() < 1)) {
			return null;
		}
		if (str.indexOf(' ') != -1) {
			str = str.substring(0, str.indexOf(' '));
		}
		StringTokenizer st = new StringTokenizer(str, "-");
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());

		return DateUtil.createTimestamp(year, month, day, 23, 59, 59);

	}

	public static BigDecimal toBigDecimal(String str) {
		try {
			return new BigDecimal(str);
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * 字符串替换(在jdk1.4 中有实现,这里是对jdk1.4之前版本的支持);
	 * 
	 * @param str
	 *            原字符串
	 * @param target
	 *            被替换的字符串
	 * @param with
	 *            替换成的字符串
	 * @return 返回结果,当with为null时,被处理为空字符串;
	 */
	public static String replace(String str, String target, String with) {
		if (str == null) {
			return null;
		} else if (str.equals("")) {
			return "";
		} else if (target == null || target.equals("")) {
			return str;
		}
		if (with == null) {
			with = "";
		}
		int len = target.length();
		int pos = str.indexOf(target);
		if (pos == -1) {
			return str;
		} else {
			return str.substring(0, pos) + with
					+ replace(str.substring(pos + len), target, with);
		}
	}

	/**
	 * 将 txt 文本以 HTML 格式输出,主要是对空格,换行和tab的替换,1tab=4空格
	 * 
	 * @param str
	 *            原字符串
	 * @return 格式化结果字符串, 当 str==null 时,返回""
	 */
	public static String htmlFormat(String str) {
		if (str == null) {
			return "";
		}
		char[] chars = str.toCharArray();
		int len = chars.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			if (chars[i] == 10) {
				sb.append("<br>");
			} else if (chars[i] == 32) {
				sb.append("&nbsp;");
			} else if (chars[i] == 9) {
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			} else {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 得到字符串的字节数
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串的字节数,str为null时返回0;
	 */
	public static int getByteCount(String str) {
		if (str == null) {
			return 0;
		}
		return str.getBytes().length;
	}

	/**
	 * 得到字符串的字符数
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串的字符数,str为null时返回0;
	 */
	public static int getCharacterCount(String str) {
		if (str == null) {
			return 0;
		}
		return str.toCharArray().length;
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param datetime
	 *            待格式化的日期
	 * @param pattern
	 *            格式化的样式,如 "yyyy.MM.dd 'at' hh:mm:ss z"
	 * @return 符合格式的字符串
	 * @see java.text.SimpleDateFormat.format(Date);
	 */
	public static String format(java.util.Date datetime, String pattern) {
		if (datetime == null) {
			return "";
		}
		SimpleDateFormat f = null;
		if (pattern != null) {
			f = new SimpleDateFormat(pattern);
		} else {
			f = new SimpleDateFormat();
		}
		return f.format(datetime);
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param datetime
	 *            待格式化的日期
	 * @param pattern
	 *            格式化的样式,如 "yyyy.MM.dd 'at' hh:mm:ss z"
	 * @return 符合格式的字符串
	 * @see format(java.util.Date, String );
	 */
	public static String format(java.sql.Timestamp datetime, String pattern) {
		if (datetime == null) {
			return "";
		}
		return format(new java.util.Date(datetime.getTime()), pattern);
	}

	/**
	 * 格式化输出日期yyyymmdd
	 * 
	 * @param date
	 *            待格式化的日期
	 * @return 符合格式的字符串
	 */
	public static String format(java.util.Date date) {
		if (date == null) {
			return "";
		}
		String rs = format(date, "yyyy-MM-dd");
		rs = rs.replaceAll("-", "");
		return rs;
	}

	/**
	 * 格式化输出日期yyyymmdd
	 * 
	 * @param datetime
	 *            待格式化的日期
	 * @return 符合格式的字符串
	 */
	public static String format(java.sql.Timestamp datetime) {
		if (datetime == null) {
			return "";
		}
		return format(new java.util.Date(datetime.getTime()));
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param datetime
	 *            待格式化的日期
	 * @param pattern
	 *            格式化的样式,如 "yyyy.MM.dd 'at' hh:mm:ss z"
	 * @return 符合格式的字符串
	 * @see format(java.util.Date, String );
	 */
	public static String format(java.util.Calendar datetime, String pattern) {
		if (datetime == null) {
			return "";
		}
		return format(datetime.getTime(), pattern);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "$#,###"
	 * @return 符合格式的字符串
	 * @see java.text.DecimalFormat.format(long number);
	 */
	public static String format(long number, String pattern) {
		DecimalFormat f = null;
		if (pattern != null) {
			f = new DecimalFormat(pattern);
		} else {
			f = new DecimalFormat();
		}
		return f.format(number);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "$#,###"
	 * @return 符合格式的字符串
	 * @see format(long, String);
	 */
	public static String format(Integer number, String pattern) {
		if (number == null) {
			return "";
		}
		return format(number.longValue(), pattern);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "$#,###"
	 * @return 符合格式的字符串
	 * @see format(long, String);
	 */
	public static String format(int number, String pattern) {
		return format((long) number, pattern);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "$#,###"
	 * @return 符合格式的字符串
	 * @see format(long, String);
	 */
	public static String format(Long number, String pattern) {
		if (number == null) {
			return "";
		}
		return format(number.longValue(), pattern);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "#,##0.0#"
	 * @return 符合格式的字符串
	 * @see java.text.DecimalFormat.format(double);
	 */
	public static String format(double number, String pattern) {
		DecimalFormat f = null;
		if (pattern != null) {
			f = new DecimalFormat(pattern);
		} else {
			f = new DecimalFormat();
		}
		return f.format(number);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "#,##0.0#"
	 * @return 符合格式的字符串
	 * @see format(Double, String)
	 */
	public static String format(Double number, String pattern) {
		if (number == null) {
			return "";
		}
		return format(number.doubleValue(), pattern);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "#,##0.0#"
	 * @return 符合格式的字符串
	 * @see format(Double, String)
	 */
	public static String format(Float number, String pattern) {
		if (number == null) {
			return "";
		}
		return format(number.doubleValue(), pattern);
	}

	/**
	 * 格式化输出数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param pattern
	 *            格式化的样式,如 "#,##0.0#"
	 * @return 符合格式的字符串
	 * @see format(Double, String)
	 */
	public static String format(float number, String pattern) {
		return format((double) number, pattern);
	}

	/**
	 * 将数组对象以字符串方式输出
	 * 
	 * @param objs
	 *            Object[]
	 * @param dlim
	 *            String 分隔符
	 * @return String
	 */
	public static String combine(Object[] objs, String dlim) {
		if (objs == null || objs.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i]).append(dlim);
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 将集合以字符串方式输出
	 * 
	 * @param col
	 *            Collection
	 * @param dlim
	 *            String 分隔符
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String combine(java.util.Collection col, String dlim) {
		if (col == null || col.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			sb.append((Object) iter.next()).append(dlim);
		}
		return sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String combine(java.util.Map map, String dlim) {
		if (map == null || map.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Object obj : map.entrySet()) {
			if (obj instanceof Map.Entry) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) obj;
				sb.append(entry.getKey()).append('=').append(entry.getValue())
						.append(dlim);
			}
		}

		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 判断一个字串是否为空字串, null或0长度
	 * 
	 * @param s
	 *            String
	 * @return boolean
	 */
	public static boolean isEmpty(String s) {
		if (s == null || s.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字串是否为非空字串, null或0长度
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * 打印字符串的编码
	 * 
	 * @param s
	 *            String
	 * @param out
	 *            PrintStream
	 */
	public static void printStringCode(String s, java.io.PrintStream out) {
		if (s == null || s.length() == 0) {
			out.println("The String is empty");
			return;
		}
		out.print("The String is:");
		out.println(s);
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			out.print(Integer.toHexString((short) chars[i]));
			out.print(",");
		}
		out.println();
	}

	/**
	 * 得到本地编码的字符串
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String nativeEncode(String s) {
		if (s == null) {
			return null;
		}
		try {
			return new String(s.getBytes("ISO-8859-1"));
		} catch (UnsupportedEncodingException ex) {
			return s;
		}
	}

	/**
	 * 本地编码转换为Unicode
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String native2Unicode(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		byte[] buffer = new byte[s.length()];
		for (int i = 0; i < s.length(); i++) {
			buffer[i] = (byte) s.charAt(i);
		}
		return new String(buffer);
	}

	/**
	 * Unicode转本地编码
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String unicode2Native(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		char[] buffer = new char[s.length() * 2];
		char c;
		int j = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 0x100) {
				c = s.charAt(i);
				byte[] buf = ("" + c).getBytes();
				buffer[j++] = (char) buf[0];
				buffer[j++] = (char) buf[1];
			} else {
				buffer[j++] = s.charAt(i);
			}
		}
		return new String(buffer, 0, j);
	}

	/**
	 * 将纯文本转换成html格式进行显示
	 * 
	 * @param text
	 * @return
	 */
	public static String textToHtml(String text) {
		if (isEmpty(text)) {
			return text;
		}
		// text = StringEscapeUtils.escapeHtml(text);
		text = text.replaceAll(" ", "&nbsp;");
		text = text.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		text = text.replaceAll("\n", "<br />");
		return text;
	}

	/**
	 * 
	 * 将对象转换为指定长度的字符串,对象toString之后的长度小于length,则自动用defaultChar填充
	 * 
	 * @param o
	 * @param length
	 * @param defaultChar
	 * @return
	 */
	public static String convertToString(Object o, int length,
			String defaultChar) {
		String r = o.toString();
		int currentLength = r.length();
		if (currentLength < length) {
			StringBuffer sb = new StringBuffer(length);
			for (int i = 0; i < length - currentLength; i++) {
				sb.append(defaultChar);
			}
			sb.append(r);
			r = sb.toString();
		}
		return r;
	}

	/**
	 * 校验身份证号码
	 * 
	 * @param idCode
	 * @return
	 */
	public static Boolean validateIdCode(String idCode) {
		if (idCode == null) {
			return false;
		}
		String idCardRegex = "^[1-9]\\d{5}[1]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}\\w$|^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		if (!idCode.matches(idCardRegex)) {
			return false;
		}

		Date birthday = null;
		if (idCode != null && idCode.length() > 0) {
			String birthdayStr = null;
			if (idCode.length() == 18) {
				birthdayStr = idCode.substring(6, 14);
			} else if (idCode.length() == 15) {
				birthdayStr = "19" + idCode.substring(6, 12);
			}
			birthday = DateUtil.valueOf(birthdayStr, "yyyyMMdd");
		}
		if (birthday == null) {
			return false;
		}

		return true;
	}

	/**
	 * 根据身份证号码取得出生日期
	 * 
	 * @param idCode
	 * @return
	 */
	public static Date getBirthDateFromIdCode(String idCode) {
		if (!validateIdCode(idCode)) {
			throw new IllegalArgumentException();
		}
		Date birthday = null;
		if (idCode != null && idCode.length() > 0) {
			String birthdayStr = null;
			if (idCode.length() == 18) {
				birthdayStr = idCode.substring(6, 14);
			} else if (idCode.length() == 15) {
				birthdayStr = "19" + idCode.substring(6, 12);
			}
			birthday = DateUtil.valueOf(birthdayStr, "yyyyMMdd");
		}

		return birthday;
	}

	/**
	 * 格式化输出日志
	 * 
	 * @param logInfos
	 * @return
	 */
	public static String formatLog(Map<String, Object> logInfos) {
		String separator = System.getProperty("line.separator");
		StringBuffer formatInfos = new StringBuffer();
		if (null == logInfos || 0 == logInfos.size()) {
			return formatInfos.append("[]").append(separator).toString();
		}
		formatInfos.append("[");
		Set<String> attributes = logInfos.keySet();
		boolean flag = false;
		for (String attribute : attributes) {
			if (flag) {
				formatInfos.append(",");
				flag = true;
			}
			formatInfos.append(attribute).append(" = ")
					.append(logInfos.get(attribute));
		}
		return formatInfos.append("]").append(separator).toString();
	}

	/**
	 * 取得一个字符串在另一个字符串中出现的次数
	 * 
	 * @param source
	 * @param toFind
	 * @return
	 */
	public static int countStr(String source, String toFind) {
		if (source == null) {
			return 0;
		}
		int count = 0;
		int index = source.indexOf(toFind);
		while (index != -1) {
			source = source.substring(index + toFind.length());
			index = source.indexOf(toFind);
			count++;
		}
		return count;
	}

	/**
	 * 
	 * <pre>
	 * 在字符串中每隔一定长度(英文1个字符，中文2个字符)插入某个字符
	 * </pre>
	 * 
	 * @param resource
	 * @param size
	 * @param insertStr
	 * @return
	 */
	public static String insertStr2Str(String resource, int size,
			String insertStr) {
		StringBuilder returnStr = new StringBuilder("");

		char[] chars = resource.toCharArray();
		int i = 0;
		for (char c : chars) {
			returnStr.append(c);

			i = i + (c > 256 ? 2 : 1);
			if (i >= size) {
				returnStr.append(insertStr);
				i = 0;
			}
		}
		return returnStr.toString();
	}

	/**
	 * 
	 * <pre>
	 * 判断第一个参数是否等于后续的参数中的某一个
	 * </pre>
	 * 
	 * @param value
	 * @param value1
	 * @param strs
	 * @return
	 */
	public static boolean isIn(String value, String value1, String... strs) {
		if (value.equals(value1)) {
			return true;
		}

		if (strs != null && strs.length > 0) {
			for (String str : strs) {
				if (str.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * <pre>
	 * 判断第一个参数是否不等于后续的所有参数
	 * </pre>
	 * 
	 * @param value
	 * @param value1
	 * @param strs
	 * @return
	 */
	public static boolean isNotIn(String value, String value1, String... strs) {
		return !isIn(value, value1, strs);
	}

	/**
	 * 将Object数组转换成String数组
	 * 
	 * @param array
	 * @return
	 */
	public static String[] toStringArray(Object array[]) {
		if (array == null || array.length==0) {
			return new String[0];
		}
		String[] results = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			results[i] = String.valueOf(array[i]);
		}
		return results;

	}

	public static String getErrorMsg(Exception e) {
		String msg = e.getMessage();
		if (MStrUtil.isNull(msg)) {
			return msg;
		}
		int length = msg.length();
		length = (length >= 200 ? 200 : length);
		msg = msg.substring(0, length);
		return msg;
	}

	/**
	 * 返回格式化字符串比如入参为"a{0}b{1}","cc","dd",则返回结果为"accbdd"
	 * 
	 * @param value
	 * @param params
	 * @return
	 */
	public static String format(String value, Object... params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				value = value.replaceAll("\\{" + i + "\\}",
						params[i] != null ? params[i].toString() : "");
			}
		}
		return value;
	}

	/**
	 * 将字符串中非法字符转换成指定的字符串
	 * 
	 * @param value
	 * @param relpaceText
	 * @return
	 */
	public static String replaceNonCn(String value, String relpaceText) {
		if (isEmpty(value)) {
			return value;
		}
		String regex = "([^\\u4e00-\\u9fa5\\x00-\\xff\\w\\pP\\pS\\pM\\pZ\\pN\\u0800-\\u4e00\\u1100-\\u11ff\\uac00-\\ud7af\\u3130-\\u318F\\u3200-\\u32FF\\uA960-\\uA97F\\uD7B0-\\uD7FF\\uFF00-\\uFFEF]+)";
		return value.replaceAll(regex, relpaceText);
	}

	/**
	 * 将Long类型逗号分隔的字符串转换成List<Long>
	 * 
	 * @param ids
	 * @return
	 */
	public static List<Long> makeIdList(String ids) {
		List<Long> idList = new ArrayList<Long>();
		if (!MStrUtil.isNull(ids)) {
			String[] arr = ids.split(",");
			for (String id : arr) {
				idList.add(Long.valueOf(id));
			}
		}
		return idList;
	}


	public static int compare(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return 0;
		} else if (s1 == null) {
			return -1;
		} else if (s2 == null) {
			return 1;
		} else {
			return s1.compareTo(s2);
		}
	}

	public static String stringToHexString(String strPart) {
		String hexString = "";
		for (int i = 0; i < strPart.length(); i++) {
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			while (strHex.length() < 4) {
				strHex = "0" + strHex;
	        }
			hexString = hexString + strHex;
		}
		return hexString;
	}

	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return "0x" + str;// 0x表示十六进制
	}

	
	//转化十六进制编码为字符串
	public static String hexUnicode2Str(String uStr, String charset) {
		try {
			byte[] bs = new byte[uStr.length() / 2];
			for (int i = 0; i < bs.length; i++) {
				bs[i] = (byte) Integer.parseInt(
						uStr.substring(i * 2, i * 2 + 2), 16);
			}
			return new String(bs, charset);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	//转化字符串为十六进制编码  
	public static String str2HexUnicode(String uStr, String charset) {
		String s = "";
		try {
			byte[] bs = uStr.getBytes(charset);
			for (byte b : bs) {
				s += Integer.toHexString(b & 0xff);
			}
		} catch (UnsupportedEncodingException e) {
			return s;
		}
		return s;

	}

	public static String str2HexUnicode(String uStr) {
		return str2HexUnicode(uStr, "UTF-8");
	}
	
	public static String hexUnicode2Str(String uStr) {
		return hexUnicode2Str(uStr, "UTF-8");
	}
	
	
    public static String str2HexStr(String str) {    
        char[] chars = "0123456789ABCDEF".toCharArray();    
        StringBuilder sb = new StringBuilder("");  
        byte[] bs = str.getBytes();    
        int bit;    
        for (int i = 0; i < bs.length; i++) {    
            bit = (bs[i] & 0x0f0) >> 4;    
            sb.append(chars[bit]);    
            bit = bs[i] & 0x0f;    
            sb.append(chars[bit]);    
        }    
        return sb.toString();    
    }
    
/*  //转化字符串为十六进制编码  
    public static String HexStringEncode(String s) {  
       String str = "";  
       for (int i = 0; i < s.length(); i++) {  
        int ch = (int) s.charAt(i);  
        String s4 = Integer.toHexString(ch);  
        str = str + s4;  
       }  
       return str;  
    } 
    
    // 转化十六进制编码为字符串
	public static String HexStringDecode(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
				s = new String(baKeyword, "utf-8");// UTF-16le:Not
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return s;
	}*/
    

	public static void main(String[] args) {

		//System.out.println(Integer.toBinaryString(4094));
		
		//System.out.println(str2HexUnicode("您好"));
		
		//System.out.println(stringToHexString("张三同学,于2014年 09月 24 日 8:35从学校 A门平安进入校园,请家长放心.").toUpperCase());
		
		//System.out.println(str2HexStr("你好，A,"));
		
		//System.out.println(Integer.toHexString(65));
		
		//String s = "2,2,4,";
		//System.out.println(makeIdList(s));
		
		//String s = "11351.79158";//"2235.07335";
		//String s1 = s.substring(0,(s.indexOf(".")-2));
		//String s2 = s.substring((s.indexOf(".")-2), s.length());
		//System.out.println(s1 + "========" + s2);
		
		//Double result = Double.valueOf(s1) + Double.valueOf(s2) / 60 ;
		//System.out.println(result);
		
		//BigDecimal b2 = BigDecimal.valueOf(Double.valueOf(s1));
		//System.out.println(b2);
		
		//BigDecimal b1 = BigDecimal.valueOf(Double.valueOf(s2));
		//System.out.println(b1);
		
		//System.out.println(NumberUtil.round(Double.valueOf(s1) + Double.valueOf(s2) / 60 ,8));
		
		//System.out.println(b2.add(b1.divide(BigDecimal.valueOf(60d),8 , BigDecimal.ROUND_HALF_EVEN)));
		
		
		//BigDecimal b2 = BigDecimal.valueOf(Double.valueOf(s1));
		//System.out.println(b2);
		
		//System.out.println(b2.add(b1.divide(BigDecimal.valueOf(60))));
		
		
		//Double result = Double.valueOf(s1) + Double.valueOf(s2) / 60 ;
		//System.out.println(result);
		
		//System.out.println(b2.add(b1.divide(BigDecimal.valueOf(60))));
		
		//Date date = new Date(130130230533L);
		//System.out.println(DateUtil.dateToString(date, DateUtil.DATETIME_PATTERN));
		
		//System.out.println(Integer.valueOf("27b4", 16));
		
		
		
		//Integer.parseInt("27b4");
		
		//System.out.println(Integer.parseInt("16",16));

		//byte d = 49;
		
		//Character c1 = (char)d;
		
		//System.out.println(c1);
		
		//System.out.println(Integer.valueOf(c1.toString()));
		
		//char c = (char)d;
		
		//int i = (int)c;
		//int i1 = (int)d;
//		9D4ABA22
		
		//System.out.println((char)d);
		
		//System.out.println(i);
		
		//System.out.println(i1);
		
		//Integer.parseInt("9D4ABA22",10);
		
		
		//System.out.println("bbbb");
		
		//System.out.println(Long.parseLong("9D4ABA22",16));
		
		//System.out.println(Long.parseLong("3ADE68B1",16));

		
		//System.out.println(Integer.parseInt("ADB3B92",16));
		
		//System.out.println();
		
		
		//String s = "2638920226";
		//System.out.println(s.substring(s.length() - 9));
		
		//long s1 = 1231;
		//System.out.println(String.format("%09d", s1));
		
		//String s = "[\"a\",\"b\"]";
		//System.out.println(s.replace("[", "").replace("]", "").replaceAll("\"", ""));
		//String s = "广东省深圳市福田区深南大道,广东省深圳市福田区深南大道2008号中国凤凰大厦1栋附近(41米),广东省深圳市福田区市民中心地铁站东(322.5米)";
		//s = str2HexUnicode(s);
		//System.out.println(s);
		//System.out.println(hexUnicode2Str(s));
		
		//String s2 = "||||";
		//System.out.println(s2.split("|").length);
		//System.out.println(DateUtil.dateToString(new Date(Long.valueOf("150420101747")), DateUtil.DATETIME_PATTERN));
		//System.out.println(System.currentTimeMillis());
		//System.out.println(DateUtil.dateToString(new Date(Long.valueOf(String.valueOf(System.currentTimeMillis()))), DateUtil.DATETIME_PATTERN));
		
//		System.out.println(Integer.parseInt("2633", 16));
//		System.out.println(Integer.parseInt("f70", 16));
//		System.out.println(Integer.parseInt("27", 16));
//		
//		System.out.println(Integer.parseInt("fa2", 16));
//		System.out.println(Integer.parseInt("1e", 16));
//		System.out.println(Integer.parseInt("fa3", 16));
//		System.out.println(Integer.parseInt("1c", 16));
		
		String s  = "AGPS,,100,31,0,,,,,,,,460,0,2633,fa2,64,,,,,,,,8,768";
		String[] s1 = s.split(",");
		String s2 = "tracker,A,100,31,0,,150427114225,2235.81186,"
				+ "N,11350.69116,E,0.041,0,0,0,0,64,,,,,,,,8,768";
		String s4 = "tracker,,100,29,0,,,,,,,,460,0,"
				+ "2633,f70,27,460,0,2633,fa2,1e,460,0,2633,fa3,1c,8,256";
		String[] s3 = s2.split(",");
		System.out.println(s1.length);
		System.out.println(s3.length);
		System.out.println(s4.split(",").length);
		
		System.out.println("tracker,A,100,18,0,,150428151654,2234.24021,N,11351.32918,E,0.901,0,0,0,0,64,,,,,,,,,,,8,768".split(",").length);
		
		
		String ss = null;
		
		System.out.println("aaa".equals(ss));
		
		System.out.println("http://localhost/".split("/").length);
		
		String url = "http://localhost/";
		
		System.out.println(url.replaceAll("http://", ""));
		
		System.out.println(url.replaceAll("http://", "").split("/").length);
	}
	

}
