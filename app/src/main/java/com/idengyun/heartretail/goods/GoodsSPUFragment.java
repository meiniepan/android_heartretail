package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.GoodsDetailBean;
import com.idengyun.heartretail.model.response.GoodsEvaluateBean;
import com.zhoubo07.bannerlib.ConvenientBanner;
import com.zhoubo07.bannerlib.banner.SimpleImageBannerBean;
import com.zhoubo07.bannerlib.banner.SimpleImageHolder;
import com.zhoubo07.bannerlib.holder.CBViewHolderCreator;
import com.zhoubo07.bannerlib.holder.Holder;
import com.zhoubo07.bannerlib.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品详情-首页
 *
 * @author aLang
 */
public final class GoodsSPUFragment extends BaseFragment implements View.OnClickListener {

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
    private View layout_goods_info_evaluate_header;
    private TextView tv_user_favorable_rate;
    private ImageView iv_user_avatar;
    private TextView tv_user_name;
    private TextView tv_user_level;
    private RatingBar rb_user_rating;
    private TextView tv_user_evaluation_date;
    private TextView tv_user_likes;
    private TextView tv_user_evaluation_content;

    /* 商品详情 */
    private LinearLayout layout_goods_detail;

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
        FragmentActivity activity = getActivity();
        assert activity != null;

        Fragment fragment = HRActivity.findFragmentByTag(getActivity(), GoodsDetailFragment.class.getName());
        if (fragment instanceof NestedScrollView.OnScrollChangeListener) {
            nested_scroll_view.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) fragment);
        }


        GDViewModel.getInstance(activity).requestGoodsDetailAPI(this, "123", 0);

        GDViewModel.observe(activity, this, new Observer<GoodsDetailBean.Data>() {
            @Override
            public void onChanged(@Nullable GoodsDetailBean.Data data) {
                if (data != null) updateUI(data);
            }
        });

        GEViewModel.observe(activity, this, new Observer<GoodsEvaluateBean.Data>() {
            @Override
            public void onChanged(@Nullable GoodsEvaluateBean.Data data) {
                if (data != null && data.current == 1 && !data.evaluationList.isEmpty()) {
                    updateUI(data);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (layout_goods_spec == v) {
            HRActivity.showFragment(getActivity(), GoodsSpecFragment.class.getName());
        } else if (layout_goods_service == v) {
            HRActivity.showFragment(getActivity(), GoodsServiceFragment.class.getName());
        } else if (layout_goods_info_evaluate_header == v) {
            GoodsDetailFragment goodsDetailFragment = (GoodsDetailFragment) HRActivity.findFragmentByTag(getActivity(), GoodsDetailFragment.class.getName());
            if (goodsDetailFragment != null) goodsDetailFragment.checkGoodsEvaluation();
        }
    }

    @MainThread
    private void updateUI(GoodsEvaluateBean.Data data) {
        int evaluationCounts = data.total;
        String praiseRate = data.praiseRate;
        tv_user_favorable_rate.setText(evaluationCounts + "+条评论，" + praiseRate + "%好评率");

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

    @MainThread
    private void updateUI(GoodsDetailBean.Data data) {
        List<GoodsDetailBean.Data.Banner> imageList = data.imageList;
        List<GoodsDetailBean.Data.Protocol> protocolList = data.protocolList;
        List<GoodsDetailBean.Data.GoodsSpec> goodsSpecList = data.goodsSpecList;
        List<GoodsDetailBean.Data.GoodsSku> goodsSkuList = data.goodsSkuList;
        List<String> goodsDetailList = data.goodsDetailList;

        if (imageList == null ||
                protocolList == null ||
                goodsSpecList == null ||
                goodsSkuList == null ||
                goodsDetailList == null) return;

//        int wholesaleFlag = data.wholesaleFlag;
        String retailPrice = data.retailPrice;
        String wholesalePrice = data.wholesalePrice;
        int goodsType = data.goodsType;
        int soldCounts = data.soldCounts;
        String goodsTitle = data.goodsTitle;

        /* 轮播图 */
        ArrayList<String> urls = new ArrayList<>();
        for (GoodsDetailBean.Data.Banner banner : imageList) {
            urls.add(banner.imgUrl);
        }
        GoodsBanner.setupBanner(layout_goods_banner_container, urls);

        /* 批发资格 */
//        layout_goods_disqualification.setVisibility(wholesaleFlag == 1 ? View.VISIBLE : View.GONE);
        layout_goods_disqualification.setVisibility(View.GONE);

        /* 商品价格 */
        String price = goodsType == 0 ? retailPrice : wholesalePrice;
        tv_goods_price.setText(price);
        tv_goods_price_small.setText("¥" + price);
        tv_goods_price_small.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_goods_sold.setText("已售" + soldCounts + "件");
        tv_goods_info.setText(goodsTitle);

        /* 选择规格 */
        List<String> idList = null;
        for (GoodsDetailBean.Data.GoodsSku goodsSku : goodsSkuList) {
            if (goodsSku.isDefault == 1) {
                idList = Arrays.asList(goodsSku.skuCombinationCode.split("_"));
                break;
            }
        }
        if (idList != null && !idList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (GoodsDetailBean.Data.GoodsSpec goodsSpec : goodsSpecList) {
                for (GoodsDetailBean.Data.GoodsSpec.SkuValue skuValue : goodsSpec.skuValueList) {
                    String specItemId = String.valueOf(skuValue.specItemId);
                    String specItemName = skuValue.specItemName;
                    if (idList.contains(specItemId)) {
                        sb.append(specItemName).append("/");
                        break;
                    }
                }
            }
            tv_goods_spec.setText(sb.subSequence(0, sb.length() - 1));
        }

        /* 服务说明 */
        StringBuilder sb = new StringBuilder();
        for (GoodsDetailBean.Data.Protocol protocol : protocolList) {
            sb.append(protocol.protocolName).append("&");
        }
        tv_goods_service.setText(sb.subSequence(0, sb.length() - 1));

        /* 商品详情 */
        layout_goods_detail.removeAllViews();
        int width = SizeUtils.dp2px(345f);
        int height = SizeUtils.dp2px(281f);
        int topMargin = SizeUtils.dp2px(8f);
        for (String url : goodsDetailList) {
            ImageView child = new ImageView(layout_goods_detail.getContext());
            ViewGroup.MarginLayoutParams params = new LinearLayout.MarginLayoutParams(width, height);
            params.topMargin = topMargin;
            ImageApi.displayImage(child.getContext(), child, url);
            layout_goods_detail.addView(child, params);
        }
    }

    public void setGoodsSpecList(CharSequence text) {
        tv_goods_spec.setText(text);
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

        layout_goods_info_evaluate_header = view.findViewById(R.id.layout_goods_info_evaluate_header);
        tv_user_favorable_rate = view.findViewById(R.id.tv_user_favorable_rate);
        iv_user_avatar = view.findViewById(R.id.iv_user_avatar);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_user_level = view.findViewById(R.id.tv_user_level);
        rb_user_rating = view.findViewById(R.id.rb_user_rating);
        tv_user_evaluation_date = view.findViewById(R.id.tv_user_evaluation_date);
        tv_user_likes = view.findViewById(R.id.tv_user_likes);
        tv_user_evaluation_content = view.findViewById(R.id.tv_user_evaluation_content);

        layout_goods_detail = view.findViewById(R.id.layout_goods_detail);

        layout_goods_spec.setOnClickListener(this);
        layout_goods_service.setOnClickListener(this);
        layout_goods_info_evaluate_header.setOnClickListener(this);
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
