package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idengyun.commonmodule.beans.OrderStatusBean;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class AwardIncomeListAdapter extends BaseQuickAdapter<OrderStatusBean, BaseViewHolder> {
    public AwardIncomeListAdapter(int layoutResId, @Nullable List<OrderStatusBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusBean item) {

    }

}
