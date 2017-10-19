package com.yq.yzs.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import com.yq.yzs.latte.activity.LatteDelegate;
import com.yq.yzs.latte.ec.R;
import com.yq.yzs.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 Yzs
 * 2017/10/13.
 * 描述:
 */

public class SignInDelegate extends LatteDelegate {
    private ISignListener mISignListener = null;
    @BindView(R2.id.edit_sign_in_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }


    // 点击登录按钮 检查输入是否合法
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            // 验证通过 登录成功
            // 验证用户名
            String name = mName.getText().toString();
            // 验证密码
            String password = mPassword.getText().toString();
            SignHandler.onSignIn(name, mISignListener);
        }
    }

    // 点击微信登录按钮
    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickSignInWeChat() {
        // TODO
        Toast.makeText(getContext(), "微信登录", Toast.LENGTH_LONG).show();
    }

    // 点击注册文本
    @OnClick(R2.id.tv_link_sign_up)
    void onClickSignUp() {
        getSupportDelegate().start(new SignUpDelegate(), SINGLETASK);
    }

    // 检查输入是否合法
    private boolean checkForm() {
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        boolean isPass = true;
        // 检查姓名是否合法
        if (name == null || name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }
        // 检查password是否合法
        if (password == null || password.length() < 6) {
            mPassword.setError("密码长度至少6位");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

    }
}
