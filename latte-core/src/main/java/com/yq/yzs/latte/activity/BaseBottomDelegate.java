package com.yq.yzs.latte.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.yq.yzs.latte.core.R;
import com.yq.yzs.latte.core.R2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述:
 */

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat bottomBar = null;

    /**
     * 所有的tabBean
     */
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();

    /**
     * 拿到所有的 delegate
     */
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();

    /**
     * BottomTabBean 与 BottomItemDelegate 的集合
     */
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    /**
     * 当前的 delegate 是第几个 delegate
     */
    private int currentDelegate = 0;

    /**
     * 第一个展示哪个
     */
    private int indexDelegate = 0;

    /**
     * 选中的颜色
     */
    private int clickedColor = Color.RED;

    /**
     * 让子类设置所有项(如果不设 则使用这里定义的ITEMS 这里一个item也没有)
     *
     * @param builder
     * @return
     */
    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    /**
     * 让子类设置默认的选中项(如果不设 默认是0)
     *
     * @param
     */
    public abstract int setIndexDelegate();

    /**
     * 让子类设置选中项的颜色(如果不设，默认是红色)
     *
     * @return
     */
    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置子类指定的默认选中项
        indexDelegate = setIndexDelegate();
        // 设置子类指定的选中项颜色
        if (setClickedColor() != 0) {
            clickedColor = setClickedColor();
        }
        // 设置子类指定的所有项
        ItemBuilder builder = ItemBuilder.builder();
        LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            BottomTabBean key = item.getKey();
            BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
        super.onBindView(savedInstanceState, rootView);
        int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, bottomBar);
            RelativeLayout item = (RelativeLayout) bottomBar.getChildAt(i);
            // 设置每个item的点击事件
            item.setTag(i); // 把当前项的id保存
            item.setOnClickListener(this);
            // 在RelativeLayout中找到字体图标和文本 并显示内容和颜色
            IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            BottomTabBean bean = TAB_BEANS.get(i); // 找到TAB_BEAN
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i == indexDelegate) {
                itemIcon.setTextColor(clickedColor);
                itemTitle.setTextColor(clickedColor);
            }
        }
        // 加载所有的内容 把n个bogttom_item_icon_text_layout布局都加载到delegate_bottom的LinearLayout中
        SupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new SupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, indexDelegate, delegateArray);
    }

    /**
     * 点击其中一项
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        // 需要重置颜色
        for (int i = 0; i < bottomBar.getChildCount(); i++) {
            RelativeLayout item = (RelativeLayout) bottomBar.getChildAt(i);
            // 在RelativeLayout中找到字体图标和文本 并重置颜色
            IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemIcon.setTextColor(Color.GRAY);
            itemTitle.setTextColor(Color.GRAY);
        }
        // 点击的那一项显示选中的颜色
        int tag = (int) view.getTag(); // 把该项的id取出
        RelativeLayout item = (RelativeLayout) view;
        // 在RelativeLayout中找到字体图标和文本 并显示指定的颜色
        IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemIcon.setTextColor(clickedColor);
        itemTitle.setTextColor(clickedColor);
        // 重新显示所有的内容(显示新的Fragment 隐藏旧的Fragment)
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(currentDelegate));
        currentDelegate = tag;

    }
}
