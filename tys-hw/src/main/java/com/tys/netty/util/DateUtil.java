package com.tys.netty.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.TimeZone;


public class DateUtil {

	/**
	 * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of
	 * December in the year 2002
	 */
	public static final String ISO_DATE_FORMAT = "yyyyMMdd";

	/**
	 * Expanded ISO 8601 Date format yyyy-MM-dd i.e., 2002-12-25 for the 25th
	 * day of December in the year 2002
	 */
	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy年MM月dd日
	 */
	public static final String CHINESE_EXPANDED_DATE_FORMAT = "yyyy年MM月dd日";

	/**
	 * Default lenient setting for getDate.
	 */
	private static final boolean LENIENT_DATE = false;

	/**
	 * HH:mm
	 */
	public static final String HHMM_DATA_FORMATE = "HH:mm";

	/**
	 * HH:mm:ss
	 */
	public static final String HHMMSS_DATA_FORMATE = "HH:mm:ss";

	public static final String YYYYMMDD_HHMM_DATA_FORMATE = "yyyy-MM-dd HH:mm";

	public final static String NON_YEAR_DATE_FORMAT = "MM月dd日";

	public static final String YYYYMMDD_HHMMSS_DATA_FORMATE = "yyyyMMddHHmmss";


	/**
	 * 获取当前的日期，不包括时间
	 *
	 * @return
	 */
	public static java.sql.Date getTodayDate() {
		Calendar c = Calendar.getInstance();
		return new java.sql.Date(c.getTimeInMillis());
	}

	/**
	 * <p>
	 * Description:得到当前时间 包括时间,分,秒
	 * </p>
	 *
	 * @return Timestamp
	 */
	public static Timestamp getNowTime() {
		return new Timestamp(System.currentTimeMillis());

	}

	/**
	 * <p>
	 * Description:把Calendar类型转换为Timestamp
	 * </p>
	 *
	 * @param calendar
	 * @return Timestamp
	 */
	public static Timestamp convertCalToTs(Calendar calendar) {
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * <p>
	 * Description:把Timestamp类型转换为Calendar
	 * </p>
	 *
	 * @param ts
	 * @return Calendar
	 */
	public static Calendar convertTsToCal(Timestamp ts) {
		Calendar cald = Calendar.getInstance();
		cald.setTime(new Date(ts.getTime()));
		return cald;
	}

	/**
	 * <p>
	 * Description:把Timestamp类型转换为Date
	 * </p>
	 *
	 * @param ts
	 * @return Date
	 */
	public static Date convertTsToDt(Timestamp ts) {
		return new Date(ts.getTime());
	}

	/**
	 * <p>
	 * Description:把Date类型转换为Timestamp
	 * </p>
	 *
	 * @param dt
	 * @return Timestamp
	 */
	public static Timestamp convertDtToTs(Date dt) {
		return new Timestamp(dt.getTime());
	}

	/**
	 * <p>
	 * Description:把Timestamp类型转换为String类型,返回数据截至到日期
	 * </p>
	 *
	 * @param ts
	 * @return String
	 */
	public static String convertTsToStr(Timestamp ts) {
		if (ts != null) {
			return ts.toString().substring(0, 10);
		}
		return "";
	}

	/**
	 * <p>
	 * Description:把Timestamp类型转换为String类型,返回数据截至到秒,格式为2003-02-05 14:23:05
	 * </p>
	 *
	 * @param ts
	 * @return String
	 */
	public static String convertTsToStrWithSecs(Timestamp ts) {
		if (ts != null) {
			return ts.toString().substring(0, 19);
		}
		return "";
	}

	/**
	 * <p>
	 * Description:把Timestamp类型转换为String类型,返回数据带有星期几显示,格式为2003-11-04 星期二
	 * </p>
	 *
	 * @param ts
	 * @return String
	 */
	public static String convertTsToStrWithDayOfWeek(Timestamp ts) {
		if (ts != null) {
			return ts.toString().substring(0, 10) + " " + getStrDay(ts);
		}
		return "";
	}

	/**
	 * <p>
	 * Description:根据传入的参数生成一个日期类型
	 * </p>
	 *
	 * @param year
	 *            :年份如1999 month:月份如3月（为实际需要创建的月份） date:日期如25
	 * @param hour
	 *            :小时如15 minute:分钟如25 second:秒如34
	 * @return String
	 */
	public static Timestamp createTimestamp(int year, int month, int date,
			int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, hour, minute, second);
		cal.set(Calendar.MILLISECOND, 0);
		return convertCalToTs(cal);
	}

