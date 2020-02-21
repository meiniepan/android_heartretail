package com.dengyun.baselibrary.utils.webview;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 全屏容器界面
 * Created by seven on 2017/9/4.
 */

public class FullscreenHolder extends FrameLayout {
    public FullscreenHolder(Context ctx) {
        super(ctx);
        setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        return true;
    }
}
