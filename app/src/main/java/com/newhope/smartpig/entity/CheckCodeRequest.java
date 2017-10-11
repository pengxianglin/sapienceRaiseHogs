package com.newhope.smartpig.entity;

/**
 * Created by newhope on 2016/4/25.
 */
public class CheckCodeRequest {
    private String account;
    private String password;
    private String tel;
    private String type;//1登录，2忘记密码，3修改密码

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
