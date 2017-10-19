package com.yq.yzs.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.yq.yzs.latte.activity.LatteDelegate;
import com.yq.yzs.latte.core.app.AccountManager;
import com.yq.yzs.latte.core.app.IUserChecker;
import com.yq.yzs.latte.ec.R;
import com.yq.yzs.latte.ui.launcher.LauncherHolderCreator;
import com.yq.yzs.latte.ui.launcher.ScrollLauncherTag;
import com.yq.yzs.latte.utils.file.strorage.LattePreference;

import java.util.ArrayList;

/**
 * @作者 Yzs
 * 2017/10/13.
 * 描述:
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {
    private ILauncherListener launcherListener = null;
    private ConvenientBanner<Integer> convenientBanner = null; // 轮播对象
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>(); // 轮播的图片集

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            launcherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        convenientBanner = new ConvenientBanner<>(getContext());
        return convenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
        initBanner();
    }

    private void initBanner() {

        INTEGERS.clear();
        // 把图片依次添加到集合中
        INTEGERS.add(R.drawable.launcher_01);
        INTEGERS.add(R.drawable.launcher_02);
        INTEGERS.add(R.drawable.launcher_03);
        INTEGERS.add(R.drawable.launcher_04);
        INTEGERS.add(R.drawable.launcher_05);

        // 初始化banner对象
        convenientBanner.setPages(new LauncherHolderCreator(), INTEGERS).setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).setOnItemClickListener(this).setCanLoop(true);


    }

    @Override
    public void onItemClick(int position) {
        // 判断点击的是否最后一张 如果是则把标志位设置为true
        if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            // 检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (launcherListener != null) {
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNoSignIn() {
                    if (launcherListener != null) {
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });

        }
    }
}