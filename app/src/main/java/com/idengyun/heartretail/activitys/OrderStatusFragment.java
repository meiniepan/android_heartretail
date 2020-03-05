package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.OderStatusListAdapter;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:订单状态详情 待付款, 代销中, 待发货, 待收货, 待评价
 * @date :2020/3/4 0004 13:25
 */
public class OrderStatusFragment extends BaseFragment {
    @BindView(R.id.rsr_order_status)
    RefreshStatusRecyclerView rsrOrderStatus;
    List<OrderStatusBean> data = new ArrayList<>();
    int status;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        status = getBundle().getInt("status");
        initData();
        OderStatusListAdapter adapter = new OderStatusListAdapter(R.layout.item_order_status, data);
        rsrOrderStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        rsrOrderStatus.setAdapter(adapter);
    }

    private void initData() {
        if (status == 0){
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店"+status;
            data.add(orderStatusBean);
        }else if (status == 1){
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            goodsBean.goodsQuantity = 3;
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            goodsBeanList.add(goodsBean);
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店"+status;
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);


        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
