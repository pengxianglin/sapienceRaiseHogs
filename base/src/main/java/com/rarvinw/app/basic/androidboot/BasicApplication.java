package com.rarvinw.app.basic.androidboot;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by newhope on 2016/4/3.
 */
public abstract class BasicApplication extends Application {

    private static BasicApplication instance = null;

    private BasicConfig config = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
        initBasic();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
//        Toast.makeText(this, "手机内存不足，需要清理内存...", Toast.LENGTH_SHORT).show();
    }

    protected void initBasic() {
        config = new BasicConfig();
    }


    public BasicConfig getConfig() {
        return config;
    }

    public void setConfig(BasicConfig config) {
        this.config = config;
    }

    public static BasicApplication getInstance() {
        return instance;
    }

}
