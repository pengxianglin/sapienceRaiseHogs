package com.example.pengxl.sapienceraisehogs.base;

import android.net.Network;

import com.rarvinw.app.basic.androidboot.mvp.IPresenter;
import com.rarvinw.app.basic.androidboot.mvp.Presenter;
import com.rarvinw.app.basic.androidboot.task.NetworkCallRunnable;

/**
 * Created by newhope on 2016/5/3.
 */
public abstract class SaveNetworkRunnable<R> extends NetworkCallRunnable<R> {

    protected  AppBasePresenter presenter;

    public SaveNetworkRunnable(AppBasePresenter presenter){
        this.presenter = presenter;
    }




    @Override
    public void onPreCall() {
        super.onPreCall();
        showAsyncRunnableState(true);
    }

    @Override
    public R doBackgroundCall() throws Throwable {
        return null;
    }
    protected void showAsyncRunnableState(boolean startOrEnd){
        presenter.setAsyncRunnableState(startOrEnd,getPromptMsg());
    }
    @Override
    public void onSuccess(R result) {
        showAsyncRunnableState(false);
    }
}
