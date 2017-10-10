package com.rarvinw.app.basic.androidboot.utils;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by newhope on 2016/4/3.
 */
public interface INetworkHelper {
    String get(String url) throws IOException;
    String post(String url, String data) throws IOException ;

    Response execute(Request request)throws IOException;

    interface IFactory{
        INetworkHelper build();
    }
    class Factory implements IFactory{
        static INetworkHelper helper = new OKHttpNetworkHelper();
        public INetworkHelper build(){
            return helper;
        }
    }
}