	/**
	 * <p>
	 * Description:根据传入的参数生成一个日期类型
	 * </p>
	 *
	 * @param year
	 *            :年份如1999 month:月份如3月（为实际需要创建的月份） date:日期如25
	 * @return String
	 */
	public static Timestamp createTimestamp(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return convertCalToTs(cal);
	}

	/**
	 * @param str
	 *            传入的字符串格式如2003-02-01,2003/02/01
	 * @param splitStr
	 *            传入字符串当中的分隔符 '-' '/'
	 * @return Timestamp 日期类型
	 */
	public static Timestamp createTimestamp(String str, String splitStr) {
		if ((str == null) || (str.trim().length() < 1)) {
			return null;
		}
		if ("".equals(splitStr)) {
			splitStr = "-";
		}
		if (str.lastIndexOf(' ') != -1) {
			str = str.substring(0, 10);
		}
		StringTokenizer st = new StringTokenizer(str, splitStr);
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int date = Integer.parseInt(st.nextToken());
		return createTimestamp(year, month, date);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期的对应年数
	 */
	public static int getYear(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.YEAR);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期的对应月份
	 */
	public static int getMonth(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.MONTH) + 1;
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期的对应日期
	 */
	public static int getDate(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应小时数
	 */
	public static int getHour(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应分钟数
	 */
	public static int getMinute(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.MINUTE);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应秒数
	 */
	public static int getSecond(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.SECOND);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应秒中的毫秒数
	 */
	public static int getMillisecond(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.MILLISECOND);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应毫秒数
	 */
	public static long getMilliseconds(Timestamp ts) {
		return ts.getTime();
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应毫秒数
	 */
	public static Long getMilliseconds(Date date) {
		return date == null ? null : date.getTime();
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应星期数，为数字1为星期天2为星期一依次类推
	 */
	public static int getDay(Timestamp ts) {
		return convertTsToCal(ts).get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应星期几，返回为中文，如星期一星期二等
	 */
	public static String getStrDay(Timestamp ts) {
		if (ts == null) {
			return null;
		}
		int day = getDay(ts);
		String weekDay = "";
		switch (day) {
		case 1:
			weekDay = "星期天";
			break;
		case 2:
			weekDay = "星期一";
			break;
		case 3:
			weekDay = "星期二";
			break;
		case 4:
			weekDay = "星期三";
			break;
		case 5:
			weekDay = "星期四";
			break;
		case 6:
			weekDay = "星期五";
			break;
		case 7:
			weekDay = "星期六";
			break;
		default:
			weekDay = "";
			break;
		}
		return weekDay;
	}

	/**
	 * @param addpart
	 *            为添加的部分可以为yy年数mm月份数dd天数hh小时数mi分钟数ss秒数
	 * @param ts
	 *            需要改动的日期类型
	 * @param addnum
	 *            添加数目 可以为负数
	 * @return 改动后的日期类型
	 */
	public static Timestamp dateAdd(String addpart, Timestamp ts, int addnum) {
		Calendar cal = convertTsToCal(ts);
		if ("yy".equals(addpart)) {
			cal.add(Calendar.YEAR, addnum);
		} else if ("mm".equals(addpart)) {
			cal.add(Calendar.MONTH, addnum);
		} else if ("dd".equals(addpart)) {
			cal.add(Calendar.DATE, addnum);
		} else if ("hh".equals(addpart)) {
			cal.add(Calendar.HOUR, addnum);
		} else if ("mi".equals(addpart)) {
			cal.add(Calendar.MINUTE, addnum);
		} else if ("ss".equals(addpart)) {
			cal.add(Calendar.SECOND, addnum);
		} else {
			return null;
		}
		return convertCalToTs(cal);
	}

	/**
	 * @param addpart
	 *            为添加的部分可以为yy年数mm月份数dd天数hh小时数mi分钟数ss秒数
	 * @param ts
	 *            需要改动的日期类型
	 * @param addnum
	 *            添加数目 可以为负数
	 * @return 改动后的日期类型
	 */
	public static Date dateAdd(String addpart, Date ts, int addnum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		if ("yy".equals(addpart)) {
			cal.add(Calendar.YEAR, addnum);
		} else if ("mm".equals(addpart)) {
			cal.add(Calendar.MONTH, addnum);
		} else if ("dd".equals(addpart)) {
			cal.add(Calendar.DATE, addnum);
		} else if ("hh".equals(addpart)) {
			cal.add(Calendar.HOUR, addnum);
		} else if ("mi".equals(addpart)) {
			cal.add(Calendar.MINUTE, addnum);
		} else if ("ss".equals(addpart)) {
			cal.add(Calendar.SECOND, addnum);
		} else {
			return null;
		}
		return cal.getTime();
	}

	/**
	 * 根据时间差返回字符串,例如"1天2小时30分"
	 *
	 * @param ts1
	 * @param ts2
	 * @return
	 */
	public static String dateDiff(Date ts1, Date ts2) {
		String result = "";
		if (ts1 != null && ts2 != null) {
			long between = Math.abs(ts1.getTime() - ts2.getTime()) / 1000;// 除以1000是为了转换成秒

			long day = between / (24 * 3600);
			long hour = between % (24 * 3600) / 3600;
			long minute = between % 3600 / 60;

			if (day > 0) {
				result += day + "天";
			}

			if (hour > 0) {
				result += hour + "小时";
			}

			if (minute > 0) {
				result += minute + "分";
			}
		}
		return result;
	}

	/**
	 * @param diffpart
	 *            比较部分YEAR为比较年份 MONTH为比较月份 DATE为比较天数 WEEK比较星期数,
	 *            如果传入的diffpart参数不为以上范围默认返回相差天数
	 * @param ts1
	 *            需要比较的日期
	 * @param ts2
	 *            需要比较的日期
	 * @return 相差的大小
	 */
	public static int dateDiff(String diffpart, Timestamp ts1, Timestamp ts2) {
		if ((ts1 == null) || (ts2 == null)) {
			return -1;
		}

		Date date1 = null;
		Date date2 = null;
		date1 = new Date(ts1.getTime());
		date2 = new Date(ts2.getTime());

		Calendar cal1 = null;
		Calendar cal2 = null;
		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();

		// different date might have different offset
		cal1.setTime(date1);
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET)
				+ cal1.get(Calendar.DST_OFFSET);

		cal2.setTime(date2);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET)
				+ cal2.get(Calendar.DST_OFFSET);

		// Use integer calculation, truncate the decimals
		int hr1 = (int) (ldate1 / 3600000); // 60*60*1000
		int hr2 = (int) (ldate2 / 3600000);

		int days1 = hr1 / 24;
		int days2 = hr2 / 24;

		int dateDiff = days2 - days1;
		int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1
				.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;
		int weekDiff = dateDiff / 7 + weekOffset;
		int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH)
				- cal1.get(Calendar.MONTH);

		if ("YEAR".equals(diffpart)) {
			return yearDiff;
		} else if ("MONTH".equals(diffpart)) {
			return monthDiff;
		} else if ("DATE".equals(diffpart)) {
			return dateDiff;
		} else if ("WEEK".equals(diffpart)) {
			return weekDiff;
		} else {
			return dateDiff;
		}
	}

	/**
	 * @param ts
	 * @return true表示ts为闰年 false表示ts不是闰年
	 */
	public static boolean isLeapyear(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		boolean booleanleapYear = ((GregorianCalendar) cal)
				.isLeapYear(getYear(ts));
		return booleanleapYear;
	}

	/**
	 * @param year
	 *            年
	 * @return true表示year为闰年 false表示ts不是闰年
	 */
	public static boolean isLeapyear(int year) {
		Calendar cal = Calendar.getInstance();
		return ((GregorianCalendar) cal).isLeapYear(year);
	}

	/**
	 * 比较传入的年月日是否与ts对应为同一天
	 *
	 * @param year
	 *            年份 传入－1表示不需要比较
	 * @param month
	 *            月份 传入－1表示不需要比较
	 * @param date
	 *            日期 传入－1表示不需要比较
	 * @param ts
	 *            完成的Timestamp日期类型
	 * @return
	 */
	public static boolean isMatchDate(int year, int month, int date,
			Timestamp ts) {
		int year1 = getYear(ts);
		int month1 = getMonth(ts);
		int date1 = getDay(ts);

		if ((year != -1) && (year != year1)) {
			return false;
		}
		if ((month != -1) && (month != month1)) {
			return false;
		}
		if ((date != -1) && (date != date1)) {
			return false;
		}
		return true;
	}

	/**
	 * 取得当前日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 去除时间后面的小时分秒，只取具体时间日期
	 *
	 * @param ts
	 * @return
	 */
	public static Timestamp formatToDate(Timestamp ts) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(ts.getTime());
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		cd.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cd.getTime().getTime());
	}

	/**
	 * 去除时间后面的小时分秒，只取具体时间日期
	 *
	 * @param dt
	 * @return
	 */
	public static java.util.Date formatToDate(java.util.Date dt) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(dt);
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		cd.set(Calendar.MILLISECOND, 0);
		return cd.getTime();
	}

