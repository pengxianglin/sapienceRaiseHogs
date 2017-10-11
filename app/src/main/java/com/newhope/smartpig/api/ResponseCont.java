package com.newhope.smartpig.api;

/**
 * Created by newhope on 2016/4/22.
 */
public class ResponseCont<T> {
    public static final String CODE_OK = "SUCCESS";
    private String resCode;
    private String resDesc;
    private T data;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
