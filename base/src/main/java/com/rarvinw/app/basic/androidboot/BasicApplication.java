package com.rarvinw.app.basic.androidboot;

import android.app.Application;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.umeng.analytics.MobclickAgent;

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
        //设置友盟AppKey,场景模式设置为普通统计，推广渠道default，Crash模式开启
        MobclickAgent.UMAnalyticsConfig config = new
                MobclickAgent.UMAnalyticsConfig(getApplicationContext(), "570c54c6e0f55a944700029c",
                "default", MobclickAgent.EScenarioType.E_UM_NORMAL, true);
        MobclickAgent.startWithConfigure(config);
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
