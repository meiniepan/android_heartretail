package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.OrderStatusBean;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class OderDetailGoodsListAdapter extends BaseQuickAdapter<OrderStatusBean.GoodsBean, BaseViewHolder> {
    public OderDetailGoodsListAdapter(int layoutResId, @Nullable List<OrderStatusBean.GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusBean.GoodsBean item) {
        helper.setText(R.id.tv_goods_title1, item.goodsName)
                .setText(R.id.tv_goods_spec1,  item.skuItemvalue)
                .setText(R.id.tv_quantity1, "X" + item.goodsNum)
                .setText(R.id.tv_price1, "¥" + item.goodsPrice);
        ImageView imageView = helper.getView(R.id.iv_goods_icon2);
        ImageApi.displayImage(mContext,imageView,item.originalImg);
    }

}
