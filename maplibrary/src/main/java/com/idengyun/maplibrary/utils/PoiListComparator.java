package com.idengyun.maplibrary.utils;

import com.amap.api.services.core.PoiItem;

import java.util.Comparator;

/**
 * @Title poi列表数据排序类
 * @Author: zhoubo
 * @CreateDate: 2020-03-10 10:27
 */
public class PoiListComparator implements Comparator<PoiItem> {
    @Override
    public int compare(PoiItem o1, PoiItem o2) {
        if (o1.getDistance() > o2.getDistance()) {
            return 1;
        } else {
            return 0;
        }
    }
}
