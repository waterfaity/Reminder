package com.waterfairy.reminder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.MemorandumCategoryDB;
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.database.greendao.MemorandumCategoryDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.widget.ChartView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemorandumFormActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar mSeekBar;
    private ChartView mChartView;
    private String TAG = "test";
    MemorandumCategoryDBDao memorandumCategoryDBDao;
    MemorandumDBDao memorandumDBDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum_form);
        findView();
        initView();
        initData();

    }

    private void initData() {
        memorandumCategoryDBDao = DataBaseManger.getInstance().getDaoSession().getMemorandumCategoryDBDao();
        memorandumDBDao = DataBaseManger.getInstance().getDaoSession().getMemorandumDBDao();
        query(7);
    }

    private void query(int day) {
        List<ChartView.ChartEntity> chartEntities = new ArrayList<>();
        List<MemorandumCategoryDB> memorandumCategoryDBS = memorandumCategoryDBDao.queryBuilder().orderDesc(MemorandumCategoryDBDao.Properties.CreateTime).list();
        for (int i = 0; i < memorandumCategoryDBS.size(); i++) {
            MemorandumCategoryDB categoryDB = memorandumCategoryDBS.get(i);
            List<MemorandumDB> list = memorandumDBDao.queryBuilder()
                    .where(MemorandumDBDao.Properties.Time.gt(new Date().getTime() - day * 24 * 60 * 60 * 1000L),
                            MemorandumDBDao.Properties.CategoryId.eq(categoryDB.getId()))
                    .list();

            chartEntities.add(new ChartView.ChartEntity(categoryDB.getName(), list.size()));
        }
        mChartView.setData(chartEntities);
    }

    private void initView() {
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setMax(99);
        mSeekBar.setProgress(6);
    }

    private void findView() {
        mSeekBar = findViewById(R.id.seek_bar);
        mChartView = findViewById(R.id.chart_view);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        ((TextView) findViewById(R.id.text2)).setText(progress + 1 + "天");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        query(seekBar.getProgress() + 1);
    }
}
