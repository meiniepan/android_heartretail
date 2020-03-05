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
    public int shopId;
    public String shopName;
    public String shopPhoto;
    public String shopAddress;
    public int status;
    public int orderType;//批发、代销
    public float totalPay;
    public List<GoodsBean> goodsBeans;

    public OrderStatusBean() {

    }

    public static class GoodsBean implements Parcelable{
        public int goodsId;
        public String goodsName;
        public String goodsTitle;
        public String goodsSpec;
        public float goodsPrice;
        public int goodsQuantity;

        public GoodsBean(Parcel in) {
            goodsId = in.readInt();
            goodsName = in.readString();
            goodsTitle = in.readString();
            goodsSpec = in.readString();
            goodsPrice = in.readFloat();
            goodsQuantity = in.readInt();
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

        public GoodsBean() {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(goodsId);
            dest.writeString(goodsName);
            dest.writeString(goodsTitle);
            dest.writeString(goodsSpec);
            dest.writeFloat(goodsPrice);
            dest.writeInt(goodsQuantity);
        }
    }

    public OrderStatusBean(Parcel in) {
        shopId = in.readInt();
        shopName = in.readString();
        shopPhoto = in.readString();
        shopAddress = in.readString();
        status = in.readInt();
        orderType = in.readInt();
        totalPay = in.readFloat();
        goodsBeans = in.createTypedArrayList(GoodsBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shopId);
        dest.writeString(shopName);
        dest.writeString(shopPhoto);
        dest.writeString(shopAddress);
        dest.writeInt(status);
        dest.writeInt(orderType);
        dest.writeFloat(totalPay);
        dest.writeTypedList(goodsBeans);
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
