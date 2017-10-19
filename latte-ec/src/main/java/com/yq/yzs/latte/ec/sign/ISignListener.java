package com.yq.yzs.latte.ec.sign;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public interface ISignListener {

    /**
     * 登录成功的回调
     */
    void onSignInSuccess();

    /**
     * 注册成功的回调
     */
    void onSignUpSuccess();
}
