package com.rarvinw.app.basic.androidboot.utils;

import android.util.Log;

import com.rarvinw.app.basic.androidboot.task.BackgroundCallRunnable;
import com.rarvinw.app.basic.androidboot.task.NetworkCallRunnable;
import com.rarvinw.app.basic.androidboot.task.TaskCallRunnable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by newhope on 2016/4/3.
 */
public class RxAsyncHelper implements IAsyncHelper {

    private static final String TAG = "RxAsyncHelper";

    public RxAsyncHelper() {

    }


    public <T> void executeTask(final BackgroundCallRunnable<T> runnable) {
        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T t = runnable.doBackground();
                subscriber.onNext(t);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        runnable.preExecute();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "Throwable :", e);
                    }

                    @Override
                    public void onNext(T t) {
                        runnable.postExecute(t);
                    }
                });
    }

    public <R> void executeTask(final TaskCallRunnable<R> runnable,
                                     Func1... funcs) {
        Observable<Void> observable = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    //T t = runnable.doBackgroundCall();
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    Exceptions.throwOrReport(e, subscriber);
                }
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        runnable.onPreCall();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                ;

        Observable observable1 = observable;
        if (funcs != null && funcs.length != 0) {
            for (Func1 func1 : funcs) {
                observable1 = observable1.map(func1);
            }
        }
        observable1 = observable1.observeOn(AndroidSchedulers.mainThread());
        observable1.subscribe(new Observer<R>() {
            @Override
            public void onCompleted() {
                //runnable.onFinished();
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "Throwable :", e);
                runnable.onError(e);
                //runnable.onFinished();
            }

            @Override
            public void onNext(R t) {
                //runnable.onSuccess(t);
                runnable.onSuccess(t);
            }
        });
    }

    public <T> void executeTask(final NetworkCallRunnable<T> runnable) {
        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    T t = runnable.doBackgroundCall();
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    Exceptions.throwOrReport(e, subscriber);
                }
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        runnable.onPreCall();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onCompleted() {
                        //runnable.onFinished();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "Throwable :", e);
                        runnable.onError(e);
                        //runnable.onFinished();
                    }

                    @Override
                    public void onNext(T t) {
                        runnable.onSuccess(t);
                    }
                });
    }
}
