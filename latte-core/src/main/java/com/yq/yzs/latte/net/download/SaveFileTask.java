package com.yq.yzs.latte.net.download;

import android.os.AsyncTask;

import com.yq.yzs.latte.net.callback.IRequest;
import com.yq.yzs.latte.net.callback.ISuccess;
import com.yq.yzs.latte.utils.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @作者 Yzs
 * 2017/10/12.
 * 描述: 异步任务下载
 * <p>
 * 如果要进行下载，
 * 1、要么传入完整的文件名name，
 * 2、要么传入扩展名
 * （这里做了简单的处理：把扩展名转大写作为文件名）
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    // 参数0 下载存放的目录 String
    // 参数1 扩展名 String
    // 参数2 请求体 RequestBody
    // 参数3 文件名 String
    @Override
    protected File doInBackground(Object... params) {
        // 获取传入的各个参数
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        ResponseBody body = (ResponseBody) params[2];
        String name = (String) params[3];
        InputStream is = body.byteStream();
        // 如果传入的下载目录是空 需要自己定义下载的目录
        if (downloadDir == null || "".equals(downloadDir)) {
            downloadDir = "down_loads";
        }
        // 如果传入的扩展名是空 自定义扩展名
        if (extension == null) {
            extension = "";
        }
        // 如果传入的文件名是空 则以扩展名取代
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }
}

