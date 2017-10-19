package com.yq.yzs.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yq.yzs.latte.activity.LatteDelegate;
import com.yq.yzs.latte.core.app.AccountManager;
import com.yq.yzs.latte.core.app.IUserChecker;
import com.yq.yzs.latte.ec.R;
import com.yq.yzs.latte.ec.R2;
import com.yq.yzs.latte.ec.sign.SignUpDelegate;
import com.yq.yzs.latte.ui.launcher.ScrollLauncherTag;
import com.yq.yzs.latte.utils.file.strorage.LattePreference;
import com.yq.yzs.latte.utils.file.timer.BaseTimerTask;
import com.yq.yzs.latte.utils.file.timer.ITimerListener;

import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 Yzs
 * 2017/10/12.
 * 描述: 实现定时跳转功能
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    private ILauncherListener launcherListener = null;

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView tvTimer = null;

    private Timer timer = null;
    private int count = 5;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener) {
            launcherListener = (ILauncherListener) activity;
        }
    }

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        // TODO 点击时直接跳过倒计时
        if (timer != null) {
            timer.cancel();
            timer = null;
            checkIsShowScroll();
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
        initTimer();
    }

    private void initTimer() {
        timer = new Timer();
        BaseTimerTask task = new BaseTimerTask(this);
        timer.schedule(task, 0, 1000);
    }

    private void checkIsShowScroll() {
        // 判断第一次启动的标志位是否为true
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            getSupportDelegate().start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    // 已经登录
                    if (launcherListener != null){
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNoSignIn() {
                    // 还没登录
                    if (launcherListener != null){
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
            getSupportDelegate().start(new SignUpDelegate(), SINGLETASK);
        }
    }

    // 每秒触发一次 进行倒计时
    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String tv = "跳过\n" + count + "s";
                if (tvTimer != null) {

                    tvTimer.setText(tv);
                    count--;
                    if (count < 0) {
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}


