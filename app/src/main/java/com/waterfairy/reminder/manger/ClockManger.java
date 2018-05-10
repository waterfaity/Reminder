package com.waterfairy.reminder.manger;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.waterfairy.reminder.application.MyApp;
import com.waterfairy.reminder.database.ClassDB;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.database.greendao.ClassDBDao;
import com.waterfairy.reminder.database.greendao.ClockDBDao;
import com.waterfairy.reminder.service.ClockService;
import com.waterfairy.reminder.utils.ShareTool;
import com.waterfairy.reminder.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.waterfairy.reminder.service.ClockService.ACTION_CLOCK_STOP;

/**
 * @author water_fairy
 * @email 971409587@qq.com
 * @date 2017/11/28
 * @Description: 闹钟管理
 */

public class ClockManger {
    private static final String TAG = "clockManger";
    private static ClockManger mClockManger;

    private ClockDBDao clockDBDao;
    private List<ClockDB> clockDBS;
    private ClockDB currentClock;

    private ClassDBDao classDBDao;
    private List<ClassDB> classDBS;
    private ClassDB currentClass;

    private ClockManger() {

    }

    public static ClockManger getInstance() {
        if (mClockManger == null) mClockManger = new ClockManger();
        return mClockManger;
    }

    public ClockManger init() {
        clockDBDao = DataBaseManger.getInstance().getDaoSession().getClockDBDao();
        classDBDao = DataBaseManger.getInstance().getDaoSession().getClassDBDao();
        return this;
    }

    public void initClock() {
        AudioManger.getInstance().stopAudio();
        String account = ShareTool.getInstance().getAccount();
        handler.removeMessages(0);
        boolean ready = false;
        if (!TextUtils.isEmpty(account) && ShareTool.getInstance().isLogin()) {
            clockDBS = clockDBDao.queryBuilder()
                    .where(ClockDBDao.Properties.Account.eq(account))
                    .list();
            if (clockDBS != null && clockDBS.size() > 0) {
                ready = true;
            }
        }
        classDBS = classDBDao.queryBuilder().list();
        if (clockDBS != null && clockDBS.size() > 0) {
            ready = true;
        }
        if (ready)
            handler.sendEmptyMessageDelayed(0, 5000);

    }

