package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.EveryDayAdapter;
import com.waterfairy.reminder.database.EveryDayDB;
import com.waterfairy.reminder.database.greendao.EveryDayDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.List;

/**
 * 每天  页面
 */

public class EveryDayActivity extends AppCompatActivity implements EveryDayAdapter.OnItemClickListener, EveryDayAdapter.OnItemLongClickListener {

    //列表 控件
    private RecyclerView mRecyclerView;
    //每天 数据库
    private EveryDayDBDao everyDayDBDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum);
        findView();
        initView();
        initData();
    }

    private void initData() {
        everyDayDBDao = DataBaseManger.getInstance().getDaoSession().getEveryDayDBDao();
        //展示数据
        showData();

    }

    /**
     * 展示数据
     */
    private void showData() {
        //获取数据
        List<EveryDayDB> memorandumDBS =
                everyDayDBDao.queryBuilder().where(EveryDayDBDao.Properties.Account.eq(ShareTool.getInstance().getAccount())).orderAsc(EveryDayDBDao.Properties.Time).list();
        //展示数据
        mRecyclerView.setAdapter(new EveryDayAdapter(this, memorandumDBS).setOnItemClickListener(this).setOnItemLongClickListener(this));
    }

    private void initView() {
        ((TextView) findViewById(R.id.title)).setText("每天");
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 返回关闭页面
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 添转 添加 页面
     *
     * @param view
     */
    public void add(View view) {
        startActivityForResult(new Intent(this, EveryDayAddActivity.class), 1);
    }

    /**
     * 添加页面(本页面打开的页面)返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //返回成功标记   说明添加成功 刷新数据
            showData();
        }
    }

    @Override
    public void onItemClick(EveryDayDB db, int pos) {

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
                everyDayDBDao.delete(db);
                ((EveryDayAdapter) mRecyclerView.getAdapter()).remove(pos);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
