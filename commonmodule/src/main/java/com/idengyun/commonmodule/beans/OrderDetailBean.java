package com.idengyun.commonmodule.beans;

import java.util.List;

/**
 * @Title 标题
 * @Desc: 描述 订单详情实体
 * @Author: Burning
 * @CreateDate: 2020-03-04 16:10
 */
public class OrderDetailBean {
    public OrderDetailBeanBody order;
    public class OrderDetailBeanBody {
        public String orderId;
        public String payOrderId;
        public int userId;
        public int orderStatus;
        public int shopId;
        public int leftTime;//剩余支付时间秒（50表示剩余50秒）
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
        public String userRemark;
        public List<GoodsBean> orderGoods;
    }
    public OrderDetailBean() {

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
