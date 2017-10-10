package com.rarvinw.app.basic.androidboot.utils;

import com.rarvinw.app.basic.androidboot.task.BackgroundCallRunnable;
import com.rarvinw.app.basic.androidboot.task.NetworkCallRunnable;
import com.rarvinw.app.basic.androidboot.task.TaskCallRunnable;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by newhope on 2016/4/3.
 */
public interface IAsyncHelper {
     <T> void executeTask(final BackgroundCallRunnable<T> runnable);
     <T> void executeTask(final NetworkCallRunnable<T> runnable);
     <R> void executeTask(final TaskCallRunnable<R> runnable,
                                       Func1... funcs);
     interface IFactory{
          IAsyncHelper build();
     }
     class Factory implements IFactory{
          static IAsyncHelper helper = new RxAsyncHelper();
          public IAsyncHelper build() {
               return helper;
          }
     }
}
