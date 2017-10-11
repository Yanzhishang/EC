package com.yq.yzs.ec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.yq.yzs.latte.activity.LatteDelegate;
import com.yq.yzs.latte.net.RestClient;
import com.yq.yzs.latte.net.callback.IError;
import com.yq.yzs.latte.net.callback.IFailure;
import com.yq.yzs.latte.net.callback.IRequest;
import com.yq.yzs.latte.net.callback.ISuccess;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述:
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
        testRestClient();
    }

    public void testRestClient() {
        //        String URL = "http://duoyue.duapp.com/httpdata/json_simple.php";
        RestClient.builder()
                .url("http://duoyue.duapp.com/httpdata/json_simple.php")
                .loader(getContext())
                .success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getContext(), "success:" + response, Toast.LENGTH_LONG).show();
            }
        }).failure(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "failure", Toast.LENGTH_LONG).show();
            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getContext(), "error:" + msg, Toast.LENGTH_LONG).show();
            }
        }).onRequest(new IRequest() {
            @Override
            public void onRequestStare() {

            }

            @Override
            public void onRequestEnd() {

            }
        }).build().get();
    }
}
