package com.newhope.smartpig.base;

import android.content.DialogInterface;

import com.newhope.smartpig.BizException;
import com.newhope.smartpig.base.cache.CacheEntity;
import com.newhope.smartpig.base.cache.ICache;
import com.rarvinw.app.basic.androidboot.mvp.BasePresenter;
import com.rarvinw.app.basic.androidboot.mvp.IView;
import com.rarvinw.app.basic.androidboot.task.BackgroundCallRunnable;
import com.rarvinw.app.basic.androidboot.task.NetworkCallRunnable;
import com.rarvinw.app.basic.androidboot.utils.AlertMsg;
import com.rarvinw.app.basic.androidboot.utils.IAsyncHelper;
import com.rarvinw.app.basic.androidboot.utils.IDBHelper;

import java.io.IOException;
import java.util.List;

/**
 * Created by newhope on 2016/4/7.
 */
public class AppBasePresenter<V extends IView> extends BasePresenter<V> {

    private static final String TAG = "AppBasePresenter";

    public boolean handException(int code, Throwable t) {
        if (t instanceof BizException) {
            //if ("SYS_NO_DATA".equals(((BizException) t).getError())) {
            //    Log.d(TAG, t.getMessage(), t);
            //}else{
            BizException bizException = (BizException) t;
            getView().showErrorMsg(code, "BizException:" + bizException.getError() + ":" +
                    bizException.getMessage());
            //}
        } else if (t instanceof IOException) {
            getView().showErrorMsg("网络出错");
        } else

        {
            getView().showErrorMsg("错误:" + t.getLocalizedMessage());
        }

        return true;
    }

    public <T> void loadData(NetworkCallRunnable<T> task) {
        IAsyncHelper asyncHelper = Helpers.asyncHelper();
        asyncHelper.executeTask(task);
    }

    public <T> void loadData(BackgroundCallRunnable<T> task) {
        IAsyncHelper asyncHelper = Helpers.asyncHelper();
        asyncHelper.executeTask(task);
    }

    public void loadCacheData(Integer type, String prompt) {
        if (type != null) {
            IDBHelper dbHelper = Helpers.dBHelper();
            final List<CacheEntity> list = dbHelper.getEntities(CacheEntity.class, "key = ?", ICache.Factory.buildKey(
                    String.valueOf(type)));
            dbHelper.deleteEntity(CacheEntity.class, "key = ?", ICache.Factory.buildKey(
                    String.valueOf(type)));
            if (list != null && list.size() > 0) {
                AlertMsg msg = new AlertMsg();
                if (prompt == null) {
                    prompt = "您还有未提交成功的信息，是否需要继续提交";
                }
                msg.setContent(prompt);
                msg.setOk("继续");
                msg.setCancel("删除");
                msg.setOnPositiveClick(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IView view = getView();
                        if (view instanceof AppBaseActivity) {
                            ((AppBaseActivity) view).setCacheData(list);
                        } else if (view instanceof AppBaseFragment) {
                            ((AppBaseFragment) view).setCacheData(list);
                        }
                    }
                });
                getView().showAlertMsg(msg);
            }

        }
    }

    /*public <P,T,R> void loadData(MultiNetworkRunnable<P,T,R> task, Func1... funcs){
        IAsyncHelper asyncHelper = Helpers.asyncHelper();
        asyncHelper.executeTask(task, funcs);
    }*/
    public <P, R> void saveData(SaveDataRunnable<P, R> task) {
        IAsyncHelper asyncHelper = Helpers.asyncHelper();
        asyncHelper.executeTask(task);
    }

    public void setAsyncRunnableState(boolean startOrEnd, String asyncMgs) {
        if (startOrEnd) {
            getView().showLoadingView(true, asyncMgs);
        } else {
            getView().showLoadingView(false);
        }

    }
}
