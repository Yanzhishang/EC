package com.yq.yzs.latte.net.callback;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述: 异常出错回调
 */

public interface IError {
    void onError(int code, String msg);
}
