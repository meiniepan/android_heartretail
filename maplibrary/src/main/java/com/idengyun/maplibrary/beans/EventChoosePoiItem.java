package com.idengyun.maplibrary.beans;


import com.amap.api.services.core.PoiItem;

/**
 * @Title 在地址输入页面 选中提示的地址的广播
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 09:55
 */
public class EventChoosePoiItem {
    public PoiItem poiItem;
    public EventChoosePoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }
}
