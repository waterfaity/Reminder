package com.waterfairy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.ClockDB;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :
 */

public class ClockDialog extends Dialog implements View.OnClickListener, TimePicker.OnTimeChangedListener {

    private final OnClockHandleListener onClockHandleListener;
    private ImageView mDelete;
    private TextView mEnsure, mCancel;
    private ClockDB clockDB;
    private TextView mTime;
    private TimeSelectDialog timeSelectDialog;
    private int position;

    public ClockDialog(@NonNull Context context, ClockDB clockDB, int position, OnClockHandleListener onClockHandleListener) {
        super(context);
        this.onClockHandleListener = onClockHandleListener;
        this.clockDB = clockDB;
        this.position = position;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_clock, null, false);
        addContentView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        findView();
        initView(view);
    }

    private void findView() {
        mDelete = findViewById(R.id.delete);
        mCancel = findViewById(R.id.cancel);
        mEnsure = findViewById(R.id.ensure);
        mTime = findViewById(R.id.time);
    }

    private void initView(View view) {
        if (clockDB == null) {
            mDelete.setVisibility(View.GONE);
        } else {
            mDelete.setOnClickListener(this);
        }
        mEnsure.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mTime.setOnClickListener(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ensure:
                if (onClockHandleListener != null) {
                    dismiss();
                    if (clockDB == null)
                        onClockHandleListener.onAdd(mTime.getText().toString(), "");
                    else onClockHandleListener.onRevise(clockDB.setTime(mTime.getText().toString())
                            .setWeek(""), position);
                }
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.delete:
                if (onClockHandleListener != null) {
                    dismiss();
                    onClockHandleListener.onDelete(clockDB, position);
                }
                break;
            case R.id.time:
                timeSelectDialog = new TimeSelectDialog(getContext(), this);
                timeSelectDialog.show();
                break;
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        timeSelectDialog.dismiss();
        mTime.setText(hourOfDay + ":" + minute);
    }

    public interface OnClockHandleListener {
        void onDelete(ClockDB clockDB, int position);

        void onAdd(String time, String week);

        void onRevise(ClockDB clockDB, int position);
    }
}
