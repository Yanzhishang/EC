package com.yq.yzs.ec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.yq.yzs.latte.core.app.Latte;
import com.yq.yzs.latte.ec.FontEcModule;
import com.yq.yzs.latte.ec.database.DatabaseManager;
import com.yq.yzs.latte.net.interceptor.DebugInterceptor;

/**
 * @作者 Yzs
 * 2017/10/9.
 * 描述:
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://www.baidu.com/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
        // 进行数据库的初始化
        DatabaseManager.getInstance().init(this);

    }


}

