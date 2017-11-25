package com.waterfairy.reminder.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :闹钟提醒
 */
@Entity
public class ReminderDB {
    @Id
    private Long id;//id
    private String account;//帐号
    private String time;//时间
    private boolean open;//开关
    @Generated(hash = 1998787289)
    public ReminderDB(Long id, String account, String time, boolean open) {
        this.id = id;
        this.account = account;
        this.time = time;
        this.open = open;
    }
    @Generated(hash = 2136009147)
    public ReminderDB() {
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
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public boolean getOpen() {
        return this.open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }


}
