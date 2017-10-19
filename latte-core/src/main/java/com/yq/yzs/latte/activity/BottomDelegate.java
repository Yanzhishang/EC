package com.yq.yzs.latte.activity;

import android.widget.Toast;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public abstract class BottomDelegate extends LatteDelegate {

    private static final long WAIT_TIME = 2000;
    // 首次点击后退按钮的时间
    private long TOUCH_TIME = 0;


    /**
     * 重写后退按钮的点击事件
     * 双击的时候可以退出应用
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            activity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(activity, "双击退出", Toast.LENGTH_LONG).show();
        }

        return true;
    }

}
