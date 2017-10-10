package com.rarvinw.app.basic.androidboot.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;

/**
 * Created by newhope on 2016/4/3.
 */
public abstract class SQLiteDBHelper extends SQLiteOpenHelper implements IDBHelper {

    //private static final String DATABASE_NAME = "databases.db";
    private static final int DATABASE_VERSION = 4;
    private static final int DATABASE_LAST_VERSION = 4;
    protected static volatile SQLiteDBHelper instance = null;

    public Cupboard cupboard;

    public SQLiteDBHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
        initOnce();
    }

    private void initOnce() {
        //if (instance == null) {
        //    synchronized (SQLiteDBHelper.class) {
        //        if (instance == null) {
        cupboard = new CupboardBuilder().useAnnotations().build();
        Class[] clazzes = getAllEntitiesClass();
        for (Class clazz : clazzes) {
            cupboard.register(clazz);
        }
        //            instance = this;
        //        }
        //    }
        //}
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard.withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_LAST_VERSION) {
            cupboard.withDatabase(db).dropAllTables();
            onCreate(db);
        } else {
            cupboard.withDatabase(db).upgradeTables();
        }
    }

    public abstract Class[] getAllEntitiesClass();

    @Override
    public <T extends DBData> long putEntity(T entity) {
        long result = -1;
        /*SQLiteDatabase db = getWritableDatabase();
        try {
            result = cupboard.withDatabase(db).put(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }*/

        try {
            result = cupboard.withDatabase(getWritableDatabase()).put(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public <T extends DBData> void putEntities(List<T> entities) {
        try {
            cupboard.withDatabase(getWritableDatabase()).put(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DBData> int clearEntities(Class<T> type) {
        /*SQLiteDatabase db = getWritableDatabase();
        cupboard.withDatabase(db).delete(type,
                "1 = 1");
        db.close();*/

        return cupboard.withDatabase(getWritableDatabase()).delete(type,
                "1 = 1");
    }

    @Override
    public <T extends DBData> int clearEntities(Class<T> type, String query, String... args) {
        return cupboard.withDatabase(getWritableDatabase()).delete(type,
                query, args);
    }

    @Override
    public <T extends DBData> List<T> getEntities(Class<T> type) {
        List<T> list = null;
        /*SQLiteDatabase db = getWritableDatabase();
        try {
            list = cupboard.withDatabase(db).query(type).list();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
        return list;*/

        try {
            list = cupboard.withDatabase(getWritableDatabase()).query(type).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public <T extends DBData> List<T> getEntities(Class<T> type, String query, String... args) {
        /*SQLiteDatabase db = getWritableDatabase();
        List<T> list = cupboard.withDatabase(db).query(type)
                .withSelection(query, args).list();
        db.close();*/

        List<T> list = cupboard.withDatabase(getWritableDatabase()).query(type)
                .withSelection(query, args).list();
        return list;
    }

    @Override
    public <T extends DBData> int deleteEntity(Class<T> type, String query, String... args) {
        /*SQLiteDatabase db = getWritableDatabase();
        int rows = cupboard.withDatabase(db).delete(type, query, args);
        db.close();*/

        int rows = cupboard.withDatabase(getWritableDatabase()).delete(type, query, args);
        return rows;
    }
}
