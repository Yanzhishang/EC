package com.yq.yzs.latte.net;


import android.content.Context;

import com.yq.yzs.latte.net.callback.IError;
import com.yq.yzs.latte.net.callback.IFailure;
import com.yq.yzs.latte.net.callback.IRequest;
import com.yq.yzs.latte.net.callback.ISuccess;
import com.yq.yzs.latte.net.callback.RequestCallbacks;
import com.yq.yzs.latte.net.callback.RestClientBuilder;
import com.yq.yzs.latte.net.download.DownloadHandler;
import com.yq.yzs.latte.ui.loader.LatteLoader;
import com.yq.yzs.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final String DOWNLOAD_DIR; // 下载后保存到哪个目录下
    private final String EXTENSION; // 文件的扩展名
    private final String NAME; // 文件名
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;// 请求体
    private final File FILE;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;


    // 构造器
    public RestClient(String url,
                      HashMap<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.PARAMS.putAll(params);
        this.URL = url;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
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
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null) {
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
            case POST_RAW: // 参数放到Body中
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                // 把文件转换成实体 并进行上传
                RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = RestCreator.getRestService().upload(URL, body);
                break;
            case DOWNLOAD:
                // TODO
                break;
        }
        // 发起请求
        if (call != null) {
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

    /**
     * 对于 POST 来说，用户提交的参数时 PARAM 或 BODY
     * 如果是 PARAM 就使用 post 方式
     * 如果是 BODY 就使用 postRaw 方式
     */
    public void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else if (PARAMS == null) {
            request(HttpMethod.POST_RAW);
        } else {
            // 如果BODY和PARAMS都非空 则抛出异常
            throw new RuntimeException("one of body or param must be null");
        }

    }

    /**
     * 对于 POST 来说，用户提交的参数时 PARAM 或 BODY
     * 如果是 PARAM 就使用 PUT 方式
     * 如果是 BODY 就使用 PUT_RAW 方式
     */
    public void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else if (PARAMS == null) {
            request(HttpMethod.PUT_RAW);
        } else {
            // 如果BODY和PARAMS都非空 则抛出异常
            throw new RuntimeException("one of body or param must be null");
        }

    }

    public void delete() {
        request(HttpMethod.DELETE);
    }

    /**
     * 前面的操作都是发起 request 请求
     * <p>
     * 下载的操作与前面的不一样，它是专门启动一个 Handler 区进行下载的操作
     */
    public void download() {
        new DownloadHandler(URL,
                DOWNLOAD_DIR,
                EXTENSION,
                NAME,
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR).handleDownload();
    }


}
