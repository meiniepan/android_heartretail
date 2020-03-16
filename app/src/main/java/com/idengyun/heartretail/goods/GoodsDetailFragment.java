package com.idengyun.heartretail.goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.RadioGroup;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.activitys.ConfirmOrderActivity;

/**
 * 商品详情-控制器
 *
 * @author aLang
 */
public final class GoodsDetailFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, NestedScrollView.OnScrollChangeListener {

    private int dimension;

    /* 标题 */
    private View iv_goods_detail_back;
    private View layout_second_title;
    private View layout_main_title;
    private View tv_goods_detail_back;
    private View iv_goods_1;
    private View iv_goods_2;
    private View iv_evaluation_1;
    private View iv_evaluation_2;
    private RadioGroup radio_group;
    private View rb_goods;
    private View rb_evaluation;

    private View layout_goods_detail_customer_service;
    private View layout_goods_detail_buy_now;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_goods) {
            updateGoodsInfoUI();
        } else if (checkedId == R.id.rb_evaluation) {
            updateGoodsEvaluationUI();
        }
    }

    @Override
    public void onClick(View v) {
        if (iv_goods_detail_back == v || tv_goods_detail_back == v) {
            if (getActivity() != null) getActivity().onBackPressed();
        } else if (layout_goods_detail_customer_service == v) {

        } else if (layout_goods_detail_buy_now == v) {
            GoodsSPUFragment goodsSpecFragment = (GoodsSPUFragment) HRActivity.findFragmentByTag(getActivity(), GoodsSPUFragment.class.getName());
            if (goodsSpecFragment == null || !goodsSpecFragment.isCanBuy()) {
                ToastUtils.showShort("去选择规格");
                return;
            }
            ConfirmOrderActivity.start(getContext(), null);
        }
    }

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
    }

    public void checkGoodsEvaluation() {
        radio_group.check(R.id.rb_evaluation);
    }

    private void updateGoodsInfoUI() {
        iv_goods_1.setVisibility(View.VISIBLE);
        iv_goods_2.setVisibility(View.VISIBLE);
        iv_evaluation_1.setVisibility(View.INVISIBLE);
        iv_evaluation_2.setVisibility(View.INVISIBLE);
        HRActivity.showFragment(getActivity(), GoodsInfoFragment.class.getName());
        HRActivity.hideFragment(getActivity(), GoodsEvaluateFragment.class.getName());
    }

    private void updateGoodsEvaluationUI() {
        iv_goods_1.setVisibility(View.INVISIBLE);
        iv_goods_2.setVisibility(View.INVISIBLE);
        iv_evaluation_1.setVisibility(View.VISIBLE);
        iv_evaluation_2.setVisibility(View.VISIBLE);
        HRActivity.showFragment(getActivity(), GoodsEvaluateFragment.class.getName());
        HRActivity.hideFragment(getActivity(), GoodsInfoFragment.class.getName());
    }

    private void init() {
        iv_goods_detail_back.setOnClickListener(this);
        tv_goods_detail_back.setOnClickListener(this);
        radio_group.setOnCheckedChangeListener(this);

        layout_goods_detail_customer_service.setOnClickListener(this);
        layout_goods_detail_buy_now.setOnClickListener(this);

        radio_group.clearCheck();
        radio_group.check(R.id.rb_goods);

        iv_goods_detail_back.setVisibility(View.VISIBLE);
        layout_main_title.setVisibility(View.GONE);
        dimension = SizeUtils.dp2px(64f);

        HRActivity.showFragment(getActivity(), GoodsInfoFragment.class.getName());
        HRActivity.hideFragment(
                getActivity(),
                GoodsEvaluateFragment.class.getName(),
                GoodsSPUFragment.class.getName(),
                GoodsServiceFragment.class.getName()
        );

    }

    private void findViewById(@NonNull View view) {
        layout_second_title = view.findViewById(R.id.layout_second_title);
        iv_goods_detail_back = view.findViewById(R.id.iv_goods_detail_back);
        layout_main_title = view.findViewById(R.id.layout_main_title);
        tv_goods_detail_back = view.findViewById(R.id.tv_goods_detail_back);
        iv_goods_1 = view.findViewById(R.id.iv_goods_1);
        iv_goods_2 = view.findViewById(R.id.iv_goods_2);
        iv_evaluation_1 = view.findViewById(R.id.iv_evaluation_1);
        iv_evaluation_2 = view.findViewById(R.id.iv_evaluation_2);
        radio_group = view.findViewById(R.id.radio_group);
        rb_goods = view.findViewById(R.id.rb_goods);
        rb_evaluation = view.findViewById(R.id.rb_evaluation);

        layout_goods_detail_customer_service = view.findViewById(R.id.layout_goods_detail_customer_service);
        layout_goods_detail_buy_now = view.findViewById(R.id.layout_goods_detail_buy_now);
    }
}

