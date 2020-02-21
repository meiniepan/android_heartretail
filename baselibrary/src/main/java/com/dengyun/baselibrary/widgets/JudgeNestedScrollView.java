package com.dengyun.baselibrary.widgets;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @类名称: 判断是否竖直滑动拦截
 * @类描述: 判断嵌套滑动是否拦截、焦点问题的NestedScrollView
 * @创建人：zhoubo
 * @创建时间：2018/11/9 16:28
 * @备注：
 */
public class JudgeNestedScrollView extends NestedScrollView {
    private int scaledTouchSlop;
    private float xDistance, yDistance, xLast, yLast;
    private boolean isInterceptChild = true;

    public JudgeNestedScrollView(Context context) {
        super(context, null);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //判断滑动的最小距离
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                return !(xDistance >= yDistance || yDistance < scaledTouchSlop) && isInterceptChild;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 是否拦截子view，由此scrollview处理竖直滑动
     * 默认true拦截：除非 x方向滑动较大 或者 y方向滑动距离过小
     *
     * @param isInterceptChild true 拦截 false 不拦截
     */
    public void isInterceptChildScroll(boolean isInterceptChild){
        this.isInterceptChild = isInterceptChild;
    }

}
