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
public class MemorandumDB {
    @Id
    private Long id;
    private String account;
    private String content;
    private long time;
    private long changeTime;

    @Generated(hash = 982489309)
    public MemorandumDB(Long id, String account, String content, long time, long changeTime) {
        this.id = id;
        this.account = account;
        this.content = content;
        this.time = time;
        this.changeTime = changeTime;
    }

    @Generated(hash = 513710216)
    public MemorandumDB() {
    }

    public MemorandumDB(String content, long time, long changeTime) {
        this.account = ShareTool.getInstance().getAccount();
        this.content = content;
        this.time = time;
        this.changeTime = changeTime;
    }

    public MemorandumDB(String account, String content, long time, long changeTime) {
        this.account = account;
        this.content = content;
        this.time = time;
        this.changeTime = changeTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
