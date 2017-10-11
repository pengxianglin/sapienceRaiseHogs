package com.newhope.smartpig.base;

import com.newhope.smartpig.BizException;
import com.newhope.smartpig.api.ApiResult;
import com.newhope.smartpig.api.ResponseData;
import com.rarvinw.app.basic.androidboot.base.BaseInteractor;
import com.rarvinw.app.basic.androidboot.utils.IJsonHelper;
import com.rarvinw.app.basic.androidboot.utils.INetworkHelper;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by newhope on 2016/4/7.
 */
public class AppBaseInteractor extends BaseInteractor {

    public static final MediaType MEDIA_TYPE_BINARY
            = MediaType.parse("application/octet-stream");

    public <T> String object2Json(T data) {
        IJsonHelper helper = Helpers.jsonHelper();
        return helper.toJson(data);
//        return String.format("data=%s",helper.toJson(data));
    }

    public <T> String object2PostJson(T data) {
        IJsonHelper helper = Helpers.jsonHelper();
        return helper.toJson(data);
//        return String.format("data=%s",helper.toJson(data));
    }

    public String postImage(String url, String filePath) {
        INetworkHelper helper = new INetworkHelper.Factory().build();
        File file = new File(filePath);


        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_BINARY, file))
                .build();

        okhttp3.Response response = null;
        try {
            response = helper.execute(request);
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> ApiResult<T> execute(Call<ResponseData<T>> call) throws IOException, BizException {


        Response<ResponseData<T>> response = call.execute();

        ApiResult<T> result = new ApiResult<T>();
        result.setCode(response.code());
        result.setMsg(response.message());
        if (response.code() == ApiResult.HTTP_OK) {
            ResponseData<T> data = response.body();
            if (data.isSuccess()) {
                result.setCode(ApiResult.SUCCESS);
                result.setData(data.getResCont().getData());
            } else {
                result.setCode(ApiResult.FAILED);
                String errorMsg = null;
                String reason = null;
                if (data.getResCont() != null) {
                    reason = data.getResCont().getResCode();
                    errorMsg = data.getResCont().getResDesc();
                }
                throw new BizException(BizException.CODE_BIZ_FAILED, reason, errorMsg);
            }
        } else {
            throw new IOException(response.code() + ":" + response.message());
        }
        return result;
    }


   /* public <T> ApiResult<T> execute(Call<ResponseData> call, Class<T> type) throws IOException, BizException {

        Response<ResponseData> response = call.execute();

        ApiResult<T> result = new ApiResult<T>();
        result.setCode(response.code());
        result.setMsg(response.message());
        if (response.code() == ApiResult.HTTP_OK) {
            ResponseData data = response.body();
            if(data.isSuccessed()){
                result.setCode(ApiResult.SUCCESS);
                if(type != null){
                    result.setData(data.getDataModel(type));
                }
            }else{
                result.setCode(ApiResult.FAILED);
                throw new BizException(BizException.CODE_BIZ_FAILED, data.getRspMsg());
            }
        }else{
            throw new IOException(response.code()+":"+response.message());
        }
        return result;
    }

    public <T> void enqueue(Call<ResponseData> call, final ApiResultCallback<T> callback,
                            final Class<T> type) {
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ApiResult<T> result = new ApiResult<T>();
                result.setCode(response.code());
                result.setMsg(response.message());
                if (response.code() ==  ApiResult.HTTP_OK) {
                    ResponseData data = response.body();
                    if(data.isSuccessed()){
                        result.setCode(ApiResult.SUCCESS);
                        if(type != null){
                            result.setData(data.getDataModel(type));
                        }
                    }else{
                        result.setCode(ApiResult.FAILED);
                        callback.onBizException(new BizException(BizException.CODE_BIZ_FAILED, data.getRspMsg()));
                    }
                }
                callback.onResponse(result);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }*/

    abstract class ApiResultCallback<T> {
        abstract public void onResponse(ApiResult<T> result);

        abstract public void onFailure(Throwable t);

        abstract public void onBizException(Throwable t);
    }
}
