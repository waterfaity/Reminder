package com.waterfairy.reminder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.EventAdapter;

public class EventShowActivity extends AppCompatActivity {
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_show);
        fintView();
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {


    }

    private void fintView() {

    }
}
