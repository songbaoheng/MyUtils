package com.example.myutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PublicTool {

	private static java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * 获得系统时间.<br />
	 * 
	 * 
	 * @return
	 */
	public static String getNowTime() {
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = f.format(c.getTime());
		return nowTime;
	}

	/**
	 * 
	 * 获得系统时间.<br />
	 * 
	 * 
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String getNowTime(String format) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(format);
		String nowTime = f.format(c.getTime());
		return nowTime;
	}

	/**
	 * 
	 * 获得系统日期.<br />
	 * 
	 * 
	 * @param format
	 *            日期格式化
	 * @return
	 */
	public static String getNowDate(String format) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(format);
		String nowTime = f.format(c.getTime());
		return nowTime;
	}

	/**
	 * 
	 * 获得系统日期.<br />
	 * 
	 * 
	 * @return
	 */
	public static String getNowDate() {
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String nowTime = f.format(c.getTime());
		return nowTime;
	}

	/**
	 * 根据传入时间，往前推多少天
	 * 
	 * @Title: getDateTimeForDate
	 * @Description: String 转成Date
	 * @param datetime
	 * @return
	 */
	public static String getBeforeDate(String datetime, int days) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		try {
			Calendar c = java.util.Calendar.getInstance();
			Date date = f.parse(datetime);
			c.setTime(date);
			if (days >= 0) {
				c.add(Calendar.DAY_OF_MONTH, -days);
			} else {
				c.add(Calendar.DAY_OF_MONTH, days);
			}

			return f.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return datetime;
		}
	}

	/**
	 * 计算两个时间内的相差的日期，例如：2013-12-30与2014-01-01,返回30、31、01
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String[] getDiffDays(String start, String end) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sft = new SimpleDateFormat("dd");
		try {
			Date date1 = simpleFormate.parse(start);
			Date date2 = simpleFormate.parse(end);
			Calendar calendar = Calendar.getInstance();
			int count = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24) + 1;
			// System.out.println(count);
			String[] days = new String[count];
			for (int i = 0; i < count; i++) {
				calendar.setTime(date1);
				calendar.add(Calendar.DAY_OF_MONTH, i);
				days[i] = simpleFormate.format(calendar.getTime());
				// System.out.println(days[i]);
			}
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算两个时间差，返回秒
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getTimeDifference(String start, String end) {
		long diff = 0;
		try {
			Date d1 = format.parse(start);
			Date d2 = format.parse(end);
			diff = d2.getTime() - d1.getTime();
			diff = (long) (diff / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * 
	 * @Title: getAfterDate
	 * @Description:获得后一天日期
	 * @return
	 */
	public static String getAfterDate(String datetime) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		Date date = null;
		try {
			date = f.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		return f.format(c.getTime());
	}

	/**
	 * 
	 * @Title: getBeforeDate
	 * @Description:获得前一天日期
	 * @return
	 */
	public static String getBeforeDate(String datetime) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		Date date = null;
		try {
			date = f.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		return f.format(c.getTime());
	}

	/**
	 * 
	 * @Title: getDateTimeForDate
	 * @Description:传入一个Date ，格式化输出
	 * @param date
	 * @return
	 */
	public static String getDateTimeForDate(Date date) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = f.format(date);
		return nowTime;
	}

	/**
	 * 
	 * @Title: getDateTimeForDate
	 * @Description: String 转成Date
	 * @param datetime
	 * @return
	 */
	public static Date getDateTimeForDate(String datetime) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date date = f.parse(datetime);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: DateAuthentication
	 * @Description:判断当前时间是否在开始时间，和结束时间之间，0=未开始，1=进行中，2=结束
	 * @param btime
	 * @param etime
	 * @return
	 */
	public static int DateAuthentication(Date btime, Date etime) {
		int i = 0;
		Date dtime = Calendar.getInstance().getTime();
		if (dtime.before(btime)) {
			i = 0;
		} else if (dtime.after(etime)) {
			i = 2;
		} else {
			i = 1;
		}
		return i;
	}

	/**
	 * 
	 * @Title: isNumeric
	 * @Description:判断字符串是否为数字组成
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 
	 * @Title: AuthenticationPhoneNumber
	 * @Description: 验证手机号 0：小灵通，1：移动，2：联通,3：电信 4：未知 5：电话号码不符合规则
	 * @return
	 */
	public int AuthenticationPhoneNumber(String mobile) {
		// mTypeName手机类型：0网通1移动2联通3电信
		// 号段分类
		String mTypeWT = "031,033";
		String mTypeYD = "134.135.136.137.138.139.147.150.158.159.157.151.152.182.183.187.188";
		String mTypeLT = "130.131.132.145.156.155.185.186";
		String mTypeDX = "133.153.180.189";
		if (this.isNumeric(mobile) && mobile.length() > 10
				&& mobile.length() < 13) {// 返回true表明是数字
			String str = mobile.substring(0, 3);
			// 0：小灵通，1：移动，2：联通,3：电信
			if (mTypeWT.indexOf(str) != -1) {
				return 0;
			} else if (mTypeYD.indexOf(str) != -1) {
				return 1;
			} else if (mTypeLT.indexOf(str) != -1) {
				return 2;
			} else if (mTypeDX.indexOf(str) != -1) {
				return 3;
			} else {
				return 4;
			}
		} else {
			return 5;
		}
	}

	/**
	 * 
	 * @Title: ThreeMD5
	 * @Description:三次MD5加密
	 * @param password
	 * @return
	 */
	public static String ThreeMD5(String password) {
		for (int i = 0; i < 3; i++) {
			password = compute(password);
		}
		return password;
	}

	/**
	 * 
	 * MD5加密.<br />
	 * 
	 * 
	 * @param inStr
	 * @return
	 */
	public static String compute(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	/**
	 * 传入一个Date ，格式化输出
	 */
	public static String getDateTimeForString(Date date) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = f.format(date);
		return nowTime;
	}

	/**
	 * 传入一个Date ，格式化输出
	 */
	public static String getDateForString(Date date) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String nowTime = f.format(date);
		return nowTime;
	}

	/**
	 * String 转成Date
	 */
	public static Date getDateForDate(String datetime) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		try {
			Date date = f.parse(datetime);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 获取当前时间是星期几.<br />
	 * 
	 * @return
	 */
	public static String getWeek() {
		java.util.Calendar c = java.util.Calendar.getInstance();
		List<String> list = new ArrayList<String>();
		list.add(0, "星期日");
		list.add(1, "星期一");
		list.add(2, "星期二");
		list.add(3, "星期三");
		list.add(4, "星期四");
		list.add(5, "星期五");
		list.add(6, "星期六");
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		String weekname = list.get(week);
		return weekname;
	}

	/**
	 * 计算两个时间之间的时间段（小时）
	 */
	public static String getTimeSegForHour(String sTime, String eTime) {
		if (sTime == null || sTime.equals("") || eTime == null
				|| eTime.equals("")) {
			return "";
		}
		// String regex =
		// "^\\d{4}(-|/)\\d{1,2}(-|/)\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$";
		// if (!sTime.matches(regex) || !eTime.matches(regex)) {
		// return "";
		// }
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		Date sDate = null;
		try {
			sDate = f.parse(sTime);
		} catch (ParseException e) {
			return "";
		}

		Date eDate = null;
		try {
			eDate = f.parse(eTime);
		} catch (ParseException e) {
			return "";
		}

		long timeSeg = eDate.getTime() - sDate.getTime();

		BigDecimal big = new BigDecimal((double) timeSeg / 3600000).setScale(2,
				BigDecimal.ROUND_HALF_UP);

		return String.valueOf(big);
	}

	/**
	 * 计算两个时间之间的时间段（分钟）
	 */
	public static String getTimeSegForMin(String sTime, String eTime) {
		if (sTime == null || sTime.equals("") || eTime == null
				|| eTime.equals("")) {
			return "";
		}

		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		Date sDate = null;
		try {
			sDate = f.parse(sTime);
		} catch (ParseException e) {
			return "";
		}

		Date eDate = null;
		try {
			eDate = f.parse(eTime);
		} catch (ParseException e) {
			return "";
		}

		long timeSeg = eDate.getTime() - sDate.getTime();

		BigDecimal big = new BigDecimal((double) timeSeg / 60000).setScale(2,
				BigDecimal.ROUND_HALF_UP);

		return String.valueOf(big);
	}

	/**
	 * 生成小数点后两位的百分数
	 */
	public static String getTwoBitPointForPercent(int up, int down) {
		if (up == 0 || down == 0) {
			return "0.00%";
		}

		// 先生成小数点后两位小数，小数点再右移两位
		BigDecimal big = new BigDecimal((double) up / down).setScale(4,
				BigDecimal.ROUND_HALF_UP).movePointRight(2);
		String str = String.valueOf(big + "%");

		return str;
	}

	/**
	 * 取得某年某月的天数
	 */
	public static int getDayNum(int year, int month) {
		int dayNum = 0;
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			dayNum = 30;
		} else if (month == 2) {
			if (year % 4 == 0) {
				dayNum = 29;
			} else {
				dayNum = 28;
			}
		} else {
			dayNum = 31;
		}
		return dayNum;
	}

	/**
	 * 
	 * 生成8位随机数
	 */
	public static String getRandom() {
		String a = String.valueOf(System.nanoTime());
		String vcode = String.valueOf((int) (Math.random() * 9000) + 1000)
				+ a.substring(a.length() - 4);
		return vcode;
	}

	/**
	 * 
	 * 过滤乱码
	 */
	public String chopWhitespace(String str) {
		if (null == str || "".equals(str))
			return "";

		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			int ci = ch;
			if (9 == ci || 10 == ci || 13 == ci || 32 <= ci
					&& !Character.isISOControl(ci)) {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * px转换为dip
	 * */
	public static int convertPxOrDip(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * 转换dip为px
	 * */
	public static int convertDipOrPx(Context context, int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	/**
	 * @Author:songbaoheng
	 * @Title: shareText
	 * @Description:分享文本
	 * @param context
	 * @param shareContent
	 * @Copyright:copyright(c) 2015,Rights Reserved
	 * @Company：2015 地阳传奇 Inc. All rights reserved
	 * @date：2015-1-22
	 */
	public static void shareText(Context context, String shareContent) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "");
		intent.putExtra(Intent.EXTRA_TEXT, shareContent);
		context.startActivity(Intent.createChooser(intent, "分享到"));

	}

	/**
	 * toast显示文本
	 * 
	 * @param context
	 * @param showContent
	 */
	public static void showText(Context context, String showContent) {

		Toast.makeText(context, showContent, Toast.LENGTH_SHORT).show();

	}
	/**
	 * 判断字符是否为空
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isNull(String content) {
		if (content == null || content.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
