package com.rarvinw.app.basic.androidboot.utils;

import com.rarvinw.app.basic.androidboot.BasicApplication;

import java.util.List;

/**
 * Created by newhope on 2016/4/3.
 */
public interface IDBHelper {
    <T extends DBData> long putEntity(T entity);
    <T extends DBData> void putEntities(List<T> entities);
    <T extends DBData> int deleteEntity(Class<T> type, String query, String... args);
    <T extends DBData> int clearEntities(Class<T> type);
    <T extends DBData> int clearEntities(Class<T> type, String query, String... args) ;
    <T extends DBData> List<T> getEntities(Class<T> type,String query, String... args);
    <T extends DBData> List<T> getEntities(Class<T> type);


    interface IFactory{
        IDBHelper build();
    }
    class Factory implements IFactory{
        private static DataBaseHelper instance = null;
        public IDBHelper build(){
            if(instance == null){
                synchronized (Factory.class){
                    if(instance == null){
                        instance = new DataBaseHelper(BasicApplication.getInstance()
                                .getConfig().configContext());
                    }
                }
            }
            return instance;
        }
    }
}