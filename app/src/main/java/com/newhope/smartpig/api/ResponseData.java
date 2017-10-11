package com.newhope.smartpig.api;

/**
 * Created by newhope on 2016/4/11.
 */
public class ResponseData<T> {
    private String sign;
    private ResponseCont<T> resCont;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ResponseCont<T> getResCont() {
        return resCont;
    }

    public void setResCont(ResponseCont<T> resCont) {
        this.resCont = resCont;
    }

    public  T getDataModel() {
        if(isSuccess()){
            return resCont.getData();
        }
        return null;
    }
    public boolean isSuccess(){
        return ResponseCont.CODE_OK.equals(resCont.getResCode());
    }
}
