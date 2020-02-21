package com.dengyun.baselibrary.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * @Title 判断嵌套的自定义webview
 * @Desc: 解决webview和刷新框架的下拉冲突的问题
 *         原因：web页面设置高度为100%，web滑动就是内部滑动，webview获取getScrollY就一直为0；
 *         触摸的时候先给1像素的滑动（webview的getScrollY就不为0，不会触发下拉刷新了）
 * @Author: zhoubo
 * @CreateDate: 2019-12-26 16:02
 */
public class JudgeNestedWebview extends WebView {
    public JudgeNestedWebview(Context context) {
        super(context);
    }

    public JudgeNestedWebview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public JudgeNestedWebview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public JudgeNestedWebview(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public JudgeNestedWebview(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(this.getScrollY() <= 0){
                    this.scrollTo(0,1);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /*@Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (iWebViewScrollListener != null && t == 0) {
            iWebViewScrollListener .onTop();
        } else if (mIWebViewScroll != null && t != 0) {
            iWebViewScrollListener .notOnTop();
        }
    }*/
}
