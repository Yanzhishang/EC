package com.yq.yzs.latte.core.app;

import android.content.Context;

/**
 * @作者 Yzs
 * 2017/10/9.
 * 描述: 外部统一调用这个类的方法  初始化
 */

public final class Latte {

    public static Configurator init(Context context) {
        Configurator.getInstance() // 获得实例
                .getLatteConfigs() // 获得所有配置项
                .put(ConfigKeys.APPLICATION_CONTEXT, context);
        return Configurator.getInstance();
    }

    // 直接返回 Configurator 对象
    public Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    // 根据枚举值获取配置项
    public static  <T> T getConfiguration(Object key) {
        return Configurator.getInstance().getConfiguration(key);
    }

    // 获取上下文
    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }
}
