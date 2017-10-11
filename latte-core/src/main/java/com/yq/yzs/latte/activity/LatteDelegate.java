package com.yq.yzs.latte.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @作者 Yzs
 * 2017/10/10.
 * 描述:
 */

public abstract class LatteDelegate  extends PermissionCheckerDelegate {
    @Override
    public Object setLayout() {
        return null;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

    }
}
