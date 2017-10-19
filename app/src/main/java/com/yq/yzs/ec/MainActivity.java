package com.yq.yzs.ec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.joanzapata.iconify.Iconify;
import com.yq.yzs.latte.activity.LatteDelegate;
import com.yq.yzs.latte.activity.ProxyActivity;
import com.yq.yzs.latte.ec.launcher.ILauncherListener;
import com.yq.yzs.latte.ec.launcher.LauncherDelegate;
import com.yq.yzs.latte.ec.launcher.OnLauncherFinishTag;
import com.yq.yzs.latte.ec.main.EcBottomDelegate;
import com.yq.yzs.latte.ec.sign.ISignListener;
import com.yq.yzs.latte.ec.sign.SignInDelegate;

public class MainActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(getApplicationContext(), "启动结束，用户已经登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(getApplicationContext(), "启动结束，用户未登录", Toast.LENGTH_LONG).show();
                // 进入登录界面// 进入登录界面
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
        }
        Iconify iconify;
    }
}
