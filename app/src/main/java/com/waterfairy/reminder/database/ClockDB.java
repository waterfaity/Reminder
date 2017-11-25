package com.waterfairy.reminder.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :
 */

@Entity
public class ClockDB {
    @Id
    private Long id;

    private boolean open;//开启true? 不开 false
    private String account;//用户
    private String time;//时间12:12
    private String week;//0.1.2.3.4.5.6

    public ClockDB(boolean open, String account, String time, String week) {
        this.open = open;
        this.account = account;
        this.time = time;
        this.week = week;
    }

    @Generated(hash = 513362500)
    public ClockDB(Long id, boolean open, String account, String time,
            String week) {
        this.id = id;
        this.open = open;
        this.account = account;
        this.time = time;
        this.week = week;
    }

    @Generated(hash = 1927445362)
    public ClockDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
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

    public ClockDB setTime(String time) {
        this.time = time;
        return this;
    }

    public String getWeek() {
        return this.week;
    }

    public ClockDB setWeek(String week) {
        this.week = week;
        return this;
    }

}
