package com.idengyun.heartretail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.OverScroller;

import com.dengyun.baselibrary.base.fragment.BaseFragment;

import java.lang.reflect.Field;

/**
 * 商品详情
 *
 * @author aLang
 */
public final class GoodsDetailsFragment extends BaseFragment {
    private int homeHeight;
    private int middleHeight;
    private int endHeight;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_details;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NestedScrollView nested_scroll_view = view.findViewById(R.id.nested_scroll_view);
        View layout_home = view.findViewById(R.id.layout_home);
        View layout_middle = view.findViewById(R.id.layout_middle);
        View layout_end = view.findViewById(R.id.layout_end);
        View btn_shortcut_home = view.findViewById(R.id.btn_shortcut_home);
        View btn_shortcut_end = view.findViewById(R.id.btn_shortcut_end);
        View btn_shortcut_m = view.findViewById(R.id.btn_shortcut_m);

        nested_scroll_view.setSmoothScrollingEnabled(true);
        try {
            Field mScroller = nested_scroll_view.getClass().getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            OverScroller scroller = new OverScroller(nested_scroll_view.getContext()) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, 1000);
                }
            };
            mScroller.set(nested_scroll_view, scroller);
        } catch (Throwable e) {
            e.printStackTrace();
        }


        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (v == layout_home) homeHeight = bottom - top;
                if (v == layout_middle) middleHeight = bottom - top;
                if (v == layout_end) endHeight = bottom - top;
            }
        };
        layout_home.addOnLayoutChangeListener(onLayoutChangeListener);
        layout_middle.addOnLayoutChangeListener(onLayoutChangeListener);
        layout_end.addOnLayoutChangeListener(onLayoutChangeListener);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_shortcut_home) nested_scroll_view.smoothScrollTo(0, 0);
                if (v == btn_shortcut_m) nested_scroll_view.smoothScrollTo(0, homeHeight);
                if (v == btn_shortcut_end)
                    nested_scroll_view.smoothScrollTo(0, homeHeight + middleHeight);
            }
        };
        btn_shortcut_home.setOnClickListener(onClickListener);
        btn_shortcut_end.setOnClickListener(onClickListener);
        btn_shortcut_m.setOnClickListener(onClickListener);
    }

}

