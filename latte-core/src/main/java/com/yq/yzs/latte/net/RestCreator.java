package com.yq.yzs.latte.net;


import com.yq.yzs.latte.core.app.ConfigKeys;
import com.yq.yzs.latte.core.app.Latte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述:
 */

public final class RestCreator {

    // OkHttpClient 对象
    private static final class OKHttpHolder {

        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);
        // 1 创建Builder对象
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        // 2 在Builder中添加拦截器参数
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor); // 添加配置项中的拦截器
                }
            }
            return BUILDER;
        }

        // 3 在Builder中添加其他参数
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                //.addInterceptor() // 添加其他应用拦截器
                //.addNetworkInterceptor() // 添加其他网络拦截器
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


    }

    // Retrofit 的 Builder
    private static final class RetrofitHolder {
        private static final String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CIIENT = new Retrofit.Builder().baseUrl(BASE_URL).client(OKHttpHolder.OK_HTTP_CLIENT).addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    // 提供 Service 的接口
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CIIENT.create(RestService.class);
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    //提供参数接口
    private static final class ParamsHolder {
        private static final HashMap<String, Object> PARAMS = new HashMap<>();
    }

    public static HashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

}
