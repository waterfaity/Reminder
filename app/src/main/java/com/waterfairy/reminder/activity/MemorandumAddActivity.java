package com.waterfairy.reminder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.TimeUtils;
import com.waterfairy.utils.ToastUtils;

import java.util.Date;

/**
 * 备忘录 添加页面
 */
public class MemorandumAddActivity extends AppCompatActivity implements OnSureLisener {
    public static final String STR_DB = "memorandumDb";
    public static final String STR_POS = "pos";
    private TextView mTime;
    private EditText mContent;
    private Date date;
    private MemorandumDBDao memorandumDBDao;
    private MemorandumDB memorandumDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum_add);
        findView();
        initView();
        initData();
    }

    private void initData() {
        memorandumDBDao = DataBaseManger.getInstance().getDaoSession().getMemorandumDBDao();
        memorandumDB = (MemorandumDB) getIntent().getSerializableExtra(STR_DB);
        if (memorandumDB == null) {
            onSure(new Date());
        } else {
            //修改
            onSure(new Date(memorandumDB.getTime()));
            mContent.setText(memorandumDB.getContent());
        }
    }

    private void initView() {

    }

    private void findView() {
        mContent = findViewById(R.id.et_content);
        mTime = findViewById(R.id.time);
    }

    public void back(View view) {
        finish();
    }

    /**
     * 保存备忘录主要页面
     */
    public void save(View view) {

        String s = mContent.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtils.show("请输入内容");
            return;
        }
        if (memorandumDB != null) {
            //修改
            memorandumDB.setContent(s);
            memorandumDB.setTime(date.getTime());
            memorandumDB.setChangeTime(new Date().getTime());
            memorandumDBDao.update(memorandumDB);
        } else {
            //创建新的
            memorandumDBDao.save(new MemorandumDB(s, date.getTime(), new Date().getTime()));
        }
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 设置时间显示弹窗
     *
     * @param view
     */
    public void setTime(View view) {
        //时间选择
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(this);
        dialog.show();
    }

    /**
     * 选择时间 显示到文本框
     *
     * @param date
     */
    @Override
    public void onSure(Date date) {
        this.date = date;
        mTime.setText(TimeUtils.format(date));
    }

}
