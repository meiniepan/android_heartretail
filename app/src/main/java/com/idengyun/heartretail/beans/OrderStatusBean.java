package com.idengyun.heartretail.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @Title 标题
 * @Desc: 描述 订单状态实体
 * @Author: Burning
 * @CreateDate: 2020-03-04 16:10
 */
public class OrderStatusBean implements Parcelable {
    public String orderId;
    public String payOrderId;
    public int userId;
    public int orderStatus;
    public int shopId;
    public String shopName;
    public String shippingPrice;
    public String couponPrice;
    public float totalAmount;
    public float orderAmount;
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

    public static class GoodsBean implements Parcelable{
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


        protected GoodsBean(Parcel in) {
            orderId = in.readString();
            userId = in.readInt();
            goodsId = in.readInt();
            goodsName = in.readString();
            goodsNum = in.readInt();
            goodsTitle = in.readString();
            originalImg = in.readString();
            skuSpecname = in.readString();
            skuItemvalue = in.readString();
            goodsPrice = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(orderId);
            dest.writeInt(userId);
            dest.writeInt(goodsId);
            dest.writeString(goodsName);
            dest.writeInt(goodsNum);
            dest.writeString(goodsTitle);
            dest.writeString(originalImg);
            dest.writeString(skuSpecname);
            dest.writeString(skuItemvalue);
            dest.writeString(goodsPrice);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
            @Override
            public GoodsBean createFromParcel(Parcel in) {
                return new GoodsBean(in);
            }

            @Override
            public GoodsBean[] newArray(int size) {
                return new GoodsBean[size];
            }
        };
    }


    protected OrderStatusBean(Parcel in) {
        orderId = in.readString();
        payOrderId = in.readString();
        userId = in.readInt();
        orderStatus = in.readInt();
        shopId = in.readInt();
        shopName = in.readString();
        shippingPrice = in.readString();
        couponPrice = in.readString();
        totalAmount = in.readFloat();
        orderAmount = in.readFloat();
        createTime = in.readString();
        payTime = in.readString();
        orderType = in.readInt();
        proxySalesId = in.readInt();
        proxySalesCode = in.readString();
        proxySalesName = in.readString();
        proxySalesTime = in.readString();
        orderGoods = in.createTypedArrayList(GoodsBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(payOrderId);
        dest.writeInt(userId);
        dest.writeInt(orderStatus);
        dest.writeInt(shopId);
        dest.writeString(shopName);
        dest.writeString(shippingPrice);
        dest.writeString(couponPrice);
        dest.writeFloat(totalAmount);
        dest.writeFloat(orderAmount);
        dest.writeString(createTime);
        dest.writeString(payTime);
        dest.writeInt(orderType);
        dest.writeInt(proxySalesId);
        dest.writeString(proxySalesCode);
        dest.writeString(proxySalesName);
        dest.writeString(proxySalesTime);
        dest.writeTypedList(orderGoods);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderStatusBean> CREATOR = new Creator<OrderStatusBean>() {
        @Override
        public OrderStatusBean createFromParcel(Parcel in) {
            return new OrderStatusBean(in);
        }

        @Override
        public OrderStatusBean[] newArray(int size) {
            return new OrderStatusBean[size];
        }
    };
}
