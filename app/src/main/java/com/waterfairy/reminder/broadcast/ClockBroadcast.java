package com.waterfairy.reminder.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.waterfairy.utils.ToastUtils;

/**
 * @author water_fairy
 * @email 971409587@qq.com
 * @date 2017/11/28
 * @Description: 闹钟广播  未用到
 */

public class ClockBroadcast extends BroadcastReceiver {
    public static final int REQUEST_ONE = 1;
    public static final int REQUEST_REPEAT = 2;

    public static final String ACTION_CLOCK_START = "com.waterfairy.reminder.clock.start";
    public static final String ACTION_CLOCK_STOP = "com.waterfairy.reminder.clock.stop";
    private static final String TAG = "clockBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ToastUtils.show(action + "--" + intent.getStringExtra("tag"));
        Log.i(TAG, "onReceive: " + action + "--" + intent.getStringExtra("tag"));
    }
}
