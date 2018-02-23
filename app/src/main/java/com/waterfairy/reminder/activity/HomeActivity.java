package com.waterfairy.reminder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.waterfairy.reminder.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
}
