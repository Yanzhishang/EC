package com.yq.yzs.latte.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @作者 Yzs
 * 2017/10/13.
 * 描述:
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {
    // 创建LauncherHolder对象
    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
