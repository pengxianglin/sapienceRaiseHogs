package com.newhope.smartpig.base;

import com.rarvinw.app.basic.androidboot.task.NetworkCallRunnable;

/**
 * Created by newhope on 2016/4/13.
 * <p/>
 * 从网络加载，成功则保存到数据库，失败后从本地获取。isSaveMemory指示是否保存内存,默认不保存
 * 如果从网络加载出错，并且本地没有数据，则抛出异常
 */
public abstract class LoadDataRunnable<P, T> extends NetworkCallRunnable<T> {

    AppBasePresenter presenter = null;
    DataLoader<P, T, ? extends Object> loader;
    P param;
    boolean isSaveMemory;
    Throwable temp = null;
    private boolean isStartTask = true;
    private boolean isLastTask = true;

    public LoadDataRunnable(AppBasePresenter presenter,
                            DataLoader<P, T, ? extends Object> loader,
                            P param) {
        this(presenter, loader, param, false);
    }

    public LoadDataRunnable(AppBasePresenter presenter,
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
        T data = null;

        try {
            data = loader.loadDataFromNetwork(param);
        } catch (Throwable e) {
            temp = e;
        } finally {
            if (data == null) {
                data = loader.loadDataFromDB(param);
            } else {
                loader.saveDataToDB(data);
            }
            if (isSaveMemory) {
                loader.saveDataToMemory(data);
            }
        }
        if(data == null && temp != null){
            throw temp;
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
}
