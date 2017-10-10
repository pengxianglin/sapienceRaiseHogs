package com.example.pengxl.sapienceraisehogs.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pengxl.sapienceraisehogs.base.cache.CacheEntity;
import com.rarvinw.app.basic.androidboot.assign.NewBaseFragment;
import com.rarvinw.app.basic.androidboot.mvp.IPresenter;
import com.rarvinw.app.basic.androidboot.utils.AlertMsg;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.ButterKnife;

/**
 * Created by newhope on 2016/4/6.
 */
public abstract class AppBaseFragment<T extends IPresenter> extends NewBaseFragment<T> {

    ProgressDialog dialog;
    AlertDialog alertDialog;
    private volatile AtomicInteger countBgRunnable = new AtomicInteger(0);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void setCacheData(List<CacheEntity> cacheList) {

    }

    @Override
    public void showLoadingView(boolean isShow) {
        super.showLoadingView(isShow);
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
                dialog = new ProgressDialog(this.getBaseContext());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    public void showAlertMsg(AlertMsg msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
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

    public Context getBaseContext() {
        return this.getContext();
    }

    //
//    public static interface OnInteractionListener{
//       void setTitle(String title);
//    }
//
//
//    public OnInteractionListener getOnInteractionListener() {
//        return onInteractionListener;
//    }
//
//    public void setOnInteractionListener(OnInteractionListener onInteractionListener) {
//        this.onInteractionListener = onInteractionListener;
//    }
//
//    OnInteractionListener onInteractionListener;
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面，"MainScreen"为页面名称
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }
}
