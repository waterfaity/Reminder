package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.CategoryAdapter;
import com.waterfairy.reminder.database.MemorandumCategoryDB;
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.database.greendao.MemorandumCategoryDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.dialog.ContextDialog;
import com.waterfairy.reminder.dialog.InputDialog;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.DialogUtils;
import com.waterfairy.utils.ToastUtils;

import java.util.Date;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private MemorandumCategoryDBDao dbDao;
    private CategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        findView();
        initView();
        initData();
    }

    private void initData() {
        dbDao = DataBaseManger.getInstance().getDaoSession().getMemorandumCategoryDBDao();
        queryDb();
    }

    private void queryDb() {
        List<MemorandumCategoryDB> list = dbDao.queryBuilder().orderDesc(MemorandumCategoryDBDao.Properties.CreateTime).list();
        mRecyclerView.setAdapter(mAdapter = new CategoryAdapter(this, list));
        mAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public void onClick(final View v) {
        final MemorandumCategoryDB categoryDB = mAdapter.getItem((int) v.getTag());
        if (TextUtils.equals(categoryDB.getName(), "其它")) return;
        ContextDialog contextDialog = new ContextDialog(this);
        contextDialog.setOnMenuClickListener(new ContextDialog.OnMenuClickListener() {
            @Override
            public void onMenuClick(int pos) {
                if (pos == 0) {
                    edit(categoryDB, (int) v.getTag());
                } else {
                    delete(categoryDB, (int) v.getTag());
                }
            }
        });
        contextDialog.show();

    }

    /**
     * 删除分类
     *
     * @param categoryDB
     * @param tag
     */
    private void delete(final MemorandumCategoryDB categoryDB, final int tag) {
        DialogUtils.getDialog(this, "所有属于该分类的备忘录记录分类将改为 其它。\n是否删除?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    dbDao.delete(categoryDB);
                    mAdapter.getData().remove(tag);
                    mAdapter.notifyDataSetChanged();
                    updateMemory(categoryDB.getId(), mAdapter.getData().get(mAdapter.getData().size() - 1).getId());
                }
                dialog.dismiss();
            }
        }).show();

    }

    private void updateMemory(Long deleteId, Long newCategoryId) {
        MemorandumDBDao dbDao = DataBaseManger.getInstance().getDaoSession().getMemorandumDBDao();
        List<MemorandumDB> memorandumDBS = dbDao.queryBuilder().where(MemorandumDBDao.Properties.CategoryId.eq(deleteId)).list();
        for (int i = 0; i < memorandumDBS.size(); i++) {
            MemorandumDB memorandumDB = memorandumDBS.get(i);
            memorandumDB.setCategoryId(newCategoryId);
            dbDao.save(memorandumDB);
        }
    }

    private void edit(final MemorandumCategoryDB categoryDB, int tag) {
        //修改分类
        new InputDialog(this, "编辑分类", categoryDB.getName(), new InputDialog.OnInputListener() {
            @Override
            public void onInput(String content) {

                if (checkExist(content)) {
                    ToastUtils.show("已经存在该类");
                    return;
                }
                categoryDB.setName(content);
                categoryDB.setUpdateTime(new Date().getTime());
                dbDao.save(categoryDB);
                mAdapter.notifyDataSetChanged();
            }
        }).show();
    }


    public void back(View view) {
        finish();
    }

    /**
     * 添加分类
     *
     * @param view
     */
    public void add(View view) {
        new InputDialog(this, "添加分类", "", new InputDialog.OnInputListener() {
            @Override
            public void onInput(String content) {
                if (checkExist(content)) {
                    ToastUtils.show("已经存在该类");
                    return;
                }
                dbDao.save(new MemorandumCategoryDB(new Date().getTime(), new Date().getTime(), content));
                queryDb();
            }


        }).show();
    }

    private boolean checkExist(String content) {
        List<MemorandumCategoryDB> list = dbDao.queryBuilder().where(MemorandumCategoryDBDao.Properties.Name.eq(content)).limit(1).list();
        return list.size() != 0;
    }

}
