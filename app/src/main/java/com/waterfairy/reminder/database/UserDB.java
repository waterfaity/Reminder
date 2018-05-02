package com.waterfairy.reminder.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2017/11/25
 * des  :账号
 */
@Entity
public class UserDB {
    public UserDB(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Generated(hash = 594337692)
    public UserDB(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    @Generated(hash = 1312299826)
    public UserDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    private Long id;

    private String account;
    private String password;
}
