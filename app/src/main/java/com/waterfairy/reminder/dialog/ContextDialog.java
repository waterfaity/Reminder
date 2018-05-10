package com.waterfairy.reminder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.waterfairy.reminder.R;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2018/4/19
 * des  :
 */

public class ContextDialog extends Dialog {
    private OnMenuClickListener onMenuClickListener;

    public ContextDialog(@NonNull Context context) {
        this(context, null, null);
    }

    public ContextDialog(@NonNull Context context, String text1, String text2) {
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_context_menu, null));
        if (!TextUtils.isEmpty(text1))
            ((TextView) findViewById(R.id.menu1)).setText(text1);
        findViewById(R.id.menu1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuClickListener != null) onMenuClickListener.onMenuClick(0);
                dismiss();
            }
        });
        if (!TextUtils.isEmpty(text2))
            ((TextView) findViewById(R.id.menu2)).setText(text2);
        findViewById(R.id.menu2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuClickListener != null) onMenuClickListener.onMenuClick(1);
                dismiss();
            }
        });
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener {
        void onMenuClick(int pos);
    }

}
