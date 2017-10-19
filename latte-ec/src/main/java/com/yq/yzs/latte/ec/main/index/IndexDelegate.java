package com.yq.yzs.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yq.yzs.latte.activity.BottomItemDelegate;
import com.yq.yzs.latte.ec.R;

/**
 * @author Yzs
 * @date 2017/10/17.
 * 描述: 购物车
 */

public class IndexDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

    }
}

