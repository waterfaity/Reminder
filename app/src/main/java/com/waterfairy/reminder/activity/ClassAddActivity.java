package com.waterfairy.reminder.activity;

//引入的java类

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.ClassSpinnerAdapter;
import com.waterfairy.reminder.database.ClassDB;
import com.waterfairy.reminder.database.greendao.ClassDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.utils.ToastUtils;

import java.util.Date;
import java.util.List;

/**
 * 课程添加页面
 */
public class ClassAddActivity extends AppCompatActivity {


    //课程名字
    private EditText mClassName;
    //课程数据库
    private ClassDBDao classDBDao;

    //1.(time)"上午", "下午", "晚上"; 2.(level)"第一节", "第二节", "第三节", "第四节"; 3.(week)"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
    private Spinner mTimeSpinner, mLevelSpinner, mWeekSpinner;
    //适配器 用于显示上面对应的列表
    private ClassSpinnerAdapter mTimeSpinnerAdapter, mLevelSpinnerAdapter, mWeekSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_add);
        //定义控件
        findView();
        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    //初始化数据
    private void initData() {
        classDBDao = DataBaseManger.getInstance().getDaoSession().getClassDBDao();
    }

    //初始化控件
    private void initView() {
        mWeekSpinner.setAdapter(mWeekSpinnerAdapter = new ClassSpinnerAdapter(this, new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"}));
        mLevelSpinner.setAdapter(mLevelSpinnerAdapter = new ClassSpinnerAdapter(this, new String[]{"第一节", "第二节", "第三节", "第四节"}));
        mTimeSpinner.setAdapter(mTimeSpinnerAdapter = new ClassSpinnerAdapter(this, new String[]{"上午", "下午", "晚上"}));
    }

    //定义控件
    private void findView() {
        mClassName = findViewById(R.id.class_name);
        mWeekSpinner = findViewById(R.id.week);
        mTimeSpinner = findViewById(R.id.time);
        mLevelSpinner = findViewById(R.id.level);
    }

    //返回按钮
    public void back(View view) {
        //关闭页面
        finish();
    }

    //添加课程主要代码
    //保存(判断输入的内容 是否之前有存储 例如:  1.上午,2.第一节课,3.星期一   如果有 删除 ,并保存新的课程,如果没有 直接保存)
    public void save(View view) {
        //从课程输入框  获取文本
        final String s = mClassName.getText().toString();
        //判断文本是否为空
        if (TextUtils.isEmpty(s)) {
            //如果为空 提示
            ToastUtils.show("请输入课程");
            //终止操作
            return;
        }
        //不为空继续执行
        //从数据库查询课程列表(查询条件: 获取输入的3个值  )
        List<ClassDB> list = classDBDao.queryBuilder().where(
                ClassDBDao.Properties.Week.eq(mWeekSpinnerAdapter.getItemId(mWeekSpinner.getSelectedItemPosition())),
                ClassDBDao.Properties.Time.eq(mTimeSpinnerAdapter.getItemId(mTimeSpinner.getSelectedItemPosition())),
                ClassDBDao.Properties.Level.eq(mLevelSpinnerAdapter.getItemId(mLevelSpinner.getSelectedItemPosition()))
        ).list();
        //如果list 有数据  删除数据的所有数据
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                classDBDao.delete(list.get(i));
            }
        }
        //数据库保存课程
        classDBDao.save(new ClassDB(
                mWeekSpinnerAdapter.getItemId(mWeekSpinner.getSelectedItemPosition()),
                mTimeSpinnerAdapter.getItemId(mTimeSpinner.getSelectedItemPosition()),
                mLevelSpinnerAdapter.getItemId(mLevelSpinner.getSelectedItemPosition()),
                s, new Date().getTime()
        ).setTag(mWeekSpinner.getSelectedItemPosition() + "_" + (1 + mTimeSpinner.getSelectedItemPosition() * 4 + (mLevelSpinner.getSelectedItemPosition()))));
        //保存成功  给上个页面一个信号 说明有新的课程保存 上个页面会去刷新
        setResult(RESULT_OK);
        //关闭页面
        finish();
    }


}
