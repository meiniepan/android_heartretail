package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.ShippingListBean;
import com.idengyun.heartretail.widget.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class ShippingListAdapter extends BaseQuickAdapter<ShippingListBean.ShippingBean, BaseViewHolder> {
    public ShippingListAdapter(int layoutResId, @Nullable List<ShippingListBean.ShippingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShippingListBean.ShippingBean item) {
        helper.setText(R.id.tv_shipping_name, item.shippingName + " " + item.shippingCode);
        StatusRecyclerView recyclerView = helper.getView(R.id.sr_shipping_goods);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return helper.itemView.onTouchEvent(event);
            }
        });
        initRecycler(recyclerView,item);
    }
    private void initRecycler(StatusRecyclerView recyclerView, ShippingListBean.ShippingBean item) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1),mContext.getResources().getColor(R.color.lineColor));
        recyclerView.addItemDecoration(divider);
        ShippingGoodsListAdapter adapter = new ShippingGoodsListAdapter(R.layout.item_order_status_goods, item.goods);
        recyclerView.setAdapter(adapter);
    }
}
