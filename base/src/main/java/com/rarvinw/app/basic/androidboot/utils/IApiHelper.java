package com.rarvinw.app.basic.androidboot.utils;

import android.util.Log;

import com.rarvinw.app.basic.androidboot.BasicApplication;
import com.rarvinw.app.basic.androidboot.BasicConfig;

import okhttp3.Interceptor;

/**
 * Created by newhope on 2016/4/7.
 */
public interface IApiHelper {
    <T> T createApiService(Class<T> type);
    void initApiHelper(String url, Interceptor customInterceptor,  Interceptor networkInterceptor);

    interface IFactory{
        IApiHelper build();
    }
    class Factory implements IFactory{
        static IApiHelper apiHelper = new RetrofitApiHelper();
        static{
            BasicConfig config = BasicApplication.getInstance().getConfig();
            apiHelper.initApiHelper(config.configApiUrl(), config.configCustomInterceptor()
            ,config.configNetworkInterceptor());
        }

        public IApiHelper build(){
            return apiHelper;
        }
    }
}
