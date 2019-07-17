package com.temp.common.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: DateUtils
 * @date Jun 28, 2019 11:37:04 AM
 * @author ToniR
 * @Description: 日期工具类
 */
@Slf4j
public class DateUtils {

	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYMM = "yyMM";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String YYYY年MM月 = "yyyy年MM月";
	public static final String DAY_LAST_TIME = "23:59:59";
	public static final String DAY_FIRST_TIME = "00:00:00";

	/** 1天的秒数 */
	public static final int ONE_DAY_SECONDS = 24 * 3600;
	/** 1小时的秒数 */
	public static final int ONE_HOUR_SECONDS = 3600;
	/** 1分钟的秒数 */
	public static final int ONE_MINUTE_SECONDS = 60;

	private final static int MONTH_DAYS[] = { 29, 30 };

	private final static int DAYS_MONTH[][] = { { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
			{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

	private final static int DATAS[][] = { { 23, 3, 2, 17, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0 },
			{ 41, 0, 4, 23, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1 },
			{ 30, 7, 5, 28, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1 },
			{ 49, 0, 6, 33, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 38, 0, 0, 38, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 26, 6, 2, 44, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 45, 0, 3, 49, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 35, 0, 4, 54, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 24, 4, 5, 59, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 43, 0, 0, 5, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
			{ 32, 0, 1, 10, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 21, 2, 2, 15, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 40, 0, 3, 20, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 28, 7, 5, 26, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 47, 0, 6, 31, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 36, 0, 0, 36, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 26, 5, 1, 41, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 44, 0, 3, 47, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 33, 0, 4, 52, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
			{ 23, 3, 5, 57, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1 },
			{ 42, 0, 6, 2, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1 },
			{ 30, 8, 1, 8, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0 },
			{ 48, 0, 2, 13, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0 },
			{ 38, 0, 3, 18, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 27, 6, 4, 23, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 45, 0, 6, 29, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0 },
			{ 35, 0, 0, 34, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 24, 4, 1, 39, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0 },
			{ 43, 0, 2, 44, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0 },
			{ 32, 0, 4, 50, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1 },
			{ 20, 3, 5, 55, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 },
			{ 39, 0, 6, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0 },
			{ 29, 7, 0, 5, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 47, 0, 2, 11, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 36, 0, 3, 16, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0 },
			{ 26, 5, 4, 21, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1 },
			{ 45, 0, 5, 26, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 33, 0, 0, 32, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1 },
			{ 22, 4, 1, 37, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1 },
			{ 41, 0, 2, 42, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1 },
			{ 30, 8, 3, 47, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1 },
			{ 48, 0, 5, 53, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 },
			{ 37, 0, 6, 58, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 27, 6, 0, 3, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0 },
			{ 46, 0, 1, 8, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
			{ 35, 0, 3, 14, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1 },
			{ 24, 4, 4, 19, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1 },
			{ 43, 0, 5, 24, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1 },
			{ 32, 10, 6, 29, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1 },
			{ 50, 0, 1, 35, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 39, 0, 2, 40, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1 },
			{ 28, 6, 3, 45, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0 },
			{ 47, 0, 4, 50, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 36, 0, 6, 56, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0 },
			{ 26, 5, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1 },
			{ 45, 0, 1, 6, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0 },
			{ 34, 0, 2, 11, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0 },
			{ 22, 3, 4, 17, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 40, 0, 5, 22, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 30, 8, 6, 27, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1 },
			{ 49, 0, 0, 32, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1 },
			{ 37, 0, 2, 38, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 27, 5, 3, 43, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 46, 0, 4, 48, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1 },
			{ 35, 0, 5, 53, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1 },
			{ 23, 4, 0, 59, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 42, 0, 1, 4, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 31, 0, 2, 9, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 21, 2, 3, 14, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 39, 0, 5, 20, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 28, 7, 6, 25, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 48, 0, 0, 30, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
			{ 37, 0, 1, 35, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 25, 5, 3, 41, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 44, 0, 4, 46, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 33, 0, 5, 51, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 22, 4, 6, 56, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 40, 0, 1, 2, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 30, 9, 2, 7, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 49, 0, 3, 12, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1 },
			{ 38, 0, 4, 17, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
			{ 27, 6, 6, 23, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1 },
			{ 46, 0, 0, 28, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0 },
			{ 35, 0, 1, 33, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 },
			{ 24, 4, 2, 38, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 42, 0, 4, 44, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 31, 0, 5, 49, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 21, 2, 6, 54, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 40, 0, 0, 59, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 28, 6, 2, 5, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0 },
			{ 47, 0, 3, 10, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1 },
			{ 36, 0, 4, 15, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1 },
			{ 25, 5, 5, 20, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 },
			{ 43, 0, 0, 26, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1 },
			{ 32, 0, 1, 31, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0 },
			{ 22, 3, 2, 36, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0 }

	};

	/**
	 * 根据阴历取得对应的阳历日期（该日期必须在1936—2028年之间）
	 *
	 * @param date 阴历日期（字符串）
	 * @return 返回对应的阳历日期
	 */
	public static Date getGregorianCalendar(String date) {
		Calendar c = getCalendar(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int index = year - 1936;
		int endIndex = month;

		if ((DATAS[index][1] != 0) && (month > DATAS[index][1])) {
			endIndex++;
		}

		int dayNum = 0;

		for (int i = 0; i < (endIndex - 1); i++) {
			dayNum += MONTH_DAYS[DATAS[index][4 + i]];
		}

		dayNum += day;
		dayNum += DATAS[index][0];

		int year_days = 365;

		if (isLeapYear(year)) {
			year_days = 366;
		}

		if (dayNum > year_days) {
			year++;
			dayNum -= year_days;
		}

		month = 1;

		int dayOfMonth[] = DAYS_MONTH[0];

		if (isLeapYear(year)) {
			dayOfMonth = DAYS_MONTH[1];
		}

		int i = 0;

		for (; i < 12; i++) {
			dayNum -= dayOfMonth[i];

			if (dayNum <= 0) {
				break;
			}

			month++;
		}

		day = dayOfMonth[i] + dayNum;

		return getDate(year + "-" + month + "-" + day);
	}

	/**
	 * 将一个秒数格式化为易于阅读的格式
	 * 
	 * @param second 秒数
	 * @return 格式化的字符串，如 3天14小时43分15秒
	 */
	public static String formatPrettySecond(int second) {
		int modSecond = second;
		int days = modSecond / ONE_DAY_SECONDS;
		modSecond = modSecond % ONE_DAY_SECONDS;

		int hours = modSecond / ONE_HOUR_SECONDS;
		modSecond = modSecond % ONE_HOUR_SECONDS;

		int minutes = modSecond / ONE_MINUTE_SECONDS;
		modSecond = modSecond % ONE_MINUTE_SECONDS;

		StringBuilder sb = new StringBuilder();
		if (days > 0) {
			sb.append(days).append("天");
		}
		if (hours > 0) {
			sb.append(hours).append("小时");
		}
		if (minutes > 0) {
			sb.append(minutes).append("分");
		}
		if (modSecond > 0) {
			sb.append(modSecond).append("秒");
		}

		return sb.toString();
	}

	/**
	 * 将一个秒数格式化为易于阅读的格式
	 * 
	 * @param second 秒数
	 * @return 格式化的字符串，如 3天14小时43分15秒
	 */
	public static String formatForDisplaySecond(int second) {
		int modSecond = second;
		int days = modSecond / ONE_DAY_SECONDS;
		modSecond = modSecond % ONE_DAY_SECONDS;

		int hours = modSecond / ONE_HOUR_SECONDS;
		modSecond = modSecond % ONE_HOUR_SECONDS;

		int minutes = modSecond / ONE_MINUTE_SECONDS;
		modSecond = modSecond % ONE_MINUTE_SECONDS;

		StringBuilder sb = new StringBuilder();
		if (days > 0) {
			sb.append(days).append("天");
			if (hours > 0) {
				sb.append(hours).append("小时");
			}
		} else if (hours > 0) {
			sb.append(hours).append("小时");
			if (minutes > 0) {
				sb.append(minutes).append("分");
			}
		} else if (minutes > 0) {
			sb.append(minutes).append("分");
			if (modSecond > 0) {
				sb.append(modSecond).append("秒");
			}
		} else {
			sb.append(modSecond).append("秒");
		}

		return sb.toString();
	}

	/**
	 * 根据阳历取得对应的阴历日期（该日期必须在1936—2028年之间）
	 *
	 * @param date 阳历日期（字符串）
	 * @return 返回阴历日期
	 */
	public static Date getLunarCalendar(String date) {
		Calendar calendar = getCalendar(date);
		int year = calendar.get(Calendar.YEAR);
		int month = 1;
		int day;

		if ((year < 1936) || (year > 2028)) {
			return null;
		}

		int index = year - 1936;
		int l_days = DATAS[index][0];
		int day_year = calendar.get(Calendar.DAY_OF_YEAR);
		int days;

		if (day_year >= l_days) {
			days = day_year - l_days;
		} else {
			index--;
			year--;

			Calendar c = getCalendar(year + "-12-31");
			days = (c.get((Calendar.DAY_OF_YEAR)) + day_year) - DATAS[index][0];
		}

		int i = 0;
		int day_num = 0;

		for (; i < 13; i++) {
			day_num += MONTH_DAYS[DATAS[index][i + 4]];

			if (day_num >= days) {
				break;
			}

			month++;
		}

		day = MONTH_DAYS[DATAS[index][i + 4]] - (day_num - days);

		if ((DATAS[index][1] != 0) && (month > DATAS[index][1])) {
			month--;
		}

		return getDate(year + "-" + month + "-" + day);
	}

	public static Date getDate(String date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dd = null;

		try {
			dd = format.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dd;
	}

	/**
	 * 是否是闰年
	 * 
	 * @param year 年份
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 400) == 0) {
			return true;
		} else if ((year % 100) == 0) {
			return false;
		} else if ((year % 4) == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 将一个日期格式化为 yyyy-MM-dd格式
	 * 
	 * @param date
	 * @return 如果Date为空,返回""
	 */
	public static String format(Date date) {
		return format(date, DateUtils.YYYY_MM_DD);
	}

	/**
	 * 格式化日期为字符串
	 * 
	 * @param date
	 * @param fmt  格式,可以使用DateUtils类的常量串
	 * @return 如果Date为空或格式异常,返回""
	 */
	public static String format(Date date, String fmt) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	/**
	 * 将一个日期字符串转为Date对象
	 * <p>
	 * 兼容日期格式:<br>
	 * yyyy-MM-dd <br>
	 * yyyy-MM-dd HH:mm:ss <br>
	 * yyyy-MM <br>
	 * yyyy-MM-dd HH <br>
	 * yyyy-MM-dd HH:mm <br>
	 * HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parse(String dateStr) {
		String fmt = getFmtStr(dateStr);
		if (StringUtils.isEmpty(fmt)) {
			return null;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			return sdf.parse(dateStr);
		} catch (Exception ex) {
			log.error("转换日期出错:" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * 将一个整数转为日期类型
	 * 
	 * @param time
	 * @return
	 */
	public static Date parse(Integer time, Date def) {
		if (time == null) {
			return def;
		}
		Date date = new Date();
		date.setTime(time);
		return date;
	}

	/**
	 * 将一个日期转为数字
	 * 
	 * @param d
	 * @return
	 */
	public static long toTime(Date d, long def) {
		if (d == null) {
			return def;
		}

		return d.getTime();
	}

	/**
	 * 根据日期字符串的长度,返回字符串的格式。
	 * <p>
	 * 目前支持的格式为：<br>
	 * yyyy-MM-dd <br>
	 * yyyy-MM-dd HH:mm:ss <br>
	 * yyyy-MM <br>
	 * yyyy-MM-dd HH <br>
	 * yyyy-MM-dd HH:mm <br>
	 * HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getFmtStr(String dateStr) {
		if (StringUtils.isEmpty(dateStr)) {
			return "";
		}

		int len = dateStr.trim().length();
		if (len == 10) {
			return YYYY_MM_DD;
		} else if (len == 19) {
			return YYYY_MM_DD_HH_MM_SS;
		} else if (len >= 21 && len <= 23) {
			return YYYY_MM_DD_HH_MM_SS_SSS;
		} else if (len == 7) {
			return YYYY_MM;
		} else if (len == 8) {
			return HH_MM_SS;
		} else if (len == 13) {
			return YYYY_MM_DD_HH;
		} else if (len == 14) {
			return YYYYMMDDHHMMSS;
		} else if (len == 16) {
			return YYYY_MM_DD_HH_MM;
		} else {
			return "";
		}
	}

	/**
	 * 给一个时间加减月份
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonths(Date date, int month) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 给一个时间加减年份
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date addYears(Date date, int year) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	 * 给一个时间加减天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date, int day) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static Date addHour(Date date, int hour) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	public static Date addMinute(Date date, int minute) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	public static Date addSecond(Date date, int second) {
		Calendar cal = getCalendar(date);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 获取日历对象
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		return calendar;
	}

	/**
	 * 获取日历对象
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(String date) {
		Date d = parse(date);
		return getCalendar(d);
	}

	/**
	 * 返回上个月的最后一天的数值
	 * 
	 * @return
	 */
	public static int getMaxDayOfLastMonth() {
		Date now = new Date();
		Date lastMonth = DateUtils.addMonths(now, -1);
		lastMonth = getMaxDateOfMonth(lastMonth);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastMonth);
		int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
		return maxDay;
	}

	/**
	 * 获取上个月的年份
	 * 
	 * @return
	 */
	public static int getYearOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int yearOfLastMonth = calendar.get(Calendar.YEAR);
		return yearOfLastMonth;
	}

	/**
	 * 获取上个月的月份
	 * 
	 * @return
	 */
	public static int getMonthOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int lastMonth = calendar.get(Calendar.MONTH) + 1;
		return lastMonth;
	}

	/**
	 * 获取今年的年份
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取当前日期(一个月中的几号)
	 * 
	 * @return
	 */
	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 时间校验: 开始时间不能大于当前时间.
	 * 
	 * @param startDate
	 */
	public static Date validateStartDate(Date startDate) {
		Date today = new Date();
		// 开始时间不能大于当前时间.
		if (startDate.compareTo(today) == 1) {
			startDate = today;
		}
		return startDate;
	}

	/**
	 * 时间校验: 不能晚于当前时间(如果晚于当前时间，则替换为当前时间)
	 * 
	 * @param myDate
	 */
	public static Date notAfterNow(Date myDate) {
		Date today = new Date();
		if (myDate.after(today)) {
			myDate = today;
		}
		return myDate;
	}

	/**
	 * 时间校验: 不能晚于昨天(如果晚于昨天，则替换为昨天)
	 * 
	 * @param myDate
	 */
	public static Date notAfterYesterday(Date myDate) {
		Date today = new Date();
		Date yesterday = addDays(today, -1);
		;
		// 3. 结束时间不能大于昨天.
		if (myDate.after(yesterday)) {
			myDate = yesterday;
		}
		return myDate;
	}

	/**
	 * 时间校验: 不能晚于上一个月(如果晚于上一个月，则替换为上一个月)
	 * 
	 * @param myDate
	 */
	public static Date notAfterLastMonth(Date myDate) {
		Date today = new Date();
		Date lastMonth = DateUtils.addMonths(today, -1);
		lastMonth = DateUtils.getMaxDateOfMonth(lastMonth);
		// 3. 结束时间不能大于上一个月.
		if (myDate.after(lastMonth)) {
			myDate = lastMonth;
		}
		return myDate;
	}

	/**
	 * 时间校验: 不能晚于上一年(如果晚于上一年，则替换为上一年)
	 * 
	 * @param myDate
	 */
	public static Date notAfterLastYear(Date myDate) {
		Date today = new Date();
		Date lastYear = DateUtils.addYears(today, -1);
		lastYear = DateUtils.getMaxDateOfYear(lastYear);
		// 3. 结束时间不能大于上一年.
		if (myDate.after(lastYear)) {
			myDate = lastYear;
		}
		return myDate;
	}

	/**
	 * 时间校验: myDate不能早于basicDate(如果早于basicDate，则替换为basicDate)
	 * 
	 * @param myDate
	 * @param basicStr
	 * @return
	 * @throws Exception
	 */
	public static Date notBefore(Date myDate, String basicStr) throws Exception {
		Date basicDate = parse(basicStr);
		// Date today = new Date();
		// Date yesterday = DateUtils.addDays(today, -1);;
		// 3. 结束时间不能大于昨天.
		if (myDate.before(basicDate)) {
			myDate = basicDate;
		}
		return myDate;
	}

	/**
	 * 将传入的年月日转化为Date类型
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date toDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/**
	 * 将字符串转化为日期(从一种格式到另一种格式)。
	 * 
	 * @param str
	 * @param pattern1
	 * @param pattern2
	 * @return
	 */
	public static String StringPatternToPattern(String str, String pattern1, String pattern2) {
		Date dateTime = null;
		String productStr = "";
		try {
			if (!(str == null || str.equals(""))) {
				SimpleDateFormat formater = new SimpleDateFormat(pattern1);
				dateTime = formater.parse(str);

				SimpleDateFormat formater1 = new SimpleDateFormat(pattern2);
				productStr = formater1.format(dateTime);
			}
		} catch (Exception e) {
			log.error("exception", e);
		}
		return productStr;
	}

	/**
	 * 获取当下时间
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 获取当下的时间字符串
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String nowStr() {
		return format(now(), DateUtils.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取当下的时间字符串
	 * 
	 * @param fmt 日期格式
	 * @return
	 */
	public static String nowStr(String fmt) {
		return format(now(), fmt);
	}

	/**
	 * 取得一个date对象对应的日期的0分0秒时刻的Date对象。
	 */
	public static Date getMinDateOfHour(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getMinDateOfHour(date));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		return calendar.getTime();
	}

	/**
	 * 取得一年中的最早一天。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMinDateOfYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一年中的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一周中的最早一天。
	 * 
	 * @param date
	 * @param locale
	 * @return
	 */
	public static Date getMinDateOfWeek(Date date, Locale locale) {
		if (date == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

		if (locale == null)
			locale = Locale.CHINESE;
		Date tmpDate = calendar.getTime();
		if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
			if (day_of_week == 1) {// 星期天
				tmpDate = DateUtils.addDays(tmpDate, -6);
			} else {
				tmpDate = DateUtils.addDays(tmpDate, 1);
			}
		}

		return tmpDate;
	}

	/**
	 * 取得一周中的最后一天
	 * 
	 * @param date
	 * @param locale
	 * @return
	 */
	public static Date getMaxDateOfWeek(Date date, Locale locale) {
		if (date == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		if (locale == null)
			locale = Locale.CHINESE;
		Date tmpDate = calendar.getTime();
		if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
			if (day_of_week == 1) {// 星期天
				tmpDate = DateUtils.addDays(tmpDate, -6);
			} else {
				tmpDate = DateUtils.addDays(tmpDate, 1);
			}
		}

		return tmpDate;
	}

	/**
	 * 取得一月中的最早一天。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMinDateOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一月中的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的小时的59分59秒时刻的Date对象。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfHour(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 获取2个时间相隔几秒
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getIntervalSecond(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return -1;

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long timeNumber = -1L;
		long TIME = 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			log.error("exception", e);
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几分钟
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getIntervalMinute(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return -1;

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long timeNumber = -1l;
		long TIME = 60L * 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			log.error("exception", e);
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几小时
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getIntervalHour(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return -1;

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long timeNumber = -1l;
		long TIME = 60L * 60L * 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			log.error("exception", e);
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getIntervalDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return -1;

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long dayNumber = -1L;
		long DAY = 24L * 60L * 60L * 1000L;
		try {
			// "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算三天
			dayNumber = (endDate.getTime() + 1000 - startDate.getTime()) / DAY;

		} catch (Exception e) {
			log.error("exception", e);
		}
		return (int) dayNumber;
	}

	/**
	 * 获取2个时间相隔几月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getIntervalMonth(Date startDate, Date endDate) {
		int result = 0;
		try {
			if (startDate == null || endDate == null)
				return -1;

			// swap start and end date
			if (startDate.after(endDate)) {
				Date tmp = endDate;
				endDate = startDate;
				startDate = tmp;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);

			int monthS = calendar.get(Calendar.MONTH);
			int yearS = calendar.get(Calendar.YEAR);

			calendar.setTime(endDate);
			int monthE = calendar.get(Calendar.MONTH);
			int yearE = calendar.get(Calendar.YEAR);

			if (yearE - yearS == 0) {
				result = monthE - monthS;
			} else {
				result = (yearE - yearS - 1) * 12 + (12 - monthS) + monthE;
			}

		} catch (Exception e) {
			log.error("exception", e);
		}
		return result;
	}

	/**
	 * 获取2个时间相隔几年
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getIntervalYear(Date startDate, Date endDate) {
		int result = 0;
		try {
			if (startDate == null || endDate == null)
				return -1;

			// swap start and end date
			if (startDate.after(endDate)) {
				Date tmp = endDate;
				endDate = startDate;
				startDate = tmp;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			int yearS = calendar.get(Calendar.YEAR);

			calendar.setTime(endDate);
			int yearE = calendar.get(Calendar.YEAR);

			result = yearE - yearS;

		} catch (Exception e) {
			log.error("exception", e);
		}
		return result;
	}

	/**
	 * 获取一个生日的年龄
	 * 
	 * @param birthday
	 * @return
	 */
	public static int getAge(String birthday) {
		Date d = parse(birthday);
		if (d == null) {
			return 0;
		}
		int age = getIntervalYear(new Date(), d);
		return Math.abs(age);
	}

	/**
	 * 按天拆分时间
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> splitDateByDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return null;

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(startDate);

		int num = getIntervalDay(startDate, endDate);
		for (int i = 1; i <= num; i++) {
			dateList.add(DateUtils.addDays(startDate, i));
		}

		return dateList;
	}

	/**
	 * 按月拆分时间
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> splitDateByMonth(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();

		if (startDate == null || endDate == null) {
			return dateList;
		}

		dateList.add(startDate);
		int num = getIntervalMonth(startDate, endDate);
		for (int i = 1; i <= num; i++) {
			dateList.add(DateUtils.addMonths(startDate, i));
		}

		return dateList;
	}

	/**
	 * 按年拆分时间
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> splitDateByYear(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();

		if (startDate == null || endDate == null) {
			return dateList;
		}

		dateList.add(startDate);
		int num = getIntervalYear(startDate, endDate);
		for (int i = 1; i <= num; i++) {
			dateList.add(DateUtils.addYears(startDate, i));
		}

		return dateList;
	}

	/**
	 * 本季度
	 */
	public static List<Date> getCurrentQuarter() {
		List<Date> dateList = new ArrayList<Date>();
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);// 一月为0

		dateList.add(1, calendar.getTime());// 结束时间设置为当前时间

		if (month >= 0 && month <= 2) {// 第一季度
			calendar.set(Calendar.MONTH, 0);
		} else if (month >= 3 && month <= 5) {// 第二季度
			calendar.set(Calendar.MONTH, 3);
		} else if (month >= 6 && month <= 8) {// 第三季度
			calendar.set(Calendar.MONTH, 6);
		} else {// 第四季度
			calendar.set(Calendar.MONTH, 9);
		}

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		dateList.add(0, calendar.getTime());

		return dateList;
	}

	/**
	 * 上季度
	 */
	public static List<Date> getLastQuarter() {
		List<Date> dateList = new ArrayList<Date>();
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);// 一月为0

		// 如果是第一季度则返回去年的第四季度
		if (month >= 0 && month <= 2) {// 当前第一季度
			calendar.add(Calendar.YEAR, -1);// 退到去年
			calendar.set(Calendar.MONTH, 9);// 去年十月
		} else if (month >= 3 && month <= 5) {// 当前第二季度
			calendar.set(Calendar.MONTH, 0);
		} else if (month >= 6 && month <= 8) {// 当前第三季度
			calendar.set(Calendar.MONTH, 3);
		} else {// 当前第四季度
			calendar.set(Calendar.MONTH, 6);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		dateList.add(0, calendar.getTime());

		calendar.add(Calendar.MONTH, 3);// 加3个月，到下个季度的第一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);// 退一天，得到上季度的最后一天
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		dateList.add(1, calendar.getTime());

		return dateList;
	}

	/**
	 * 返回2个日期中的大者
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date max(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	 * 返回不大于date2的日期 如果 date1 &gt;= date2 返回date2 如果 date1 &lt; date2 返回date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date ceil(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date2;
		} else {
			return date1;
		}
	}

	/**
	 * 返回不小于date2的日期 如果 date1 &gt;= date2 返回date1 如果 date1 &lt; date2 返回date2
	 */
	public static Date floor(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	 * 返回2个日期中的小者
	 */
	public static Date min(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date2;
		} else {
			return date1;
		}
	}

	/**
	 * 判断输入日期是否是一天中的最大时刻
	 */
	public static boolean isMaxDayOfDay(Date date1, String precision) {
		if (date1 == null)
			return false;
		Date date2 = getMaxDateOfDay(date1);
		int diffNum = 0;
		if ("HH".equals(precision)) {
			diffNum = getIntervalHour(date1, date2);
		} else if ("mm".equals(precision)) {
			diffNum = getIntervalMinute(date1, date2);
		} else {
			diffNum = getIntervalSecond(date1, date2);
		}
		return diffNum == 0;
	}

	/**
	 * 判断输入日期是否是一天中的最小时刻
	 */
	public static boolean isMinDayOfDay(Date date1, String precision) {
		if (date1 == null)
			return false;
		Date date2 = getMinDateOfDay(date1);
		int diffNum = 0;
		if ("HH".equals(precision)) {
			diffNum = getIntervalHour(date1, date2);
		} else if ("mm".equals(precision)) {
			diffNum = getIntervalMinute(date1, date2);
		} else {
			diffNum = getIntervalSecond(date1, date2);
		}
		return diffNum == 0;
	}

	/**
	 * 判断输入日期是否是一天中的最大时刻
	 */
	public static boolean isMaxDayOfDay(Date date1) {
		if (date1 == null)
			return false;
		Date date2 = getMaxDateOfDay(date1);
		int secondNum = getIntervalSecond(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 判断输入日期是否是一天中的最小时刻
	 */
	public static boolean isMinDayOfDay(Date date1) {
		if (date1 == null)
			return false;
		Date date2 = getMinDateOfDay(date1);
		int secondNum = getIntervalSecond(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 判断输入日期是否是一月中的最大时刻
	 */
	public static boolean isMaxDayOfMonth(Date date1) {
		if (date1 == null)
			return false;
		Date date2 = getMaxDateOfMonth(date1);
		int secondNum = getIntervalSecond(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 判断输入日期是否是一月中的最小时刻
	 */
	public static boolean isMinDayOfMonth(Date date1) {
		if (date1 == null)
			return false;
		Date date2 = getMinDateOfMonth(date1);
		int secondNum = getIntervalSecond(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 输入日期是否为同一天.
	 */
	public static boolean isTheSameDay(Date startDate, Date endDate) {
		String startDateStr = format(startDate);
		String endDateStr = format(endDate);
		return startDateStr.equals(endDateStr);
	}

	/**
	 * 功能：获取昨天最大时间。 输入: 2010-01-31 00:00:00 返回：2010-01-30 23:59:59
	 */
	public static Date getLastMaxDay(Date startDate) {
		if (startDate == null) {
			return null;
		}
		startDate = DateUtils.addDays(startDate, -1);
		return DateUtils.getMaxDateOfDay(startDate);
	}

	/**
	 * startStr 或者 startStr-endStr
	 */
	public static String getDifferentStr(String startStr, String endStr) {
		String dateRangeStr = "";
		if (startStr.equals(endStr)) {
			dateRangeStr = startStr;
		} else {
			dateRangeStr = startStr + "-" + endStr;
		}
		return dateRangeStr;
	}

	/**
	 * 给定一个日期和天数，得到这个日期+天数的日期
	 *
	 * @param date 指定日期
	 * @param num  天数
	 * @return
	 */
	public static String getNextDay(String date, int num) {
		Date d = parse(date);
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		ca.setTime(d);

		int day = ca.get(Calendar.DATE);
		day = day + num;
		ca.set(Calendar.DATE, day);
		return format(ca.getTime());

	}

	/**
	 * 获取给定日期的下一个月的日期的最晚时间
	 *
	 * @param startDate
	 * @return
	 */
	public static Date getNextMonthDay(Date startDate) {
		// 是不是
		// int month = startDate.getMonth();
		Date monthEndDate = getMaxDateOfMonth(startDate);
		Date nextMonth = DateUtils.addMonths(startDate, 1);
		nextMonth = parse(format(startDate) + " " + DAY_LAST_TIME);
		if (isTheSameDay(startDate, monthEndDate)) {
			nextMonth = getMaxDateOfMonth(nextMonth);
		}
		return nextMonth;
	}

}
