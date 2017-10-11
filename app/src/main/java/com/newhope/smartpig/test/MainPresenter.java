package com.newhope.smartpig.test;

import com.newhope.smartpig.base.AppBasePresenter;
import com.newhope.smartpig.base.RefreshDataRunnable;
import com.newhope.smartpig.entity.CheckCodeRequest;
import com.newhope.smartpig.interactor.LoginInteractor;

public class MainPresenter extends AppBasePresenter<IMainView> implements IMainPresenter {
    private static final String TAG = "MainPresenter";
    @Override
    public void sendLoginCheckCode(String name, String password) {
        LoginInteractor.SendCodeLoader loader = new LoginInteractor.SendCodeLoader();
        CheckCodeRequest request = new CheckCodeRequest();
        request.setAccount(name);
        request.setPassword(password);
        request.setType("1");
        loadData(new RefreshDataRunnable<CheckCodeRequest, Void>(this, loader, request) {
            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
//                getView().showMsg(getView().getContext().getString(R.string.code_has_send));
//                getView().showSendCode();
            }
        });
    }
}
