package com.yq.yzs.latte.net.interceptor;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * @作者 Yzs
 * 2017/10/12.
 * 描述:
 */

public abstract class BaseInterceptoe implements Interceptor {
    // 获取Url中的所有参数(GET参数)
    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        HttpUrl url = chain.request().url();
        int size = url.querySize();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    // 获取Url中对应的参数(GET参数)
    protected String getUrlParameters(Chain chain, String key) {
        HttpUrl url = chain.request().url();
        return url.queryParameter(key);
    }

    // 从请求体中获取所有参数（POST参数）
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        FormBody formBody = (FormBody) chain.request().body();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        int size = 0;
        if (formBody != null) {
            size = formBody.size();
        }
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }
}
