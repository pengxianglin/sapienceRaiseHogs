package com.newhope.smartpig.base.cache;

import com.newhope.smartpig.base.Helpers;
import com.rarvinw.app.basic.androidboot.utils.IDBHelper;

import java.util.List;

/**
 * Created by newhope on 2016/4/8.
 */
public class SQLCacheImpl implements ICache {
    IDBHelper dbHelper ;
    public SQLCacheImpl(){
        dbHelper = Helpers.dBHelper();
    }
    @Override
    public CacheEntity get(String key) {
        List<CacheEntity> list = dbHelper.getEntities(CacheEntity.class, CacheEntity.COLUME_KEY +
                " = ?", key);
        if(list != null || list.size() != 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void put(CacheEntity entity) {
        dbHelper.putEntity(entity);
    }

    @Override
    public boolean isCached(String key) {
        List<CacheEntity> list = dbHelper.getEntities(CacheEntity.class, CacheEntity.COLUME_KEY +
                " = ?", key);
        if(list != null || list.size() != 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void evictAll() {
        dbHelper.clearEntities(CacheEntity.class);
    }
}
