package com.idengyun.heartretail.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可以设置禁止用户通过数据页面左右翻转
 *
 * @author aLang
 */
public final class NoFlipViewPager extends ViewPager {
    // true 支持翻页，default = false
    private Boolean isFlip = false;

    public NoFlipViewPager(@NonNull Context context) {
        super(context);
    }

    public NoFlipViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isFlip && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isFlip && super.onTouchEvent(ev);
    }
}
