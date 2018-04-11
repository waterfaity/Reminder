package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.EveryDayAdapter;
import com.waterfairy.reminder.database.EveryDayDB;
import com.waterfairy.reminder.database.greendao.EveryDayDBDao;
import com.waterfairy.reminder.dialog.CalendarInputDialog;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener, EveryDayAdapter.OnItemLongClickListener {
    private RecyclerView mRecyclerView;
    private CalendarView mCalendarView;
    private EveryDayDBDao everyDayDB;
    private List<EveryDayDB> mDataList;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        findView();
        initView();
        initData();
    }

    private void initData() {
        date = new Date();
        queryData();
    }

    private void queryData() {
        everyDayDB = DataBaseManger.getInstance().getDaoSession().getEveryDayDBDao();
        mDataList = everyDayDB.queryBuilder()
                .where(EveryDayDBDao.Properties.Account.eq(ShareTool.getInstance().getAccount()))
                .orderDesc(EveryDayDBDao.Properties.Time)
                .orderDesc(EveryDayDBDao.Properties.ChangeTime)
                .list();
        mRecyclerView.setAdapter(new EveryDayAdapter(this, mDataList).setOnItemLongClickListener(this));
    }

    private void initView() {
        mCalendarView.setOnDateChangeListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mCalendarView = findViewById(R.id.calendar);
    }

    public void back(View view) {
        finish();
    }

    public void add(View view) {
        CalendarInputDialog calendarInputDialog = new CalendarInputDialog(this, "添加日程", "", new CalendarInputDialog.OnInputListener() {
            @Override
            public void onInput(String content) {
                everyDayDB.save(new EveryDayDB(content, date.getTime(), new Date().getTime()));
                queryData();
            }
        });
        calendarInputDialog.show();
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        date.setYear(year - 1900);
        date.setMonth(month);
        date.setDate(dayOfMonth);
        for (int i = 0; i < mDataList.size(); i++) {
            EveryDayDB everyDayDB = mDataList.get(i);
            if (date.getTime() >= everyDayDB.getTime()) {
              mRecyclerView.smoothScrollToPosition(i);
              return;
            }
        }
    }

    /**
     * 删除每天  主要代码
     *
     * @param db
     * @param pos
     */
    @Override
    public void onItemLongClick(final EveryDayDB db, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("删除这条记录吗?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                everyDayDB.delete(db);
                ((EveryDayAdapter) mRecyclerView.getAdapter()).remove(pos);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
