package com.idengyun.maplibrary;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.maplibrary.beans.EventChoosePoiItem;
import com.idengyun.maplibrary.citylist.ChooseAddrActivity;
import com.idengyun.maplibrary.citylist.CityListActivity;
import com.idengyun.maplibrary.utils.AmapLocationWapper;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.maplibrary.view.DyMapView;
import com.idengyun.maplibrary.view.PoiScrollLayout;
import com.idengyun.maplibrary.view.PoiScrollRecyclerView;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MyMapActivity extends BaseActivity {
    //标题上的选择的城市控件
    private TextView tvMapSelectCity;
    //标题上的选择的地址的控件
    private TextView tvMapSelectAddr;
    //下方的poi列表的容器
    private PoiScrollLayout slMapPoi;
    //poi列表revyclerview
    private PoiScrollRecyclerView rvMapPoi;

    //地图相关view
    DyMapView mMapView;
    private AMap aMap;

    //从上个页面传过来的定位城市和最近的店
    private String cityName,nearShop,poiId;

    //poi数据列表适配器
    private PoiListAdapter poiListAdapter;
    private ArrayList<PoiItem> pois = new ArrayList<>();



    /**
     * @param context
     * @param cityName  定位的城市名称
     * @param nearShop  定位的推荐最近的店铺
     */
    public static void start(Context context,String cityName,String nearShop,String poiId) {
        Intent starter = new Intent(context, MyMapActivity.class);
        starter.putExtra("cityName",cityName);
        starter.putExtra("nearShop",nearShop);
        starter.putExtra("poiId",poiId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_map;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        registBus();
        //findview
        mMapView = findViewById(R.id.map);
        tvMapSelectCity = findViewById(R.id.tv_map_select_city);
        tvMapSelectAddr = findViewById(R.id.tv_map_select_addr);
        slMapPoi = findViewById(R.id.sl_map_poi);
        rvMapPoi = findViewById(R.id.rv_map_poi);

        //getIntent
        cityName = getIntent().getStringExtra("cityName");
        nearShop = getIntent().getStringExtra("nearShop");
        poiId = getIntent().getStringExtra("poiId");

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        //设置定位
        startLocation();

        //setView
        tvMapSelectCity.setText(cityName);
        tvMapSelectAddr.setText(nearShop);
        initPoiList();
        setScroll();

    }

    private void setScroll() {
        slMapPoi.setOnScrollChangedListener(new PoiScrollLayout.OnScrollChangedListener() {
            @Override
            public void onScrollChange(int status) {
            }

            @Override
            public void onScrollProgress(int progress) {

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

    /*选择完地址*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventChoosePoiItem eventChoosePoiItem) {
        this.finish();
        /*Tip tip = eventChoosePoiItem.chooseTip;
        LatLonPoint point = tip.getPoint();
        LatLng latLng = new LatLng(point.getLatitude(),point.getLongitude());
        //绘制点
        AmapPointUtil.drawOnePoint(aMap,latLng,tip.getName(),tip.getDistrict());*/
    }

    public void back(View view) {
        this.finish();
    }


    private void initPoiList() {
        rvMapPoi.setLayoutManager(new LinearLayoutManager(this));
        rvMapPoi.addItemDecoration(new RecycleViewDivider(this, SizeUtils.dp2px(10)));
        poiListAdapter = new PoiListAdapter(R.layout.item_map_poi,pois);
        rvMapPoi.setAdapter(poiListAdapter);
        poiListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PoiItem poiItem = pois.get(position);
                EventChoosePoiItem eventChoosePoiItem = new EventChoosePoiItem(poiItem);
                EventBus.getDefault().post(eventChoosePoiItem);
                MyMapActivity.this.finish();
            }
        });
    }

    private void startLocation() {
        new AmapLocationWapper().startLocationWithMap(aMap, new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //纬度
                double latitude = location.getLatitude();
                //经度
                double longitude= location.getLongitude();
                Logger.d("经度："+longitude+"   ; 纬度："+latitude);
                PoiSearchUtil.searchPOIWithBound(MyMapActivity.this, latitude, longitude, new PoiSearchUtil.OnPoiBoundSearchListener() {
                    @Override
                    public void onSearchResult(List<PoiItem> resultPois) {
                        pois.clear();
                        pois.addAll(resultPois);
                        poiListAdapter.setCurrentPoiId(poiId);
                        poiListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * @param view 点击选择城市按钮
     */
    public void chooseCity(View view) {
        CityListActivity.start(this,cityName);
    }

    /**
     * @param view 点击选择地址按钮
     */
    public void chooseAddr(View view) {
        ChooseAddrActivity.start(this,cityName);
    }
}
