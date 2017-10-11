package com.newhope.smartpig.interactor;

import com.newhope.smartpig.BizException;
import com.newhope.smartpig.entity.CheckCodeRequest;
import com.rarvinw.app.basic.androidboot.mvp.IInteractor;

import java.io.IOException;

/**
 * Created by newhope on 2016/4/6.
 */
public interface ILoginInteractor extends IInteractor {
    boolean sendLoginCheckCode(CheckCodeRequest request) throws IOException, BizException;

    class Factory {
        public static ILoginInteractor build() {
            return new LoginInteractor();
        }
    }
}
