package com.yq.yzs.latte.net.interceptor;

import android.support.annotation.RawRes;

import com.yq.yzs.latte.utils.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @作者 Yzs
 * 2017/10/12.
 * 描述:
 */

public class DebugInterceptor extends BaseInterceptoe {
    private final String DEBUG_URL; // 待拦截的URL的敏感词语
    private final int DEBUG_RAW_ID; // 自定义的raw资源 当拦截下来后 会显示该资源上的内容

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }


    // 拦截我们需要拦截的东西
    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = chain.request().url().toString();
        // 判断是否进行判断拦截
        // 如果url中包含敏感词语 则进行拦截 否则放心
        if(url.contains(DEBUG_URL)) {
            // 进行拦截
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        // 放心
        return chain.proceed(chain.request());
    }


    private Response debugResponse(Chain chain, @RawRes int debug_raw_id) {
        String json = FileUtil.getRawFile(debug_raw_id);
        return getResponse(chain, json);
    }



    private Response getResponse(Chain chain, String json) {
        // 模拟Http响应 假装访问成功 实际上访问的是本地提供的字符串
        return new Response.Builder()
                .code(200) // Http的状态码 200表示OK
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1) // 目前大部分都是使用Http1.1的协议
                .build();
    }

}
