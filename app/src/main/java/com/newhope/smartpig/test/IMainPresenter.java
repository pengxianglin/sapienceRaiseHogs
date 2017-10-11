package com.newhope.smartpig.test;

import com.rarvinw.app.basic.androidboot.mvp.IPresenter;


public interface IMainPresenter extends IPresenter<IMainView> {

    void sendLoginCheckCode(String name,String password);
}
