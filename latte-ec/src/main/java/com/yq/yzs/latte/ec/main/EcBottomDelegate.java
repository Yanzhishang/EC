package com.yq.yzs.latte.ec.main;

import android.graphics.Color;

import com.yq.yzs.latte.activity.BaseBottomDelegate;
import com.yq.yzs.latte.activity.BottomItemDelegate;
import com.yq.yzs.latte.activity.BottomTabBean;
import com.yq.yzs.latte.activity.ItemBuilder;
import com.yq.yzs.latte.ec.main.discover.DiscoverDelegate;
import com.yq.yzs.latte.ec.main.home.HomeDelegate;
import com.yq.yzs.latte.ec.main.index.IndexDelegate;
import com.yq.yzs.latte.ec.main.personal.PersonalDelegate;
import com.yq.yzs.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public class EcBottomDelegate extends BaseBottomDelegate {
    /**
     * 依次添加 主页——分类——发现——购物车——我的 这些项
     */
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "首页"), new HomeDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-home}", "发现"), new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-home}", "购物车"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-home}", "我的"), new PersonalDelegate());
        return items;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.RED;
    }

}
