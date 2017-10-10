package com.rarvinw.app.basic.androidboot.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by newhope on 2016/4/5.
 */
public class OKHttpNetworkHelper implements INetworkHelper {
    private static long READ_TIMEOUT = 1000 * 30;
    public static OkHttpClient client =
            new OkHttpClient.Builder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                    .build();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }
}
