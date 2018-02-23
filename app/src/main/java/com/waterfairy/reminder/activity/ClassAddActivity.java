package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.ClassSpinnerAdapter;
import com.waterfairy.reminder.database.ClassDB;
import com.waterfairy.reminder.database.EveryDayDB;
import com.waterfairy.reminder.database.greendao.ClassDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.utils.ToastUtils;

import java.util.Date;
import java.util.List;

public class ClassAddActivity extends AppCompatActivity {

    private EditText mClassName;
    private ClassDBDao classDBDao;

    private Spinner mTimeSpinner, mLevelSpinner, mWeekSpinner;
    private ClassSpinnerAdapter mTimeSpinnerAdapter, mLevelSpinnerAdapter, mWeekSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_add);
        findView();
        initView();
        initData();
    }

    private void initData() {
        classDBDao = DataBaseManger.getInstance().getDaoSession().getClassDBDao();
    }

    private void initView() {
        mWeekSpinner.setAdapter(mWeekSpinnerAdapter = new ClassSpinnerAdapter(this, new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"}));
        mLevelSpinner.setAdapter(mLevelSpinnerAdapter = new ClassSpinnerAdapter(this, new String[]{"第一节", "第二节", "第三节", "第四节"}));
        mTimeSpinner.setAdapter(mTimeSpinnerAdapter = new ClassSpinnerAdapter(this, new String[]{"上午", "下午", "晚上"}));
    }


    private void findView() {
        mClassName = findViewById(R.id.class_name);
        mWeekSpinner = findViewById(R.id.week);
        mTimeSpinner = findViewById(R.id.time);
        mLevelSpinner = findViewById(R.id.level);
    }

    public void back(View view) {
        finish();
    }

    public void save(View view) {
//        //保存
        final String s = mClassName.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtils.show("请输入课程");
            return;
        }
        List<ClassDB> list = classDBDao.queryBuilder().where(
                ClassDBDao.Properties.Week.eq(mWeekSpinnerAdapter.getItemId(mWeekSpinner.getSelectedItemPosition())),
                ClassDBDao.Properties.Time.eq(mTimeSpinnerAdapter.getItemId(mTimeSpinner.getSelectedItemPosition())),
                ClassDBDao.Properties.Level.eq(mLevelSpinnerAdapter.getItemId(mLevelSpinner.getSelectedItemPosition()))
        ).list();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                classDBDao.delete(list.get(i));
            }
        }
        classDBDao.save(new ClassDB(
                mWeekSpinnerAdapter.getItemId(mWeekSpinner.getSelectedItemPosition()),
                mTimeSpinnerAdapter.getItemId(mTimeSpinner.getSelectedItemPosition()),
                mLevelSpinnerAdapter.getItemId(mLevelSpinner.getSelectedItemPosition()),
                s, new Date().getTime()
        ));
        setResult(RESULT_OK);
        finish();
    }


}
