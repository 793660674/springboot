package com.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DateUtils {
	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		if (strDate != null) {
			Date strtodate = formatter.parse(strDate, pos);
			return strtodate;
		}
		return null;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		if (strDate == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd
	 */
	public static String getDateString() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将字符串格式类型的日期转换成整数类型格式的日期 如：2017-11-22 ---> 20171122
	 * 
	 * @param strDate
	 * @return
	 */
	public static int formatDateToInt(String strDate) {
		Date currentTime = strToDate(strDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		return StringUtil.getInt(dateString);
	}

	/**
	 * 将字符串格式类型的日期转换成对应年中的周数 如：2017-10-22 ---> 43
	 * 
	 * @param strDate
	 * @return
	 */
	public static int formatWeekOfYear(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 将字符串格式类型的日期转换成对应月中的周数 如：2017-11-23 ---> 4
	 * 
	 * @param strDate
	 * @return
	 */
	public static int formatWeekOfMonth(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 将字符串格式类型的日期转换成对应当前是一周的第几天 如：2017-11-23 ---> 5
	 * 
	 * @param strDate
	 * @return
	 */
	public static int formatDayOfWeek(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 将字符串格式类型的日期转换成对应当前季度 如：2017-11-23 ---> 4
	 * 
	 * @param strDate
	 * @return
	 */
	public static int formatDateToSeason(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);

		if (calendar.get(Calendar.MONTH) >= 0 && calendar.get(Calendar.MONTH) <= 2) {
			return 1;
		} else if (calendar.get(Calendar.MONTH) >= 3 && calendar.get(Calendar.MONTH) <= 5) {
			return 2;
		} else if (calendar.get(Calendar.MONTH) >= 6 && calendar.get(Calendar.MONTH) <= 8) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 将日期格式(yyyy/MM/dd) 转换成 (yyyy-MM-dd)类型
	 * 
	 * @param formDate
	 * @return
	 */
	public static String formatDate(String formDate) {
		try {
			if (formDate != null) {
				if (!formDate.trim().equals("")) {
					return formDate.replace("/", "-");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * 将formBean 里的字符时间(yyyy-MM-dd) 转换成Date类型
	 * 
	 * @param formDate
	 * @return
	 */
	public static Date formBeanDateToPODate(String formDate) {
		try {
			if (formDate != null) {
				if (!formDate.trim().equals("")) {
					return java.sql.Date.valueOf(formDate);
				}
			}
		} catch (Exception e) {
			System.out.println("DateUtils:时间转换异常");
			return new Date();
		}
		return null;

	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 * 
	 * @param nowdate
	 *            格式：yyyy-MM-dd
	 * @param delay
	 *            天数
	 * @return
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			return format.format(d);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 * 
	 * @param nowdate
	 *            格式：yyyy-MM-dd
	 * @param delay
	 *            天数
	 * @return
	 */
	public static String getNextDay(String nowdate, int delay) {
		try {
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d = strToDate(nowdate);
			gc.setTime(d);

			// GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
			// field参数表示年,月.日等. amount参数表示要加减的数量

			// gc.add(1,+1)表示年份加一
			// gc.add(2,+1) 表示月份加一
			// gc.add(3,+1)表示周数加一
			// gc.add(5,+1)表示天数加一

			gc.add(5, delay);
			gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));

			return format.format(gc.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的小时数
	 * 
	 * @param nowdate
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @param delay
	 *            小时
	 * @return
	 */
	public static Date getNextDay(Date nowdate, int delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long myTime = (nowdate.getTime() / 1000) + 3600 * delay;
			nowdate.setTime(myTime * 1000);
			return strToDateLong(format.format(nowdate));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的月数
	 * 
	 * @param nowdate
	 *            格式：yyyy-MM-dd
	 * @param delay
	 *            月数
	 * @return
	 */
	public static String getNextMonth(String nowdate, int delay) {
		try {
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d = strToDate(nowdate);
			gc.setTime(d);

			// GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
			// field参数表示年,月.日等.
			// amount参数表示要加减的数量
			// gc.add(1,+1)表示年份加一 gc.add(2,+1) 表示月份加一 gc.add(3,+1)表示周数加一 gc.add(5,+1)表示天数加一

			gc.add(2, delay);// 表示月份加一.
			gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));

			return format.format(gc.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的年数
	 * 
	 * @param nowdate
	 *            格式：yyyy-MM-dd
	 * @param delay
	 *            月数
	 * @return
	 */
	public static String getNextYear(String nowdate, int delay) {
		try {
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d = strToDate(nowdate);
			gc.setTime(d);

			// GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
			// field参数表示年,月.日等.
			// amount参数表示要加减的数量
			// gc.add(1,+1)表示年份加一 gc.add(2,+1) 表示月份加一 gc.add(3,+1)表示周数加一 gc.add(5,+1)表示天数加一

			gc.add(1, delay);// 表示月份加一.
			gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));

			return format.format(gc.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance(Locale.CHINA);
		Calendar cal2 = Calendar.getInstance(Locale.CHINA);
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = DateUtils.strToDate(sdate);
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtils.strToDate(sdate);
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeekStr(String sdate) {
		String str = "";
		str = DateUtils.getWeek(sdate);
		if ("1".equals(str)) {
			str = "星期日";
		} else if ("2".equals(str)) {
			str = "星期一";
		} else if ("3".equals(str)) {
			str = "星期二";
		} else if ("4".equals(str)) {
			str = "星期三";
		} else if ("5".equals(str)) {
			str = "星期四";
		} else if ("6".equals(str)) {
			str = "星期五";
		} else if ("7".equals(str)) {
			str = "星期六";
		}
		return str;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	 * 此函数返回该日历第一行星期日所在的日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate) {
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";

		// 得到这个月的1号是星期几
		Date date = DateUtils.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = DateUtils.getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */

	public static String getNo(int k) {

		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 取得数据库主键 生成格式为yyyyMMddhhmmss+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */

	public static String getDateRandom(int k) {

		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int length) {
		char[] charArray = new char[length];
		for (int i = 0; i < length; i++) {
			Random r = new Random();
			int n = r.nextInt(999);
			while (n < 48 || (n > 57 && n < 65) || (n > 90 && n < 97) || n > 122) {
				// (!((n>=48 && n<=57) || (n>=65 && n<=90) && (n>=97 &&
				// n<=122))){
				n = r.nextInt(999);
			}
			charArray[i] = (char) n;
		}
		return String.valueOf(charArray);
	}

	/**
	 * 检查日期格式是否正确
	 * 
	 * @param date
	 */
	public static boolean RightDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	/**
	 * 获得系统年份，以字符串"YYYY"返回
	 * 
	 * @return 返回"YYYY"字符串
	 */
	public static String getStrCurrentYear() {
		return timeFormate("yyyy");
	}

	/**
	 * 获得系统月份，以字符串"MM"返回
	 * 
	 * @return 返回"MM"字符串
	 */
	public static String getStrCurrentMonth() {
		return timeFormate("MM");
	}

	/**
	 * 获得系统天数，以字符串"DD"返回
	 * 
	 * @return 返回"DD"字符串
	 */
	public static String getStrCurrentDay() {
		return timeFormate("dd");
	}

	/**
	 * 获得系统当前季度数
	 * 
	 * @return
	 */
	public static Integer getCurrentSeason() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		if (c.get(Calendar.MONTH) >= 0 && c.get(Calendar.MONTH) <= 2) {
			return 1;
		} else if (c.get(Calendar.MONTH) >= 3 && c.get(Calendar.MONTH) <= 5) {
			return 2;
		} else if (c.get(Calendar.MONTH) >= 6 && c.get(Calendar.MONTH) <= 8) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 获得系统当前年的周数
	 * 
	 * @return
	 */
	public static Integer getCurrentWeekOfYear() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获得系统当前月的周数
	 * 
	 * @return
	 */
	public static Integer getCurrentWeekOfMonth() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		return c.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获得系统当前天的周数（星期几）
	 * 
	 * @return
	 */
	public static Integer getCurrentDayOfWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得系统年份，以字符串"YYYYMM"返回
	 * 
	 * @return 返回"YYYYMM"字符串
	 */
	public static String getCurrentYearMonth() {
		return timeFormate("yyyyMM");
	}

	// 日期格式
	private synchronized static String timeFormate(String format) {
		SimpleDateFormat _dateFormat = new SimpleDateFormat(format);
		// 获得当前的时间
		Date _currentDate = Calendar.getInstance().getTime();
		return _dateFormat.format(_currentDate);
	}

	/**
	 * 得到年月，如：2017年9月
	 * 
	 * @param date
	 *            格式：yyyy-mm-dd
	 * @return
	 */
	public static String getYearMonth(String date) {
		if (date == null || "".equals(date)) {
			return date;
		}
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);

		return Integer.parseInt(year) + "年" + Integer.parseInt(month) + "月";

	}

	/**
	 * 得到月日，如：9月22日
	 * 
	 * @param date
	 *            格式：yyyy-mm-dd
	 * @return
	 */
	public static String getMonthDay(String date) {
		if (date == null || "".equals(date)) {
			return date;
		}

		String month = date.substring(5, 7);
		String day = date.substring(8, 10);

		return Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日";
	}

	/**
	 * 返回当前时间距离到期时间剩余的月数,本月过期返回0，若已过期则返回负数
	 * 
	 * @param nowDate
	 *            当前时间
	 * @param endDate
	 *            会员到期时间
	 * @return 返回当前时间距离到期时间剩余的月数,本月过期返回0，若已过期则返回负数
	 * @throws ParseException
	 */
	public static Integer getMonthSpace(String nowDate, String endDate) {
		if (endDate == null || "".equals(endDate) || nowDate == null || "".equals(nowDate)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c2.setTime(sdf.parse(endDate));
			c1.setTime(sdf.parse(nowDate));
			int c = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12 + c2.get(Calendar.MONTH)
					- c1.get(Calendar.MONTH);
			// 如果两个日期处在同年同月，则根据日期判断是否过期
			if (c == 0) {
				if (c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH) > 0) {
					c = -1;
				} else {
					c = 0;
				}
			}
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 返回两个日期之间的差值（单位：秒），日期格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param startTime
	 * @param endTime
	 * 
	 */
	public static long dateDiff(String startTime, String endTime) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		// long nh = 1000 * 60 * 60;// 一小时的毫秒数
		// long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff = 0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			// long day = diff / nd;// 计算差多少天
			// long hour = diff % nd / nh;// 计算差多少小时
			// long min = diff % nd % nh / nm;// 计算差多少分钟
			// long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// // 输出结果
			// System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟"
			// + sec + "秒。");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff / ns;
	}

	/**
	 * 处理时间格式2017-05-11 成 5.11
	 * 
	 * @param str
	 * @return
	 */
	public static String timeFormat(String time) {
		String[] split = time.split("-");
		String month = "";
		String day = "";
		if (split[1].startsWith("0")) {
			month = split[1].substring(1);
		} else {
			month = split[1];
		}
		if (split[2].startsWith("0")) {
			day = split[2].substring(1);
		} else {
			day = split[2];
		}
		return month + "." + day;
	}

	/**
	 * 获取某年某周的第一天 @Title:getFirstDayOfWeek @Description: @param:@param
	 * year @param:@param week @param:@return @return:String @throws
	 */
	public static String getFirstDayOfWeek(int year, int week) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置周
		cal.set(Calendar.WEEK_OF_YEAR, week);
		// 设置该周第一天为星期一
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfWeek = sdf.format(cal.getTime());

		return firstDayOfWeek;
	}

	/**
	 * 获取当前周的第一天
	 * 
	 * @return
	 */

	public static String getWeekStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfWeek = sdf.format(cal.getTime());
		return firstDayOfWeek;
	}
	/**
	 * 获取本月第一天
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		//获取当前月第一天：
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * 获取某年某周的第一天和最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstAndLastOfWeek(int year, int week) {
		String dataStr = getFirstDayOfWeek(year, week);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int d = 0;
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			d = -7;
		} else {
			d = 1 - cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, d);
		// 所在周开始日期
		String data1 = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		// 所在周结束日期
		String data2 = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
		return data1 + "-" + data2;
	}
	/**
	 * 获取两个日期之间的所有日期 不包含起始日期和结束日期
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonthBetweenDateStr(String minDate, String maxDate) throws ParseException{
		List<String> listDate = new ArrayList<String>();
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(minDate);
		startCalendar.setTime(startDate);
		Date endDate = df.parse(maxDate);
		endCalendar.setTime(endDate);
		while(true){
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
			if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){
				listDate.add(df.format(startCalendar.getTime()));
			}else{
				break;
			}
		}
		return listDate;
	}
	public static void main(String[] args) {
		System.out.println(getFirstDayOfMonth());
		// System.out.println(formatWeekOfMonth("2017-11-23"));
		// System.out.println(formatDayOfWeek("2017-11-23"));
		// System.out.println(formatDateToSeason("2017-11-23"));

		// Date d = strToDateLong("2017-09-19 18:18:10");
		// System.out.println("原始时间："+d);
		// System.out.println("相加之后的时间："+getNextDay(d, 2));
		// System.out.println(getCurrentWeekOfYear());
		// System.out.println(getFirstAndLastOfWeek(2017, 41));

		System.out.println(dateDiff("2017-12-29 15:30:30", "2017-12-29 15:41:30"));
	}

}
