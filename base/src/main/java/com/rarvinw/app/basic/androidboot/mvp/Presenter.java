package com.rarvinw.app.basic.androidboot.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by newhope on 2016/4/4.
 */
public abstract class Presenter<V extends IView> implements IPresenter<V> {
    private WeakReference<V> viewRef;


    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