	/**
	 * 去除时间后面的小时分秒，只取具体时间日期
	 *
	 * @param dt
	 * @return
	 */
	public static java.sql.Date formatToDate(java.sql.Date dt) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(dt.getTime());
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		cd.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cd.getTime().getTime());
	}

	/**
	 * 传入Timestamp类型日期 返回中文大写日期 如贰零零肆年五月拾捌日
	 *
	 * @param ts
	 *            Timestamp
	 * @return String
	 */
	public static String formatDateToCn(java.sql.Timestamp ts) {
		if (ts == null) {
			return "";
		}
		int year = getYear(ts);
		int month = getMonth(ts);
		int date = getDate(ts);

		String hanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆",
				"柒", "捌", "玖", "拾" };

		StringBuilder cnDate = new StringBuilder(20);

		String tempYear = String.valueOf(year);
		String tempMonth = String.valueOf(month);
		String tempDate = String.valueOf(date);

		if (tempYear.length() != 4) {
			return "";
		}

		for (int i = 0; i < tempYear.length(); i++) {
			cnDate.append(hanDigiStr[Integer.valueOf(
					tempYear.substring(i, i + 1)).intValue()]);
		}
		cnDate.append("年");

		if (month < 1 || month > 12 || date < 1 || date > 31) {
			return "";
		}
		// cnDate.append(StringUtil.positiveIntegerToHanStr(tempMonth));
		cnDate.append("月");
		// cnDate.append(StringUtil.positiveIntegerToHanStr(tempDate));
		cnDate.append("日");

		return cnDate.toString();
	}

	/**
	 * 取得指定月的上个月。
	 *
	 * @return
	 */
	public static String getLastMonth(String yearM) {
		String yymmdd = yearM + "01";
		Date date = DateUtil.valueOf(yymmdd, ISO_DATE_FORMAT);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, -1);
		// 设置时间为0时
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);

		String lastMonth = dateToString(cal.getTime(), ISO_DATE_FORMAT);

		return lastMonth.substring(0, lastMonth.length() - 2);
	}

	/**
	 * 取得指定月的前第十二个月。
	 *
	 * @return
	 */
	public static String getBeforTwelveMonth(String yearM) {
		String yymmdd = yearM + "01";
		Date date = DateUtil.valueOf(yymmdd, ISO_DATE_FORMAT);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, -12);
		// 设置时间为0时
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);

		String lastMonth = dateToString(cal.getTime(), ISO_DATE_FORMAT);

		return lastMonth.substring(0, lastMonth.length() - 2);
	}

	/**
	 * 根据时间变量返回时间字符串 yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 根据时间变量返回时间字符串
	 *
	 * @return 返回时间字符串
	 * @param pattern
	 *            时间字符串样式
	 * @param date
	 *            时间变量
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);
			return sfDate.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 长整型转换为日期java.util.Date
	 */
	public static Date valueOf(Long dateLong) {
		if (dateLong == null) {
			return null;
		}

		return new Date(dateLong);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 *
	 * @param dateString
	 *            字符串
	 */
	public static Date valueOf(String dateString) {
		return valueOf(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 *
	 * @param dateString
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date valueOf(String dateString, String format) {
		return valueOf(dateString, format, LENIENT_DATE);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 *
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @param lenient
	 *            日期越界标志
	 * @return
	 */
	public static Date valueOf(String dateText, String format, boolean lenient) {

		DateFormat df = null;
		try {
			if (format == null) {
				df = new SimpleDateFormat();
			} else {
				df = new SimpleDateFormat(format);
			}

			// setLenient avoids allowing dates like 9/32/2001
			// which would otherwise parse to 10/2/2001
			df.setLenient(lenient);

			return df.parse(dateText);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 根据当前月向前取月份。月份跨度由span指定
	 *
	 * @return 年月。For example:200907
	 */
	public static String getBeforeMonth(Date currentDate, int span) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, -span);

		// 设置时间为0时
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		String lastMonth = dateToString(cal.getTime(), "yyyyMM");

		return lastMonth;
	}

	/**
	 * 根据当前月向前取月份。月份跨度由span指定
	 *
	 * @return 年月。For example:200907
	 */
	public static String getAfterMonth(Date currentDate, int span) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, span);

		// 设置时间为0时
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(java.util.GregorianCalendar.MINUTE, 0);
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		String lastMonth = dateToString(cal.getTime(), "yyyyMM");

		return lastMonth;
	}

	/**
	 * 将毫秒数换算成x天x时x分x秒x毫秒
	 *
	 * @param ms
	 * @return
	 */
	public static String format(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : ""
				+ strMilliSecond;
		return strDay + "天," + strHour + "小时," + strMinute + "分钟," + strSecond
				+ "秒," + strMilliSecond + "毫秒";
	}

	/**
	 * 获取第i天后的日期
	 *
	 * @param date
	 * @param i
	 */
	public static Date getDateAfterIDay(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, i);
		return c.getTime();
	}

	/**
	 * 获取第i天前的日期
	 *
	 * @param date
	 * @param i
	 */
	public static Date getDateBeforeIDay(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1 * i);
		return c.getTime();
	}

	/**
	 * 获取第i小时后的日期
	 *
	 * @param date
	 * @param i
	 */
	public static Date getDateAfterIHours(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, i);
		return c.getTime();
	}

	/**
	 * 获取第i小时前的日期
	 *
	 * @param date
	 * @param i
	 */
	public static Date getDateBeforeIHours(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, -1 * i);
		return c.getTime();
	}

	/**
	 * 获取第i分钟后的日期
	 *
	 * @param date
	 * @param i
	 */
	public static Date getDateAfterIMinutes(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, i);
		return c.getTime();
	}

	/**
	 * 获取第i分钟前的日期
	 *
	 * @param date
	 * @param i
	 */
	public static Date getDateBeforeIMinutes(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, -1 * i);
		return c.getTime();
	}

	/**
	 * 获取第second秒钟后的日期
	 *
	 * @param date
	 * @param second
	 */
	public static Date getDateAfterISeconds(Date date, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}

	public static Date convertCalToTs(Date date, int hour, int minute,
			int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);

		return c.getTime();
	}

	/**
	 *
	 * 设置给定日期的时分秒
	 *
	 * @param c
	 *            需要调整的日期
	 * @param hour
	 *            设定的小时值
	 * @param minute
	 *            设定的分钟值
	 * @param second
	 *            设定的秒值
	 *
	 */
	public static void setHMS(Calendar c, int hour, int minute, int second) {
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
	}

	/**
	 * 设置给定日期的时分秒
	 *
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getDateWithSpecificHMS(Date date, int hour, int minute,
			int second) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setHMS(calendar, hour, minute, second);
		return calendar.getTime();
	}

	/**
	 *
	 * 设置给定日期的时分秒
	 *
	 * @param date
	 *            需要调整的日期
	 * @param minute
	 *            设定的分钟值
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	/**
	 *
	 * 设置给定日期的时分秒毫秒
	 *
	 * @param c
	 *            需要调整的日期
	 * @param hour
	 *            设定的小时值
	 * @param minute
	 *            设定的分钟值
	 * @param second
	 *            设定的秒值
	 * @param milliSecond
	 *            设定的毫秒秒值
	 */
	public static void setHMSM(Calendar c, int hour, int minute, int second,
			int milliSecond) {
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, milliSecond);
	}

	/**
	 * 获取日期类型的对应小时数
	 *
	 * @param date
	 *            传入的日期
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	

	public static boolean afterOrEqual(Date date1, Date date2) {
		return date1.after(date2) || date1.equals(date2);
	}

	public static boolean beforeOrEqual(Date date1, Date date2) {
		return date1.before(date2) || date1.equals(date2);
	}

	/**
	 * Jmock测试用，指定系统时间。<br>
	 * 只能在Jmock测试时使用，其它地方千万不要用<br>
	 *
	 * @param dateString
	 *            yyyy-MM-dd HH:mm:ss
	 */
	/*
	 * public static void setThreadLocalDate(String dateString) {
	 * TestNowTimeListener.setNowTime(Timestamp.valueOf(dateString)); }
	 */

	/**
	 * 设置指定日期的时分秒
	 *
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date setTimeOfDate(Date date, String time) {
		String[] times = time.split(":");
		int hour = times.length > 0 ? Integer.parseInt(times[0]) : 0;
		int minute = times.length > 1 ? Integer.parseInt(times[1]) : 0;
		int second = times.length > 2 ? Integer.parseInt(times[2]) : 0;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * 计算两个日期之间间隔的天数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Integer getDayInterval(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			return null;
		}

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
		beginCalendar.set(Calendar.MINUTE, 0);
		beginCalendar.set(Calendar.SECOND, 0);
		beginCalendar.set(Calendar.MILLISECOND, 0);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		endCalendar.set(Calendar.HOUR_OF_DAY, 0);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		endCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((endCalendar.getTime().getTime() - beginCalendar
				.getTime().getTime()) / 1000 / 60 / 60 / 24);
	}

	/**
	 * 计算两个日期之间间隔的分钟数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Integer getMinuteInterval(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			return null;
		}

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		beginCalendar.set(Calendar.SECOND, 0);
		beginCalendar.set(Calendar.MILLISECOND, 0);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		endCalendar.set(Calendar.SECOND, 0);
		endCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((endCalendar.getTime().getTime() - beginCalendar
				.getTime().getTime()) / 1000 / 60);
	}

	/**
	 * 返回时间跨越的周日期
	 */
	/*
	 * public static List<WeekRange> getWeeks(Date startDate, Date endDate) {
	 * LinkedList<WeekRange> list = new LinkedList<WeekRange>();
	 * 
	 * // 如果异常数据，就返回空 if (startDate.getTime() > endDate.getTime()) { return
	 * list; } Date start = startDate; do { list.addLast(getWeek(start)); start
	 * = DateUtils.addDays(list.getLast().getEnd(), 1); } while (start.getTime()
	 * <= endDate.getTime());
	 * 
	 * return list; }
	 *//**
	 * 返回时间对应的周
	 */
	/*
	 * public static WeekRange getWeek(Date focus) { WeekRange range = new
	 * WeekRange();
	 * 
	 * // 计算周的起点和终点 Calendar gval = DateUtils.toCalendar(focus); Calendar start
	 * = DateUtils.truncate(gval, Calendar.DATE); Calendar end =
	 * DateUtils.truncate(gval, Calendar.DATE); int startCutoff =
	 * Calendar.MONDAY; int endCutoff = Calendar.SUNDAY; while
	 * (start.get(Calendar.DAY_OF_WEEK) != startCutoff) {
	 * start.add(Calendar.DATE, -1); } while (end.get(Calendar.DAY_OF_WEEK) !=
	 * endCutoff) { end.add(Calendar.DATE, 1); }
	 * range.setStart(start.getTime()); range.setEnd(end.getTime());
	 * 
	 * // 计算年和周 calendarWeekFormat(end); range.setYear(end.get(Calendar.YEAR));
	 * range.setWeek(end.get(Calendar.WEEK_OF_YEAR));
	 * 
	 * return range; }
	 *//**
	 * 根据年，周，返回周对应的周一和周日的日期
	 */
	/*
	 * public static WeekRange getWeek(int year, int week) { WeekRange weekRange
	 * = new WeekRange(); weekRange.setYear(year); weekRange.setWeek(week);
	 * 
	 * Calendar cal = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE);
	 * calendarWeekFormat(cal);
	 * 
	 * cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); cal.set(Calendar.YEAR,
	 * year); cal.set(Calendar.WEEK_OF_YEAR, week);
	 * weekRange.setStart(cal.getTime()); cal.add(Calendar.DAY_OF_MONTH, 6);
	 * weekRange.setEnd(cal.getTime());
	 * 
	 * return weekRange; }
	 */

	/**
	 * 设置: 星期一为第一周的开始 一周有7天 对于夸年的周，一周的最小天数是 1
	 *
	 */
	private static void calendarWeekFormat(Calendar calendar) {
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(1);
	}
	
	
	 /** 
     * 将当前时间转换成UTC时间，类型为字符串<br /> 
     * 如果获取失败，返回null 
     * @return 
     */  
	public static Date getUTCTime(String dateFormat) {
		DateFormat format = new SimpleDateFormat(dateFormat);
		StringBuffer UTCTimeBuffer = new StringBuffer();
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		UTCTimeBuffer.append(year).append("-").append(month).append("-")
				.append(day);
		UTCTimeBuffer.append(" ").append(hour).append(":").append(minute)
				.append(":").append(second);
		try {
			return format.parse(UTCTimeBuffer.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}  
      
    /** 
     * 将UTC时间转换为东八区时间 
     * @param UTCTime 
     * @return 
     */  
    public static String getLocalTimeFromUTC(String UTCTime,String dateFormat){  
    	DateFormat format = new SimpleDateFormat(dateFormat);
        Date UTCDate = null ;  
        String localTimeStr = null ;  
        try {  
            UTCDate = format.parse(UTCTime);  
            format.setTimeZone(TimeZone.getTimeZone("GMT-8")) ;  
            localTimeStr = format.format(UTCDate) ;  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return localTimeStr ;  
    }  
	
	
	
	public static void main(String[] args) {
//		Date r = new Date();
//		Date d = DateUtil.addMinute(r, 5);
//		d.toString();
		
		//LogUtil.log.info(dateToString(valueOf("130130230533","yyMMddHHmmss"),"yyyy-MM-dd HH:mm:ss"));
		
		
		//LogUtil.log.info(getMilliseconds(getNowTime()));
		//LogUtil.log.info(dateToString(getUTCTime(DATETIME_PATTERN), YYYYMMDD_HHMMSS_DATA_FORMATE));
		
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYYMMDD_HHMMSS_DATA_FORMATE);
	        //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	        //LogUtil.log.info(simpleDateFormat.format(new Date()));
	}
}
