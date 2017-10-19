package com.yq.yzs.latte.utils.file.timer;

import java.util.TimerTask;

/**
 * @作者 Yzs
 * 2017/10/12.
 * 描述: BaseTimerTask 实现定时器，当定时器到的时候就会做run的操作。
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener listener = null;

    public BaseTimerTask(ITimerListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        // 当定时器到的时候就会做run的操作
        if (listener != null) {
            listener.onTimer();
        }
    }
}
