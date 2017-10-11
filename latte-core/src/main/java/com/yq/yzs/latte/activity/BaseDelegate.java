package com.yq.yzs.latte.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述: delegate 的基类 ，定义子类需要实现的方法
 * 实现 onCreateView() 和 onDestroyView() 等生命周期
 */

public abstract class BaseDelegate extends SwipeBackFragment {
    //让子类实现 setLayout() 。规定要么返回布局 id 要么返回 view
    public abstract Object setLayout();

    //让子类实现 onBindView()
    public abstract void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView);

    private Unbinder mUnbinder = null;

    /**
     * 创建视图 并返回
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        // 分几种不同情况，分别按不同方式创建 rootView
        // 1、setLayout() 返回布局 id
        // 2、setLayout() 返回 view
        if (setLayout() instanceof Integer) {
            // 1、setLayout() 返回布局 id
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            // 2、setLayout() 返回 view
            rootView = (View) setLayout();
        } else {
            // 如果返回其他就 抛出异常
            throw new ClassCastException("type of setLayout() must be int or View !!! ");
        }

        // 添加 ButterKnife 的使用
        mUnbinder = ButterKnife.bind(this, rootView);

        onBindView(savedInstanceState, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
