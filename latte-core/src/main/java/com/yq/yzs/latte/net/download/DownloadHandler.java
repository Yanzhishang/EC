package com.yq.yzs.latte.net.download;

import com.yq.yzs.latte.net.RestCreator;
import com.yq.yzs.latte.net.callback.IError;
import com.yq.yzs.latte.net.callback.IFailure;
import com.yq.yzs.latte.net.callback.IRequest;
import com.yq.yzs.latte.net.callback.ISuccess;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @作者 Yzs
 * 2017/10/12.
 * 描述: 进行下载的处理
 */

public class DownloadHandler {

    private final HashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR; // 下载后保存到哪个目录下
    private final String EXTENSION; // 文件的扩展名
    private final String NAME; // 文件名
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;


    // 构造器
    public DownloadHandler(String url, String downloadDir, String extension, String name, IRequest request, ISuccess success, IFailure failure, IError error) {
        this.URL = url;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;

    }

    /**
     * 提供一个下载的方法
     */
    public final void handleDownload() {
        if(REQUEST != null) {
            REQUEST.onRequestStart();
        }
        RestCreator
                .getRestService()
                .download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.execute(DOWNLOAD_DIR, EXTENSION, response.body(), NAME);
                            // 以防文件下载不全进行的判断
                            if(task.isCancelled()) {
                                if(REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if(ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(FAILURE != null) {
                            FAILURE.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });
    }
}
