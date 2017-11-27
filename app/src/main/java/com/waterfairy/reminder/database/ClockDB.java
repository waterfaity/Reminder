package com.waterfairy.reminder.database;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

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
    private int hour;//小时
    private int minute;//分钟
    private String week;//0.1.2.3.4.5.6
    private boolean oneTime;//响一次
    @Transient
    private String time;

    @Generated(hash = 1439968449)
    public ClockDB(Long id, boolean open, String account, int hour, int minute,
                   String week, boolean oneTime) {
        this.id = id;
        this.open = open;
        this.account = account;
        this.hour = hour;
        this.minute = minute;
        this.week = week;
        this.oneTime = oneTime;
    }

    @Generated(hash = 1927445362)
    public ClockDB() {
    }

    public Long getId() {
        return this.id;
    }

    public ClockDB setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean getOpen() {
        return this.open;
    }

    public ClockDB setOpen(boolean open) {
        this.open = open;
        return this;
    }

    public String getAccount() {
        return this.account;
    }

    public ClockDB setAccount(String account) {
        this.account = account;
        return this;
    }

    public int getHour() {
        return this.hour;
    }

    public ClockDB setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public int getMinute() {
        return this.minute;
    }

    public ClockDB setMinute(int minute) {
        this.minute = minute;
        return this;
    }

    public String getWeek() {
        return this.week;
    }

    public ClockDB setWeek(String week) {
        this.week = week;
        return this;
    }

    public boolean getOneTime() {
        return this.oneTime;
    }

    public ClockDB setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
        return this;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public String getTime() {
        String firstTime = hour + "";
        if (hour < 10) {
            firstTime = "0" + hour;
        }
        String secondTime = minute + "";
        if (minute < 10) {
            secondTime = "0" + minute;
        }
        return firstTime + ":" + secondTime;
    }

    public ClockDB setTime(String time) {
        this.time = time;
        if (!TextUtils.isEmpty(time)) {
            String[] split = time.split(":");
            hour = Integer.parseInt(split[0]);
            minute = Integer.parseInt(split[1]);
        }
        return this;
    }
}
