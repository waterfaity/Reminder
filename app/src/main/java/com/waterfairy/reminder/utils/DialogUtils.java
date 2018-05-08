package com.waterfairy.reminder.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/5/9
 * des  :
 */

public class DialogUtils {
    public static Dialog getDialog(Context context, String content, final DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onClickListener != null) onClickListener.onClick(dialog, which);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onClickListener != null) onClickListener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

}
