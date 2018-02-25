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
import com.waterfairy.reminder.adapter.MemorandumAdapter;
import com.waterfairy.reminder.database.EveryDayDB;
import com.waterfairy.reminder.database.greendao.EveryDayDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.List;

public class EveryDayActivity extends AppCompatActivity implements EveryDayAdapter.OnItemClickListener, EveryDayAdapter.OnItemLongClickListener {

    private RecyclerView mRecyclerView;
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
        showData();

    }

    private void showData() {
        List<EveryDayDB> memorandumDBS =
                everyDayDBDao.queryBuilder().where(EveryDayDBDao.Properties.Account.eq(ShareTool.getInstance().getAccount())).orderAsc(EveryDayDBDao.Properties.Time).list();
        mRecyclerView.setAdapter(new EveryDayAdapter(this, memorandumDBS).setOnItemClickListener(this).setOnItemLongClickListener(this));
    }

    private void initView() {
        ((TextView) findViewById(R.id.title)).setText("每天");
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void back(View view) {
        finish();
    }

    public void add(View view) {
        startActivityForResult(new Intent(this, EveryDayAddActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            showData();
        }
    }

    @Override
    public void onItemClick(EveryDayDB db, int pos) {

    }

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
