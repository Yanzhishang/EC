package com.yq.yzs.latte.net;


import android.content.Context;

import com.yq.yzs.latte.net.callback.IError;
import com.yq.yzs.latte.net.callback.IFailure;
import com.yq.yzs.latte.net.callback.IRequest;
import com.yq.yzs.latte.net.callback.ISuccess;
import com.yq.yzs.latte.net.callback.RequestCallbacks;
import com.yq.yzs.latte.net.callback.RestClientBuilder;
import com.yq.yzs.latte.ui.loader.LatteLoader;
import com.yq.yzs.latte.ui.loader.LoaderStyle;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述: 封装建造者模式的对象3——建造者
 */

public final class RestClient {
    private static final HashMap<String, Object> PARAMS = RestCreator.getParams();

    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;// 请求体
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;


    // 构造器
    public RestClient(String url,
                      HashMap<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      Context context,
                      LoaderStyle style) {
        this.PARAMS.putAll(params);
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = style;
    }

    //建造者
    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    //处理建造者
    // 处理请求
    private void request(HttpMethod method) {
        RestService service = RestCreator.getRestService();
        // 请求开始
        if(REQUEST != null) {
            REQUEST.onRequestStare();
        }if(LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        // 根据不同的枚举类型进行不同的请求
        Call<String> call = null;
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                // TODO
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                // TODO
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                // TODO
                break;
            case DOWNLOAD:
                // TODO
                break;
        }
        // 发起请求
        if(call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    // 获取RequestCallback对象
    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }


    // 以下是给外部提供的方法
    public void get() {
        request(HttpMethod.GET);
    }

    public void post() {
        request(HttpMethod.POST);
    }

    public void put() {
        request(HttpMethod.PUT);
    }

    public void delete() {
        request(HttpMethod.DELETE);
    }
}
