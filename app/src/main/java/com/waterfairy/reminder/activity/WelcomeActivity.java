package com.waterfairy.reminder.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.greendao.UserDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;
import com.waterfairy.utils.PermissionUtils;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

/**
 * 欢迎页
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //检查权限(android 5.0  )
        requestPermission();
    }

    private void requestPermission() {
        if (PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE)) {
            initData();
        }
    }

    private void initData() {
        DataBaseManger.getInstance().initDataBase();
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (!ShareTool.getInstance().isLogin()) {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, ClockActivity.class));
                }
                finish();
            }
        }.sendEmptyMessageDelayed(0, 300);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtils.onRequestPermissionsResultForSDCard(permissions, grantResults)) {
            initData();
        } else {
            requestPermission();
        }
    }
}
