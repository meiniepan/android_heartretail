package com.idengyun.heartretail.beans;

/**
 * @Title 标题
 * @Desc: 描述
 * @Author: zhoubo
 * @CreateDate: 2020-02-28 16:10
 */
public class ShopListBean {
    private int shopId;
    private String shopName;
    private String shopPhoto;
    private String shopAddress;
    private float distance;
    private int isUsed;//上次使用：1：是；0：否
    private int isNear;//距离最近：1：是；0：否

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getIsNear() {
        return isNear;
    }

    public void setIsNear(int isNear) {
        this.isNear = isNear;
    }
}
