package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.OrderStatusBean;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class OderStatusGoodsListAdapter extends BaseQuickAdapter<OrderStatusBean.GoodsBean, BaseViewHolder> {
    public OderStatusGoodsListAdapter(int layoutResId, @Nullable List<OrderStatusBean.GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusBean.GoodsBean item) {
        helper.setText(R.id.tv_goods_title1, item.goodsTitle)
        .setText(R.id.tv_quantity1,"X"+item.goodsQuantity);
    }

}
