package com.example.pengxl.sapienceraisehogs.interactor;

import com.example.pengxl.sapienceraisehogs.BizException;
import com.example.pengxl.sapienceraisehogs.api.ApiResult;
import com.example.pengxl.sapienceraisehogs.api.ApiService;
import com.example.pengxl.sapienceraisehogs.api.ResponseData;
import com.example.pengxl.sapienceraisehogs.base.AppBaseInteractor;
import com.example.pengxl.sapienceraisehogs.base.DataLoader;
import com.example.pengxl.sapienceraisehogs.entity.CheckCodeRequest;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by newhope on 2016/4/6.
 */
public class LoginInteractor extends AppBaseInteractor implements ILoginInteractor {
    private static final String TAG = "LoginInteractor";


    @Override
    public boolean sendLoginCheckCode(CheckCodeRequest request) throws IOException, BizException {
        Call<ResponseData<String>> datacall = ApiService.Factory.build().
                getCheckCode(object2Json(request));
        ApiResult<String> result = execute(datacall);
        return result.getCode() == ApiResult.SUCCESS;
    }


    public static class SendCodeLoader extends DataLoader<CheckCodeRequest, Void, ApiResult<String>> {

        public SendCodeLoader() {
        }

        @Override
        public Void loadDataFromNetwork(CheckCodeRequest request) throws Throwable {
            final ILoginInteractor loginInteractor = ILoginInteractor.Factory.build();
            loginInteractor.sendLoginCheckCode(request);
            return null;
        }
    }

}
