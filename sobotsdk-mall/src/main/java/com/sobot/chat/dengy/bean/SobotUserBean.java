package com.sobot.chat.dengy.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liupeng on 2017/11/3.
 */

public class SobotUserBean implements Parcelable {
    public String isShowOrder;
    public String isShowAfterOrder;
    public String fromWhere;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.isShowOrder);
        dest.writeString(this.isShowAfterOrder);
        dest.writeString(this.fromWhere);

    }

    public SobotUserBean() {
    }

    protected SobotUserBean(Parcel in) {
        this.isShowOrder = in.readString();
        this.isShowAfterOrder = in.readString();
        this.fromWhere = in.readString();
    }

    public static final Creator<SobotUserBean> CREATOR = new Creator<SobotUserBean>() {
        @Override
        public SobotUserBean createFromParcel(Parcel source) {
            return new SobotUserBean(source);
        }

        @Override
        public SobotUserBean[] newArray(int size) {
            return new SobotUserBean[size];
        }
    };
}
