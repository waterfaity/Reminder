package com.waterfairy.reminder.database;

import com.waterfairy.reminder.utils.ShareTool;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2018/2/23
 * des  :
 */
@Entity
public class MemorandumDB implements Serializable {
    private static final long serialVersionUID = 20180419220311L;
    @Id
    private Long id;
    private String account;
    private String content;
    private long time;
    private long changeTime;

    private Long categoryId;

    @Generated(hash = 1997177675)
    public MemorandumDB(Long id, String account, String content, long time, long changeTime,
            Long categoryId) {
        this.id = id;
        this.account = account;
        this.content = content;
        this.time = time;
        this.changeTime = changeTime;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return this.categoryId;
    }

    public MemorandumDB setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
