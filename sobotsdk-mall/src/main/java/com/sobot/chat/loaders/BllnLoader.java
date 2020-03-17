package com.sobot.chat.loaders;

import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.sobot.chat.dengy.bean.AfterOrderBean;
import com.sobot.chat.dengy.bean.BllnOrderListBean;
import com.sobot.chat.dengy.bean.OrderGoodsBean;
import com.sobot.chat.dengy.utils.BllnStatusEnum;
import com.sobot.chat.dengy.utils.MtmyStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title 蓓丽莲娜加载器
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 15:44
 */
public class BllnLoader implements SobotLoader{

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
                final BllnOrderListBean bean = new Gson().fromJson(response.body(), BllnOrderListBean.class);
                if ("200".equals(bean.getCode())) {
                    ArrayList<OrderGoodsBean> orderGoodsList = new ArrayList<>();
                    List<BllnOrderListBean.DataBean> data = bean.getData();
                    for (int x = 0; x < data.size(); x++) {
                        List<BllnOrderListBean.DataBean.GoodsListBean> goodsList = data.get(x).getGoodsList();
                        String createDate = data.get(x).getCreateDate();

                        int customStatus = data.get(x).getCustomStatus();
                        String orderNo = data.get(x).getOrderNo();

                        for (int y = 0; y < goodsList.size(); y++) {
                            OrderGoodsBean orderGoodsBean = new OrderGoodsBean();
                            orderGoodsBean.name = "订单";
                            orderGoodsBean.createDate = createDate;
                            orderGoodsBean.customStatus = BllnStatusEnum.getName(customStatus);
                            orderGoodsBean.orderNo = orderNo;
                            orderGoodsBean.firstImg = goodsList.get(y).getFirstImg();
                            orderGoodsBean.goodsName = goodsList.get(y).getGoodsName();
                            orderGoodsBean.price = goodsList.get(y).getPrice();
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
