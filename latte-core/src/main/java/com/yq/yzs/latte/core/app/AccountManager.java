package com.yq.yzs.latte.core.app;

import com.yq.yzs.latte.utils.file.strorage.LattePreference;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public class AccountManager {
    private enum SignTag {
        SIGN_TAG
    }
    // 保存用户登录状态 登录后调用
    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }
    // 判断是否已经登录
    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if(isSignIn()) {
            checker.onSignIn(); // 如果已经登录 执行登录的回调
        } else {
            checker.onNoSignIn(); // 如果未登录 执行未登录的回调
        }
    }
}


