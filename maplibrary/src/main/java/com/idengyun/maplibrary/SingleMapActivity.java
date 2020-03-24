package com.idengyun.maplibrary;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.maplibrary.utils.AmapLocationWapper;
import com.idengyun.maplibrary.utils.AmapPointUtil;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.maplibrary.view.DyMapView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @Title 单独的地图页面
 * @Author: zhoubo
 * @CreateDate: 2020年03月17日09:55:29
 */
public class SingleMapActivity extends BaseActivity {

    //地图相关view
    DyMapView mMapView;
    private AMap aMap;

    /**
     * @param latitude  跳转过来传的纬度
     * @param longitude 跳转过来传的经度
     * @param tipTitle  标记点的显示的标题
     * @param tipDesc   标记点的显示的描述
     */
    public static void start(Context context,
                             double latitude,
                             double longitude,
                             String tipTitle,
                             String tipDesc) {
        Intent starter = new Intent(context, SingleMapActivity.class);
        starter.putExtra("latitude", latitude);
        starter.putExtra("longitude", longitude);
        starter.putExtra("tipTitle", tipTitle);
        starter.putExtra("tipDesc", tipDesc);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_map;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mMapView = findViewById(R.id.map);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        //设置定位
        startLocation();
        moveToSpecity();
    }

    /**
     * 移动到指定位置
     */
    private void moveToSpecity() {
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        String tipTitle = getIntent().getStringExtra("tipTitle");
        String tipDesc = getIntent().getStringExtra("tipDesc");

        LatLng latLng = new LatLng(latitude, longitude);
        //绘制点
        AmapPointUtil.drawOnePoint(aMap, latLng, tipTitle, tipDesc);
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
        finish();
    }

    private void startLocation() {
        new AmapLocationWapper().startLocationWithMap(aMap, null);
    }

}
