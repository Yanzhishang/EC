package com.yq.yzs.latte.ui.loader;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.yq.yzs.latte.core.R;

import java.util.ArrayList;

/**
 * @作者 Yzs
 * 2017/10/11.
 * 描述:
 */

public class LatteLoader {
    private static int LOADER_SIZE_SCALE = 8;
    private static int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<Dialog> LOADERS = new ArrayList<>();
    // 默认的进度条
    private static final String DEFAULT_LOADER = LoaderStyle.CubeTransitionIndicator.name();

    // 重载几个显示进度条加载的方法
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    public static void showLoading(Context context, String type) {
        // 这里的才是核心
        Dialog dialog = new Dialog(context, R.style.dialog);

        AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);
        // 得到屏幕的宽和高
        int deviceWidth = DimenUtils.getScreenWidth();
        int deviceHeight = DimenUtils.getScreenHeight();
        // 把进度条放到屏幕的正中央
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = deviceWidth / LOADER_SIZE_SCALE;
        lp.height = deviceHeight / LOADER_SIZE_SCALE;
        lp.height = lp.height + deviceHeight / LOADER_SIZE_SCALE;
        lp.gravity = Gravity.CENTER;
        // 显示进度条
        LOADERS.add(dialog);
        dialog.show();
    }

    // 隐藏进度条
    public static void stopLoading() {
        for(Dialog dialog : LOADERS) {
            if(dialog != null && dialog.isShowing()) {
                dialog.cancel();
            }
        }
    }
}


