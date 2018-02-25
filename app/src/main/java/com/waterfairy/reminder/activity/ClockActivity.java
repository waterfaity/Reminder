package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.ClockAdapter;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.database.greendao.ClockDBDao;
import com.waterfairy.reminder.dialog.ClockDialog;
import com.waterfairy.reminder.manger.ClockManger;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.List;

public class ClockActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, ClockDialog.OnClockHandleListener, ClockAdapter.OnItemClickListener {
    private static final String TAG = "clock1";
    private ClockAdapter mClockAdapter;
    private ClockDBDao mClockDBDao;
    private List<ClockDB> mDataList;
    private RecyclerView mRecyclerView;

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
                .where(ClockDBDao.Properties.Account.eq(ShareTool.getInstance().getAccount())).orderDesc(ClockDBDao.Properties.CreateTime)
                .list();
        mClockAdapter = new ClockAdapter(this, mDataList);
        mClockAdapter.setOnCheckedChangeListener(this);
        mClockAdapter.setOnClickListener(this);
        mClockAdapter.setOnItemClickListener(this);
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
        ClockManger.getInstance().initClock();
    }

    /**
     * 点击条目修改
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
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
        ClockManger.getInstance().initClock();
    }

    @Override
    public void onAdd(ClockDB clockDB) {
        mClockDBDao.insert(clockDB);
        mDataList.add(0, clockDB);
        mClockAdapter.notifyItemInserted(0);
        ClockManger.getInstance().initClock();
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
        ClockManger.getInstance().initClock();
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onItemClick(int pos, ClockDB clockDB) {
        new ClockDialog(this, clockDB, pos, this).show();
    }

    @Override
    public void onItemDeleteClick(final int pos, final ClockDB clockDB) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("删除该闹钟?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDelete(clockDB, pos);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
