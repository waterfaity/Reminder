package com.waterfairy.reminder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.UserDB;
import com.waterfairy.reminder.database.greendao.DaoMaster;
import com.waterfairy.reminder.database.greendao.DaoSession;
import com.waterfairy.reminder.database.greendao.UserDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;
import com.waterfairy.utils.ToastUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private UserDBDao userDBDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {
        boolean login = ShareTool.getInstance().isLogin();
        if (login) {
            ((TextView) findViewById(R.id.account)).setText(ShareTool.getInstance().getAccount());
            ((TextView) findViewById(R.id.password)).setText(ShareTool.getInstance().getPassword());
        }

    }

    private void initData() {
        userDBDao = DataBaseManger.getInstance().getDaoSession().getUserDBDao();
    }

    public void login(View view) {
        String account = ((TextView) findViewById(R.id.account)).getText().toString();
        String password = ((TextView) findViewById(R.id.password)).getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.show("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show("请输入密码");
            return;
        }
        if (TextUtils.equals("admin", account) && TextUtils.equals("admin", password)) {
            startActivity(new Intent(this, HomeActivity.class));
            save(account, password);
            finish();
            return;
        }
        List<UserDB> list = userDBDao.queryBuilder()
                .where(UserDBDao.Properties.Account.eq(account))
                .list();
        if (list != null && list.size() > 0) {
            if (TextUtils.equals(password, list.get(0).getPassword())) {
                startActivity(new Intent(this, HomeActivity.class));
                save(account, password);
                finish();
            } else {
                ToastUtils.show("密码不正确");
            }
        } else {
            ToastUtils.show("该账号不存在");
        }
    }

    private void save(String account, String password) {
        ShareTool.getInstance().saveLogin(true);
        ShareTool.getInstance().saveAccount(account);
        ShareTool.getInstance().savePassword(password);
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
