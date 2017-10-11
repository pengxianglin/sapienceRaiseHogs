package com.newhope.smartpig.base;


import com.newhope.smartpig.api.ApiResult;

/**
 * Created by newhope on 2016/4/18.
 */
public abstract class SaveDataRunnable<P,R> extends SaveNetworkRunnable<ApiResult<R>> {


    protected P data;
    DataLoader<? extends Object, P, ApiResult<R>> loader ;
    public SaveDataRunnable(AppBasePresenter presenter, DataLoader<? extends Object, P, ApiResult<R>> loader, P data){
        super(presenter);
        this.data = data;
        this.loader = loader;
    }
    @Override
    public void onError(Throwable re) {
        presenter.handException(loader.getClass().hashCode(), re);
        showAsyncRunnableState(false);
    }
    @Override
    public ApiResult<R> doBackgroundCall() throws Throwable {
        ApiResult<R> result = loader.saveDataToNetwork(data);
        return result;
    }

    //public abstract int getOperType();
    //public abstract ApiResult<R> saveDataToNetwork(P data) throws Throwable;


}
