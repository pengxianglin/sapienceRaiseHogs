package com.example.pengxl.sapienceraisehogs.utils;


import com.example.pengxl.sapienceraisehogs.MyApplication;

import java.util.List;

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
