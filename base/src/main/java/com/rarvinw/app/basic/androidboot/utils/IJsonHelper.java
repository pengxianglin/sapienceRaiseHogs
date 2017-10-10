package com.rarvinw.app.basic.androidboot.utils;

/**
 * Created by newhope on 2016/4/5.
 */
public interface IJsonHelper {
    <T> String toJson(T t);
    <T> T fromJson(String json, Class<T> type);

    interface IFactory{
        IJsonHelper build();
    }
    class Factory implements IFactory{
        static IJsonHelper helper = new GsonHelper();
        public IJsonHelper build(){
            return helper;
        }
    }
}
