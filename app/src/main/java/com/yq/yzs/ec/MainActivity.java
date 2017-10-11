package com.yq.yzs.ec;

import com.yq.yzs.latte.activity.LatteDelegate;
import com.yq.yzs.latte.activity.ProxyActivity;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

}
