package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.ToastUtils;

import com.idengyun.heartretail.MainActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.shop.ShopListActivity;
import com.idengyun.maplibrary.MyMapActivity;
import com.idengyun.maplibrary.beans.EventChoosePoiItem;
import com.idengyun.maplibrary.utils.AmapLocationWapper;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.usermodule.LoginActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class FirstActivity extends BaseActivity {

    private AmapLocationWapper amapLocationWapper;
    private TextView tvFirstLocation;
    private String cityName;
    private String nearShop;
    private String poiId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        registBus();
        tvFirstLocation = findViewById(R.id.tv_first_location);
        startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        amapLocationWapper.stopLocation();
        amapLocationWapper.onDestroy();
    }

    /*选择完地址*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventChoosePoiItem eventChoosePoiItem) {
        cityName = eventChoosePoiItem.poiItem.getCityName();
        nearShop = eventChoosePoiItem.poiItem.getTitle();
        poiId = eventChoosePoiItem.poiItem.getPoiId();
        tvFirstLocation.setText(nearShop);
        //设置全局的定位属性
        PoiSearchUtil.setGlobalLocationByPoiItem(eventChoosePoiItem.poiItem);
    }

    private void startLocation() {
        amapLocationWapper = new AmapLocationWapper();
        amapLocationWapper.startLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                PoiSearchUtil.searchPOIWithBound(FirstActivity.this, latitude, longitude,
                        new PoiSearchUtil.OnPoiBoundSearchListener() {
                            @Override
                            public void onSearchResult(List<PoiItem> pois) {
                                //pois已经在上层判过空了
                                if (null != tvFirstLocation) {
                                    PoiItem poiItem = pois.get(0);
                                    cityName = poiItem.getCityName();
                                    nearShop = poiItem.getTitle();
                                    poiId = poiItem.getPoiId();
                                    tvFirstLocation.setText(poiItem.getTitle());
                                    //设置全局的定位属性
                                    PoiSearchUtil.setGlobalLocationByPoiItem(poiItem);
                                }
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
    } /* 跳到订单列表 */
    public void skipOrderCommit(View view) {
//        OrderListActivity.start(this, 1);
        ConfirmOrderActivity.start(this, null);
    }

    /* 跳到地图页面 */
    public void skipToMap(View view) {
        if (TextUtils.isEmpty(cityName)) {
            ToastUtils.showShort("还没有定位成功");
        }else {
            MyMapActivity.start(this,cityName,nearShop,poiId);
        }
    }
}
