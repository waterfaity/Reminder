package com.waterfairy.reminder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.waterfairy.dialog.ClockDialog;
import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.ClockAdapter;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.database.greendao.ClockDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.List;

public class ClockActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, ClockDialog.OnClockHandleListener {
    private static final String TAG = "clock1";
    private ClockAdapter mClockAdapter;
    private ClockDBDao mClockDBDao;
    private List<ClockDB> mDataList;
    private RecyclerView mRecyclerView;
    private ClockDialog clockDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        findView();
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    private void initData() {
        mClockDBDao = DataBaseManger.getInstance().getDaoSession().getClockDBDao();
        mDataList = mClockDBDao.queryBuilder()
                .where(ClockDBDao.Properties.Account.eq(ShareTool.getInstance().getAccount()))
                .list();
        mClockAdapter = new ClockAdapter(this, mDataList);
        mClockAdapter.setOnCheckedChangeListener(this);
        mClockAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mClockAdapter);


    }

    /**
     * 点击按钮添加
     *
     * @param view
     */
    public void addClock(View view) {
        new ClockDialog(this, null, 0, this).show();
    }

    /**
     * 复选按钮(闹钟开关)
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        ClockDB clockDB = mDataList.get(position);
        clockDB.setOpen(isChecked);
        mClockDBDao.update(clockDB);
    }

    /**
     * 点击条目修改
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Log.i(TAG, "onClick: " + position);
        ClockDB clockDB = mDataList.get(position);
        clockDialog = new ClockDialog(this, clockDB, position, this);
        clockDialog.show();
    }

    /**
     * 确认删除
     *
     * @param clockDB
     * @param position
     */
    @Override
    public void onDelete(ClockDB clockDB, int position) {
        mClockDBDao.delete(clockDB);
        mDataList.remove(clockDB);
        mClockAdapter.notifyDataSetChanged();
//        mClockAdapter.notifyItemRemoved(position);
//        mClockAdapter.notifyItemChanged(position, mDataList.size() - position);
    }

    /**
     * 确认添加
     *
     * @param time
     * @param week
     */
    @Override
    public void onAdd(String time, String week) {
        ClockDB clockDB = new ClockDB(true, ShareTool.getInstance().getAccount(), time, week);
        mClockDBDao.insert(clockDB);
        mDataList.add(clockDB);
        mClockAdapter.notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 确认修改
     *
     * @param clockDB
     * @param position
     */
    @Override
    public void onRevise(ClockDB clockDB, int position) {
        mClockDBDao.update(clockDB);
        mClockAdapter.notifyItemChanged(position);
    }

    public void back(View view) {
        finish();
    }
}
