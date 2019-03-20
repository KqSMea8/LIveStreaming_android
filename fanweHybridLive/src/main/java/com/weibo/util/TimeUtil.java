package com.weibo.util;

import android.text.TextUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具 Created by Nereo on 2015/4/8.
 */
public class TimeUtil {
	public static final String PATTEN_ONE="yyyy-MM-dd";
	public static final String PATTEN_TWO="yyyyMMddHHmmss";
	public static final String PATTEN_DATA="yyyyMMdd";
	public static final String PATTEN_THREE="yyyy年MM月dd日";

	/**
	 * PHP时间戳在JAVA中使用，最后加三位，用000补充,如：1294890859->1294890859000
	 * 
	 * @param timeMillis
	 *            1462931125
	 * @return
	 */
	public static String timeFormat(long timeMillis) {
		return timeFormat(timeMillis, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @param timeMillis
	 *            1462931125
	 * @param pattern
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String timeFormat(long timeMillis, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
		return format.format(new Date(timeMillis));
	}

	/**
	 * 
	 * @param time
	 *            1970-01-01 00:00:00
	 * @return
	 */
	public static long timeFormatData(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		try {
			return format.parse(time).getTime() + 86400000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 *
	 * @param timeCardCreate 20160721181623
	 * @return
     */
	public static String getTimeDesc(String timeCardCreate){
		if(TextUtils.isEmpty(timeCardCreate)||timeCardCreate.length()<14){
			return "";
		}else{
			String y=timeCardCreate.substring(0,4)+"年";
			String m=timeCardCreate.substring(4,6)+"月";
			String d=timeCardCreate.substring(6,8)+"日";
			return y+m+d;
		}
	}
	/**
	 * 
	 * @param time
	 *            1970-01-01 00:00:00
	 * @param pattern
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long timeFormatData(String time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
		try {
			return format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取文件夹最后修改时间
	 * 
	 * @param path
	 *            FilePath
	 * @return
	 */
	public static String formatFileDate(String path) {
		File file = new File(path);
		if (file.exists()) {
			long time = file.lastModified();
			return timeFormat(time);
		}
		return "1970-01-01";
	}

	/**
	 * 获取文件夹最后修改时间
	 * 
	 * @param path
	 *            FilePath
	 * @return
	 */
	public static long fileDate(String path) {
		File file = new File(path);
		if (file.exists()) {
			long time = file.lastModified();
			return time;
		}
		return 0;
	}
	public static int getCurrentYear(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	/**
	 * 给时间控件用的时候不能加一。
	 * @return
     */
	public static int getCurrentMonth(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH);
	}
	public static int getCurrentDay(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * yyyy-MM-dd"显示上时间需加
	 * @return yyyy-MM-dd"
     */
	public static String getCurrentDataDes(){
		return  timeFormat(System.currentTimeMillis(),PATTEN_ONE);
	}
	/**
	 * yyyy-MM-dd"显示上时间需加
	 * @return yyyy-MM-dd"
	 */
	public static String getCurrentDataDesNextMonth(){
		Calendar cal= Calendar.getInstance();
		cal.add(Calendar.MONTH,1);
		return  timeFormat(cal.getTimeInMillis(),PATTEN_ONE);
	}

	/**
	 *
	 * @param year
	 * @param month 0-11 显示需加一
	 * @param day
     * @return yyyy-MM-dd
     */
	public static String getDateDes(int year, int month, int day){
		String m="";
		m= String.format("%02d",month+1);
		String d= String.format("%02d",day);
		return year+"-"+m+"-"+d;
	}
	public static String getDateForRequestParam(int year, int month, int day){
		String m="";
		m= String.format("%02d",month+1);
		String d= String.format("%02d",day);
		return year+m+d;
	}

	/**
	 * @param timeDes yyyy-MM-dd
	 * @return
     */
	public static int getCurrentYear(String timeDes){
		long time=timeFormatData(timeDes,PATTEN_ONE);
		Date date=new Date(time);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		 return c.get(Calendar.YEAR);
	}

	/**
	 *
	 * @param timeDes yyyy-MM-dd
	 * @return
     */
	public static int getCurrentMonth(String timeDes){
		long time=timeFormatData(timeDes,PATTEN_ONE);
		Date date=new Date(time);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	/**
	 *
	 * @param timeDes yyyy-MM-dd
	 * @return
     */
	public static int getCurrentDay(String timeDes){
		long time=timeFormatData(timeDes,PATTEN_ONE);
		Date date=new Date(time);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 *
	 * @param timedesOld
	 * @param pattenOld
     * @return yyyyMMddHHmmss
     */
	public static String turnTimeDesToPattenTwo(String timedesOld, String pattenOld){
		long time=timeFormatData(timedesOld,pattenOld);
		return timeFormat(time,PATTEN_TWO);
	}
	public static String turnTimeDesToPattenTwo(String timedesOld, String pattenOld, boolean isEndDay){
		long time=timeFormatData(timedesOld,pattenOld);
		if(isEndDay){
			return timeFormat(time,PATTEN_DATA)+"235959";
		}
		return timeFormat(time,PATTEN_TWO);
	}

	/**
	 *
	 * @param timedesOld
	 * @param pattenOld
     * @return  yyyy-MM-dd
     */
	public static String turnTimeDesToPattenOne(String timedesOld, String pattenOld){
		long time=timeFormatData(timedesOld,pattenOld);
		return timeFormat(time,PATTEN_ONE);
	}
	public static String turnTimeDesToPattenNew(String timedesOld, String pattenOld, String pattenNew){
		long time=timeFormatData(timedesOld,pattenOld);
		return timeFormat(time,pattenNew);
	}
}
