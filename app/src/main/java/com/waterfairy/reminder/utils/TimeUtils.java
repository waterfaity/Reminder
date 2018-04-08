package com.waterfairy.reminder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/2/23
 * des  : 时间格式化工具
 */

public class TimeUtils {
    public static String format(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String format(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String format(long date, String pattern) {
        return format(new Date(date), pattern);
    }

    public static String format(long date) {
        return format(new Date(date));
    }
}
