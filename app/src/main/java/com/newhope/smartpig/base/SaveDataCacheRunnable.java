package com.newhope.smartpig.base;

import com.newhope.smartpig.api.ApiResult;
import com.newhope.smartpig.base.cache.CacheEntity;
import com.newhope.smartpig.base.cache.ICache;
import com.rarvinw.app.basic.androidboot.utils.IDBHelper;

import java.io.IOException;

/**
 * Created by newhope on 2016/4/18.
 */
public abstract class SaveDataCacheRunnable<P, R> extends SaveNetworkRunnable<ApiResult<R>> {


    protected P data;
    protected CacheEntity cacheEntity;
    DataLoader<? extends Object, P, ApiResult<R>> loader;

    public SaveDataCacheRunnable(AppBasePresenter presenter, DataLoader<? extends Object, P, ApiResult<R>> loader, P data) {
        super(presenter);
        this.data = data;
        this.loader = loader;
    }

    @Override
    public ApiResult<R> doBackgroundCall() throws Throwable {
        IDBHelper dbHelper = Helpers.dBHelper();
        cacheEntity = new CacheEntity();
        cacheEntity.setKey(ICache.Factory.buildKey(String.valueOf(loader.getOperType())));
        cacheEntity.setOperate(loader.getOperType());
        cacheEntity.setData(data);
        cacheEntity.setTime(System.currentTimeMillis());
        dbHelper.putEntity(cacheEntity);

        ApiResult<R> result = loader.saveDataToNetwork(data);
        return result;
    }

    //public abstract int getOperType();
    //public abstract ApiResult<R> saveDataToNetwork(P data) throws Throwable;

    @Override
    public void onSuccess(ApiResult<R> result) {
        IDBHelper dbHelper = Helpers.dBHelper();
        dbHelper.deleteEntity(CacheEntity.class, "key = ?", cacheEntity.getKey());
        showAsyncRunnableState(false);
    }

    @Override
    public void onError(Throwable re) {
        if (!(re instanceof IOException)) {
            IDBHelper dbHelper = Helpers.dBHelper();
            dbHelper.deleteEntity(CacheEntity.class, "key = ?", cacheEntity.getKey());
        }
        presenter.handException(loader.getClass().hashCode(), re);
        showAsyncRunnableState(false);
    }

    protected void showAsyncRunnableState(boolean startOrEnd) {
        presenter.setAsyncRunnableState(startOrEnd, getPromptMsg());
    }

    @Override
    public void onPreCall() {
        super.onPreCall();
        showAsyncRunnableState(true);
    }
}
