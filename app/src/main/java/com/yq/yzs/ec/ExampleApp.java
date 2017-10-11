package com.yq.yzs.ec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.yq.yzs.latte.core.app.Latte;
import com.yq.yzs.latte.ec.FontEcModule;

/**
 * @作者 Yzs
 * 2017/10/9.
 * 描述:
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String host = "http://www.baidu,com";
        Latte.init(this)
                .withApiHost(host)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .configure();
    }


}
