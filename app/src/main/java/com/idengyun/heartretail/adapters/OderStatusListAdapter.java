package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class OderStatusListAdapter extends BaseQuickAdapter<OrderStatusBean, BaseViewHolder> {
    public OderStatusListAdapter(int layoutResId, @Nullable List<OrderStatusBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusBean item) {
        helper.setText(R.id.tv_order_status_shop_name, item.shopName);
        initGoodsListRecyclerView(helper.getView(R.id.sr_goods), item.goodsBeans);
    }

    private void initGoodsListRecyclerView(StatusRecyclerView recyclerView, List<OrderStatusBean.GoodsBean> goodsBeans) {
        OderStatusGoodsListAdapter adapter = new OderStatusGoodsListAdapter(R.layout.item_order_status_goods, goodsBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1),mContext.getResources().getColor(R.color.lineColor));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
    }
}
