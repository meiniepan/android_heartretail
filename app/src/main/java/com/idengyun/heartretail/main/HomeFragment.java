package com.idengyun.heartretail.main;

import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.activitys.ShareQRCodeActivity;
import com.idengyun.heartretail.goods.GoodsListFragment;
import com.idengyun.maplibrary.MyMapActivity;
import com.idengyun.maplibrary.beans.EventChoosePoiItem;
import com.idengyun.maplibrary.utils.AmapLocationWapper;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.msgmodule.NoticeActivity;
import com.idengyun.msgmodule.beans.NoticeCountBean;
import com.idengyun.msgmodule.viewmodel.NoticeViewModel;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.LoginActivity;
import com.idengyun.usermodule.VerifyDeviceActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 首页
 *
 * @author aLang
 */
public final class HomeFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (NoticeActivity.ACTION_ON_NOTICE_ARRIVED.equals(action) || LoginActivity.ACTION_ON_LOGIN_SUCCEED.equals(action)) {
                requestNoticeCount();
            }
        }
    };
    private NoticeViewModel noticeViewModel;

    private View layout_home_title;
    private TextView tv_home_location;
    private View tv_home_share;
    private View tv_home_notice;
    private TextView tv_home_notice_count;

    private NestedScrollView nsv_home_bg;
    private NestedScrollView nested_scroll_view;

    private View tv_home_rule;

    private View iv_home_goods_indicator_retail;
    private View iv_home_goods_indicator_wholesale;
    private View iv_home_goods_retail;
    private View iv_home_goods_wholesale;
    private RadioGroup rg_home_tab_bar;

    //定位功能的包装类
    private AmapLocationWapper amapLocationWapper;
    //定位（选择poi点）的城市名称、poi名称、poiId
    private String cityName, poiName, poiId;

    private GoodsListFragment retailFragment;
    private GoodsListFragment wholesaleFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        if (context != null) {
            IntentFilter filter = new IntentFilter(NoticeActivity.ACTION_ON_NOTICE_ARRIVED);
            filter.addAction(LoginActivity.ACTION_ON_LOGIN_SUCCEED);
            context.registerReceiver(receiver, filter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
        registBus();
        startLocation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observe();
        retailFragment = new GoodsListFragment();
        wholesaleFragment = new GoodsListFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.layout_home_content, retailFragment, "零售区")
                .add(R.id.layout_home_content, wholesaleFragment, "批发区")
                .hide(retailFragment)
                .hide(wholesaleFragment)
                .commit();
        nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                nsv_home_bg.setTranslationY(-scrollY);

                int height = SizeUtils.dp2px(68f);
                if (scrollY < height) {
                    layout_home_title.setBackgroundColor(Color.TRANSPARENT);
                    tv_home_location.setSelected(false);
                    tv_home_share.setSelected(false);
                    tv_home_notice.setSelected(false);
                    tv_home_notice_count.setSelected(false);
                } else {
                    layout_home_title.setBackgroundColor(Color.WHITE);
                    tv_home_location.setSelected(true);
                    tv_home_share.setSelected(true);
                    tv_home_notice.setSelected(true);
                    tv_home_notice_count.setSelected(true);
                }
            }
        });
        requestNoticeCount();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        amapLocationWapper.stopLocation();
        amapLocationWapper.onDestroy();
    }

    @Override
    public void onDestroy() {
        Context context = getContext();
        if (context != null) context.unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) return;
        if (rg_home_tab_bar.getCheckedRadioButtonId() == View.NO_ID)
            rg_home_tab_bar.check(R.id.rb_home_retail);
    }

    @Override
    public void onClick(View v) {
        if (tv_home_share == v) {
            if (!HRUser.isLogin()) {
                LoginActivity.start(getContext());
                return;
            }

            if (!HRUser.isAuthentication()) {
                VerifyDeviceActivity.start(getContext());
                return;
            }

            ShareQRCodeActivity.start(getContext());
        } else if (tv_home_notice == v) {
            if (!HRUser.isLogin()) {
                LoginActivity.start(getContext());
                return;
            }

            if (!HRUser.isAuthentication()) {
                VerifyDeviceActivity.start(getContext());
                return;
            }

            NoticeActivity.start(getContext());
        } else if (tv_home_location == v) {
            if (TextUtils.isEmpty(cityName)) {
                ToastUtils.showShort("还没有定位成功");
            } else {
                MyMapActivity.start(getContext(), cityName, poiName, poiId);
            }
        } else if (tv_home_rule == v) {
            HRActivity.start(getContext(), PlayRuleDetailFragment.class);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (R.id.rb_home_retail == checkedId) {
            iv_home_goods_indicator_retail.setVisibility(View.VISIBLE);
            iv_home_goods_indicator_wholesale.setVisibility(View.INVISIBLE);
            getChildFragmentManager()
                    .beginTransaction()
                    .show(retailFragment)
                    .hide(wholesaleFragment)
                    .commit();
        } else if (R.id.rb_home_wholesale == checkedId) {
            iv_home_goods_indicator_retail.setVisibility(View.INVISIBLE);
            iv_home_goods_indicator_wholesale.setVisibility(View.VISIBLE);
            getChildFragmentManager()
                    .beginTransaction()
                    .show(wholesaleFragment)
                    .hide(retailFragment)
                    .commit();
        }
    }

    private void observe() {
        if (noticeViewModel == null) {
            noticeViewModel = NoticeViewModel.getInstance(this);
            noticeViewModel.getNoticeCount().observe(this, new Observer<NoticeCountBean>() {
                @Override
                public void onChanged(@Nullable NoticeCountBean noticeCountBean) {
                    updateNoticeCount(noticeCountBean);
                }
            });
        }
    }

    private void updateNoticeCount(@Nullable NoticeCountBean noticeCountBean) {
        if (noticeCountBean == null) return;
        List<NoticeCountBean.Data> dataList = noticeCountBean.data;

        int count = 0;
        for (NoticeCountBean.Data data : dataList) count += data.counts;
        String text = count > 999 ? "999+" : "" + count;
        tv_home_notice_count.setText(text);
        tv_home_notice_count.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void requestNoticeCount() {
        if (!HRUser.isLogin()) return;
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (noticeViewModel != null) noticeViewModel.requestNoticeCount(activity);
    }

    /*选择完地址*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventChoosePoiItem eventChoosePoiItem) {
        cityName = eventChoosePoiItem.poiItem.getCityName();
        poiName = eventChoosePoiItem.poiItem.getTitle();
        poiId = eventChoosePoiItem.poiItem.getPoiId();
        tv_home_location.setText(poiName);
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
                PoiSearchUtil.searchPOIWithBound(getContext(), latitude, longitude,
                        new PoiSearchUtil.OnPoiBoundSearchListener() {
                            @Override
                            public void onSearchResult(List<PoiItem> pois) {
                                //pois已经在上层判过空了
                                if (null != tv_home_location) {
                                    PoiItem poiItem = pois.get(0);
                                    cityName = poiItem.getCityName();
                                    poiName = poiItem.getTitle();
                                    poiId = poiItem.getPoiId();
                                    tv_home_location.setText(poiItem.getTitle());
                                    //设置全局的定位属性
                                    PoiSearchUtil.setGlobalLocationByPoiItem(poiItem);
                                }
                            }
                        });
            }
        });
    }

    private void findViewById(@NonNull View view) {
        layout_home_title = view.findViewById(R.id.layout_home_title);
        tv_home_location = view.findViewById(R.id.tv_home_location);
        tv_home_share = view.findViewById(R.id.tv_home_share);
        tv_home_notice = view.findViewById(R.id.tv_home_notice);
        tv_home_notice_count = view.findViewById(R.id.tv_home_notice_count);

        nsv_home_bg = view.findViewById(R.id.nsv_home_bg);
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);
        tv_home_rule = view.findViewById(R.id.tv_home_rule);

        iv_home_goods_indicator_retail = view.findViewById(R.id.iv_home_goods_indicator_retail);
        iv_home_goods_indicator_wholesale = view.findViewById(R.id.iv_home_goods_indicator_wholesale);
        iv_home_goods_retail = view.findViewById(R.id.iv_home_goods_retail);
        iv_home_goods_wholesale = view.findViewById(R.id.iv_home_goods_wholesale);
        rg_home_tab_bar = view.findViewById(R.id.rg_home_tab_bar);

        tv_home_location.setOnClickListener(this);
        tv_home_share.setOnClickListener(this);
        tv_home_notice.setOnClickListener(this);
        tv_home_rule.setOnClickListener(this);
        rg_home_tab_bar.setOnCheckedChangeListener(this);
        rg_home_tab_bar.clearCheck();
    }
}