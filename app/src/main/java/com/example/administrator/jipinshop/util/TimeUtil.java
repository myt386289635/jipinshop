package com.example.administrator.jipinshop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 莫小婷
 * @create 2019/9/25
 * @Describe
 */
public class TimeUtil {

    public static String getCountTimeByLong(long millisUntilFinished){
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = millisUntilFinished / dd;
        long hour = ((millisUntilFinished - day * dd) / hh);
        long minute = (millisUntilFinished- hour * hh - day * dd) / mi;
        long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;
        long milliSecond = millisUntilFinished  - hour * hh - minute * mi - second * ss - day * dd;

        String result = "";
        if(day != 0){
            result = day + "天" + hour + "小时" + minute + "分钟" + second + "秒";
        }else if(hour != 0){
            result = hour + "小时" + minute + "分钟" + second + "秒";
        }else if(minute != 0){
            result = minute + "分钟" + second + "秒";
        }else if(second != 0){
            result = second + "秒";
        }

        return result;
    }

    public static long dateAddOneDay(String time){
        long currentTime = 0l;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            currentTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }


    public static String getCountTimeByLong2(long millisUntilFinished){
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = millisUntilFinished / dd;
        long hour = ((millisUntilFinished - day * dd) / hh);
        long minute = (millisUntilFinished- hour * hh - day * dd) / mi;
        long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;
        long milliSecond = millisUntilFinished  - hour * hh - minute * mi - second * ss - day * dd;

        String result = ((day * 24) + hour) + ":" + minute + ":" + second ;
        return result;
    }

    public static String getCountTimeByLong3(long millisUntilFinished){
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = millisUntilFinished / dd;
        long hour = ((millisUntilFinished - day * dd) / hh);
        long minute = (millisUntilFinished- hour * hh - day * dd) / mi;
        long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;
        long milliSecond = millisUntilFinished  - hour * hh - minute * mi - second * ss - day * dd;

        long time = ((day * 24) + hour);
        String sTime;
        if (time < 10){
            sTime = "0" + time;
        }else {
            sTime = "" + time;
        }
        String sMinute;
        if (minute < 10){
            sMinute = "0" + minute;
        }else {
            sMinute = "" + minute;
        }
        String sSecond;
        if (second < 10){
            sSecond = "0" + second;
        }else {
            sSecond = "" + second;
        }
        String result = sTime + ":" +  sMinute + ":" + sSecond;
        return result;
    }

    public static String getMinuteByLong(long millisUntilFinished){
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = millisUntilFinished / dd;
        long hour = ((millisUntilFinished - day * dd) / hh);
        long minute = (millisUntilFinished- hour * hh - day * dd) / mi;

        String sMinute;
        if (minute < 10){
            sMinute = "0" + minute;
        }else {
            sMinute = "" + minute;
        }
        return sMinute;
    }

    public static String getSecondByLong(long millisUntilFinished){
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = millisUntilFinished / dd;
        long hour = ((millisUntilFinished - day * dd) / hh);
        long minute = (millisUntilFinished- hour * hh - day * dd) / mi;
        long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;

        String sSecond;
        if (second < 10){
            sSecond = "0" + second;
        }else {
            sSecond = "" + second;
        }
        return sSecond;
    }
}
