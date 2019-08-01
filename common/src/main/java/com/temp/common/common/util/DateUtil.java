package com.temp.common.common.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	private static  final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	public static String dateDisplay(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = new Date(time);
		return format.format(d);
	}

	/**
	 * 日期转换：指定的日期转换为形如yyyy-mm-dd hh:mm的格式
	 * @param d 需要显示的日期
	 * @return
	 */
	public static String dateDisplay(Date d) {
		if (null != d) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return format.format(d);
		} else {
			return "";
		}
	}
	
	public static String dateDisplay_1(Date d) {
		if (null != d) {
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
			return format.format(d);
		} else {
			return "";
		}
	}

	/**
	 * 获取完整是时间，格式yyyy-mm-dd hh:mm:ss
	 * @param d
	 * @return
	 */
	public static String getFullTime(Date d) {
		if (null != d) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss:SSS");//注：如果是hh:mm:ss:SSS则是１２小时制
			return format.format(d);
		} else {
			return "";
		}
	}

	public static String getYMDHMS(Date d) {
		if (null != d) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");//注：如果是hh:mm:ss则是１２小时制
			return format.format(d);
		} else {
			return "";
		}
	}

	/**
	 * 日期转换：指定的日期转换为形如yyyy-mm-dd的格式
	 * @param d 需要显示的日期
	 * @return
	 */
	public static String dateDisplayToYMD(Date d) {
		if (null != d) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(d);
		} else {
			return "";
		}
	}

	/**
	 * 日期转换：指定的日期转换为形如yyyy-mm的格式
	 * @param d 需要显示的日期
	 * @return
	 */
	public static String dateDisplayToYM(Date d) {
		if (null != d) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			return format.format(d);
		} else {
			return "";
		}
	}

	/**
	 * 日期转换：指定的日期转换为形如yyyymmdd的格式
	 * @param d 需要显示的日期
	 * @return
	 */
	public static String dateDisplayToYyyymmdd(Date d) {
		if (null != d) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			return format.format(d);
		} else {
			return "";
		}
	}

	/**
	 * 当前日期转换为“yyyy-mm-dd”的格式
	 * @return
	 */
	public static String dateDisplay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		return format.format(new Date());
	}

	/**
	 * 获得时间
	 * @param key
	 * @return
	 */
	public static String getTime(String key) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {

			return String.valueOf(format.parse(key).getTime());
		} catch (ParseException e) {
			LOGGER.info("DateUtil->getTime():" + e.toString());
			return "0";
		}
	}

	/**
	 *获得指定格式的日期
	 * @param key
	 * @return
	 */
	public static String getDisplayTime(String key) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		try {
			Date date = new Date(Long.parseLong(key));
			return format.format(date);
		} catch (Exception e) {
			LOGGER.info("DateUtil->getDisplayTime(String key):" + e.toString());
			return "0";
		}
	}

	/**
	 *获得指定格式的日期
	 * @param key
	 * @return
	 */
	public static Date getDate(String key) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// Date date = new Date(Long.parseLong(key));
			Date date = format.parse(key);
			return format.parse(key);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("DateUtil->getDate(String key):" + e.toString());
			return null;
		}
	}

	/**
	 * 将字符型日期加时间转成日期加时间类型
	 * @param key String
	 * @return Date
	 */
	public static Date getDateAndTime(String key) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			// Date date = new Date(Long.parseLong(key));
			Date date = format.parse(key);
			return format.parse(key);
		} catch (Exception e) {
			LOGGER.info("DateUtil->getDateAndTime(String key):" + e.toString());
			return null;
		}

	}
	/**
	 * 将字符型日期加时间转成日期加时间类型
	 * @param key String
	 * @return Date
	 */
	public static Date getDateYYMMDDhhmmss(String key) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
		try {
			// Date date = new Date(Long.parseLong(key));
			Date date = format.parse(key);
			return format.parse(key);
		} catch (Exception e) {
			LOGGER.info("DateUtil->getDateAndTime(String key):" + e.toString());
			return null;
		}

	}

	/**
	 * 将字符型日期加时间转成日期加时间类型
	 * @param key String
	 * @return Date
	 */
	public static Date getXMLDateAndTime(String key) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			// Date date = new Date(Long.parseLong(key));
			Date date = format.parse(key);
			return format.parse(key);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("DateUtil->getDateAndTime(String key):" + e.toString());
			return null;
		}

	}

	/**
	 * 将字符型日期加时间转成日期加时间类型
	 * @return Date
	 */
	public static String getXMLDateAndTimeStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			return format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("DateUtil->getXMLDateAndTimeStr(Date date):"
					+ e.toString());
			return null;
		}

	}

	/**
	 * 获得指定格式的日期时间格式
	 * @param time
	 * @return
	 */
	public static String getTime(long time) {
		StringBuffer str = new StringBuffer();
		int hour = (int) time / (60 * 60 * 1000);
		int minute = (int) (time % (60 * 60 * 1000)) / (60 * 1000);
		int second = (int) (time - hour * (60 * 60 * 1000) - minute
				* (60 * 1000)) / 1000;

		if (hour > 0) {
			str.append(hour + "小时:");
		}
		if (minute > 0) {
			str.append(minute + "分种");
		}
		if (second >= 0) {
			str.append(second + "秒");
		}
		return str.toString();
	}

	/**
	 * 将dd/mm/yyyy类型的字符串转化成yyyy-mm-dd
	 * @param value String
	 * @return String
	 */
	public static String getDateByString(String value) {
		if (value == null || "".equals(value)) {
			return value;
		}
		value = value.substring(0, 10);
		String[] date = value.split("/");
		String mm = "";
		String dd = "";
		String yyyy = date[2].trim().substring(0, 4);
		if (date[0].trim().length() < 2) {
			mm = "0" + date[0].trim();
		} else {
			mm = date[0].trim();
		}
		if (date[1].trim().length() < 2) {
			dd = "0" + date[1].trim();
		} else {
			dd = date[1].trim();
		}
		value = yyyy + "-" + mm + "-" + dd;
		return value;
	}

	/**
	 *获得带毫秒的字符串
	 * @return String
	 */
	public static String getDateString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return format.format(new Date());
	}
	
	/**
	   *获得年月日时分的字符串 时间以24小时计算
	   * @return String
	   */
	  public static String getDateString2() {
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddkkmm");
	    return format.format(new Date());
	  }

	public final static String svDatePatten = "yyyy-MM-dd";

	public final static String svDateTimePatten = "yyyy-MM-dd HH:mm";

	public final static String svLongDatePatten = "yyyy-MM-dd";

	public final static String svLongDateTimePatten = "yyyy-MM-dd HH:mm";

	public final static String svDateTimeFilePattern = "yyyy-MM-dd HHmmss ";

	private final static SimpleDateFormat mvDateFormatter = new SimpleDateFormat(
			svDatePatten);

	private final static SimpleDateFormat mvDateTimeFormatter = new SimpleDateFormat(
			svDateTimePatten);

	private final static SimpleDateFormat mvLongDateFormatter = new SimpleDateFormat(
			svLongDatePatten);

	private final static SimpleDateFormat mvLongDateTimeFormatter = new SimpleDateFormat(
			svLongDateTimePatten);

	/*****Date function start***/
	public static java.util.Date getCurrentDate() {
		return new java.util.Date(System.currentTimeMillis());
	}

	public static java.util.Date getYesterday() {
		return rollDay(getCurrentDate(), -1);
	}

	public static java.util.Date getTomorrow() {
		return rollDay(getCurrentDate(), 1);
	}

	private static java.util.Date rollDay(java.util.Date pDate, int pDay) {
		java.util.Calendar lvCal = java.util.Calendar.getInstance();
		lvCal.setTime(pDate);
		lvCal.add(lvCal.HOUR, pDay * 24);
		lvCal.set(lvCal.HOUR, 0);
		lvCal.set(lvCal.MINUTE, 0);
		lvCal.set(lvCal.SECOND, 0);
		lvCal.set(lvCal.MILLISECOND, 0);
		return lvCal.getTime();
	}

	public static java.sql.Timestamp getCurrentTimeStamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	public static String formatDate(java.util.Date p1) {
		return mvDateFormatter.format(p1);
	}

	public static String formatDate(java.sql.Date p1) {
		if (p1 != null) {
			java.util.Date lvDate = new java.util.Date(p1.getTime());
			return mvDateFormatter.format(lvDate);
		} else
			return null;

	}

	public static String formatDateTime(java.util.Date p1) {
		if (p1 != null)
			return mvDateTimeFormatter.format(p1);
		else
			return null;
	}

	public static String formatDateTime(java.sql.Date p1) {
		if (p1 != null) {
			java.util.Date lvDate = new java.util.Date(p1.getTime());
			return mvDateTimeFormatter.format(lvDate);
		} else
			return null;
	}

	public static String formatLongDate(java.util.Date p1) {
		if (p1 != null)
			return mvLongDateFormatter.format(p1);
		else
			return null;
	}

	public static String formatLongDateTime(java.util.Date p1) {
		if (p1 != null)
			return mvLongDateTimeFormatter.format(p1);
		else
			return null;
	}

	public static java.util.Date parseDate(String p1)
			throws java.text.ParseException {
		return mvDateFormatter.parse(p1);
	}

	public static java.sql.Date parseSqlDate(String p1)
			throws java.text.ParseException {
		return new java.sql.Date(mvDateFormatter.parse(p1).getTime());
	}

	public static java.util.Date parseDateTime(String p1)
			throws java.text.ParseException {
		return mvDateTimeFormatter.parse(p1);
	}

	public static java.sql.Timestamp parseSqlTime(String p1)
			throws java.text.ParseException {
		return new java.sql.Timestamp(mvDateTimeFormatter.parse(p1).getTime());
	}

	public static java.util.Date convertSqlToUtilDate(java.sql.Date p1) {
		return new java.util.Date(p1.getTime());
	}

	public static java.sql.Date convertUtilToSqlDate(java.util.Date p1) {
		return new java.sql.Date(p1.getTime());
	}
	/**
	 * 获取两日期之间的分钟数，日期格式：2005-02-06 11:59
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getMinute(String d1, String d2) {

		String t1 = d1.substring(11, 16);
		StringTokenizer st1 = new StringTokenizer(t1, ":");
		ArrayList<String> arrTmp1 = new ArrayList<String>();
		while (st1.hasMoreTokens()) {
			arrTmp1.add(st1.nextToken());
		}

		String t2 = d2.substring(11, 16);
		StringTokenizer st2 = new StringTokenizer(t2, ":");
		ArrayList<String> arrTmp2 = new ArrayList<String>();
		while (st2.hasMoreTokens()) {
			arrTmp2.add(st2.nextToken());
		}

		int n1 = Integer.parseInt((String) arrTmp1.get(0)) * 60
				+ Integer.parseInt((String) arrTmp1.get(1));

		int n2 = Integer.parseInt((String) arrTmp2.get(0)) * 60
				+ Integer.parseInt((String) arrTmp2.get(1));

		return Math.abs(n2 - n1);
	}
	
	/**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

	public static void main(String arg[]) {

		System.out.println(DateUtil.getXMLDateAndTime("2005-02-06 11:43:11")
				.toLocaleString());
		
		System.out.println(""+DateUtil.getMinute("2005-02-06 11:59","2005-02-06 12:01"));
	}
	public static XMLGregorianCalendar dateToXmlDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);  
        DatatypeFactory dtf = null;
         try {  
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
        }  
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        //由于Calendar.MONTH取值范围为0~11,需要加1  
        dateType.setMonth(cal.get(Calendar.MONTH)+1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));
        return dateType;  
    }   

/** 
 * 将XMLGregorianCalendar转换为Date 
 * @param cal 
 * @return  
 */  
public static Date xmlDate2Date(XMLGregorianCalendar cal){
	if(cal==null){
		return null;
	}
    return cal.toGregorianCalendar().getTime();  
}  
/** 
 * 将XMLGregorianCalendar转换为Date 
 * @return
 */  
public XMLGregorianCalendar dateToXml(Date date){
	GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
             e.printStackTrace();
        }
	return gc;
 }
}
