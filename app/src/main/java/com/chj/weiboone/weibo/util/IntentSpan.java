package com.chj.weiboone.weibo.util;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 文本超链接
 *
 * Created by C on 2016/5/7.
 */
public class IntentSpan extends ClickableSpan {

    private final View.OnClickListener listener;
    public IntentSpan(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }
}
