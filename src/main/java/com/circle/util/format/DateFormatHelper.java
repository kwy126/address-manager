package com.circle.util.format;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keweiyang on 2017/3/16.
 */
public class DateFormatHelper {
    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";


    private DateFormatHelper() {

    }

    public static String long2str(long time) {
        Date date = new Date(time);
        DateFormat df = new SimpleDateFormat(FORMAT);

        return df.format(date);
    }

    public static long str2long(String string) throws ParseException {
        DateFormat df = new SimpleDateFormat(FORMAT);
        return df.parse(string).getTime();
    }

}
