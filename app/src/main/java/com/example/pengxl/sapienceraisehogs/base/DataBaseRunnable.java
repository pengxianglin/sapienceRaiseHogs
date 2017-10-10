package com.example.pengxl.sapienceraisehogs.base;

import com.rarvinw.app.basic.androidboot.task.BackgroundCallRunnable;

/**
 * Created by Administrator on 2016-4-26.
 *
 * 仅从数据库加载，加载完成后根据 isSaveMemory 保存内存
 */
public class DataBaseRunnable<P,T> extends BackgroundCallRunnable<T> {

    AppBasePresenter presenter = null;
    DataLoader<P,T,? extends Object> loader;

    private boolean isSaveMemory;

    P param;

    public DataBaseRunnable(AppBasePresenter presenter, DataLoader<P,T,? extends Object> loader, P param) {
        this(presenter,loader,param,false);
    }

    public DataBaseRunnable(AppBasePresenter presenter,
                            DataLoader<P,T,? extends Object> loader,
                            P param,boolean isSaveMemory) {
        this.presenter = presenter;
        this.loader = loader;
        this.param = param;
        this.isSaveMemory = isSaveMemory;
    }

    @Override
    public T doBackground() {
        T data;

        data = loader.loadDataFromDB(param);

        if(isSaveMemory){
            loader.saveDataToMemory(data);
        }

        return data;
    }

    @Override
    public void preExecute() {
        super.preExecute();
        showAsyncRunnableState(true);
    }

    protected void showAsyncRunnableState(boolean startOrEnd){
        presenter.setAsyncRunnableState(startOrEnd,getPromptMsg());
    }

    @Override
    public void postExecute(T result) {
        super.postExecute(result);
        showAsyncRunnableState(false);
    }
}
