package com.circle.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by keweiyang on 2017/6/5.
 */
public class EncryUtil implements Serializable {
    public static String md5(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        return DigestUtils.md5Hex(string);
    }
}
