package com.yq.yzs.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.yq.yzs.latte.activity.LatteDelegate;
import com.yq.yzs.latte.ec.R;
import com.yq.yzs.latte.ec.R2;
import com.yq.yzs.latte.net.RestClient;
import com.yq.yzs.latte.net.callback.IError;
import com.yq.yzs.latte.net.callback.IFailure;
import com.yq.yzs.latte.net.callback.ISuccess;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Yzs
 * @date 2017/10/13.
 * <p>
 * 描述:注册
 */

public class SignUpDelegate extends LatteDelegate {

    private ISignListener mISignListener = null;
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    // 点击注册按钮 检查输入是否合法
    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            // 验证通过 注册
            Toast.makeText(getContext(), "验证通过", Toast.LENGTH_LONG).show();
            RestClient.builder().url("http://duoyue.duapp.com/httpdata/json_userprofile.php").params("name", mName.getText().toString()).params("email", mEmail.getText().toString()).params("phone", mPhone.getText().toString()).params("password", mPassword.getText().toString()).success(new ISuccess() {
                @Override
                public void onSuccess(String response) {

                    Log.i("SignUpDelegate", response);
                    SignHandler.onSignUp(response, mISignListener);
                }
            }).failure(new IFailure() {
                @Override
                public void onFailure() {
                    Log.i("SignUpDelegate", "failure");

                }
            }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    Log.i("SignUpDelegate", "error");
                }
            }).build().post();

        }
    }

    // 点击登录
    @OnClick(R2.id.tv_link_sign_in)
    void onClickSignIn() {
        getSupportDelegate().start(new SignInDelegate(), SINGLETASK);
    }


    // 检查输入是否合法
    private boolean checkForm() {
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        String rePassword = mRePassword.getText().toString();
        boolean isPass = true;
        // 检查姓名是否合法
        if (name == null || name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }
        // 检查email是否合法
        if (email == null || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }
        // 检查phone是否合法
        if (phone == null || phone.length() != 11) {
            mPhone.setError("手机号码格式错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }
        // 检查password是否合法
        if (password == null || password.length() < 6) {
            mPassword.setError("密码长度至少6位");
            isPass = false;
        } else {
            mPassword.setError(null);
        }
        // 检查repassword是否合法
        if (rePassword == null || rePassword.length() < 6 || !rePassword.equals(password)) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

    }
}
