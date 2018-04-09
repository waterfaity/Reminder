package com.waterfairy.reminder.database;

import com.waterfairy.reminder.utils.ShareTool;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/2/23
 * des  :
 */

@Entity
public class ClassDB {
    @Id
    private Long id;
    private String account;
    private int week;//星期，0，1，2，3，4，5，6
    private int time;//上午，下午，晚上，0，1，2
    private int level;//第一节课，0，1，2，3
    private String className;//课程
    private String tag;//标记  对应课表坐标 (0_1) 开始
    private long changTime;

    @Generated(hash = 1243900389)
    public ClassDB(Long id, String account, int week, int time, int level, String className, String tag,
            long changTime) {
        this.id = id;
        this.account = account;
        this.week = week;
        this.time = time;
        this.level = level;
        this.className = className;
        this.tag = tag;
        this.changTime = changTime;
    }

    @Generated(hash = 370749809)
    public ClassDB() {
    }

    public ClassDB( String className, String tag, long changTime) {
         this.className = className;
        this.tag = tag;
        this.changTime = changTime;
    }

    public ClassDB(long week, long time, long level, String className, long changTime) {
        this.account = ShareTool.getInstance().getAccount();
        this.week = (int) week;
        this.time = (int) time;
        this.level = (int) level;
        this.className = className;
        this.changTime = changTime;
    }

    public ClassDB(String account, long week, long time, long level, String className, long changTime) {
        this.account = account;
        this.week = (int) week;
        this.time = (int) time;
        this.level = (int) level;
        this.className = className;
        this.changTime = changTime;
    }

    public String getTag() {
        return tag;
    }

    public ClassDB setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getChangTime() {
        return this.changTime;
    }

    public void setChangTime(long changTime) {
        this.changTime = changTime;
    }

    public String getTimeString() {
        switch (time) {
            case 0:
                return "上午";
            case 1:
                return "下午";
            case 2:
                return "晚上";

        }
        return "";
    }

    public String getLevelString() {
        switch (level) {
            case 0:
                return "第一节";
            case 1:
                return "第二节";
            case 2:
                return "第三节";
            case 3:
                return "第四节";

        }
        return "";
    }

    public String getWeekString() {
        switch (week) {
            case 0:
                return "周一";
            case 1:
                return "周二";
            case 2:
                return "周三";
            case 3:
                return "周四";
            case 4:
                return "周五";
            case 5:
                return "周六";
            case 6:
                return "周日";
        }
        return "";
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
