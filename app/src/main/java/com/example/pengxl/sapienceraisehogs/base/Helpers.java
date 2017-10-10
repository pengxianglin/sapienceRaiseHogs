package com.example.pengxl.sapienceraisehogs.base;

import com.rarvinw.app.basic.androidboot.utils.IApiHelper;
import com.rarvinw.app.basic.androidboot.utils.IAsyncHelper;
import com.rarvinw.app.basic.androidboot.utils.IDBHelper;
import com.rarvinw.app.basic.androidboot.utils.IImageHelper;
import com.rarvinw.app.basic.androidboot.utils.IJsonHelper;
import com.rarvinw.app.basic.androidboot.utils.INetworkHelper;

/**
 * Created by newhope on 2016/4/8.
 */
public class Helpers {

    //static IDBHelper.IFactory dbFactory = new IDBHelper.Factory();
    static IDBHelper.IFactory dbFactory = new NewDBHelper.Factory();
    static IApiHelper.IFactory apiFactory = new IApiHelper.Factory();
    static IAsyncHelper.IFactory asyncFactory = new IAsyncHelper.Factory();
    static IImageHelper.IFactory imageFactory = new IImageHelper.Factory();
    static IJsonHelper.IFactory jsonFactory = new IJsonHelper.Factory();
    static INetworkHelper.IFactory networkFactory = new INetworkHelper.Factory();


    public static IDBHelper dBHelper(){
        return dbFactory.build();
    }
    public static IApiHelper apiHelper(){
        return apiFactory.build();
    }
    public static IAsyncHelper asyncHelper(){
        return asyncFactory.build();
    }
    public static IImageHelper imageHelper(){
        return imageFactory.build();
    }
    public static IJsonHelper jsonHelper(){
        return jsonFactory.build();
    }
    public static INetworkHelper networkHelper(){
        return networkFactory.build();
    }
}
