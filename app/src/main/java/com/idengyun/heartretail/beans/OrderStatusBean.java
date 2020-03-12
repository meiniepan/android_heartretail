package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @Title 标题
 * @Desc: 描述 订单状态实体
 * @Author: Burning
 * @CreateDate: 2020-03-04 16:10
 */
public class OrderStatusBean {
    public String orderId;
    public String payOrderId;
    public int userId;
    public int orderStatus;
    public int shopId;
    public String shopName;
    public String shippingPrice;
    public String couponPrice;
    public String totalAmount;
    public String orderAmount;
    public String createTime;
    public String payTime;
    public int orderType;//订单类型1零售2
    public int proxySalesId;
    public String proxySalesCode;
    public String proxySalesName;
    public String proxySalesTime;
    public List<GoodsBean> orderGoods;

    public OrderStatusBean() {

    }

    public static class GoodsBean {
        public String orderId;
        public int userId;
        public int goodsId;
        public String goodsName;
        public int goodsNum;
        public String goodsTitle;
        public String originalImg;
        public String skuSpecname;
        public String skuItemvalue;
        public String goodsPrice;


        public GoodsBean() {

        }


    }
}
