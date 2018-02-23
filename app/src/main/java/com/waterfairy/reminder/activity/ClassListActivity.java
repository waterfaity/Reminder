package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.ClassAdapter;
import com.waterfairy.reminder.database.ClassDB;
import com.waterfairy.reminder.database.greendao.ClassDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;

import java.util.List;

public class ClassListActivity extends AppCompatActivity implements ClassAdapter.OnItemClickListener, ClassAdapter.OnItemLongClickListener {

    private RecyclerView mRecyclerView;
    private ClassDBDao classDBDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        findView();
        initView();
        initData();
    }

    private void initData() {
        classDBDao = DataBaseManger.getInstance().getDaoSession().getClassDBDao();
        showData();

    }

    private void showData() {
        List<ClassDB> memorandumDBS =
                classDBDao.queryBuilder()
                        .orderAsc(ClassDBDao.Properties.Week)
                        .orderAsc(ClassDBDao.Properties.Time)
                        .orderAsc(ClassDBDao.Properties.Level)
                        .list();
        mRecyclerView.setAdapter(new ClassAdapter(this, memorandumDBS).setOnItemClickListener(this).setOnItemLongClickListener(this));
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
        startActivityForResult(new Intent(this, ClassAddActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            showData();
        }
    }

    @Override
    public void onItemClick(ClassDB db, int pos) {

    }

    @Override
    public void onItemLongClick(final ClassDB db, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("删除这条记录吗?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                classDBDao.delete(db);
                ((ClassAdapter) mRecyclerView.getAdapter()).remove(pos);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
