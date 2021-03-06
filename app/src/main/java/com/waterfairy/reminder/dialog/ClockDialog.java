package com.waterfairy.reminder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.utils.ShareTool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2017/11/25
 * des  :闹钟设置 (添加/修改/删除) 弹窗
 */

public class ClockDialog extends Dialog implements View.OnClickListener, TimePicker.OnTimeChangedListener {
    //view
    private View mRootView;//根布局 view
    private ImageView mDelete;//删除闹钟
    private TextView mEnsure, mCancel;//确定/取消
    private TextView mTime;//时间显示 view
    private List<TextView> mWeekList;
    //dialog
    private TimeSelectDialog mTimeSelectDialog;//时间选择 dialog
    //数据
    private ClockDB mClockDB;//闹钟数据 如果为空说明是创建操作
    private int mPosition;//点击位置(从列表中传来)
    private int[] mWeekIds = new int[]{R.id.week0, R.id.week1, R.id.week2, R.id.week3, R.id.week4, R.id.week5, R.id.week6,};
    //监听
    private final OnClockHandleListener onClockHandleListener;//点击处理监听

    public ClockDialog(@NonNull Context context, ClockDB mClockDB, int mPosition, OnClockHandleListener onClockHandleListener) {
        super(context);
        this.onClockHandleListener = onClockHandleListener;
        this.mClockDB = mClockDB;
        this.mPosition = mPosition;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_clock, null, false);
        addContentView(mRootView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        findView();
        initView();
    }

    private void findView() {
        mDelete = findViewById(R.id.delete);
        mCancel = findViewById(R.id.cancel);
        mEnsure = findViewById(R.id.ensure);
        mTime = findViewById(R.id.time);

    }

    private void initView() {
        String weekString = "";
        if (mClockDB == null) {
            //如果为空说明是创建操作 隐藏删除按钮
            mDelete.setVisibility(View.GONE);
            Calendar instance = Calendar.getInstance();
            int hour = instance.get(Calendar.HOUR_OF_DAY);
            int minute = instance.get(Calendar.MINUTE);
            mTime.setText((hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute));
        } else {
            mDelete.setOnClickListener(this);
            mTime.setText(mClockDB.getTime());
            weekString = mClockDB.getWeek();
        }

        mWeekList = new ArrayList<>();
        for (int i = 0; i < mWeekIds.length; i++) {
            TextView week = findViewById(mWeekIds[i]);
            week.setTag(false);
            week.setOnClickListener(new OnWeekClickListener());
            if (weekString.contains(i + "")) {
                week.setBackgroundResource(R.drawable.week_style_checked);
                week.setTag(true);
            }
            mWeekList.add(week);
        }

        mEnsure.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mTime.setOnClickListener(this);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击确认按钮-确认添加或修改完成
            case R.id.ensure:
                if (onClockHandleListener != null) {
                    dismiss();
                    String week = getWeek();
                    if (mClockDB == null) {//添加
                        ClockDB clockDB = new ClockDB()
                                .setTime(mTime.getText().toString())
                                .setWeek(week)
                                .setOneTime(TextUtils.isEmpty(week))
                                .setOpen(true)
                                .setAccount(ShareTool.getInstance().getAccount())
                                .setCreateTime(System.currentTimeMillis());
                        onClockHandleListener.onAdd(clockDB);
                    } else { //修改
                        onClockHandleListener.onRevise(
                                mClockDB.setTime(mTime.getText().toString())
                                        .setWeek(week)
                                        .setOneTime(TextUtils.isEmpty(week))
                                        .setUpdateTime(System.currentTimeMillis()),
                                mPosition);
                    }
                }
                break;
            //点击取消按钮-不做操作
            case R.id.cancel:
                dismiss();
                break;
            //点击删除-删除闹钟
            case R.id.delete:
                if (onClockHandleListener != null) {
                    dismiss();
                    onClockHandleListener.onDelete(mClockDB, mPosition);
                }
                break;
            //点击时间显示view-去选择时间
            case R.id.time:
                mTimeSelectDialog = new TimeSelectDialog(getContext(), this);
                mTimeSelectDialog.show();
                break;
        }
    }

    private String getWeek() {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < mWeekList.size(); i++) {
            if ((Boolean) mWeekList.get(i).getTag()) {
                stringBuffer.append(i);
                stringBuffer.append(",");
            }
        }
        if (stringBuffer.length() != 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    /**
     * 时间修改完成
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        mTimeSelectDialog.dismiss();
        mTime.setText(((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0" + minute : minute)));
    }

    public interface OnClockHandleListener {
        /**
         * 删除
         *
         * @param clockDB
         * @param position 删除位置
         */
        void onDelete(ClockDB clockDB, int position);

        /**
         * 添加
         *
         * @param clockDB
         */
        void onAdd(ClockDB clockDB);

        /**
         * 修改
         *
         * @param clockDB
         * @param position 修改位置
         */
        void onRevise(ClockDB clockDB, int position);
    }

    private class OnWeekClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Boolean tag = (Boolean) v.getTag();
            if (tag) {
                v.setBackgroundResource(R.drawable.week_style_no_checked);
            } else {
                v.setBackgroundResource(R.drawable.week_style_checked);
            }
            v.setTag(!tag);
        }
    }
}
