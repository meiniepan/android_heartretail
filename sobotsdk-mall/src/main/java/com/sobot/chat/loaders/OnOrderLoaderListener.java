package com.sobot.chat.loaders;

import com.sobot.chat.dengy.bean.OrderGoodsBean;

import java.util.List;

/**
 * @Title 加载器的订单请求的监听
 * @Desc: 描述
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 15:32
 */
public interface OnOrderLoaderListener {
    void onSuccess(List<OrderGoodsBean> returnOrderList);
    void onError();
}
