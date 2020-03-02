package com.idengyun.heartretail.goods;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.zhoubo07.bannerlib.ConvenientBanner;
import com.zhoubo07.bannerlib.banner.SimpleImageBannerBean;
import com.zhoubo07.bannerlib.banner.SimpleImageHolder;
import com.zhoubo07.bannerlib.holder.CBViewHolderCreator;
import com.zhoubo07.bannerlib.holder.Holder;
import com.zhoubo07.bannerlib.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * 商品详情
 *
 * @author aLang
 */
public final class GoodsFragment extends BaseFragment {


    private NestedScrollView nested_scroll_view;

    private ViewGroup layout_banner_container;
    private View layout_disqualification;
    private View tv_price;
    private View tv_price_small;
    private View tv_sold;
    private View tv_goods_info;
    private View layout_goods_specification;
    private View tv_goods_specification;
    private View layout_service_note;
    private View tv_service_note;

    private View tv_favorable_rate;
    private View iv_user_avatar;
    private View tv_user_name;
    private View tv_user_level;
    private View rb_user_rating;
    private View tv_user_evaluation_date;
    private View tv_user_likes;
    private View tv_user_evaluation_content;

    private View iv_goods_detail;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
        GoodsBanner.setupBanner(layout_banner_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final float dimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64f, getResources().getDisplayMetrics());
        /*nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println(scrollX + " " + scrollY);
                Log.e("aaaa", "scrollX=" + scrollY);
            }
        });*/
        nested_scroll_view.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) getParentFragment());
    }

    private void findViewById(@NonNull View view) {
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);
        layout_banner_container = view.findViewById(R.id.layout_banner_container);
        layout_disqualification = view.findViewById(R.id.layout_disqualification);
        tv_price = view.findViewById(R.id.tv_price);
        tv_price_small = view.findViewById(R.id.tv_price_small);
        tv_sold = view.findViewById(R.id.tv_sold);
        tv_goods_info = view.findViewById(R.id.tv_goods_info);
        layout_goods_specification = view.findViewById(R.id.layout_goods_specification);
        tv_goods_specification = view.findViewById(R.id.tv_goods_specification);
        layout_service_note = view.findViewById(R.id.layout_service_note);
        tv_service_note = view.findViewById(R.id.tv_service_note);
        tv_favorable_rate = view.findViewById(R.id.tv_favorable_rate);
        iv_user_avatar = view.findViewById(R.id.iv_user_avatar);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_user_level = view.findViewById(R.id.tv_user_level);
        rb_user_rating = view.findViewById(R.id.rb_user_rating);
        tv_user_evaluation_date = view.findViewById(R.id.tv_user_evaluation_date);
        tv_user_likes = view.findViewById(R.id.tv_user_likes);
        tv_user_evaluation_content = view.findViewById(R.id.tv_user_evaluation_content);
        iv_goods_detail = view.findViewById(R.id.iv_goods_detail);
    }

    static class GoodsBanner implements CBViewHolderCreator, OnItemClickListener {
        static void setupBanner(ViewGroup parent) {
            parent.addView(new GoodsBanner().createBanner(parent.getContext()));
        }

        private GoodsBanner() {
        }

        private ConvenientBanner<SimpleImageBannerBean> createBanner(@NonNull Context context) {
            ConvenientBanner<SimpleImageBannerBean> banner = new ConvenientBanner<>(context);
            ArrayList<SimpleImageBannerBean> imageList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                SimpleImageBannerBean e = new SimpleImageBannerBean();
                e.setBannerImageUrl("");
                e.setBannerImageDefult(R.drawable.image_placeholder);
                imageList.add(e);
            }
            banner.setShowLeftCardWidth(0);

            banner
                    .setPages(this, imageList)
                    .setPageIndicator(new int[]{R.drawable.shape_banner_indicator_gray, R.drawable.shape_banner_indicator_orange})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setOnItemClickListener(this);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            banner.setLayoutParams(params);

            banner.setBackgroundColor(Color.TRANSPARENT);
            return banner;
        }

        @Override
        public Holder createHolder(View itemView) {
            return new SimpleImageHolder(itemView);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fragment_goods_banner_item;
        }

        @Override
        public void onItemClick(int position) {

        }
    }
}
