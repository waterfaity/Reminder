package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.EveryDayAdapter;
import com.waterfairy.reminder.database.EveryDayDB;
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.database.greendao.EveryDayDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.TimeUtils;
import com.waterfairy.utils.ToastUtils;

import java.util.Date;
import java.util.List;

public class EveryDayAddActivity extends AppCompatActivity {
    private TextView mTime;
    private EditText mContent;
    private Date date;
    private EveryDayDBDao memorandumDBDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum_add);
        findView();
        initView();
        initData();
    }

    private void initData() {
        memorandumDBDao = DataBaseManger.getInstance().getDaoSession().getEveryDayDBDao();
    }

    private void initView() {

    }

    private void findView() {
        mContent = findViewById(R.id.et_content);
        mTime = findViewById(R.id.time);
        mTime.setVisibility(View.GONE);
    }

    public void back(View view) {
        finish();
    }

    public void save(View view) {
        //保存
        final String s = mContent.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtils.show("请输入内容");
            return;
        }
        final long time = new Date().getTime() / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000);
        final List<EveryDayDB> list = memorandumDBDao.queryBuilder().where(EveryDayDBDao.Properties.Time.eq(time)).list();
        if (list != null && list.size() != 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("是否覆盖今天的记录?");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < list.size(); i++) {
                        memorandumDBDao.delete(list.get(i));
                    }
                    save(s, time);
                }
            });
            builder.setNegativeButton("取消", null);
            builder.create().show();
        } else {
            save(s, time);
        }
    }

    private void save(String s, long time) {
        memorandumDBDao.save(new EveryDayDB(s, time, new Date().getTime()));
        setResult(RESULT_OK);
        finish();
    }


}
