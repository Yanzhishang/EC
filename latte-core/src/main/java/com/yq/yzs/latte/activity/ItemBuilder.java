package com.yq.yzs.latte.activity;

import java.util.LinkedHashMap;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:建造BottomTabBean(图标+文本) 和 BottomItemDelegate（内容）
 */

public class ItemBuilder {
    /**
     * 使用LinkedHashMap保证顺序是 主页——分类——发现——购物车——我的
     */
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    /**
     * 建造该对象
     */
    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    /**
     * 添加一个项目
     *
     * @param bean
     * @param delegate
     * @return
     */
    public ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    /**
     * 添加多个项目
     */
    public ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    /**
     * 获取所有的项目
     *
     * @return
     */
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }


}
