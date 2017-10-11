package com.newhope.smartpig.interactor;

import com.newhope.smartpig.BizException;
import com.newhope.smartpig.api.ApiResult;
import com.newhope.smartpig.api.ApiService;
import com.newhope.smartpig.api.ResponseData;
import com.newhope.smartpig.base.AppBaseInteractor;
import com.newhope.smartpig.base.DataLoader;
import com.newhope.smartpig.entity.CheckCodeRequest;

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
