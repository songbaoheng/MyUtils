/**
 * 
 */
package com.example.myutil;

import java.text.SimpleDateFormat;
import java.util.Date;



/** 

 * @ClassName:PrettyTime 

 * @Description: TODO：用于生成 类似于 评论表中   “几分钟前”“几小时 几分钟前” 的时间戳

 * @author Liuzhibo 刘志波

 * @param String类型的时间
 
 * @date 2014-8-7 下午04:16:34 
 

 */
public class PrettyTime {

private final static long minute = 60 * 1000;// 1分钟
private final static long hour = 60 * minute;// 1小时
private final static long day = 24 * hour;// 1天
private final static long month = 31 * day;// 月
private final static long year = 12 * month;// 年

/**
 * 返回文字描述的日期<br/>
 * 类似于 评论表中   “几分钟前”“几小时 几分钟前” 的时间戳
 * 
 * @param String(date)
 * @return ？ 天 ？小时  ？ 分钟前
 */
public static String getTimeFormatText(String date) {
    if (date == null) {
        return null;
    }
    long diff = 1000 * PublicTool.getTimeDifference(date,PublicTool.getNowTime());
    long r = 0;
    long r2 = 0;
    long r3 = 0;
    long r4 = 0;
    if (diff > year) {
        r = (diff / year);
        return r + "年前";
    }
    if (diff > month) {
        r4 = (diff / month);
        r3 = (diff % month)/day;
        r4 = (diff % month % day)/hour ;
        r4 = (diff % month % day % hour)/minute;
//      return r4 + "个月 "+r3 + "天 "+r2+"小时 "+r+"分钟前";
        return r4 + "个月 前";
    }
    if (diff > day) {
        r3 = (diff / day);
        r2 = (diff % day)/hour;
        r = (diff % day % hour)/minute;
        if(r2 == 0 ){
        	return r3 + "天"+r+"分钟前";
        }else{
	        return r3 + "天"+r2+"小时"+r+"分钟前";
        }
    }    
    if (diff > hour) {
        r2 = (diff /hour);
        r = (diff % hour)/minute;
        if (r2 == 0) {
        	return r+"分钟前";
		}else {
			return r2 + "个小时"+r+"分钟前";
		}
    }
    if (diff > minute) {
        r = (diff / minute);
        return r + "分钟前";
    }
    return "刚刚";
	}
}