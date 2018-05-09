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
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.database.UserDB;
import com.waterfairy.reminder.database.MemorandumCategoryDB;

import com.waterfairy.reminder.database.greendao.ClassDBDao;
import com.waterfairy.reminder.database.greendao.ClockDBDao;
import com.waterfairy.reminder.database.greendao.EveryDayDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumDBDao;
import com.waterfairy.reminder.database.greendao.UserDBDao;
import com.waterfairy.reminder.database.greendao.MemorandumCategoryDBDao;

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
    private final DaoConfig memorandumDBDaoConfig;
    private final DaoConfig userDBDaoConfig;
    private final DaoConfig memorandumCategoryDBDaoConfig;

    private final ClassDBDao classDBDao;
    private final ClockDBDao clockDBDao;
    private final EveryDayDBDao everyDayDBDao;
    private final MemorandumDBDao memorandumDBDao;
    private final UserDBDao userDBDao;
    private final MemorandumCategoryDBDao memorandumCategoryDBDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        classDBDaoConfig = daoConfigMap.get(ClassDBDao.class).clone();
        classDBDaoConfig.initIdentityScope(type);

        clockDBDaoConfig = daoConfigMap.get(ClockDBDao.class).clone();
        clockDBDaoConfig.initIdentityScope(type);

        everyDayDBDaoConfig = daoConfigMap.get(EveryDayDBDao.class).clone();
        everyDayDBDaoConfig.initIdentityScope(type);

        memorandumDBDaoConfig = daoConfigMap.get(MemorandumDBDao.class).clone();
        memorandumDBDaoConfig.initIdentityScope(type);

        userDBDaoConfig = daoConfigMap.get(UserDBDao.class).clone();
        userDBDaoConfig.initIdentityScope(type);

        memorandumCategoryDBDaoConfig = daoConfigMap.get(MemorandumCategoryDBDao.class).clone();
        memorandumCategoryDBDaoConfig.initIdentityScope(type);

        classDBDao = new ClassDBDao(classDBDaoConfig, this);
        clockDBDao = new ClockDBDao(clockDBDaoConfig, this);
        everyDayDBDao = new EveryDayDBDao(everyDayDBDaoConfig, this);
        memorandumDBDao = new MemorandumDBDao(memorandumDBDaoConfig, this);
        userDBDao = new UserDBDao(userDBDaoConfig, this);
        memorandumCategoryDBDao = new MemorandumCategoryDBDao(memorandumCategoryDBDaoConfig, this);

        registerDao(ClassDB.class, classDBDao);
        registerDao(ClockDB.class, clockDBDao);
        registerDao(EveryDayDB.class, everyDayDBDao);
        registerDao(MemorandumDB.class, memorandumDBDao);
        registerDao(UserDB.class, userDBDao);
        registerDao(MemorandumCategoryDB.class, memorandumCategoryDBDao);
    }
    
    public void clear() {
        classDBDaoConfig.clearIdentityScope();
        clockDBDaoConfig.clearIdentityScope();
        everyDayDBDaoConfig.clearIdentityScope();
        memorandumDBDaoConfig.clearIdentityScope();
        userDBDaoConfig.clearIdentityScope();
        memorandumCategoryDBDaoConfig.clearIdentityScope();
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

    public MemorandumDBDao getMemorandumDBDao() {
        return memorandumDBDao;
    }

    public UserDBDao getUserDBDao() {
        return userDBDao;
    }

    public MemorandumCategoryDBDao getMemorandumCategoryDBDao() {
        return memorandumCategoryDBDao;
    }

}
