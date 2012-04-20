package com.dish.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-8
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    public static final long DAY_TIME_IN_MILLIS = 86400000l;

    public static long getCurrDay() {
        Calendar cal = Calendar.getInstance();
        GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static String getCurrDayString() {
        return DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
    }

    public static String getCurrTimeString() {
        return DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm");
    }
}
