package com.circle.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keweiyang on 2017/6/10.
 */
public class DateTimeUtil {

    public static Date getCurrentTime() {
        return new Date();
    }

    public static String getCurrentMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMM");
        return dateFormat.format(new Date());
    }

    //TODO
    public static String conversionTime(Date date, String format) {
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getCurrentMonth());
    }
}
