package com.rarvinw.app.basic.androidboot.utils;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by newhope on 2016/4/7.
 */
public class RetrofitApiHelper implements IApiHelper {

    Retrofit retrofit = null;

    @Override
    public <T> T createApiService(Class<T> type) {
        return retrofit.create(type);
    }

    private static long READ_TIMEOUT = 1000 * 30;

    @Override
    public void initApiHelper(String url, Interceptor customInterceptor, Interceptor networkInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (customInterceptor != null) {
            builder.addInterceptor(customInterceptor);
        }
        if (networkInterceptor != null) {
            builder.addNetworkInterceptor(networkInterceptor);
        }
        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.retryOnConnectionFailure(false);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .callFactory(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonHelper().getGson()))
                .baseUrl(url)
                .build();
    }
}
