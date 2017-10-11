package com.newhope.smartpig.api;

/**
 * Created by newhope on 2016/4/25.
 */
public class RequestData<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
