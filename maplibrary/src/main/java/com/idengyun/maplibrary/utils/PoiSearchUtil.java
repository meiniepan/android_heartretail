package com.idengyun.maplibrary.utils;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;

/**
 * @Title poi搜索的工具类
 * @Author: zhoubo
 * @CreateDate: 2020-03-09 09:19
 */
public class PoiSearchUtil {
    private PoiSearchUtil poiSearchUtil;

    public static PoiSearchUtil getInstance() {
        return new PoiSearchUtil();
    }

    private PoiSearchUtil() {
    }

    /**
     * 搜索周边一公里范围的poi数据
     * @param latitude  纬度
     * @param longitude 经度
     * @param onPoiSearchListener 搜索的结果回调
     */
    public void searchPOIWithBound(Context context,
                                   double latitude,
                                   double longitude,
                                   PoiSearch.OnPoiSearchListener onPoiSearchListener) {
        // 1、构造 PoiSearch.Query 对象，通过 PoiSearch.Query(String query, String ctgr, String city) 设置搜索条件。
        /*keyWord表示搜索字符串，
        第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索*/
        PoiSearch.Query query = new PoiSearch.Query("", "120000|100000","");
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        //设置查询页码
        query.setPageNum(1);

        // 2、构造 PoiSearch 对象，并设置监听。
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(onPoiSearchListener);

        // 3:周边检索POI
        //设置周边搜索的中心点以及半径
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 2000));

        // 4、调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。
        poiSearch.searchPOIAsyn();
    }
}
