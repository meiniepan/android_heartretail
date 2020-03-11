package com.idengyun.maplibrary.beans;


/**
 * @Title 在地址输入页面 选中提示的地址的广播
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 09:55
 */
public class EventChooseAddrTip {
    public String cityName;
    public String poiId;
    public String choosePoiName;

    public EventChooseAddrTip(String cityName, String poiId, String choosePoiName) {
        this.cityName = cityName;
        this.poiId = poiId;
        this.choosePoiName = choosePoiName;
    }
}
