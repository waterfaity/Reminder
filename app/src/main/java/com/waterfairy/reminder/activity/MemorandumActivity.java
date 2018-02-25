package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.MemorandumAdapter;
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.List;

public class MemorandumActivity extends AppCompatActivity implements MemorandumAdapter.OnItemClickListener, MemorandumAdapter.OnItemLongClickListener {

    private RecyclerView mRecyclerView;
    private MemorandumDBDao memorandumDBDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum);
        findView();
        initView();
        initData();
    }

    private void initData() {
        memorandumDBDao = DataBaseManger.getInstance().getDaoSession().getMemorandumDBDao();
        showData();

    }

    private void showData() {
        List<MemorandumDB> memorandumDBS =
                memorandumDBDao.queryBuilder().where(MemorandumDBDao.Properties.Account.eq(ShareTool.getInstance().getAccount())).orderAsc(MemorandumDBDao.Properties.Time).list();
        mRecyclerView.setAdapter(new MemorandumAdapter(this, memorandumDBS).setOnItemClickListener(this).setOnItemLongClickListener(this));
    }

    private void initView() {

    }

    private void findView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void back(View view) {
        finish();
    }

    public void add(View view) {
        startActivityForResult(new Intent(this, MemorandumAddActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            showData();
        }
    }

    @Override
    public void onItemClick(MemorandumDB db, int pos) {

    }

    @Override
    public void onItemLongClick(final MemorandumDB db, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("删除这条备忘录吗?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                memorandumDBDao.delete(db);
                ((MemorandumAdapter) mRecyclerView.getAdapter()).remove(pos);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
