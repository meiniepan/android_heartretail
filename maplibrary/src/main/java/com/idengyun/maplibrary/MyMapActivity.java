package com.idengyun.maplibrary;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.maplibrary.utils.AmapLocationUtil;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.maplibrary.view.DyMapView;
import com.orhanobut.logger.Logger;

public class MyMapActivity extends BaseActivity {
    //标题上的选择的城市控件
    private TextView tvMapSelectCity;
    //标题上的选择的地址的控件
    private TextView tvMapSelectAddr;

    DyMapView mMapView;
    private AMap aMap;

    private String cityName;
    private String nearShop;

    /**
     * @param context
     * @param cityName  定位的城市名称
     * @param nearShop  定位的推荐最近的店铺
     */
    public static void start(Context context,String cityName,String nearShop) {
        Intent starter = new Intent(context, MyMapActivity.class);
        starter.putExtra("cityName",cityName);
        starter.putExtra("nearShop",nearShop);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_map;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mMapView = findViewById(R.id.map);
        tvMapSelectCity = findViewById(R.id.tv_map_select_city);
        tvMapSelectAddr = findViewById(R.id.tv_map_select_addr);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        cityName = getIntent().getStringExtra("cityName");
        nearShop = getIntent().getStringExtra("nearShop");
        tvMapSelectCity.setText(cityName);
        tvMapSelectAddr.setText(nearShop);
        //设置定位
        startLocation();

    }

    private void startLocation() {
        AmapLocationUtil.getInstance().startLocationWithMap(aMap, new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //纬度
                double latitude = location.getLatitude();
                //经度
                double longitude= location.getLongitude();
                Logger.d("经度："+longitude+"   ; 纬度："+latitude);
                PoiSearchUtil.getInstance()
                        .searchPOIWithBound(MyMapActivity.this, latitude, longitude, new PoiSearch.OnPoiSearchListener() {
                            @Override
                            public void onPoiSearched(PoiResult poiResult, int i) {

                            }

                            @Override
                            public void onPoiItemSearched(PoiItem poiItem, int i) {

                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    public void back(View view) {
        this.finish();
    }
}
