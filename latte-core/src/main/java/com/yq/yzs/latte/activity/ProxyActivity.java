package com.yq.yzs.latte.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.yq.yzs.latte.core.R;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述: 在这里实现 onCreate() onDestroy() 等生命周期的方法
 * ,这样子类就不需要把经历放在生命周期上
 */

public abstract class ProxyActivity extends SupportActivity {
    private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DELEGATE 初始化
        DELEGATE.onCreate(savedInstanceState);
        //初始化视图
        initContainer(savedInstanceState);
    }

    //初始化视图
    private void initContainer(Bundle savedInstanceState) {
        //
        ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        // activity 的 onCreate() 最终目的是 setContentView()
        setContentView(container);
        // 在activity 中添加 delegate(fragmrnt)
        if (savedInstanceState == null) {
            DELEGATE.loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        DELEGATE.onDestroy();
        super.onDestroy();
        System.gc();


    }

    //提供子类需要实现的方法,目的是让  进行绑定
    public abstract LatteDelegate setRootDelegate();
}
