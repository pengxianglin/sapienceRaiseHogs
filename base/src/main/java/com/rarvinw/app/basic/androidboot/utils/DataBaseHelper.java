package com.rarvinw.app.basic.androidboot.utils;

import android.content.Context;

import com.rarvinw.app.basic.androidboot.BasicApplication;

import java.util.List;

/**
 * Created by newhope on 2016/4/7.
 */
public class DataBaseHelper extends SQLiteDBHelper implements IDBHelper {

    public DataBaseHelper(Context context){
        super(context, "databases.db");
    }

    public DataBaseHelper(Context context, String name){
        super(context, name);
    }

    @Override
    public Class[] getAllEntitiesClass() {
        return BasicApplication.getInstance().getConfig().configAllEntitiesClass();
    }
}
