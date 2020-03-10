package com.idengyun.maplibrary.utils;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.dengyun.baselibrary.utils.Utils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

/**
 * @Title 定位的工具类
 * @Author: zhoubo
 * @CreateDate: 2020-03-09 09:32
 */
public class AmapLocationUtil {

    public static AmapLocationUtil getInstance() {
        return new AmapLocationUtil();
    }

    private AmapLocationUtil() {
    }

    public void startLocationWithMap(AMap aMap, AMap.OnMyLocationChangeListener onMyLocationChangeListener) {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        //定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);

        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(17);
        aMap.animateCamera(mCameraUpdate);

        //设置定位的回调
        aMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
    }

    private AMapLocationClient mLocationClient;
    public void startLocation(AMapLocationListener mLocationListener) {
        AndPermission.with(Utils.getApp())
                .runtime()
                .permission(Permission.Group.LOCATION)
                // 用户授权了
                .onGranted(permissions -> {
                    // 1,初始化定位
                    mLocationClient = new AMapLocationClient(Utils.getApp());
                    //设置定位回调监听
                    mLocationClient.setLocationListener(mLocationListener);

                    // 2，配置参数
                    AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
                    //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
                    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    //获取一次定位结果：
                    //该方法默认为false。
                    mLocationOption.setOnceLocation(true);
                    //设置定位间隔,单位毫秒,默认为2000ms
                    mLocationOption.setInterval(2000);
                    //设置是否返回地址信息（默认返回地址信息）
                    mLocationOption.setNeedAddress(true);
                    //设置是否允许模拟位置,默认为false，不允许模拟位置
                    mLocationOption.setMockEnable(false);

                    // 3，启动定位
                    mLocationClient.setLocationOption(mLocationOption);
                    //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                    mLocationClient.stopLocation();
                    mLocationClient.startLocation();

                    // 4：停止定位、回收
                    // 当页面销毁时记得调用停止和回收

                })
                // 用户拒绝授权
                .onDenied(permisson -> {
                    // 判断用户是否勾选不再提示
                    if (AndPermission.hasAlwaysDeniedPermission(Utils.getApp(), permisson)) {

                    }
                })
                .start();

    }

    public void stopLocation(){
        //停止定位后，本地定位服务并不会被销毁
        if (null!=mLocationClient) mLocationClient.stopLocation();
    }

    public void onDestroy(){
        //销毁定位客户端，同时销毁本地定位服务。
        if (null!=mLocationClient) mLocationClient.onDestroy();
    }
}
