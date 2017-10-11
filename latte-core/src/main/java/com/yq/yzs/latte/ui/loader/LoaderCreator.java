package com.yq.yzs.latte.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.HashMap;

/**
 * @作者 Yzs
 * 2017/10/11.
 * 描述:
 */

public final class LoaderCreator  {
    private static final HashMap<String, Indicator> LOADER_MAP = new HashMap<>();

    public static AVLoadingIndicatorView create(String type, Context context) {
        AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADER_MAP.get(type) == null) {
            // 根据 type 生成默认的 Indicator
            Indicator indicator = geteIndicator(type);
            LOADER_MAP.put(type, indicator);
        }
        return avLoadingIndicatorView;
    }

    // 根据 type 生成 Indicator
    // type 为 LoaderStyle 枚举中的值（PacmanIndicator）
    // 比如 name = "PacmanIndicator";

    private static Indicator geteIndicator(String name) {
        if (name == null || "".equals(name)){
            return null;
        }

        // 通过反射的方式 用类名去创建一个实例
        // com.wang.avi.indicators.PacmanIndicator

        // 获取AVLoadingIndicatorView的包名(com.wang.avi)
        String packageName = AVLoadingIndicatorView.class.getPackage().getName();

        // 在com.wang.avi包名后面再添加一级子包indicators
        // PacmanIndicator等类是在indicators子包下的
        // 字符串拼接的方式
        // 1。
        String className = new StringBuilder()
                .append(packageName)
                .append(".")
                .append("indicators")
                .append(".")
                .append(name)
                .toString();
        //2。
//        packageName = packageName + "." + "indicators" + "." + name;
        try {
            Class<?> aClass = Class.forName(packageName);
            return (Indicator) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
