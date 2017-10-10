package com.rarvinw.app.basic.androidboot.mvp;

/**
 * Created by newhope on 2016/4/4.
 */
public interface  IPresenter<V extends IView> {

    void init();
    void suspend();

    void attachView(V view);

    void detachView(boolean retainInstance);
    void loadCacheData(Integer type,String prompt);
}
