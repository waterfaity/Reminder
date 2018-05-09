package com.waterfairy.reminder.database.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.waterfairy.reminder.database.ClassDB;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.database.EveryDayDB;
import com.waterfairy.reminder.database.MemorandumCategoryDB;
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.database.UserDB;

import com.waterfairy.reminder.database.greendao.ClassDBDao;
import com.waterfairy.reminder.database.greendao.ClockDBDao;
import com.waterfairy.reminder.database.greendao.EveryDayDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumCategoryDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.database.greendao.UserDBDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig classDBDaoConfig;
    private final DaoConfig clockDBDaoConfig;
    private final DaoConfig everyDayDBDaoConfig;
    private final DaoConfig memorandumCategoryDBDaoConfig;
    private final DaoConfig memorandumDBDaoConfig;
    private final DaoConfig userDBDaoConfig;

    private final ClassDBDao classDBDao;
    private final ClockDBDao clockDBDao;
    private final EveryDayDBDao everyDayDBDao;
    private final MemorandumCategoryDBDao memorandumCategoryDBDao;
    private final MemorandumDBDao memorandumDBDao;
    private final UserDBDao userDBDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        classDBDaoConfig = daoConfigMap.get(ClassDBDao.class).clone();
        classDBDaoConfig.initIdentityScope(type);

        clockDBDaoConfig = daoConfigMap.get(ClockDBDao.class).clone();
        clockDBDaoConfig.initIdentityScope(type);

        everyDayDBDaoConfig = daoConfigMap.get(EveryDayDBDao.class).clone();
        everyDayDBDaoConfig.initIdentityScope(type);

        memorandumCategoryDBDaoConfig = daoConfigMap.get(MemorandumCategoryDBDao.class).clone();
        memorandumCategoryDBDaoConfig.initIdentityScope(type);

        memorandumDBDaoConfig = daoConfigMap.get(MemorandumDBDao.class).clone();
        memorandumDBDaoConfig.initIdentityScope(type);

        userDBDaoConfig = daoConfigMap.get(UserDBDao.class).clone();
        userDBDaoConfig.initIdentityScope(type);

        classDBDao = new ClassDBDao(classDBDaoConfig, this);
        clockDBDao = new ClockDBDao(clockDBDaoConfig, this);
        everyDayDBDao = new EveryDayDBDao(everyDayDBDaoConfig, this);
        memorandumCategoryDBDao = new MemorandumCategoryDBDao(memorandumCategoryDBDaoConfig, this);
        memorandumDBDao = new MemorandumDBDao(memorandumDBDaoConfig, this);
        userDBDao = new UserDBDao(userDBDaoConfig, this);

        registerDao(ClassDB.class, classDBDao);
        registerDao(ClockDB.class, clockDBDao);
        registerDao(EveryDayDB.class, everyDayDBDao);
        registerDao(MemorandumCategoryDB.class, memorandumCategoryDBDao);
        registerDao(MemorandumDB.class, memorandumDBDao);
        registerDao(UserDB.class, userDBDao);
    }
    
    public void clear() {
        classDBDaoConfig.clearIdentityScope();
        clockDBDaoConfig.clearIdentityScope();
        everyDayDBDaoConfig.clearIdentityScope();
        memorandumCategoryDBDaoConfig.clearIdentityScope();
        memorandumDBDaoConfig.clearIdentityScope();
        userDBDaoConfig.clearIdentityScope();
    }

    public ClassDBDao getClassDBDao() {
        return classDBDao;
    }

    public ClockDBDao getClockDBDao() {
        return clockDBDao;
    }

    public EveryDayDBDao getEveryDayDBDao() {
        return everyDayDBDao;
    }

    public MemorandumCategoryDBDao getMemorandumCategoryDBDao() {
        return memorandumCategoryDBDao;
    }

    public MemorandumDBDao getMemorandumDBDao() {
        return memorandumDBDao;
    }

    public UserDBDao getUserDBDao() {
        return userDBDao;
    }

}
