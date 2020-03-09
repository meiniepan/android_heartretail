package com.idengyun.heartretail.goods;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.GoodsDetailBean;
import com.idengyun.heartretail.model.response.GoodsEvaluateBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;
import com.zhoubo07.bannerlib.ConvenientBanner;
import com.zhoubo07.bannerlib.banner.SimpleImageBannerBean;
import com.zhoubo07.bannerlib.banner.SimpleImageHolder;
import com.zhoubo07.bannerlib.holder.CBViewHolderCreator;
import com.zhoubo07.bannerlib.holder.Holder;
import com.zhoubo07.bannerlib.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-首页
 *
 * @author aLang
 */
public final class GoodsInfoFragment extends BaseFragment implements View.OnClickListener {

    /* 滚动器 */
    private NestedScrollView nested_scroll_view;

    /* 商品banner 商品资格 商品价格 商品已售 商品信息 */
    private ViewGroup layout_goods_banner_container;
    private View layout_goods_disqualification;
    private TextView tv_goods_price;
    private TextView tv_goods_price_small;
    private TextView tv_goods_sold;
    private TextView tv_goods_info;

    /* 商品规格 商品服务 */
    private View layout_goods_spec;
    private TextView tv_goods_spec;
    private View layout_goods_service;
    private TextView tv_goods_service;

    /* 用户评价 */
    private TextView tv_user_favorable_rate;
    private ImageView iv_user_avatar;
    private TextView tv_user_name;
    private TextView tv_user_level;
    private RatingBar rb_user_rating;
    private TextView tv_user_evaluation_date;
    private TextView tv_user_likes;
    private TextView tv_user_evaluation_content;

