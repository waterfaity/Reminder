package com.waterfairy.reminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.manger.DataInitManger;
import com.waterfairy.reminder.service.ClockService;
import com.waterfairy.reminder.utils.ShareTool;
import com.waterfairy.utils.PermissionUtils;

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
        //开启闹钟服务
        startService(new Intent(this, ClockService.class));
    }

    //请求内存卡读写权限(android 5.0  )
    private void requestPermission() {
        if (PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE)) {
            initData();
        }
    }

    /**
     * 处理 欢迎页 将要跳转的页面
     * 已经登录  ->主页
     * 未登录   ->登录页
     */
    private void initData() {
        DataInitManger.getInstance().init();
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (!ShareTool.getInstance().isLogin()) {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                }
                finish();
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 请求权限返回的结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtils.onRequestPermissionsResultForSDCard(permissions, grantResults)) {
            //权限获取成功
            initData();
        } else {
            //权限不成功  在此请求  如果被用户拒绝 并 设置不在提示  ,该代码将不起作用
            requestPermission();
        }
    }
}
