package com.newhope.smartpig;

/**
 * Created by newhope on 2016/4/11.
 */
public class BizException extends Exception {
    public static final int CODE_BIZ_FAILED = 1;
    public static final int CODE_LOAD_FAILED = 2;
    private int code;
    private String error;

    public BizException() {
        super();
    }

    public BizException(int code) {
        super();
        this.code = code;
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(int code, String error, String msg) {
        super(msg);
        this.code = code;
        this.error = error;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }
}
