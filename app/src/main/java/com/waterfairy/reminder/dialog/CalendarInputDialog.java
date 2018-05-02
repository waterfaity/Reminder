package com.waterfairy.reminder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.utils.ToastUtils;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2017/11/25
 * des  :选择时间
 */

public class CalendarInputDialog extends Dialog {

    public CalendarInputDialog(Context context, final String title, String content, final OnInputListener onInputListener) {
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_calendar_input, null));
        final EditText editText = findViewById(R.id.content);
        final TextView titleTV = findViewById(R.id.title);
        titleTV.setText(title);
        editText.setText(content);
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.ensure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();

                if (TextUtils.isEmpty(content)) {
                    ToastUtils.show(title + "");
                } else {
                    if (onInputListener != null) {
                        onInputListener.onInput(content);
                        dismiss();
                    }
                }
            }
        });


    }

    public interface OnInputListener {
        void onInput(String content);
    }
}
