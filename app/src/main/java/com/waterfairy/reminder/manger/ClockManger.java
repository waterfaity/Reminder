package com.waterfairy.reminder.manger;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.waterfairy.reminder.application.MyApp;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.database.greendao.ClockDBDao;
import com.waterfairy.reminder.service.ClockService;
import com.waterfairy.reminder.utils.ShareTool;
import com.waterfairy.reminder.utils.TimeUtils;

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
    //    private AlarmManager mAlarmManager;
    private Context mContext;
    private HashMap<String, ClockDB> mClockDBHashMap;
    private ClockDBDao clockDBDao;
    private List<ClockDB> clockDBS;
    private ClockDB currentClock;

    private ClockManger() {

    }

    public static ClockManger getInstance() {
        if (mClockManger == null) mClockManger = new ClockManger();
        return mClockManger;
    }

    public ClockManger init() {
        mContext = MyApp.getApp().getApplicationContext();
        clockDBDao = DataBaseManger.getInstance().getDaoSession().getClockDBDao();
        return this;
    }

    public void initClock() {
        AudioManger.getInstance().stopAudio();
        String account = ShareTool.getInstance().getAccount();
        handler.removeMessages(0);
        if (!TextUtils.isEmpty(account) && ShareTool.getInstance().isLogin()) {
            clockDBS = clockDBDao.queryBuilder()
                    .where(ClockDBDao.Properties.Account.eq(account))
                    .list();
            if (clockDBS != null && clockDBS.size() > 0) {
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }
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
                sendEmptyMessageDelayed(0, 3000);
            }
        }
    };

    /**
     * 闹钟设置主要代码2
     */
    private void setClock() {
        //获取该用户下的 clock
        if (clockDBS != null && clockDBS.size() > 0) {
            boolean hasClock = false;
            long currentTimeMillis = System.currentTimeMillis();
            String currentTimeStr = TimeUtils.format(currentTimeMillis, "HH:mm");
            for (int i = 0; i < clockDBS.size(); i++) {
                ClockDB clockDB = clockDBS.get(i);
                String clockDBTime = clockDB.getTime();
                if (clockDB.isOpen() && TextUtils.equals(clockDBTime, currentTimeStr)) {
                    if (currentClock == null || !TextUtils.equals(currentClock.getTime(), currentTimeStr)) {
                        hasClock = true;
                        currentClock = clockDB;
                    }
                    break;
                }
            }
            if (hasClock) {
                startClock();
            }
        }
    }

    /**
     * 闹钟启动
     */
    private void startClock() {
        handler.removeMessages(0);
        Log.i(TAG, "startAudio: ");
        if (currentClock != null) {
            AudioManger.getInstance().startAudio();
            ClockService.getInstance().showNotification(MyApp.getApp().getApplicationContext(), currentClock.getTime());
        }
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
