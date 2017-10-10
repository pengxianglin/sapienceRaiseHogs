/*
package com.rarvinw.app.basic.androidboot.inject;

import android.content.Context;

import com.rarvinw.app.basic.androidboot.utils.GsonHelper;
import com.rarvinw.app.basic.androidboot.utils.IAsyncHelper;
import com.rarvinw.app.basic.androidboot.utils.IImageHelper;
import com.rarvinw.app.basic.androidboot.utils.IJsonHelper;
import com.rarvinw.app.basic.androidboot.utils.INetworkHelper;
import com.rarvinw.app.basic.androidboot.utils.OKHttpNetworkHelper;
import com.rarvinw.app.basic.androidboot.utils.PicassoImageHelper;
import com.rarvinw.app.basic.androidboot.utils.RxAsyncHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

*/
/**
 * Created by newhope on 2016/4/3.
 *//*

@Module
public class BasicModule {
    @Provides
    public static Context provideApplicationContext(){
        return DaggerBasicApplication.getInstance();
    }
    @Singleton
    @Provides
    public static IAsyncHelper provideIAsyncExcutor(){
        return new RxAsyncHelper();
    }

    */
/*@Singleton
    @Provides
    public static IDBHelper provideIDBHelper(){
        return new SQLiteDBHelper(provideApplicationContext()) {
            @Override
            public Class[] getAllEntitiesClass() {
                Class[] clazzes = {User.class};
                return clazzes;
            }
        };
    }*//*


    @Singleton
    @Provides
    public static IImageHelper provideIImageHelper(){
        return new PicassoImageHelper(provideApplicationContext());
    }

    @Singleton
    @Provides
    public static INetworkHelper provideINetworkHelper(){
        return new OKHttpNetworkHelper();
    }

    @Singleton
    @Provides
    public static IJsonHelper provideIJsonHelper(){
        return new GsonHelper();
    }
}
*/
