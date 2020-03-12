package com.idengyun.maplibrary.citylist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.GlobalProperty;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idengyun.maplibrary.R;
import com.idengyun.maplibrary.beans.CityBean;
import com.idengyun.maplibrary.beans.EventChooseAddrTip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityListActivity extends BaseActivity {

    //标题上的选择的城市控件
    private TextView tvMapSelectCity;
    //标题上的选择的地址的控件
    private TextView tvMapSelectAddr;

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private CityListAdapter adapter;
    private List<CityBean> listCity = new ArrayList<>();


    private String cityName;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> sourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    /**
     * 从地图页面跳过来要带着城市名称
     * @param city
     */
    public static void start(Context context, String city) {
        Intent starter = new Intent(context, CityListActivity.class);
        starter.putExtra("cityName", city);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        registBus();
        tvMapSelectCity = findViewById(R.id.tv_map_select_city);
        tvMapSelectAddr = findViewById(R.id.tv_map_select_addr);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        //初始化cityName
        initCityName();

        ApiBean<List<CityBean>> apiBean = new Gson().fromJson(CityList.cityjson, new TypeToken<ApiBean<List<CityBean>>>() {
        }.getType());
        listCity.addAll(apiBean.getData());
        initViews();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initCityName();
    }

    /**
     * 初始化城市名
     */
    private void initCityName() {
        cityName = getIntent().getStringExtra("cityName");
        tvMapSelectCity.setText(cityName);
    }

    /*选择完地址*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventChooseAddrTip eventChooseAddrTip) {
       this.finish();
    }


    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*CityBean cityBean = new CityBean();
                cityBean.setId(sourceDateList.get(position).getCityid());
                cityBean.setName(sourceDateList.get(position).getName());*/

                SortModel sortModel = sourceDateList.get(position);


                GlobalProperty.getInstance().setCity(sourceDateList.get(position).getName());
                GlobalProperty.getInstance().setLocationId(sourceDateList.get(position).getCityid());
                GlobalProperty.getInstance().setProvince("");
                GlobalProperty.getInstance().setDistrict("");

                cityName = sourceDateList.get(position).getName();
                tvMapSelectCity.setText(cityName);


            }
        });
        sourceDateList = filledData(listCity);

        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);
        adapter = new CityListAdapter(this, sourceDateList);
        sortListView.setAdapter(adapter);


    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<CityBean> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getName());
            sortModel.setCityid(date.get(i).getId());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            if (sortModel.getName().equals("重庆市")) {
                sortModel.setSortLetters("C");
            }
            if (sortModel.getName().equals("衢州市")) {
                sortModel.setSortLetters("Q");
            }
            if (sortModel.getName().equals("儋州市")) {
                sortModel.setSortLetters("D");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    public void back(View view) {
        this.finish();
    }

    /**
     * @param view 点击选择城市按钮
     */
    public void chooseCity(View view) {

    }

    /**
     * @param view 点击选择地址按钮
     */
    public void chooseAddr(View view) {
        ChooseAddrActivity.start(this,cityName);
    }
}
