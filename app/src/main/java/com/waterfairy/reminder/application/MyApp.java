package com.waterfairy.reminder.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.waterfairy.reminder.database.greendao.DaoMaster;
import com.waterfairy.reminder.utils.ShareTool;
import com.waterfairy.utils.ToastUtils;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :
 */

public class MyApp extends Application {
    private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        ShareTool.getInstance().initShare();
        ToastUtils.initToast(this);
    }

    public static MyApp getApp() {
        return myApp;
    }
}
