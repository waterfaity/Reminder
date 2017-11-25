package com.waterfairy.reminder.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :课表
 */
@Entity
public class SyllabusDB {
    @Id
    private Long id;//id 课表id
    private String account;//帐号
    private String title;//课表名称
    @Generated(hash = 2100219358)
    public SyllabusDB(Long id, String account, String title) {
        this.id = id;
        this.account = account;
        this.title = title;
    }
    @Generated(hash = 15505791)
    public SyllabusDB() {
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
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
