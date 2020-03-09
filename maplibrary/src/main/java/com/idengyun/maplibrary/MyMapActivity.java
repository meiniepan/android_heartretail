package com.idengyun.maplibrary;

import android.location.Location;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.MyLocationStyle;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.maplibrary.R;
import com.idengyun.maplibrary.view.DyMapView;
import com.orhanobut.logger.Logger;

public class MyMapActivity extends BaseActivity {
    DyMapView mMapView;
    private AMap aMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_map;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();

        //设置地图定位配置
        setAmapStytle();
        //设置定位回调监听
        setLocationListener();

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


    private void setLocationListener() {
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //纬度
                double latitude = location.getLatitude();
                //经度
                double longitude= location.getLongitude();
                Logger.d("经度："+longitude+"   ; 纬度："+latitude);
            }
        });

    }

    private void setAmapStytle() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //myLocationStyle.interval(2000);
        //只定位一次
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
    }
}
