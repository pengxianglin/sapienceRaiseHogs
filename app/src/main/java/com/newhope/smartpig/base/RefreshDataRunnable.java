package com.newhope.smartpig.base;

import com.rarvinw.app.basic.androidboot.task.NetworkCallRunnable;

/**
 * Created by newhope on 2016/4/20.
 *
 * 从网络取数据，并根据isSave是否保存到数据库，默认不保存
 */
public abstract class RefreshDataRunnable<P, T> extends NetworkCallRunnable<T> {

    AppBasePresenter presenter = null;
    DataLoader<P, T, ? extends Object> loader;
    P param;
    private boolean isSave;
    boolean isSaveMemory;
    private boolean isStartTask = true;
    private boolean isLastTask = true;
    public RefreshDataRunnable(AppBasePresenter presenter, DataLoader<P,T, ? extends Object> loader, P param) {
        this(presenter,loader, param, false,false);
    }
    public RefreshDataRunnable(AppBasePresenter presenter, DataLoader<P,T, ? extends Object> loader, P param, boolean isSave) {
        this(presenter,loader, param, isSave,false);
    }
    public RefreshDataRunnable(AppBasePresenter presenter,
                               DataLoader<P,T, ? extends Object> loader, P param,
                               boolean isSave, boolean isSaveMemory) {
        this.presenter = presenter;
        this.loader = loader;
        this.isSave = isSave;
        this.param = param;
        this.isSaveMemory = isSaveMemory;
    }


    @Override
    public T doBackgroundCall() throws Throwable {
        T data;

        data = loader.loadDataFromNetwork(param);
        if(data != null && isSave){
            loader.saveDataToDB(data);
        }
        if(data != null && isSaveMemory){
            loader.saveDataToMemory(data);
        }

        return data;
    }

    @Override
    public void onError(Throwable re) {

        presenter.handException(loader.getClass().hashCode(),re);
        if(isLastTask) {
            showAsyncRunnableState(false);
        }
    }

    @Override
    public void onPreCall() {
        super.onPreCall();
        if(isStartTask()) {
            showAsyncRunnableState(true);
        }
    }

    public boolean isStartTask() {
        return isStartTask;
    }

    public void setStartTask(boolean startTask) {
        isStartTask = startTask;
    }

    public boolean isLastTask() {
        return isLastTask;
    }

    public void setLastTask(boolean lastTask) {
        isLastTask = lastTask;
    }

    @Override
    public void onSuccess(T result) {
        if(isLastTask()) {
            showAsyncRunnableState(false);
        }
    }
    protected void showAsyncRunnableState(boolean startOrEnd){
        presenter.setAsyncRunnableState(startOrEnd,getPromptMsg());
    }

}
