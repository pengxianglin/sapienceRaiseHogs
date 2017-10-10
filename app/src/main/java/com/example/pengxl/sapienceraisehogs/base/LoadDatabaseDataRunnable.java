package com.example.pengxl.sapienceraisehogs.base;

import com.rarvinw.app.basic.androidboot.task.NetworkCallRunnable;

/**
 * Created by newhope on 2016/4/28.
 * <p/>
 * 先从本地加载，加载失败从网络加载。最后根据isSaveMemory是否保存内存，默认保存
 */
public abstract class LoadDatabaseDataRunnable<P, T> extends NetworkCallRunnable<T> {
    AppBasePresenter presenter = null;
    DataLoader<P, T, ? extends Object> loader;
    P param;
    boolean isSaveMemory;
    Throwable temp = null;
    private boolean isStartTask = true;
    private boolean isLastTask = true;

    public LoadDatabaseDataRunnable(AppBasePresenter presenter, DataLoader<P, T, ? extends Object> loader, P param) {
        this(presenter, loader, param, true);
    }

    public LoadDatabaseDataRunnable(AppBasePresenter presenter,
                                    DataLoader<P, T, ? extends Object> loader,
                                    P param,
                                    boolean isSaveMemory) {
        this.presenter = presenter;
        this.loader = loader;
        this.param = param;
        this.isSaveMemory = isSaveMemory;
    }

    @Override
    public T doBackgroundCall() throws Throwable {
        T data = loader.loadDataFromDB(param);
        if (data == null) {
            data = loader.loadDataFromNetwork(param);
            if (data != null) {
                loader.saveDataToDB(data);
            }
        }
        if (isSaveMemory) {
            loader.saveDataToMemory(data);
        }
        return data;
    }

    private T loadDataFromLocal(P param) {
        T data = loader.loadDataFromMemory(param);
        if (data == null) {
            data = loader.loadDataFromDB(param);
        }
        return data;
    }

    private void saveDataToLocal(T data) {
        loader.saveDataToDB(data);
        loader.saveDataToMemory(data);
    }

    @Override
    public void onError(Throwable re) {

        presenter.handException(loader.getClass().hashCode(), re);
        if (isLastTask()) {
            showAsyncRunnableState(false);
        }
    }

    protected void showAsyncRunnableState(boolean startOrEnd) {
        presenter.setAsyncRunnableState(startOrEnd, getPromptMsg());
    }

    @Override
    public void onPreCall() {
        super.onPreCall();
        if (isStartTask()) {
            showAsyncRunnableState(true);
        }
    }

    @Override
    public void onSuccess(T result) {
        if (isLastTask()) {
            showAsyncRunnableState(false);
        }
        if (temp != null) {
            onError(temp);
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

}
