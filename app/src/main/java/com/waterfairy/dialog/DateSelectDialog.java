package com.waterfairy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import com.waterfairy.reminder.R;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/1/6
 * des  :
 */

public class DateSelectDialog extends Dialog {
    public DateSelectDialog(@NonNull Context context,CalendarView.OnDateChangeListener onDateChangeListener) {
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_date_select, null));
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.ensure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onTimeChangedListener.onTimeChanged(timePicker, hour, minute);
            }
        });
    }
}
