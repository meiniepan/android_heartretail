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
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.maplibrary.utils.AmapLocationUtil;
import com.idengyun.maplibrary.utils.PoiListComparator;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.maplibrary.view.DyMapView;
import com.idengyun.maplibrary.view.PoiScrollLayout;
import com.idengyun.maplibrary.view.PoiScrollRecyclerView;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;

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
    private String cityName,nearShop;
    //当前选中的poi数据下标
    private int currentIndex = 0;

    //poi数据列表适配器
    private PoiListAdapter poiListAdapter;
    private ArrayList<PoiItem> pois = new ArrayList<>();



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
        //findview
        mMapView = findViewById(R.id.map);
        tvMapSelectCity = findViewById(R.id.tv_map_select_city);
        tvMapSelectAddr = findViewById(R.id.tv_map_select_addr);
        slMapPoi = findViewById(R.id.sl_map_poi);
        rvMapPoi = findViewById(R.id.rv_map_poi);

        //getIntent
        cityName = getIntent().getStringExtra("cityName");
        nearShop = getIntent().getStringExtra("nearShop");

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

    public void back(View view) {
        this.finish();
    }

    private final int minBottomHeight = SizeUtils.dp2px(192);
    private final int maxBottomHeight = SizeUtils.dp2px(392);

    private int bottomPoiHeight;

    private void initPoiList() {
        bottomPoiHeight = minBottomHeight;

        rvMapPoi.setLayoutManager(new LinearLayoutManager(this));
        rvMapPoi.addItemDecoration(new RecycleViewDivider(this, SizeUtils.dp2px(10)));
        poiListAdapter = new PoiListAdapter(R.layout.item_map_poi,pois);
        rvMapPoi.setAdapter(poiListAdapter);
        poiListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentIndex = position;
                tvMapSelectAddr.setText(pois.get(currentIndex).getTitle());
            }
        });
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
                                ArrayList<PoiItem> resultPois = poiResult.getPois();
                                if (!ListUtils.isEmpty(resultPois)){
                                    Collections.sort(resultPois,new PoiListComparator());
                                    currentIndex = 0;
                                    pois.clear();
                                    pois.addAll(resultPois);
                                    poiListAdapter.setCurrentIndex(currentIndex);
                                    poiListAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onPoiItemSearched(PoiItem poiItem, int i) {

                            }
                        });
            }
        });
    }

}
