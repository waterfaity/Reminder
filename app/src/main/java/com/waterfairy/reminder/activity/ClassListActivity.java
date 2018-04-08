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

/**
 * 课程列表展示页面啊
 */
public class ClassListActivity extends AppCompatActivity implements ClassAdapter.OnItemClickListener, ClassAdapter.OnItemLongClickListener {

    //列表展示 控件
    private RecyclerView mRecyclerView;
    //课程数据库
    private ClassDBDao classDBDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        //定义控件
        findView();
        //初始化控件
        initView();
        initData();
    }

    private void initData() {
        classDBDao = DataBaseManger.getInstance().getDaoSession().getClassDBDao();
        showData();

    }

    /**
     * 展示课程主要代码
     * 查询所有的课程(排序:1.week,2.time,3.level) 并展示
     */
    private void showData() {
        //查询
        List<ClassDB> memorandumDBS =
                classDBDao.queryBuilder()
                        .orderAsc(ClassDBDao.Properties.Week)
                        .orderAsc(ClassDBDao.Properties.Time)
                        .orderAsc(ClassDBDao.Properties.Level)
                        .list();
        //展示
        mRecyclerView.setAdapter(new ClassAdapter(this, memorandumDBS).setOnItemClickListener(this).setOnItemLongClickListener(this));
    }

    private void initView() {
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //    返回  关闭页面
    public void back(View view) {
        finish();
    }

    //    点击添加按钮
    public void add(View view) {
        startActivityForResult(new Intent(this, ClassAddActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //接收上个页面的返回结果  (添加成功) - 刷新数据
            showData();
        }
    }

    @Override
    public void onItemClick(ClassDB db, int pos) {

    }

    /**
     * 删除课程主要代码
     * 长按某个 课程 跳出弹窗(dialog)
     *
     * @param db
     * @param pos
     */
    @Override
    public void onItemLongClick(final ClassDB db, final int pos) {
        //弹窗展示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("删除这条记录吗?");
        //设置确认按钮 并监听
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //数据库删除
                classDBDao.delete(db);
                //列表展示删除
                ((ClassAdapter) mRecyclerView.getAdapter()).remove(pos);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
