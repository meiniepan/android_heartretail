package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.widgets.imageview.RoundImageView;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.ShopListBean;
import com.idengyun.heartretail.shop.ShopDetailActivity;
import com.idengyun.heartretail.utils.DecimalFormatUtil;

import java.util.List;

/**
 * @Title 店铺列表适配器
 * @Author: zhoubo
 * @CreateDate: 2020-02-28 16:10
 */
public class ShopListAdapter extends BaseQuickAdapter<ShopListBean, BaseViewHolder> {
    public ShopListAdapter(int layoutResId, @Nullable List<ShopListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListBean item) {
        RoundImageView rivShopPhoto = helper.getView(R.id.riv_shop_photo);
        TextView tvShopName = helper.getView(R.id.tv_shop_name);
        TextView tvShopUsed = helper.getView(R.id.tv_shop_used);
        TextView tvShopNear = helper.getView(R.id.tv_shop_near);
        TextView tvShopDistance = helper.getView(R.id.tv_shop_distance);
        TextView tvShopAddress = helper.getView(R.id.tv_shop_address);
        TextView tvShopDetai = helper.getView(R.id.tv_shop_detai);

        //店铺照片
        ImageApi.displayImage(mContext,rivShopPhoto,item.getShopHeadImg());
        //店铺名称
        tvShopName.setText(item.getShopName());
        //是否是上次使用
        tvShopUsed.setVisibility(item.getIsRecommend()==1? View.VISIBLE:View.GONE);
        //是否是距离最近
        tvShopNear.setVisibility(item.getIsNearest()==1? View.VISIBLE:View.GONE);
        //店铺地址
        tvShopAddress.setText(item.getShopAddress());
        //距离
        float distance = item.getGatheringDistance();
        String distanceStr = "";
        if (distance>=1000){
            distanceStr = "距离"+DecimalFormatUtil.getFormatDecimal("0.0",distance/1000d)+"km";
        }else if (distance>1){
            distanceStr = "距离"+distance+"m";
        }else {
            distanceStr = "距离1m";
        }
        tvShopDistance.setText(distanceStr);
        //店铺详情按钮监听
        tvShopDetai.setOnClickListener(v -> {
            ShopDetailActivity.start(mContext,item.getShopId());
        });

    }
}
