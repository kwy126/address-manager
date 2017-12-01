package com.circle.util.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keweiyang on 2017/6/5.
 */
public class DateTimeUtil {
    public static Date getCurrentTime() {
        return new Date();
    }

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date stringToDate(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getCurrentTime();
    }
}
