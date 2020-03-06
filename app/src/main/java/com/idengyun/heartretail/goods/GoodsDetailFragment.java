package com.idengyun.heartretail.goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.RadioGroup;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.BGoodsDetail;

import java.util.List;

/**
 * 商品详情-控制器
 *
 * @author aLang
 */
public final class GoodsDetailFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, NestedScrollView.OnScrollChangeListener {
    /*private int homeHeight;
    private int middleHeight;
    private int endHeight;*/
    private GoodsInfoFragment goodsInfoFragment;
    private GoodsEvaluateFragment evaluationFragment;

    private View layout_second_title;
    private View iv_back;

    private View layout_main_title;
    private View tv_back;
    private View iv_goods_1;
    private View iv_goods_2;
    private View iv_evaluation_1;
    private View iv_evaluation_2;
    private RadioGroup radio_group;
    private View rb_goods;
    private View rb_evaluation;

    private View layout_customer_service;
    private View layout_buy_now;

    /**
     * 周一 心项目-商品详情页 商品规格页 用户评价页 1.UI编写 2.api调试
     * 周二 心项目-确认订单页 1.UI编写 2.api调试
     * 周三 心项目-门店列表页 门店详情页 1.UI编写 2.api调试
     * 周四 心项目-登录页 注册页 找回密码页 个人资料页 1.UI编写 2.api调试
     * 周五 心项目-各种协议页 1.UI编写 2.api调试
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_details;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        goodsInfoFragment = new GoodsInfoFragment();
        evaluationFragment = new GoodsEvaluateFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.layout_container, goodsInfoFragment)
                .add(R.id.layout_container, evaluationFragment)
                //.hide(goodsFragment)
                .hide(evaluationFragment)
                .commit();
//        RatingBar r;
//        ProgressBar progressBar = new ProgressBar(getContext());
//        System.out.println(progressBar.getMax() + "=max    min=" + progressBar.getMin());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_goods) {
            iv_goods_1.setVisibility(View.VISIBLE);
            iv_goods_2.setVisibility(View.VISIBLE);
            iv_evaluation_1.setVisibility(View.INVISIBLE);
            iv_evaluation_2.setVisibility(View.INVISIBLE);
            if (goodsInfoFragment != null && evaluationFragment != null) {
                getChildFragmentManager()
                        .beginTransaction()
                        .show(goodsInfoFragment)
                        .hide(evaluationFragment)
                        .commit();
            }
        } else if (checkedId == R.id.rb_evaluation) {
            iv_goods_1.setVisibility(View.INVISIBLE);
            iv_goods_2.setVisibility(View.INVISIBLE);
            iv_evaluation_1.setVisibility(View.VISIBLE);
            iv_evaluation_2.setVisibility(View.VISIBLE);
            if (goodsInfoFragment != null && evaluationFragment != null) {
                getChildFragmentManager()
                        .beginTransaction()
                        .show(evaluationFragment)
                        .hide(goodsInfoFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back || v == tv_back) {
            if (getActivity() != null) getActivity().onBackPressed();
        }
    }

    int dimension;

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        // System.out.println("scrollY=" + scrollY + "\t" + "oldScrollY=" + oldScrollY);

        if (scrollY < dimension) {
            layout_second_title.setVisibility(View.VISIBLE);
            layout_main_title.setVisibility(View.GONE);
        } else {
            layout_second_title.setVisibility(View.GONE);
            layout_main_title.setVisibility(View.VISIBLE);
        }

        /* 向上滑动手势 */
        /*if (scrollY > oldScrollY) {
            if (scrollY <= dimension) {
                layout_second_title.setAlpha(1f - 1f * scrollY / dimension);
                layout_main_title.setAlpha(1f * scrollY / dimension);
            } else {
                layout_main_title.setAlpha(1f);
            }
        }*/

        /* 向下滑动手势 */
        /*if (scrollY < oldScrollY) {
            if (scrollY <= dimension) {
                layout_second_title.setAlpha(1f - 1f * scrollY / dimension);
                layout_main_title.setAlpha(1f * scrollY / dimension);
            } else {
                layout_main_title.setAlpha(1f);
            }
        }*/
    }

    private String getDefaultSpecText(List<BGoodsDetail.Data.GoodsSpec> goodsSpecList, String skuCombinationCode) {
        StringBuilder sb = new StringBuilder();
        for (BGoodsDetail.Data.GoodsSpec goodsSpec : goodsSpecList) {
            for (BGoodsDetail.Data.GoodsSpec.SkuValue skuValue : goodsSpec.skuValueList) {
                String specItemId = String.valueOf(skuValue.specItemId);
                String specItemName = skuValue.specItemName;
                if (skuCombinationCode.contains(specItemId)) {
                    sb.append(specItemName).append("/");
                }
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private void findViewById(@NonNull View view) {
        layout_second_title = view.findViewById(R.id.layout_second_title);
        iv_back = view.findViewById(R.id.iv_back);

        layout_main_title = view.findViewById(R.id.layout_main_title);
        tv_back = view.findViewById(R.id.tv_back);
        iv_goods_1 = view.findViewById(R.id.iv_goods_1);
        iv_goods_2 = view.findViewById(R.id.iv_goods_2);
        iv_evaluation_1 = view.findViewById(R.id.iv_evaluation_1);
        iv_evaluation_2 = view.findViewById(R.id.iv_evaluation_2);
        radio_group = view.findViewById(R.id.radio_group);
        rb_goods = view.findViewById(R.id.rb_goods);
        rb_evaluation = view.findViewById(R.id.rb_evaluation);

        layout_customer_service = view.findViewById(R.id.layout_customer_service);
        layout_buy_now = view.findViewById(R.id.layout_buy_now);

        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        radio_group.setOnCheckedChangeListener(this);

        radio_group.clearCheck();
        radio_group.check(R.id.rb_goods);

        layout_second_title.setVisibility(View.VISIBLE);
        layout_main_title.setVisibility(View.GONE);

        // layout_second_title.setVisibility(View.VISIBLE);
        // layout_main_title.setVisibility(View.VISIBLE);

        // layout_second_title.setAlpha(1f);
        // layout_main_title.setAlpha(0f);
        ;
        dimension = SizeUtils.dp2px(64f);

    }
}
/*NestedScrollView nested_scroll_view = view.findViewById(R.id.nested_scroll_view);
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
        btn_shortcut_m.setOnClickListener(onClickListener);*/
