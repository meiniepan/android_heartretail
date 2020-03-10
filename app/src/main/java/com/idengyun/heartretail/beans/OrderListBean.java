package com.idengyun.heartretail.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/10 0010 15:17
 */
public class OrderListBean implements Parcelable {
    public String ordersCount;
    public List<OrderStatusBean> orders;

    protected OrderListBean(Parcel in) {
        ordersCount = in.readString();
        orders = in.createTypedArrayList(OrderStatusBean.CREATOR);
    }

    public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel in) {
            return new OrderListBean(in);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ordersCount);
        dest.writeTypedList(orders);
    }
}
