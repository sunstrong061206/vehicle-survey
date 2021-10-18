package com.fibikky.vehicle.common.log;

import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author 16861
 */
public class LogUtil {
    public static <T> void trace(T type, String trace) {
        LoggerFactory.getLogger(type.getClass().getName()).trace(trace);
    }

    public static <T> void debug(T type, String debug) {
        LoggerFactory.getLogger(type.getClass().getName()).debug(debug);
    }

    public static <T> void info(T type, String info) {
        LoggerFactory.getLogger(type.getClass().getName()).info(info);
    }

    public static <T> void warn(T type, String warn) {
        LoggerFactory.getLogger(type.getClass().getName()).warn(warn);
    }

    public static <T> void error(T type, String error) {
        LoggerFactory.getLogger(type.getClass().getName()).error(error);
    }
}
