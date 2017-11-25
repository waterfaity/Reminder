package com.waterfairy.reminder.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :事件管理
 */
@Entity
public class EventDB {
    @Id
    private Long id;
    private String time;//事件时间
    private String title;//时间标题
    private String content;//事件内容
    @Generated(hash = 166783285)
    public EventDB(Long id, String time, String title, String content) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.content = content;
    }
    @Generated(hash = 1893651901)
    public EventDB() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }



}