    /**
     * 闹钟设置主要代码1
     */
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage: ");
            removeMessages(0);
            boolean login = ShareTool.getInstance().isLogin();
            if (login) {
                //登录的时候才会启动闹钟
                setClock();
                sendEmptyMessageDelayed(0, 10000);
            }
        }
    };

    /**
     * 闹钟设置主要代码2
     */
    private void setClock() {
        //获取该用户下的 clock
        boolean hasClock = false;
        long currentTimeMillis = System.currentTimeMillis();
        String currentTimeStr = TimeUtils.format(currentTimeMillis, "HH:mm");
        if (clockDBS != null && clockDBS.size() > 0) {
            for (int i = 0; i < clockDBS.size(); i++) {
                ClockDB clockDB = clockDBS.get(i);
                String clockDBTime = clockDB.getTime();
                if (clockDB.isOpen() && TextUtils.equals(clockDBTime, currentTimeStr)) {
                    if (currentClock == null || !TextUtils.equals(currentClock.getTime(), currentTimeStr)) {
                        //相同时间响一个
                        hasClock = true;
                        currentClock = clockDB;
                    }
                    break;
                }
            }
        }


        boolean hasClassClock = false;

        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (classDBS != null && classDBS.size() > 0) {
            for (int i = 0; i < classDBS.size(); i++) {
                ClassDB classDB = classDBS.get(i);
                if (classDB.isOpen()) {
                    //计算当前星期
                    String tag = classDB.getTag();
                    String[] split = tag.replace("_", "-").split("-");

                    if (week == Integer.parseInt(split[0]) + 1 && TextUtils.equals(getTimeStr(split[1]), currentTimeStr)) {
                        if (currentClass == null || !TextUtils.equals(currentClass.getTag(), tag)) {
                            //相同时间响一个
                            hasClassClock = true;
                            currentClass = classDB;
                        }
                        break;

                    }
                }
            }
        }

        Log.i(TAG, "setClock: "+hasClock+" -- "+hasClassClock);

        if (hasClock || hasClassClock) {
            startClock(hasClock, hasClassClock, currentClass);
        }
    }

    /**
     * 1.2.3... 12 节课
     *
     * @param s
     */
    private String getTimeStr(String s) {
        int level = Integer.parseInt(s);
        String time = "";
        switch (level) {
            case 1:
            case 2:
            case 3:
            case 4:
                time = TimeUtils.format(new Date((level - 1) * 60 * 60 * 1000 - 10 * 60 * 1000), "HH:mm");
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                time = TimeUtils.format(new Date((1 + level) * 60 * 60 * 1000 - 10 * 60 * 1000), "HH:mm");
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                time = TimeUtils.format(new Date((2 + level) * 60 * 60 * 1000 - 10 * 60 * 1000), "HH:mm");
                break;
        }
        return time;
    }

    /**
     * 闹钟启动
     */
    private void startClock(boolean hasClock, boolean hasClassClock, ClassDB currentClass) {
        handler.removeMessages(0);
        Log.i(TAG, "startAudio: ");
        if (currentClock != null || currentClass != null) {
            AudioManger.getInstance().startAudio();
            if (hasClassClock)
                ClockService.getInstance().showNotification(MyApp.getApp().getApplicationContext(), "课程提醒", getContent(currentClass));
            else {
                ClockService.getInstance().showNotification(MyApp.getApp().getApplicationContext(), "闹钟", currentClock.getTime());
            }
        }
    }

    private String getContent(ClassDB currentClass) {
        String tag = currentClass.getTag();
        String[] split = tag.split("_");
        String week = "";
        switch (split[0]) {
            case "0":
                week = "周日";
                break;
            case "1":
                week = "周一";
                break;
            case "2":
                week = "周二";
                break;
            case "3":
                week = "周三";
                break;
            case "4":
                week = "周四";
                break;
            case "5":
                week = "周五";
                break;
            case "6":
                week = "周六";
                break;

        }

        return currentClass.getClassName() + ":" + week + " " + getTimeStr(split[1]);
    }

    public void stopClock(Context context) {
        Intent intentStop = new Intent(context, ClockService.class);
        intentStop.setAction(ACTION_CLOCK_STOP);
        context.startService(intentStop);
    }


    /**
     * 即将提醒的时间  最小单位  分钟
     *
     * @param hour
     * @param minute
     * @return
     */
    public long getTime(int hour, int minute) {
        Calendar instance = Calendar.getInstance();
        long curMill = instance.getTimeInMillis();//当前mill
        int curHour = instance.get(Calendar.HOUR_OF_DAY);
        int curMinute = instance.get(Calendar.MINUTE);
        //相差时间 //第二天(前小时*60分钟+后分钟)
        int addHour = 0;
        if (curHour > hour || (curHour == hour && curMinute > minute)) {
            addHour = 24;
        }
        long dMinute = (hour + addHour - curHour) * 60 + (minute - curMinute);//相差分钟
        return (curMill / 60000 + dMinute) * 60000;
    }

    public String getTime(long millTime) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(millTime);
        return instance.get(Calendar.YEAR)
                + "-" + (instance.get(Calendar.MONTH) + 1)
                + "-" + instance.get(Calendar.DAY_OF_MONTH)
                + " " + instance.get(Calendar.HOUR_OF_DAY)
                + ":" + instance.get(Calendar.MINUTE)
                + ":" + instance.get(Calendar.SECOND)
                + ":" + instance.get(Calendar.MILLISECOND);
    }

}
