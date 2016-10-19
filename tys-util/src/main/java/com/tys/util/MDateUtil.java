package com.tys.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MDateUtil {

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
	 * 格式化日期
	 * 
	 * @param pattern
	 *            详情查看SimpleDateFormat javadoc
	 * @param time
	 * @return
	 */
	public static String format(String pattern, long time) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date(time));
	}

	/**
	 * 计算date2与date1相差分钟数
	 * 
	 * @param date1
	 * @param date2
	 * @return date2 < date1为负值，否则为正值
	 */
	public static int getMinSpace(Date date1, Date date2) {
		int result = (int) ((date2.getTime() - date1.getTime()) / (60 * 1000));
		return result;
	}

	/**
	 * 计算date2与date1相差日数
	 * 
	 * @param date1
	 * @param date2
	 * @return date2 < date1为负值，否则为正值
	 */
	public static int getDateSpace(Date date1, Date date2) {
		int result = (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
		return result;
	}

	/**
	 * 计算date2与date1相差日数(只取天数)
	 * 
	 * @param date1
	 * @param date2
	 * @return date2 < date1为负值，否则为正值
	 */
	public static int getDateSpacesExt(Date date1, Date date2) {
		int result = (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));// 取整天数

		int yu = (int) (date2.getTime() - date1.getTime()) % (60 * 60 * 1000);// 获取剩余小时
		Calendar c = Calendar.getInstance();
		c.setTime(date2);
		if ((c.get(Calendar.HOUR) + yu) >= 24) {
			result += 1;
		}
		return result;
	}

	/**
	 * 计算date2与date1相差月数
	 * 
	 * @param date1
	 * @param date2
	 * @return date2 < date1为负值，否则为正值
	 */
	public static int getMonthSpace(Date date1, Date date2) {
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		// 再加上年间隔
		int yearSpace = getYearSpace(date1, date2);
		result += yearSpace * 12;
		return result;
	}

	/**
	 * 计算date2与date1相差年数
	 * 
	 * @param date1
	 * @param date2
	 * @return date2 < date1为负值，否则为正值
	 */
	public static int getYearSpace(Date date1, Date date2) {
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		result = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		return result;
	}

	/**
	 * 日期增减天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.add(Calendar.DAY_OF_MONTH, day);
		return c1.getTime();
	}

	/**
	 * 日期增减月数
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.add(Calendar.MONTH, month);
		return c1.getTime();
	}

	/**
	 * 获取当月天数
	 */
	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取上月天数
	 */
	public static int getLastMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.add(Calendar.MONTH, -1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 得到指定月的天数
	 */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取当前时间字串
	 * 
	 * @return
	 */
	public static String formatDate() {
		return formatDate("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取指定格式当前时间字串
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatDate(String pattern) {
		return formatDate(pattern, new Date());
	}

	/**
	 * 获取指定格式时间字串
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String formatDate(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 解析时间字串
	 * 
	 * @param pattern
	 * @param text
	 * @return
	 */
	public static Date parseDateStr(String pattern, String text) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(text);
		} catch (ParseException ex) {
		}
		return new Date();
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
	 * @param ts
	 *            日期类型
	 * @return 传入日期类型的对应毫秒数
	 */
	public static Long getMilliseconds(Date date) {
		return date == null ? null : date.getTime();
	}

}
