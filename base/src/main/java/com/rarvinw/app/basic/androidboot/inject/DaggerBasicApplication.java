/*
package com.rarvinw.app.basic.androidboot.inject;

import com.rarvinw.app.basic.androidboot.BasicApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

*/
/**
 * Created by newhope on 2016/4/5.
 *//*

public class DaggerBasicApplication extends BasicApplication {


    private BasicShop basicShop;
    @Override
    public void onCreate() {
        super.onCreate();
        basicShop = DaggerBasicShop.builder().build();
    }


    public  Class getDaggerComponentClass(){
        return BasicShop.class;
    }

    public  Object getDaggerComponent(){
        return basicShop;
    }


    public void inject(Class type, Object t) {
        Class<?> clazz = getDaggerComponentClass();
        try {
            Method method = clazz.getDeclaredMethod("inject", type);
            method.invoke(getDaggerComponent(), t);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
*/
