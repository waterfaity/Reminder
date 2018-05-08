package com.waterfairy.reminder.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/5/8
 * des  :
 */
@Entity
public class MemorandumCategoryDB {
    @Id
    private Long id;
    private long createTime;
    private long updateTime;
    private String name;

    @Generated(hash = 1813990018)
    public MemorandumCategoryDB(Long id, long createTime, long updateTime,
            String name) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.name = name;
    }

    public MemorandumCategoryDB(long createTime, long updateTime, String name) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.name = name;
    }

    @Generated(hash = 636261799)
    public MemorandumCategoryDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
