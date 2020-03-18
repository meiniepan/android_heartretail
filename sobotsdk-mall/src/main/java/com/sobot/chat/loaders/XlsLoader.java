package com.sobot.chat.loaders;

import android.support.v4.app.FragmentActivity;

import com.idengyun.commonmodule.beans.OrderListBean;
import com.idengyun.commonmodule.beans.OrderStatusBean;
import com.idengyun.commonmodule.utils.OrderStatusUtil;
import com.idengyun.routermodule.ARouterInstance;
import com.idengyun.routermodule.AppRouter;
import com.sobot.chat.dengy.bean.OrderGoodsBean;
import com.sobot.chat.dengy.utils.MtmyStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title 心零售的加载器
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 16:00
 */
public class XlsLoader implements SobotLoader {
    @Override
    public void requestOrderList(FragmentActivity activity, int page, final OnOrderLoaderListener onOrderLoaderListener) {
        ARouterInstance.getAppRouter().getOrderListAsyn(activity, 0, page, new AppRouter.OnRequestOrderListListener() {
            @Override
            public void onResultOrderList(OrderListBean resultOrderListBean) {
                ArrayList<OrderGoodsBean> orderGoodsList = new ArrayList<>();
                for (int i = 0; i < resultOrderListBean.orders.size(); i++) {
                    //订单实体
                    OrderStatusBean xlsOrderBean = resultOrderListBean.orders.get(i);
                    //订单下的商品列表
                    List<OrderStatusBean.GoodsBean> xlsOrderGoodsList = xlsOrderBean.orderGoods;
                    for (int j = 0; j < xlsOrderGoodsList.size(); j++) {
                        OrderGoodsBean orderGoodsBean = new OrderGoodsBean();
                        orderGoodsBean.name = "订单";
                        orderGoodsBean.orderNo = xlsOrderBean.orderId;
                        orderGoodsBean.createDate = xlsOrderBean.createTime;
                        orderGoodsBean.firstImg = xlsOrderGoodsList.get(j).originalImg;
                        orderGoodsBean.goodsName = xlsOrderGoodsList.get(j).goodsName;
                        try {
                            orderGoodsBean.price = Double.parseDouble(xlsOrderGoodsList.get(j).goodsPrice);
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                        orderGoodsBean.customStatus = OrderStatusUtil.getStatusName(xlsOrderBean.orderStatus);
                        orderGoodsList.add(orderGoodsBean);
                    }
                }

                if (null!=onOrderLoaderListener){
                    onOrderLoaderListener.onSuccess(orderGoodsList);
                }
            }

            @Override
            public void onError() {
                onOrderLoaderListener.onError();
            }
        });
    }

    @Override
    public void requestAfterOrderList(FragmentActivity activity, int page, OnAfterOrderLoaderListener onAfterOrderLoaderListener) {

    }
}
