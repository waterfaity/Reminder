package com.waterfairy.reminder.database.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.waterfairy.reminder.database.EveryDayDB;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EVERY_DAY_DB".
*/
public class EveryDayDBDao extends AbstractDao<EveryDayDB, Long> {

    public static final String TABLENAME = "EVERY_DAY_DB";

    /**
     * Properties of entity EveryDayDB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property Time = new Property(3, long.class, "time", false, "TIME");
        public final static Property ChangeTime = new Property(4, long.class, "changeTime", false, "CHANGE_TIME");
    }


    public EveryDayDBDao(DaoConfig config) {
        super(config);
    }
    
    public EveryDayDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EVERY_DAY_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ACCOUNT\" TEXT," + // 1: account
                "\"CONTENT\" TEXT," + // 2: content
                "\"TIME\" INTEGER NOT NULL ," + // 3: time
                "\"CHANGE_TIME\" INTEGER NOT NULL );"); // 4: changeTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EVERY_DAY_DB\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EveryDayDB entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(2, account);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getTime());
        stmt.bindLong(5, entity.getChangeTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EveryDayDB entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(2, account);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getTime());
        stmt.bindLong(5, entity.getChangeTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public EveryDayDB readEntity(Cursor cursor, int offset) {
        EveryDayDB entity = new EveryDayDB( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // account
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.getLong(offset + 3), // time
            cursor.getLong(offset + 4) // changeTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EveryDayDB entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAccount(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.getLong(offset + 3));
        entity.setChangeTime(cursor.getLong(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(EveryDayDB entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(EveryDayDB entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EveryDayDB entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
