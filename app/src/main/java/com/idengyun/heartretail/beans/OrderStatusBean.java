package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @Title 标题
 * @Desc: 描述 订单状态实体
 * @Author: Burning
 * @CreateDate: 2020-03-04 16:10
 */
public class OrderStatusBean {
    private int shopId;
    private String shopName;
    private String shopPhoto;
    private String shopAddress;
    private int status;
    private int orderType;//批发、代销
    private float totalPay;
    private List<GoodsBean> goodsBeans;

    public class GoodsBean {
        private int goodsId;
        private String goodsName;
        private String goodsTitle;
        private String goodsSpec;
        private float goodsPrice;
        private int goodsQuantity;
    }
}
