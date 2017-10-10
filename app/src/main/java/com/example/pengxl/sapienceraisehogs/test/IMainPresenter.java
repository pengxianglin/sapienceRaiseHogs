package com.example.pengxl.sapienceraisehogs.test;

import com.rarvinw.app.basic.androidboot.mvp.IPresenter;


public interface IMainPresenter extends IPresenter<IMainView> {

    void sendLoginCheckCode(String name,String password);
}
