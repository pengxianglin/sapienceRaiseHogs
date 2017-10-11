package com.newhope.smartpig.base;

import android.content.Context;
import android.util.Log;

import com.newhope.smartpig.utils.Constants;
import com.rarvinw.app.basic.androidboot.BasicApplication;
import com.rarvinw.app.basic.androidboot.utils.DBData;
import com.rarvinw.app.basic.androidboot.utils.DataBaseHelper;
import com.rarvinw.app.basic.androidboot.utils.IDBHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by newhope on 2016/4/28.
 */
public class NewDBHelper implements IDBHelper {
    private static final String TAG = "NewDBHelper";
    private Context context;
    private Map<Class, String> names = new HashMap<Class, String>();
    private Map<String, DataBaseHelper> dbs = new HashMap<String, DataBaseHelper>();

    public NewDBHelper(Context context) {
        this.context = context;
    }

    private DBConfig config;

    public interface DBConfig {
        public String getDBName(Class type);
    }

    public DBConfig getConfig() {
        return config;
    }

    public void setConfig(DBConfig config) {
        this.config = config;
    }

    static class Factory implements IFactory {
        private static NewDBHelper instance = null;

        public IDBHelper build() {
            if (instance == null) {
                synchronized (Factory.class) {
                    if (instance == null) {
                        instance = new NewDBHelper(BasicApplication.getInstance()
                                .getConfig().configContext());
                        instance.setConfig(new DBConfig() {
                            @Override
                            public String getDBName(Class type) {
                                String name = type.getSimpleName();
                                if (name.equals("AreaData")
                                        || name.equals("DataDefineSourceData")
                                        || name.equals("DataDefineData")
                                        || name.equals("LoginInfo")
                                        || name.equals("OrganizeModel")
                                        || name.equals("RolesModel")
                                        || name.equals("OrgRolesModel")) {
                                    return Constants.DB_NAME_BASIC;
                                } else {
                                    return null;
//                                    return IAppState.Factory.build().getLoginInfo().getAccount() + ".db";
                                }
                            }
                        });
                    }
                }
            }
            return instance;
        }
    }

    public void resetNewDBHelper() {
        this.names.clear();
        this.dbs.clear();
    }

    private DataBaseHelper getDatabase(Class type) {
        String dbName = names.get(type);
        if (dbName == null || dbName.equals("")) {
            if (config != null) {
                dbName = config.getDBName(type);
                if (dbName == null || dbName.equals("")) {
                    dbName = Constants.DB_NAME_BASIC;
                }
            }
            names.put(type, dbName);
        }
        DataBaseHelper db = dbs.get(dbName);
        if (db == null) {
            db = new DataBaseHelper(context, dbName);
            dbs.put(dbName, db);
        }
        Log.d(TAG, "type:" + type.getName() + " db:" + db + " cupboard:" + db.cupboard);
        return db;
    }

    @Override
    public <T extends DBData> long putEntity(T entity) {
        if (entity.getDBid() == null) {
            entity.setDBid(entity.getUniqualId().hashCode());
        }
        return getDatabase(entity.getClass()).putEntity(entity);
    }

    @Override
    public <T extends DBData> void putEntities(List<T> entities) {
        if (entities != null && entities.size() != 0) {
            for (T entity : entities) {
                if (entity.getDBid() == null) {
                    entity.setDBid(entity.getUniqualId().hashCode());
                }
            }
            getDatabase(entities.get(0).getClass()).putEntities(entities);
        }
    }

    @Override
    public <T extends DBData> int clearEntities(Class<T> type) {
        return getDatabase(type).clearEntities(type);
    }

    @Override
    public <T extends DBData> int clearEntities(Class<T> type, String query, String... args) {
        return getDatabase(type).clearEntities(type, query, args);
    }

    @Override
    public <T extends DBData> List<T> getEntities(Class<T> type) {
        return getDatabase(type).getEntities(type);
    }

    @Override
    public <T extends DBData> List<T> getEntities(Class<T> type, String query, String... args) {
        return getDatabase(type).getEntities(type, query, args);
    }

    @Override
    public <T extends DBData> int deleteEntity(Class<T> type, String query, String... args) {
        return getDatabase(type).deleteEntity(type, query, args);
    }
}
