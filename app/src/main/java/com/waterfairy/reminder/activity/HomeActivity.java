package com.waterfairy.reminder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.utils.ShareTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        test();
    }

    private void test() {
        try {
            File databasesFile = new File(getCacheDir().getParentFile(), "databases");
            File[] files = databasesFile.listFiles();
            for (File file1 : files) {
                copyFile(file1);
            }

        } catch (Exception ignored) {

        }

    }

    private void copyFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            String localFile = getLocalFile(file);
            if (TextUtils.isEmpty(localFile)) {
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(localFile);
            byte[] bytes = new byte[1024 * 512];

            try {
                int len = 0;
                while ((len = fileInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                }
                fileOutputStream.flush();
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getLocalFile(File file) {
        File targetFile = new File("/sdcard/reminder/" + file.getName());
        if (!targetFile.exists()) {
            File parentFile = targetFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                targetFile.createNewFile();
                return targetFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return targetFile.getAbsolutePath();
        }
        return null;
    }


    /**
     * 备忘录
     *
     * @param view
     */
    public void memorandum(View view) {
        startActivity(new Intent(this, MemorandumActivity.class));
    }

    /**
     * 每天
     *
     * @param view
     */
    public void everyday(View view) {
        startActivity(new Intent(this, CalendarActivity.class));

    }

    /**
     * 闹钟
     *
     * @param view
     */
    public void clock(View view) {
        startActivity(new Intent(this, ClockActivity.class));
    }

    /**
     * 课程表
     *
     * @param view
     */
    public void classList(View view) {
        startActivity(new Intent(this, ClassListActivity.class));
    }

    /**
     * 退出帐号
     *
     * @param view
     */
    public void quit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("退出帐号?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShareTool.getInstance().saveLogin(false);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
