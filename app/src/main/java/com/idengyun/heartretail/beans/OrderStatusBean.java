package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @Title 标题
 * @Desc: 描述 订单状态实体
 * @Author: Burning
 * @CreateDate: 2020-03-04 16:10
 */
public class OrderStatusBean {
    public int shopId;
    public String shopName;
    public String shopPhoto;
    public String shopAddress;
    public int status;
    public int orderType;//批发、代销
    public float totalPay;
    public List<GoodsBean> goodsBeans;

    public static class GoodsBean {
        public int goodsId;
        public String goodsName;
        public String goodsTitle;
        public String goodsSpec;
        public float goodsPrice;
        public int goodsQuantity;
    }
}
