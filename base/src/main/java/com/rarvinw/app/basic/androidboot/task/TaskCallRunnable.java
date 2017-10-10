package com.rarvinw.app.basic.androidboot.task;

/**
 * Created by newhope on 2016/4/22.
 */
public abstract class TaskCallRunnable<R> {

    public void onPreCall() {}

    public abstract void onSuccess(R result);

    public abstract void onError(Throwable re);

}
