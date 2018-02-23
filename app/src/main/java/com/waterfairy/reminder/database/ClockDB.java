package com.waterfairy.reminder.database;

import android.text.TextUtils;

import com.waterfairy.reminder.manger.ClockManger;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :闹钟数据库
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
    private long createTime;//创建时间
    private long updateTime;//更新时间
    private long firstTime;//闹钟第一次时间
    @Transient
    private String time;


    @Generated(hash = 2071920913)
    public ClockDB(Long id, boolean open, String account, int hour, int minute,
                   String week, boolean oneTime, long createTime, long updateTime,
                   long firstTime) {
        this.id = id;
        this.open = open;
        this.account = account;
        this.hour = hour;
        this.minute = minute;
        this.week = week;
        this.oneTime = oneTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.firstTime = firstTime;
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
            //计算第一闹钟时间
            firstTime = ClockManger.getInstance().getTime(hour, minute);
        }
        return this;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public String getTag() {
        return createTime + "";
    }

    public ClockDB setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public ClockDB setCreateTime(long createTime) {
        this.createTime = createTime;
        this.updateTime = createTime;
        return this;
    }

    public long getFirstTime() {
        return this.firstTime;
    }

    public ClockDB setFirstTime(long firstTime) {
        this.firstTime = firstTime;
        return this;
    }
}
