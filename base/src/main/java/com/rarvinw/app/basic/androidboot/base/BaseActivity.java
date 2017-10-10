package com.rarvinw.app.basic.androidboot.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rarvinw.app.basic.androidboot.mvp.IView;
import com.rarvinw.app.basic.androidboot.utils.AlertMsg;

/**
 * Created by newhope on 2016/4/3.
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoadingView(boolean isShow, String loadingMsg) {

    }

    @Override
    public final void showLoadingView(boolean isShow) {
        showLoadingView(isShow, null);
    }

    @Override
    public void showErrorMsg(String msg) {
        //Toast.makeText(this, "ErrorMsg:"+msg, Toast.LENGTH_SHORT).show();
        showErrorMsg(0, msg);
    }

    @Override
    public void showErrorMsg(int code, String msg) {
        String[] strs = msg.split(":");
        if (strs.length >= 2) {
            if ("SYS_NO_DATA".equals(strs[1])) {
                return;
            }
        }
        if (strs.length >= 3) {
            Toast.makeText(this, strs[2], Toast.LENGTH_SHORT).show();
        } else if(strs.length >= 2){
            Toast.makeText(this, strs[1], Toast.LENGTH_SHORT).show();
        }else if(strs.length >= 1){
            Toast.makeText(this, strs[0], Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "错误:"+code, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showAlertMsg(AlertMsg msg) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}