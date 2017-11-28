package com.waterfairy.reminder.manger;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.waterfairy.reminder.application.MyApp;
import com.waterfairy.reminder.broadcast.ClockBroadcast;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.database.greendao.ClockDBDao;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @author water_fairy
 * @email 995637517@qq.com
 * @date 2017/11/28
 * @Description: 闹钟管理
 */

public class ClockManger {
    private static final String TAG = "clockManger";
    private long ONE_DAY = 24 * 60 * 60 * 1000;
    private static ClockManger mClockManger;
    private AlarmManager mAlarmManager;
    private Context mContext;
    private HashMap<String, ClockDB> mClockDBHashMap;


    private ClockManger() {

    }

    public static ClockManger getInstance() {
        if (mClockManger == null) mClockManger = new ClockManger();
        return mClockManger;
    }

    public void initClock() {
        mContext = MyApp.getApp().getApplicationContext();
        initAlarmManger();
        ClockDBDao clockDBDao = DataBaseManger.getInstance().getDaoSession().getClockDBDao();
        //获取该用户下的 clock
        String account = null;
        if (!TextUtils.isEmpty(account = ShareTool.getInstance().getAccount())) {
            List<ClockDB> list = clockDBDao.queryBuilder()
                    .where(ClockDBDao.Properties.Account.eq(account))
                    .list();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ClockDB clockDB = list.get(i);
                    if (clockDB.isOpen()) {
                        if (clockDB.isOneTime()) {
                            //启动 一次性闹钟
                            if (System.currentTimeMillis() >= clockDB.getFirstTime()) {
                                //超过时间
                                clockDB.setOpen(false);
                                clockDB.setUpdateTime(System.currentTimeMillis());
                                clockDBDao.update(clockDB);
                            } else {
                                setClock(clockDB);
                            }
                        } else {
                            //循环闹钟

                        }
                    } else {

                    }

                }
            }
        }
    }

    private void initAlarmManger() {
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * 启动闹钟 一次性
     *
     * @param clockDB
     */

    public void setClock(ClockDB clockDB) {
        Intent intent = new Intent(mContext, ClockBroadcast.class);
        intent.putExtra("tag", clockDB.getTag());
        intent.setAction(ClockBroadcast.ACTION_CLOCK_START);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ClockBroadcast.REQUEST_ONE, intent, PendingIntent.FLAG_ONE_SHOT);
        long triggerTime = clockDB.getFirstTime();
        mAlarmManager.set(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent);
        Log.i(TAG, "setClock: " + clockDB.getTime() + "---" + getTime(triggerTime));
    }

    public void setRepeatClock(ClockDB clockDB) {
    }


    public void stopClock() {

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
