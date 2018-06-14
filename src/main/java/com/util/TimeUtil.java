package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	private static SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		Date date = format.parse("2013-11-11 18:35:35");
		System.out.println(formatDay(date));
	}

	public static String formatDay(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 7L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}
	/**
	 * 格式化回复时间
	 * @param dateTime  yyyy-MM-dd格式
	 * @return
	 * by dh
	 */
	public static String formatDay(String dateTime) {
		Date date = new Date();
        try {
	        date = shortFormat.parse(dateTime);
        } catch (ParseException e) {
        	date = new Date();
	        e.printStackTrace();
        }
		long delta = new Date().getTime() - date.getTime();
		if (delta < 24L * ONE_HOUR) {
			return "今天";
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 7L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		} else {
			return dateTime;
		}
	}
	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}
}
