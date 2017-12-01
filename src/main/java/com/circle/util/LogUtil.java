package com.circle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by keweiyang on 2017/6/5.
 */
public class LogUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void info(String string) {
        logger.info(string);
    }

    public void info(String str, Object... params) {
        logger.info(str, params);
    }

    public void error(String str, Object... params) {
        logger.error(str, params);
    }

    public void warning(String str, Object... params) {
        logger.warn(str, params);
    }
}
