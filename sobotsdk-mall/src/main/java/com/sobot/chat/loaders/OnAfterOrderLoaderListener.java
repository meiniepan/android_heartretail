package com.sobot.chat.loaders;

import com.sobot.chat.dengy.bean.OrderGoodsBean;

import java.util.List;

/**
 * @Title 加载器的售后订单的监听
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 16:01
 */
public interface OnAfterOrderLoaderListener {
    void onSuccess(List<OrderGoodsBean> returnOrderList);
    void onError();
}
