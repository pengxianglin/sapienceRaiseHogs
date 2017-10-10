package com.rarvinw.app.basic.androidboot.mvp;

import android.content.Context;

import com.rarvinw.app.basic.androidboot.utils.AlertMsg;

/**
 * Created by newhope on 2016/4/4.
 */
public interface IView {
    void showLoadingView(boolean isShow);
    void showLoadingView(boolean isShow, String loadingMsg);
    void showErrorMsg(String msg);
    void showErrorMsg(int code, String msg);
    void showMsg(String msg);
    void showAlertMsg(AlertMsg msg);
    String getPreferenceString(String str) ;
    void addPreferenceData(String key, String value) ;
    Context getContext();
}
