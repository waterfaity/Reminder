package com.waterfairy.reminder.manger;

/**
 * @author water_fairy
 * @email 995637517@qq.com
 * @date 2017/11/28
 * @Description: 用于 刚启动时 数据的初始化
 */

public class DataInitManger {
    private static final String TAG = "DataInitManger";

    private static DataInitManger dataInitManger;

    private DataInitManger() {
    }

    public static DataInitManger getInstance() {
        if (dataInitManger == null) {
            dataInitManger = new DataInitManger();
        }
        return dataInitManger;
    }

    public void init() {
        //数据库
        DataBaseManger.getInstance().initDataBase();
        //闹钟
        ClockManger.getInstance().init().initClock();
        //  audio  - 复制音频到内存卡
        AudioManger.getInstance().init();
    }
}
