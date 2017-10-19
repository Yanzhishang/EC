package com.yq.yzs.latte.core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * @作者 Yzs
 * 2017/10/9.
 * 描述: 配置项, 单例, hashMap
 */

public final class Configurator {
    //HashMap 保存所有配置选项
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();

    // 保存所有的字体图标
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    // 保存所有的拦截器
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    // 1、构造器私有化
    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    // 2、提供获取 Configurator 对象的方法
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    // Configuator 的对象（唯一）
    private static class Holder {
        // 饿汉式
        private static final Configurator INSTANCE = new Configurator();// 创建 Configurator 的对象
    }

    // 开始进行配置
    public void configure() {
        // TODO 进行配置
        initIcons();
        // 配置结束后 把 CONFIG_READY 设置为 true
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    // 初始化字体图标
    private void initIcons() {
        if (ICONS.size() > 0) {
            Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            //其他的看情况添加
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    // 获得所有配置项
    public HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }


    // 检查配置是否完成 (只有调用了 configure() 才算配置完成了)
    private void checkConfiguration() {
        boolean isConfigReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isConfigReady) {
            // 如果配置没有完成，就抛异常
            throw new RuntimeException("Configuration is not ready, please call configure()!!!");
        }
    }

    // 根据枚举值获取配置项
    @SuppressWarnings("unchecked")// 不进行动态检查
    public <T> T getConfiguration(Object key) {
        checkConfiguration();
        Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + "is NULL!!!");
        }
        return (T) value;
    }

    // 配置 API_HOST
    public Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    // 配置字体图标
    public Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    // 配置单个拦截器
    public Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }


    // 配置多个拦截器
    public Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

}
