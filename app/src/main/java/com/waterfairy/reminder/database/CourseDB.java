package com.waterfairy.reminder.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2017/11/25
 * des  :课程
 */
@Entity
public class CourseDB {
    @Id
    private Long id;
    private long syllabusId;//课表id
    private String name;//名称
    private String week;//天  0.1.2.3.4.5.6 (周日->周六)
    private int num;//第几节课
    @Generated(hash = 1516747717)
    public CourseDB(Long id, long syllabusId, String name, String week, int num) {
        this.id = id;
        this.syllabusId = syllabusId;
        this.name = name;
        this.week = week;
        this.num = num;
    }
    @Generated(hash = 1140258734)
    public CourseDB() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getSyllabusId() {
        return this.syllabusId;
    }
    public void setSyllabusId(long syllabusId) {
        this.syllabusId = syllabusId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getWeek() {
        return this.week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }


}
