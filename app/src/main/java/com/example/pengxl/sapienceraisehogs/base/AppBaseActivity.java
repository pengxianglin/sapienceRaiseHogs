package com.example.pengxl.sapienceraisehogs.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.example.pengxl.sapienceraisehogs.R;
import com.example.pengxl.sapienceraisehogs.base.cache.CacheEntity;
import com.rarvinw.app.basic.androidboot.assign.NewBaseActivity;
import com.rarvinw.app.basic.androidboot.mvp.IPresenter;
import com.rarvinw.app.basic.androidboot.utils.AlertMsg;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by newhope on 2016/4/6.
 */
public abstract class AppBaseActivity<T extends IPresenter> extends NewBaseActivity<T> {

    ProgressDialog dialog = null;
    AlertDialog alertDialog = null;
    boolean isUpdate = false;
    private volatile AtomicInteger countBgRunnable = new AtomicInteger(0);
    @Nullable
    @Bind(R.id.common_error_layout)
    ViewGroup vgError;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected boolean isNeedToLogin() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity+fragment方式，在加载页面的时候是先加载activity，在加载fragment，中间有间隔，感觉像是黑屏一下，这里起到一个缓冲作用
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public Context getContext() {
        return this;
    }

    public String getPreferenceString(String str) {
        SharedPreferences preferences = getSharedPreferences("user",
                Context.MODE_PRIVATE);
        String ret = preferences.getString(str, "");
        return ret;
    }

    public void removePreferenceData(String s) {
        SharedPreferences mShared = mShared = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mShared.edit();
        editor.remove(s);
        editor.commit();
    }

    public void addPreferenceData(String key, String value) {
        SharedPreferences preferences = getSharedPreferences("user",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public void setCacheData(List<CacheEntity> cacheList) {

    }

    @Override
    public void showLoadingView(boolean isShow, String promptMsg) {
        super.showLoadingView(isShow, promptMsg);
        if (promptMsg == null) {
            promptMsg = "正在操作...";
        }
        if (isShow) {
            if (countBgRunnable.incrementAndGet() == 1) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                dialog = new ProgressDialog(this);
                dialog.setMessage(promptMsg);
                dialog.setTitle("请稍候");
                dialog.show();
            } else {
                dialog.setMessage(promptMsg);
            }
        } else {
            int count = countBgRunnable.decrementAndGet();
            if (count == 0) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
            } else if (count < 0) {
                countBgRunnable.set(0);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        }
    }

    public void showErrorView(boolean isShow) {
        if (vgError != null) {
            if (isShow) {
                vgError.setVisibility(View.VISIBLE);
            } else {
                vgError.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showAlertMsg(AlertMsg msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(msg.getTitle());
        builder.setMessage(msg.getContent());
        if (msg.getOk() == null) {
            msg.setOk("确定");
        }
        if (msg.getCancel() == null) {
            msg.setCancel("取消");
        }
        builder.setPositiveButton(msg.getOk(), msg.getOnPositiveClick());
        builder.setNegativeButton(msg.getCancel(), msg.getOnNegativeClick());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public void closed() {
        if (isUpdate()) {
            this.setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        closed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
