package com.newhope.smartpig.utils;


import com.newhope.smartpig.MyApplication;

/**
 * Created by Administrator on 2016-4-15.
 */
public interface IAppState {

    class Factory {
        public static IAppState build() {
            return ((MyApplication) MyApplication.getInstance()).getAppState();
        }
    }
}
