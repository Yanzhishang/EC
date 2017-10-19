package com.yq.yzs.latte.core.app;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */



public interface IUserChecker {
    // 用户已经登录
    void onSignIn();
    // 用户未登录
    void onNoSignIn();
}

