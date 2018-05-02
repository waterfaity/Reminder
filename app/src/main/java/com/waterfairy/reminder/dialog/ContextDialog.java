package com.waterfairy.reminder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

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
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_context_menu, null));
        findViewById(R.id.menu1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuClickListener != null) onMenuClickListener.onMenuClick(0);
                dismiss();
            }
        });
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
