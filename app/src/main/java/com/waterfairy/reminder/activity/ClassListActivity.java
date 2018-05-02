package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.adapter.ClassAdapter;
import com.waterfairy.reminder.database.ClassDB;
import com.waterfairy.reminder.database.greendao.ClassDBDao;
import com.waterfairy.reminder.dialog.InputDialog;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.WeekUtils;
import com.waterfairy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 课程列表展示页面啊
 */
public class ClassListActivity extends AppCompatActivity implements ClassAdapter.OnItemClickListener, View.OnClickListener, View.OnLongClickListener {

    //课程数据库
    private ClassDBDao classDBDao;
    private List<List<TextView>> weekList;

    private int[] weekRes = new int[]{R.id.week_1, R.id.week_2, R.id.week_3, R.id.week_4, R.id.week_5, R.id.week_6, R.id.week_7};
    private int[] classRes = new int[]{R.id.class_1, R.id.class_2, R.id.class_3, R.id.class_4, R.id.class_5,
            R.id.class_6, R.id.class_7, R.id.class_8, R.id.class_9, R.id.class_10,
            R.id.class_11, R.id.class_12, R.id.class_13};
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list_2);
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
        List<ClassDB> classDBS =
                classDBDao.queryBuilder()
                        .orderAsc(ClassDBDao.Properties.Week)
                        .orderAsc(ClassDBDao.Properties.Time)
                        .orderAsc(ClassDBDao.Properties.Level)
                        .list();
        //展示
        for (int i = 0; i < classDBS.size(); i++) {
            ClassDB classDB = classDBS.get(i);
            String tag = classDB.getTag();
            String[] split = tag.split("_");
            TextView textView = weekList.get(Integer.parseInt(split[0])).get(Integer.parseInt(split[1]));
            textView.setText(classDB.getClassName());

        }
    }

    private void initView() {
        for (int i = 0; i < 7; i++) {
            TextView textView = weekList.get(i).get(0);
            textView.setBackgroundColor(Color.parseColor("#44FFFFFF"));
            textView.setText(WeekUtils.getWeeks(i + ""));
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        }
    }

    private void findView() {
        weekList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<TextView> classList = new ArrayList<>();
            View viewById = findViewById(weekRes[i]);
            for (int j = 0; j < 13; j++) {
                TextView textView = (TextView) viewById.findViewById(classRes[j]).findViewById(R.id.class_class);
                if (j != 0) {
                    textView.setOnClickListener(this);
                    textView.setOnLongClickListener(this);
                    textView.setTag(i + "_" + j);
                }
                classList.add(textView);
            }
            weekList.add(classList);
        }
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
     */
    public void onDelete(final ClassDB db) {
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
                textView.setText("");
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                textView.setBackgroundColor(Color.WHITE);
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.class_class) {
            final String tag = (String) v.getTag();
            textView = (TextView) v;
            textView.setBackgroundColor(Color.CYAN);
            InputDialog inputDialog = new InputDialog(this, "请输入课程", "", new InputDialog.OnInputListener() {
                @Override
                public void onInput(String content) {
                    save(tag, content);
                }
            });
            inputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    textView.setBackgroundColor(Color.WHITE);
                }
            });
            inputDialog.show();
        }
    }
    //添加课程主要代码
    //保存(判断输入的内容 是否之前有存储 例如:  1.上午,2.第一节课,3.星期一   如果有 删除 ,并保存新的课程,如果没有 直接保存)

    private void save(String tag, String content) {
        //从课程输入框  获取文本
        //判断文本是否为空
        if (TextUtils.isEmpty(content)) {
            //如果为空 提示
            ToastUtils.show("请输入课程");
            //终止操作
            return;
        }
        //不为空继续执行
        //从数据库查询课程列表(查询条件: 获取输入的3个值  )
        List<ClassDB> list = classDBDao.queryBuilder().where(ClassDBDao.Properties.Tag.eq(tag)
        ).list();
        //如果list 有数据  删除数据的所有数据
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                classDBDao.delete(list.get(i));
            }
        }
        //数据库保存课程
        classDBDao.save(new ClassDB(content, tag, new Date().getTime()));
        textView.setText(content);
    }

    @Override
    public boolean onLongClick(View v) {
        textView = (TextView) v;
        textView.setBackgroundColor(Color.CYAN);
        String tag = (String) v.getTag();
        if (!TextUtils.isEmpty(textView.getText().toString())) {
            List<ClassDB> list = classDBDao.queryBuilder().where(
                    ClassDBDao.Properties.Tag.eq(tag)).list();
            if (list != null) {
                ClassDB classDB = list.get(0);
                onDelete(classDB);
            }
        }
        return false;
    }
}
