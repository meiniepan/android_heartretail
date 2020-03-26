package com.idengyun.heartretail.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @类名称: CLASS
 * @类描述:
 * @创建人：Burning
 * @创建时间：2020/3/12 16:50
 * @备注：
 */
public class ShippingListBean {
    public List<ShippingBean> shippings;

    public static class ShippingBean implements Parcelable {
        public int shippingId;
        public int shippingStatus;
        public String shippingCode;
        public String shippingTime;
        public String shippingName;
        public String pickupCode;
        public List<Goods> goods;
        public List<LogisticsTraces> logisticsTraces;

        protected ShippingBean(Parcel in) {
            shippingId = in.readInt();
            shippingStatus = in.readInt();
            shippingCode = in.readString();
            shippingTime = in.readString();
            shippingName = in.readString();
            pickupCode = in.readString();
            goods = in.createTypedArrayList(Goods.CREATOR);
            logisticsTraces = in.createTypedArrayList(LogisticsTraces.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(shippingId);
            dest.writeInt(shippingStatus);
            dest.writeString(shippingCode);
            dest.writeString(shippingTime);
            dest.writeString(shippingName);
            dest.writeString(pickupCode);
            dest.writeTypedList(goods);
            dest.writeTypedList(logisticsTraces);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ShippingBean> CREATOR = new Creator<ShippingBean>() {
            @Override
            public ShippingBean createFromParcel(Parcel in) {
                return new ShippingBean(in);
            }

            @Override
            public ShippingBean[] newArray(int size) {
                return new ShippingBean[size];
            }
        };
    }

    public static class Goods implements Parcelable{
        public int goodsId;
        public String goodsName;
        public int goodsNum;
        public String originalImg;
        public String skuItemvalue;
        public String skuSpecname;
        public String goodsPrice;

        protected Goods(Parcel in) {
            goodsId = in.readInt();
            goodsName = in.readString();
            goodsNum = in.readInt();
            originalImg = in.readString();
            skuItemvalue = in.readString();
            skuSpecname = in.readString();
            goodsPrice = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(goodsId);
            dest.writeString(goodsName);
            dest.writeInt(goodsNum);
            dest.writeString(originalImg);
            dest.writeString(skuItemvalue);
            dest.writeString(skuSpecname);
            dest.writeString(goodsPrice);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Goods> CREATOR = new Creator<Goods>() {
            @Override
            public Goods createFromParcel(Parcel in) {
                return new Goods(in);
            }

            @Override
            public Goods[] newArray(int size) {
                return new Goods[size];
            }
        };
    }

    public static class LogisticsTraces implements Parcelable{
        public String Action;
        public String AcceptStation;
        public String AcceptTime;
        public String Location;

        protected LogisticsTraces(Parcel in) {
            Action = in.readString();
            AcceptStation = in.readString();
            AcceptTime = in.readString();
            Location = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Action);
            dest.writeString(AcceptStation);
            dest.writeString(AcceptTime);
            dest.writeString(Location);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<LogisticsTraces> CREATOR = new Creator<LogisticsTraces>() {
            @Override
            public LogisticsTraces createFromParcel(Parcel in) {
                return new LogisticsTraces(in);
            }

            @Override
            public LogisticsTraces[] newArray(int size) {
                return new LogisticsTraces[size];
            }
        };
    }


}
