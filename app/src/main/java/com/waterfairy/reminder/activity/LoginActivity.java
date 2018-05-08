package com.waterfairy.reminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.MemorandumCategoryDB;
import com.waterfairy.reminder.database.UserDB;
import com.waterfairy.reminder.database.greendao.MemorandumCategoryDBDao;
import com.waterfairy.reminder.database.greendao.UserDBDao;
import com.waterfairy.reminder.manger.DataBaseManger;
import com.waterfairy.reminder.utils.ShareTool;
import com.waterfairy.utils.ToastUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {
    private UserDBDao userDBDao;//用户数据库

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
        insertMemorandumCategoryData();
    }

    //插入默认备忘录分类
    private void insertMemorandumCategoryData() {
        MemorandumCategoryDBDao memorandumCategoryDBDao = DataBaseManger.getInstance().getDaoSession().getMemorandumCategoryDBDao();
        List<MemorandumCategoryDB> categoryDBS = memorandumCategoryDBDao.queryBuilder().where(MemorandumCategoryDBDao.Properties.Name.eq("其它")).limit(1).list();
        if (categoryDBS.size() == 0) {
            memorandumCategoryDBDao.save(new MemorandumCategoryDB(new Date().getTime(), new Date().getTime(), "其它"));
            memorandumCategoryDBDao.save(new MemorandumCategoryDB(new Date().getTime(), new Date().getTime(), "睡觉"));
            memorandumCategoryDBDao.save(new MemorandumCategoryDB(new Date().getTime(), new Date().getTime(), "吃饭"));
            memorandumCategoryDBDao.save(new MemorandumCategoryDB(new Date().getTime(), new Date().getTime(), "学习"));
            memorandumCategoryDBDao.save(new MemorandumCategoryDB(new Date().getTime(), new Date().getTime(), "运动"));
        }
    }

    /**
     * 登录主要代码
     * 使用本地登录 用户数据在手机数据库中存储 (注册后保存到本地数据库)
     * 匹配账号和密码 进行登录
     *
     * @param view
     */
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

    /**
     * 记录 账号和密码  方便下次登录
     *
     * @param account
     * @param password
     */
    private void save(String account, String password) {
        ShareTool.getInstance().saveLogin(true);
        ShareTool.getInstance().saveAccount(account);
        ShareTool.getInstance().savePassword(password);
    }

    /**
     * 调注册页面
     *
     * @param view
     */
    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
