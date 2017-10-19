package com.yq.yzs.latte.ui.launcher;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * @作者 Yzs
 * 2017/10/13.
 * 描述:
 */

public class LauncherHolder implements Holder<Integer> {

    private ImageView imageView;

    // 创建视图
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        return imageView;

    }

    // 滑动时需要更新UI
    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        // 把图片设置为背景 目的是占据整个屏幕
        imageView.setBackgroundResource(data);

    }
}
