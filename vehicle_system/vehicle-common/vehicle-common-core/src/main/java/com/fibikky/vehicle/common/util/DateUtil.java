package com.fibikky.vehicle.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author 16861
 */
public class DateUtil {
    public static Date modify(Date date, int hour, int minute, int second) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date offsetDay(Date date, int offset, int hour, int minute, int second) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -offset);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date timestamp2Date(Long timestamp) {
        try {
            return new SimpleDateFormat().parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(timestamp)));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
