package com.sobot.chat.loaders;

import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.sobot.chat.dengy.bean.AfterOrderBean;
import com.sobot.chat.dengy.bean.MtmyOrderListBean;
import com.sobot.chat.dengy.bean.OrderGoodsBean;
import com.sobot.chat.dengy.utils.MtmyStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title 每天美耶加载器
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 15:30
 */
public class MtmyLoader implements SobotLoader {

    /**
     * 请求订单列表数据
     *
     * @param page                  请求页码
     * @param onOrderLoaderListener 回调
     */
    @Override
    public void requestOrderList(FragmentActivity activity, int page, final OnOrderLoaderListener onOrderLoaderListener) {
        NetOption netOption = NetOption.newBuilder("")
                .activity(activity)
                .build();
        NetApi.getString(netOption, new JsonCallback<String>(netOption) {
            @Override
            public void onSuccess(Response<String> response) {
                final MtmyOrderListBean bean = new Gson().fromJson(response.body(), MtmyOrderListBean.class);
                if ("200".equals(bean.code)) {
                    final List<MtmyOrderListBean.DataBean> data = bean.data;
                    ArrayList<OrderGoodsBean> orderGoodsList = new ArrayList<>();
                    for (int x = 0; x < data.size(); x++) {
                        MtmyOrderListBean.DataBean dataBean = data.get(x);
                        String add_time = dataBean.add_time;
                        int order_status = dataBean.order_status;

                        List<MtmyOrderListBean.DataBean.OrderGoodsBean> orderGoods = dataBean.orderGoods;
                        for (int y = 0; y < orderGoods.size(); y++) {
                            MtmyOrderListBean.DataBean.OrderGoodsBean goods = orderGoods.get(y);
                            OrderGoodsBean orderGoodsBean = new OrderGoodsBean();
                            orderGoodsBean.name = "订单";
                            orderGoodsBean.orderNo = goods.order_id;
                            orderGoodsBean.createDate = add_time;
                            orderGoodsBean.firstImg = goods.original_img;
                            orderGoodsBean.goodsName = goods.goods_name;
                            orderGoodsBean.price = goods.goods_price;
                            orderGoodsBean.customStatus = MtmyStatusEnum.getName(order_status);
                            orderGoodsList.add(orderGoodsBean);
                        }
                    }
                    onOrderLoaderListener.onSuccess(orderGoodsList);
                } else {
                    onOrderLoaderListener.onError();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                onOrderLoaderListener.onError();
            }
        });

    }

    @Override
    public void requestAfterOrderList(FragmentActivity activity, int page, final OnAfterOrderLoaderListener onAfterOrderLoaderListener) {
        NetOption netOption = NetOption.newBuilder("")
                .activity(activity)
                .build();
        NetApi.getString(netOption, new JsonCallback<String>(netOption) {
            @Override
            public void onSuccess(Response<String> response) {
                final AfterOrderBean bean = new Gson().fromJson(response.body(), AfterOrderBean.class);
                if ("200".equals(bean.code)) {
                    ArrayList<OrderGoodsBean> orderGoodsList = new ArrayList<>();
                    for (int x = 0; x < bean.data.size(); x++) {
                        AfterOrderBean.DataBean dataBean = bean.data.get(x);
                        OrderGoodsBean orderGoodsBean = new OrderGoodsBean();
                        orderGoodsBean.name = "售后";
                        orderGoodsBean.orderNo = dataBean.id;
                        orderGoodsBean.price = dataBean.return_amount;
                        orderGoodsBean.createDate = dataBean.apply_date;
                        orderGoodsBean.customStatus = MtmyStatusEnum.getName(dataBean.return_status);
                        orderGoodsBean.firstImg = dataBean.original_img;
                        orderGoodsBean.goodsName = dataBean.goods_name;
                        orderGoodsList.add(orderGoodsBean);
                    }

                    onAfterOrderLoaderListener.onSuccess(orderGoodsList);
                } else {
                    onAfterOrderLoaderListener.onError();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                onAfterOrderLoaderListener.onError();
            }
        });
    }


}
