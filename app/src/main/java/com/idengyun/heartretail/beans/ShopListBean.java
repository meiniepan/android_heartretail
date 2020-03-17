package com.idengyun.heartretail.beans;

/**
 * @Title 店铺列表实体类
 * @Author: zhoubo
 * @CreateDate: 2020-02-28 16:10
 */
public class ShopListBean {
    private int shopId;
    private String shopName; //门店名称
    private String shopHeadImg;//门店图片
    private String shopAddress; //门店地址
    private float gatheringDistance; //相距距离
    private int isRecommend;//是否推荐1是  0否
    private int isNearest;//是否距离最近1是  0否

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

    public String getShopHeadImg() {
        return shopHeadImg;
    }

    public void setShopHeadImg(String shopHeadImg) {
        this.shopHeadImg = shopHeadImg;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public float getGatheringDistance() {
        return gatheringDistance;
    }

    public void setGatheringDistance(float gatheringDistance) {
        this.gatheringDistance = gatheringDistance;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getIsNearest() {
        return isNearest;
    }

    public void setIsNearest(int isNearest) {
        this.isNearest = isNearest;
    }
}
