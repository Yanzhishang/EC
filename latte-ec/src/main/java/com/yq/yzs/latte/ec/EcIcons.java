package com.yq.yzs.latte.ec;

import com.joanzapata.iconify.Icon;

/**
 * @作者 Yzs
 * 2017/10/9.
 * 描述:
 */

public enum EcIcons implements Icon {
    iconfont_ali_pay('\ue67c'),
    iconfont_ali_progress('\ue678'),
    iconfont_ali_RichScan('\ue67d')
    ;

    char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
