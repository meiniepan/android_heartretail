package com.idengyun.maplibrary.utils;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiSearch;
import com.dengyun.baselibrary.utils.ListUtils;
import com.idengyun.maplibrary.citylist.ChooseAddrActivity;

import java.util.List;

/**
 * @Title poi搜索的工具类
 * @Author: zhoubo
 * @CreateDate: 2020-03-09 09:19
 */
public class PoiSearchUtil {
    private final String POI_SEARCH_TYPE = "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|" +
            "生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|" +
            "交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施";

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
        PoiSearch.Query query = new PoiSearch.Query("", POI_SEARCH_TYPE,"");
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

    /**
     * 输入框提示的poi tips
     * @param cityName 搜索的当前所在城市
     * @param newStr   搜索的字符
     * @param inputtipsListener 回调
     */
    public void searchPoiTips(Context context,String cityName,String newStr,Inputtips.InputtipsListener inputtipsListener){
        // 1、构造 InputtipsQuery 对象，设置搜索条件。
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(newStr, cityName);
        //限制在当前城市
        inputquery.setCityLimit(true);

        // 2、构造 Inputtips 对象，并设置监听。
        Inputtips inputTips = new Inputtips(context, inputquery);
        inputTips.setInputtipsListener(inputtipsListener);
        // 3、调用 PoiSearch 的 requestInputtipsAsyn() 方法发送请求。
        inputTips.requestInputtipsAsyn();

        /* 4、说明：
        tipList 数组中的对象是 Tip ，Tip 类中包含 PoiID、Adcode、District、Name 等信息。
        注意：
        a 、由于提示中会出现相同的关键字，但是这些关键字所在区域不同，使用时可以通过 tipList.get(i).getDistrict() 获得区域，
            也可以在提示时在关键字后加上区域。
        b、当 Tip 的 getPoiID() 返回空，并且 getPoint() 也返回空时，表示该提示词不是一个真实存在的 POI，
            这时区域、经纬度参数都是空的，此时可根据该提示词进行POI关键词搜索
        c、当 Tip 的 getPoiID() 返回不为空，但 getPoint() 返回空时，表示该提示词是一个公交线路名称，此时用这个id进行公交线路查询。
        d、当 Tip 的 getPoiID() 返回不为空，且 getPoint() 也不为空时，表示该提示词一个真实存在的POI，可直接显示在地图上。*/
    }
}
