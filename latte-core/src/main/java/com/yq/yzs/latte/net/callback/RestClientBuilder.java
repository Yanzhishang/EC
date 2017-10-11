package com.yq.yzs.latte.net.callback;

import android.content.Context;

import com.yq.yzs.latte.net.RestClient;
import com.yq.yzs.latte.net.RestCreator;
import com.yq.yzs.latte.ui.loader.LoaderStyle;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述: 封装建造者模式的对象3——建造者
 */

public final class RestClientBuilder {
    private final HashMap<String, Object> PARAMS = RestCreator.getParams();
    private String url = null;
    private IRequest request = null;
    private ISuccess success = null;
    private IFailure failure = null;
    private IError error = null;
    private RequestBody body = null; // 请求体
    private Context context = null;
    private LoaderStyle loaderStyle = null;

    // 构造器
    public RestClientBuilder() {
    }

    // 返回本身以便链式调用
    public RestClientBuilder url(String url) {
        this.url = url;
        return this;
    }

    public RestClientBuilder onRequest(IRequest request) {
        this.request = request;
        return this;
    }

    public RestClientBuilder success(ISuccess success) {
        this.success = success;
        return this;
    }

    public RestClientBuilder failure(IFailure failure) {
        this.failure = failure;
        return this;
    }

    public RestClientBuilder error(IError error) {
        this.error = error;
        return this;
    }

    public RestClientBuilder params(String key, Object value) {
        this.PARAMS.put(key, value);
        return this;
    }

    public RestClientBuilder params(HashMap<String, Object> params) {
        this.PARAMS.putAll(params);
        return this;
    }

    public RestClientBuilder raw(String raw) {
        this.body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), raw);
        return this;
    }

    public RestClientBuilder loader(Context context) {
        this.context = context;
        this.loaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public RestClientBuilder loader(Context context, LoaderStyle style) {
        this.context = context;
        this.loaderStyle = style;
        return this;
    }


    // 通过建造者创建RestClient对象
    public RestClient build() {
        return new RestClient(url, PARAMS, request, success, failure, error, body, context, loaderStyle);
    }
}
