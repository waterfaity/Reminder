package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.manger.AudioManger;
import com.waterfairy.reminder.manger.ClockManger;
import com.waterfairy.reminder.utils.ShareTool;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        test();
    }

    private void test() {
//        AudioManger.getInstance().startAudio();
    }

    /**
     * 备忘录
     *
     * @param view
     */
    public void memorandum(View view) {
        startActivity(new Intent(this, MemorandumActivity.class));
    }

    /**
     * 每天
     *
     * @param view
     */
    public void everyday(View view) {
        startActivity(new Intent(this, EveryDayActivity.class));

    }

    /**
     * 闹钟
     *
     * @param view
     */
    public void clock(View view) {
        startActivity(new Intent(this, ClockActivity.class));
    }

    /**
     * 课程表
     *
     * @param view
     */
    public void classList(View view) {
        startActivity(new Intent(this, ClassListActivity.class));
    }

    /**
     * 退出帐号
     *
     * @param view
     */
    public void quit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("退出帐号?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShareTool.getInstance().saveLogin(false);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
