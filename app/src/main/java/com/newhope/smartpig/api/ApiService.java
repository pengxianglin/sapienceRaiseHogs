package com.newhope.smartpig.api;

import com.newhope.smartpig.base.Helpers;
import com.newhope.smartpig.utils.Constants;
import com.rarvinw.app.basic.androidboot.utils.IApiHelper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by newhope on 2016/4/7.
 */
public interface ApiService {
    //
//    //获取自动更新信息
//    @GET("http://download.pigkeeping.cn:80/ver.json")
//    Call<ResponseData<VersionData>> getVesion(@Query("data") String reqeust);
    @GET(Constants.API_BASE_PATH + "account.AccountLoginCodeServiceImpl")
    Call<ResponseData<String>> getCheckCode(@Query("data") String reqeust);

    class Factory {
        private static volatile ApiService instance = null;

        public static ApiService getInstance() {
            if (instance == null) {
                synchronized (Factory.class) {
                    if (instance == null) {
                        IApiHelper apiHelper = Helpers.apiHelper();
                        instance = apiHelper.createApiService(ApiService.class);
                    }
                }
            }
            return instance;
        }

        public static ApiService build() {
            return getInstance();
        }
    }
}
