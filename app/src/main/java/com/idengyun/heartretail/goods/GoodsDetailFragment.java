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

    private GoodsInfoFragment goodsInfoFragment;
    private GoodsEvaluateFragment goodsEvaluateFragment;
    private GoodsSpecFragment goodsSpecFragment;
    private GoodsServiceFragment goodsServiceFragment;

    private int dimension;

    /* 标题 */
    private View iv_goods_detail_back;
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

    /**
     * 周一 心项目-商品详情页 商品规格页 用户评价页 1.UI编写 2.api调试
     * 周二 心项目-确认订单页 1.UI编写 2.api调试
     * 周三 心项目-门店列表页 门店详情页 1.UI编写 2.api调试
     * 周四 心项目-登录页 注册页 找回密码页 个人资料页 1.UI编写 2.api调试
     * 周五 心项目-各种协议页 1.UI编写 2.api调试
     */
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
        goodsInfoFragment = new GoodsInfoFragment();
        goodsEvaluateFragment = new GoodsEvaluateFragment();
        goodsSpecFragment = new GoodsSpecFragment();
        goodsServiceFragment = new GoodsServiceFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.layout_goods_detail_content_container, goodsInfoFragment)
                .add(R.id.layout_goods_detail_content_container, goodsEvaluateFragment)
                .add(R.id.layout_goods_detail_dialog_container, goodsSpecFragment)
                .add(R.id.layout_goods_detail_dialog_container, goodsServiceFragment)
                .hide(goodsEvaluateFragment)
                .hide(goodsSpecFragment)
                .hide(goodsServiceFragment)
                .commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_goods) {
            iv_goods_1.setVisibility(View.VISIBLE);
            iv_goods_2.setVisibility(View.VISIBLE);
            iv_evaluation_1.setVisibility(View.INVISIBLE);
            iv_evaluation_2.setVisibility(View.INVISIBLE);
            if (goodsInfoFragment != null && goodsEvaluateFragment != null) {
                getChildFragmentManager()
                        .beginTransaction()
                        .show(goodsInfoFragment)
                        .hide(goodsEvaluateFragment)
                        .commit();
            }
        } else if (checkedId == R.id.rb_evaluation) {
            iv_goods_1.setVisibility(View.INVISIBLE);
            iv_goods_2.setVisibility(View.INVISIBLE);
            iv_evaluation_1.setVisibility(View.VISIBLE);
            iv_evaluation_2.setVisibility(View.VISIBLE);
            if (goodsInfoFragment != null && goodsEvaluateFragment != null) {
                getChildFragmentManager()
                        .beginTransaction()
                        .show(goodsEvaluateFragment)
                        .hide(goodsInfoFragment)
                        .commit();
            }
        }
    }

    public void showGoodsSpecFragment(boolean isShow) {
        if (isShow) {
            getChildFragmentManager()
                    .beginTransaction()
                    .show(goodsSpecFragment)
                    .commit();
        } else {
            getChildFragmentManager()
                    .beginTransaction()
                    .hide(goodsSpecFragment)
                    .commit();
        }
    }

    public void showGoodsServiceFragment(boolean isShow) {
        if (isShow) {
            getChildFragmentManager()
                    .beginTransaction()
                    .show(goodsServiceFragment)
                    .commit();
        } else {
            getChildFragmentManager()
                    .beginTransaction()
                    .hide(goodsServiceFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        if (iv_goods_detail_back == v || tv_goods_detail_back == v) {
            if (getActivity() != null) getActivity().onBackPressed();
        } else if (layout_goods_detail_customer_service == v) {

        } else if (layout_goods_detail_buy_now == v) {

        }
    }


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        // System.out.println("scrollY=" + scrollY + "\t" + "oldScrollY=" + oldScrollY);

        if (scrollY < dimension) {
            iv_goods_detail_back.setVisibility(View.VISIBLE);
            layout_main_title.setVisibility(View.GONE);
        } else {
            iv_goods_detail_back.setVisibility(View.GONE);
            layout_main_title.setVisibility(View.VISIBLE);
        }


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

    }
}

