package com.newhope.smartpig.api;

/**
 * Created by newhope on 2016/4/8.
 */
public class ApiResult<T> {
    public static final int HTTP_OK = 200;
    public static final int SUCCESS = 0;
    public static final int FAILED = 1;
    int code;
    String msg;
    Object attachment;
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", attachment=" + attachment +
                ", data=" + data.toString() +
                '}';
    }
}
