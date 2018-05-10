package com.waterfairy.reminder.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.manger.AudioManger;
import com.waterfairy.reminder.manger.ClockManger;
import com.waterfairy.utils.NotificationUtils;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2018/2/26
 * des  : 闹钟服务
 */

public class ClockService extends Service {
    private static ClockService clockService;
    public static final String ACTION_CLOCK_START = "com.waterfairy.reminder.clock.start";
    public static final String ACTION_CLOCK_STOP = "com.waterfairy.reminder.clock.stop";

    public static ClockService getInstance() {
        return clockService;
    }

    private static final String TAG = "ClockService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        clockService = this;
    }

    /**
     * 状态栏 提示闹钟
     *
     * @param context
     * @param time
     */
    public void showNotification(Context context, String time,String content) {
        Intent intent = new Intent(context, ClockService.class);
        intent.setAction(ACTION_CLOCK_START);
        intent.putExtra("title", time);
        intent.putExtra("content", content);
        startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Log.i(TAG, "service: " + intent.getAction());
            if (TextUtils.equals(intent.getAction(), ACTION_CLOCK_START)) {
                //闹钟启动
                NotificationUtils notificationUtils = new NotificationUtils(this);
                Notification.Builder notificationBuilder = notificationUtils.getNotificationBuilder(R.mipmap.clock_lanucher, intent.getStringExtra("title"), intent.getStringExtra("content"));
                Intent intentStop = new Intent(this, ClockService.class);
                intentStop.setAction(ACTION_CLOCK_STOP);
                PendingIntent closeIntent = PendingIntent.getService(this, 1, intentStop, 0);
                notificationBuilder.setContentIntent(closeIntent);
                notificationBuilder.setDeleteIntent(closeIntent);
                notificationUtils.sendNotification(notificationBuilder.build());
            } else {
                //关闭
                AudioManger.getInstance().stopAudio();
                ClockManger.getInstance().initClock();
            }
        }

        return super.onStartCommand(intent, flags, startId);

    }

}