    /* 商品详情 */
    private ImageView iv_goods_detail;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_info;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NestedScrollView.OnScrollChangeListener listener = (NestedScrollView.OnScrollChangeListener) getParentFragment();
        nested_scroll_view.setOnScrollChangeListener(listener);
        requestGoodsDetailAPI();
        requestEvaluateAPI();
    }

    @Override
    public void onClick(View v) {
        if (layout_goods_spec == v) {
            if (getParentFragment() instanceof GoodsDetailFragment) {
                ((GoodsDetailFragment) getParentFragment()).showGoodsSpecFragment(true);
            }
        } else if (layout_goods_service == v) {
            if (getParentFragment() instanceof GoodsDetailFragment) {
                ((GoodsDetailFragment) getParentFragment()).showGoodsServiceFragment(true);
            }
        }
    }

    private void requestEvaluateAPI() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.evaluationList())
                .fragment(this)
                .clazz(GoodsEvaluateBean.class)
                .params("goodsId", "123")
                .params("page", 1)
                .params("pageSize", 1)
                .build();
        NetApi.<GoodsEvaluateBean>getData(netOption, new JsonCallback<GoodsEvaluateBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsEvaluateBean> response) {
                updateUI(response.body().data);
            }
        });
    }

    @MainThread
    private void updateUI(GoodsEvaluateBean.Data data) {
        String praiseRate = data.praiseRate;
        tv_user_favorable_rate.setText(praiseRate);

        List<GoodsEvaluateBean.Data.Evaluation> evaluationList = data.evaluationList;
        if (evaluationList.isEmpty()) return;

        GoodsEvaluateBean.Data.Evaluation evaluation = evaluationList.get(0);
        String userImgUrl = evaluation.userImgUrl;
        String userName = evaluation.userName;
        int userLevel = evaluation.userLevel;
        String commentTime = evaluation.commentTime;
        int commentStar = evaluation.commentStar;
        String contents = evaluation.contents;
        String orderId = evaluation.orderId;
        int isShow = evaluation.isShow;

        ImageApi.displayImage(iv_user_avatar.getContext(), iv_user_avatar, userImgUrl);
        tv_user_name.setText(userName);
        tv_user_level.setText("LV" + userLevel);
        tv_user_evaluation_date.setText(commentTime);
        rb_user_rating.setNumStars(commentStar);
        tv_user_evaluation_content.setText(contents);
    }

    /* 请求商品详情 */
    private void requestGoodsDetailAPI() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.goodsDetail())
                .fragment(this)
                .clazz(GoodsDetailBean.class)
                .params("goodsId", "123")
                .params("userId", HRUser.getId())
                .params("goodsType", 0)// 0零售1批发
                .build();
        NetApi.<GoodsDetailBean>getData(netOption, new JsonCallback<GoodsDetailBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsDetailBean> response) {
                updateUI(response.body().data);
            }
        });
    }

    /* 更新页面信息 */
    @MainThread
    private void updateUI(GoodsDetailBean.Data data) {
        List<GoodsDetailBean.Data.Banner> imageList = data.imageList;
        List<GoodsDetailBean.Data.Protocol> protocolList = data.protocolList;
        List<GoodsDetailBean.Data.GoodsSpec> goodsSpecList = data.goodsSpecList;
        List<GoodsDetailBean.Data.GoodsSku> goodsSkuList = data.goodsSkuList;
        List<String> goodsDetailList = data.goodsDetailList;

        ArrayList<String> urls = new ArrayList<>();
        for (GoodsDetailBean.Data.Banner banner : imageList) {
            urls.add(banner.imgUrl);
        }
        GoodsBanner.setupBanner(layout_goods_banner_container, urls);

        String retailPrice = data.retailPrice;
        String wholesalePrice = data.wholesalePrice;
        int goodsType = data.goodsType;
        int soldCounts = data.soldCounts;
        String goodsTitle = data.goodsTitle;
        tv_goods_price.setText(goodsType == 0 ? retailPrice : wholesalePrice);
        tv_goods_price_small.setText(goodsType == 0 ? retailPrice : wholesalePrice);
        tv_goods_sold.setText("已售" + soldCounts + "件");
        tv_goods_info.setText(goodsTitle);

        tv_goods_spec.setText(null);
        tv_goods_service.setText(null);

        iv_goods_detail.setImageDrawable(null);
    }

    private void findViewById(@NonNull View view) {
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);

        layout_goods_banner_container = view.findViewById(R.id.layout_goods_banner_container);
        layout_goods_disqualification = view.findViewById(R.id.layout_goods_disqualification);
        tv_goods_price = view.findViewById(R.id.tv_goods_price);
        tv_goods_price_small = view.findViewById(R.id.tv_goods_price_small);
        tv_goods_sold = view.findViewById(R.id.tv_goods_sold);
        tv_goods_info = view.findViewById(R.id.tv_goods_info);

        layout_goods_service = view.findViewById(R.id.layout_goods_service);
        tv_goods_service = view.findViewById(R.id.tv_goods_service);
        layout_goods_spec = view.findViewById(R.id.layout_goods_spec);
        tv_goods_spec = view.findViewById(R.id.tv_goods_spec);

        tv_user_favorable_rate = view.findViewById(R.id.tv_user_favorable_rate);
        iv_user_avatar = view.findViewById(R.id.iv_user_avatar);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_user_level = view.findViewById(R.id.tv_user_level);
        rb_user_rating = view.findViewById(R.id.rb_user_rating);
        tv_user_evaluation_date = view.findViewById(R.id.tv_user_evaluation_date);
        tv_user_likes = view.findViewById(R.id.tv_user_likes);
        tv_user_evaluation_content = view.findViewById(R.id.tv_user_evaluation_content);

        iv_goods_detail = view.findViewById(R.id.iv_goods_detail);

        layout_goods_spec.setOnClickListener(this);
        layout_goods_service.setOnClickListener(this);
    }

    private static class GoodsBanner implements CBViewHolderCreator, OnItemClickListener {
        static void setupBanner(ViewGroup parent, List<String> urls) {
            new GoodsBanner(parent, urls);
        }

        private GoodsBanner(ViewGroup parent, List<String> urls) {
            Context context = parent.getContext();
            ConvenientBanner<SimpleImageBannerBean> banner = createBanner(context, urls);
            parent.addView(banner);
        }

        private ConvenientBanner<SimpleImageBannerBean> createBanner(@NonNull Context context, List<String> urls) {
            ConvenientBanner<SimpleImageBannerBean> banner = new ConvenientBanner<>(context);
            ArrayList<SimpleImageBannerBean> imageList = new ArrayList<>();
            for (String url : urls) {
                SimpleImageBannerBean e = new SimpleImageBannerBean();
                e.setBannerImageUrl(url);
                e.setBannerImageDefult(R.drawable.image_placeholder);
                imageList.add(e);
            }
            banner.setShowLeftCardWidth(0);

            banner
                    .setPages(this, imageList)
                    .setPageIndicator(new int[]{R.drawable.shape_goods_banner_indicator_gray, R.drawable.shape_goods_banner_indicator_orange})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setOnItemClickListener(this);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            banner.setLayoutParams(params);

            return banner;
        }

        @Override
        public Holder createHolder(View itemView) {
            return new SimpleImageHolder(itemView);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fragment_goods_info_banner_item;
        }

        @Override
        public void onItemClick(int position) {

        }
    }
}
