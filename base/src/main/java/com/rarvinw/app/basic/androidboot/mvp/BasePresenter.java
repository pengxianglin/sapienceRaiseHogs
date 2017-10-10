package com.rarvinw.app.basic.androidboot.mvp;

import android.content.Intent;

/**
 * Created by newhope on 2016/4/4.
 */
public abstract class BasePresenter<V extends IView> extends NullablePresenter<V> {


    private IDisplay mDisplay;
    private boolean mInited;

    public final void init() {
        if(mInited == true) throw new RuntimeException("already inited");
        mInited = true;
        onInited();
    }

    public final void suspend() {
        if(mInited == false) throw new RuntimeException("not inited");
        onSuspended();
        mInited = false;
    }

    public final boolean isInited() {
        return mInited;
    }

    protected void onInited() {
        if(view != null) {
            V v = view.get();
            if (v != null) {
                onUiAttached(v);
                populateUi(v);
            }
        }
    }

    protected void onSuspended() {

    }

    public boolean handleIntent(Intent intent) {
        return false;
    }

    protected void setDisplay(IDisplay display) {
        mDisplay = display;
    }

    protected final IDisplay getDisplay() {
        return mDisplay;
    }


    @Override
    public void attachView(V view) {
        super.attachView(view);
        if(isInited()) {
            onUiAttached(view);
            populateUi(view);
        }
    }

    /**
     * 绑定UI时Presenter的操作，一般操作Interactor
     * @param view
     */
    protected void onUiAttached(V view) {

    }


    protected void onUiDetached() {


    }

    /**
     * 绑定UI时UI的操作，一般操作UI
     * @param view
     */

    protected void populateUi(V view) {

    }

    @Override
    public void detachView(boolean retainInstance) {
        onUiDetached();
        super.detachView(retainInstance);
    }
}
