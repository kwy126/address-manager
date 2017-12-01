package com.circle.util;

/**
 * Created by keweiyang on 2017/6/12.
 */
public class CompareUtil {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        if (obj != null) {
            return true;
        }
        return false;
    }
}
