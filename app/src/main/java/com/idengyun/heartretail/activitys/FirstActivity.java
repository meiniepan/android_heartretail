package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;

import com.idengyun.heartretail.MainActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.shop.ShopListActivity;
import com.idengyun.maplibrary.MyMapActivity;
import com.idengyun.maplibrary.utils.AmapLocationUtil;
import com.idengyun.maplibrary.utils.PoiListComparator;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.usermodule.LoginActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;

@Route(path = (RouterPathConfig.app_FirstActivity))
public class FirstActivity extends BaseActivity {

    private AmapLocationUtil amapLocationUtil;
    private TextView tvFirstLocation;
    private String cityName;
    private String nearShop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvFirstLocation = findViewById(R.id.tv_first_location);
        startLocation();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        amapLocationUtil.stopLocation();
        amapLocationUtil.onDestroy();
    }

    private void startLocation() {
        amapLocationUtil = AmapLocationUtil.getInstance();
        amapLocationUtil.startLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                PoiSearchUtil.getInstance().searchPOIWithBound(FirstActivity.this, latitude, longitude,
                        new PoiSearch.OnPoiSearchListener() {
                            @Override
                            public void onPoiSearched(PoiResult poiResult, int i) {
                                ArrayList<PoiItem> pois = poiResult.getPois();
                                Collections.sort(pois,new PoiListComparator());
                                if (!ListUtils.isEmpty(pois) && null!=tvFirstLocation){
                                    cityName = pois.get(0).getCityName();
                                    nearShop = pois.get(0).getTitle();
                                    tvFirstLocation.setText(pois.get(0).getTitle());
                                }
                            }

                            @Override
                            public void onPoiItemSearched(PoiItem poiItem, int i) {

                            }
                        });
            }
        });
    }

    /* 开启登录界面 */
    public void skipLogin(View view) {
        LoginActivity.start(this);
    }

    /* 开启主界面 */
    public void skipMain(View view) {
        MainActivity.start(this);
    }

    /* 跳到店铺列表 */
    public void skipShopList(View view) {
        ShopListActivity.start(this);
    }

    /* 跳到订单列表 */
    public void skipOrderList(View view) {
        OrderListActivity.start(this, 1);
    }

    /* 跳到地图页面 */
    public void skipToMap(View view) {
        if (TextUtils.isEmpty(cityName)){
            ToastUtils.showShort("还没有定位成功");
        }else {
            MyMapActivity.start(this,cityName,nearShop);
        }
    }
}
