package com.yq.yzs.latte.ui.loader;

import android.util.DisplayMetrics;

import com.yq.yzs.latte.core.app.Latte;

/**
 * @作者 Yzs
 * 2017/10/11.
 * 描述:
 */

public class DimenUtils {
    // 获取屏幕的高度
    public static int getScreenHeight() {
        DisplayMetrics displayMetrics = Latte.getApplicationContext().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
    // 获取屏幕的宽度
    public static int getScreenWidth() {
        DisplayMetrics displayMetrics = Latte.getApplicationContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}

