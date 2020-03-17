package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @Title 店铺详情的实体类
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 08:30
 */
public class ShopDetailBean {
    public String shopName;//门店名称
    public String shopDetailAddress;//门店详细
    public String shopTelephone;//门店电话
    public String shopIntroduction;//门店简介
    public String businessHoursStart;//营业开始时间
    public String businessHoursEnd;//营业结束时间
    public String latitude;//纬度
    public String longitude;//图片集合
    public List<String> images;//经度
}
