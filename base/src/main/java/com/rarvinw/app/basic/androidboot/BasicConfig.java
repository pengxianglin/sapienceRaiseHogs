package com.rarvinw.app.basic.androidboot;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import rx.Observable;

/**
 * Created by newhope on 2016/4/9.
 */
public class BasicConfig {
    public String configApiUrl() {
        //新正式环境
//        return "http://bksapi.pigkeeping.cn:80/";
        //新测试环境
//        return "http://testbksapi.pigkeeping.cn:80/";
//        压力测试环境lo  
//        return "http://172.16.10.177:8510/";
        //新uat环境
//        return "http://uatbksapi.pigkeeping.cn:80/";
//      测试环境
        return "http://172.16.10.166:8510/";
//      测试环境2]k
//        return "http://172.16.10.168:8510/";
//      开发环境,..
//      return "http://172.16.10.190:8510/";
    }

    public Interceptor configCustomInterceptor() {
        return null;
    }

    public Interceptor configNetworkInterceptor() {
        return null;
    }


    public Class[] configAllEntitiesClass() {
        return new Class[]{};
    }

    public Context configContext() {
        return BasicApplication.getInstance();
    }

    public Map<Class, String> configTableInDB() {
        return new HashMap<>();
    }
}
