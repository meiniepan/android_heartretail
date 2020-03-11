package com.idengyun.maplibrary.citylist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.ListUtils;
import com.idengyun.maplibrary.R;
import com.idengyun.maplibrary.beans.EventChooseAddrTip;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ChooseAddrActivity extends BaseActivity {

    //显示城市的TV
    private TextView tvMapSelectCity;
    //输入地址的TV
    private EditText etMapSelectAddr;
    //提示的地址的RV
    private RecyclerView rvAddrRecommend;
    //从城市列表传过来的城市名称
    private String cityName;

    //下方的提示的地址列表的数据源和适配器
    private ArrayList<Tip> tips = new ArrayList<>();
    private ChooseAddrAdapter chooseAddrAdapter;

    public static void start(Context context,String cityName) {
        Intent starter = new Intent(context, ChooseAddrActivity.class);
        starter.putExtra("cityName",cityName);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_addr;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvMapSelectCity = findViewById(R.id.tv_map_select_city);
        etMapSelectAddr = findViewById(R.id.et_map_select_addr);
        rvAddrRecommend = findViewById(R.id.rv_addr_recommend);
        cityName = getIntent().getStringExtra("cityName");
        tvMapSelectCity.setText(cityName);
        addEditTextChangedListener();
        initAddrRV();

    }

    private void initAddrRV() {
        rvAddrRecommend.setLayoutManager(new LinearLayoutManager(this));
        rvAddrRecommend.addItemDecoration(new RecycleViewDivider(1, Color.parseColor("#DDDDDD")));
        chooseAddrAdapter = new ChooseAddrAdapter(R.layout.item_choose_addr,tips);
        rvAddrRecommend.setAdapter(chooseAddrAdapter);
        chooseAddrAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Tip tip = tips.get(position);
                EventChooseAddrTip eventChooseAddrTip = new EventChooseAddrTip(cityName,tip.getPoiID(),tip.getName());
                EventBus.getDefault().post(eventChooseAddrTip);
                ChooseAddrActivity.this.finish();
            }
        });
    }

    private void addEditTextChangedListener() {
        etMapSelectAddr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null!=s && !TextUtils.isEmpty(s.toString())){
                    String addr = s.toString();
                    searchPoiTips(addr);
                }else {
                    tips.clear();
                    chooseAddrAdapter.setSearchStr(null);
                    chooseAddrAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void searchPoiTips(String newStr){
        PoiSearchUtil.getInstance().searchPoiTips(this, cityName, newStr, new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int i) {
                if (!ListUtils.isEmpty(list)){
                    tips.clear();
                    for (int j = 0; j < list.size(); j++) {
                        if (null!=list.get(j).getPoint()){
                            tips.add(list.get(j));
                        }
                    }
                    chooseAddrAdapter.setSearchStr(newStr);
                    chooseAddrAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void back(View view) {
        this.finish();
    }

    /**
     * @param view 点击选择城市按钮
     */
    public void chooseCity(View view) {
        CityListActivity.start(this,cityName);
        finish();
    }
}
