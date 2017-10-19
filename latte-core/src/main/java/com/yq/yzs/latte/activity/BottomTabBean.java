package com.yq.yzs.latte.activity;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public class BottomTabBean {
    private CharSequence icon; // 字体图标
    private CharSequence title; // 文本

    public BottomTabBean() {
    }

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.icon = icon;
        this.title = title;
    }

    public CharSequence getIcon() {
        return icon;
    }

    public void setIcon(CharSequence icon) {
        this.icon = icon;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }
}
