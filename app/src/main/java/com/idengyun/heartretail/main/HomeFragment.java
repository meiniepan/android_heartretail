package com.idengyun.heartretail.main;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.activitys.ShareQRCodeActivity;
import com.idengyun.heartretail.goods.GoodsDetailFragment;
import com.idengyun.heartretail.goods.GoodsEvaluateFragment;
import com.idengyun.heartretail.goods.GoodsSPUFragment;
import com.idengyun.heartretail.goods.GoodsServiceFragment;
import com.idengyun.heartretail.goods.GoodsSpecFragment;
import com.idengyun.heartretail.model.response.GoodsListBean;
import com.idengyun.heartretail.viewmodel.GoodsViewModel;
import com.idengyun.maplibrary.MyMapActivity;
import com.idengyun.maplibrary.beans.EventChoosePoiItem;
import com.idengyun.maplibrary.utils.AmapLocationWapper;
import com.idengyun.maplibrary.utils.PoiSearchUtil;
import com.idengyun.msgmodule.NoticeActivity;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.LoginActivity;
import com.idengyun.usermodule.VerifyDeviceActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * @author aLang
 */
public final class HomeFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private View tv_home_share;
    private View tv_home_notice;
    //首页左上角定位的tv
    private TextView tvHomeLocation;
    private NestedScrollView nsv_home_bg;
    private NestedScrollView nested_scroll_view;

    private View iv_home_goods_indicator_retail;
    private View iv_home_goods_indicator_wholesale;
    private View iv_home_goods_retail;
    private View iv_home_goods_wholesale;
    private RadioGroup rg_home_tab_bar;

    private RecyclerView recycler_view;

    private GoodsViewModel goodsViewModel;

    //定位功能的包装类
    private AmapLocationWapper amapLocationWapper;
    //定位（选择poi点）的城市名称、poi名称、poiId
    private String cityName, poiName, poiId;

    private HomeAdapter homeAdapter;
    private List<GoodsListBean.Data.Goods> retailGoodsList = new ArrayList<>();
    private List<GoodsListBean.Data.Goods> wholesaleGoodsList = new ArrayList<>();

    private int goodsType = 0;

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
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        amapLocationWapper.stopLocation();
        amapLocationWapper.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) return;
        if (rg_home_tab_bar.getCheckedRadioButtonId() == View.NO_ID)
            rg_home_tab_bar.check(R.id.rb_home_retail);
        // requestAPI();
    }

    @Override
    public void onClick(View v) {
        if (tv_home_share == v) {
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
        } else if (tvHomeLocation == v) {
            if (TextUtils.isEmpty(cityName)) {
                ToastUtils.showShort("还没有定位成功");
            } else {
                MyMapActivity.start(getContext(), cityName, poiName, poiId);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (R.id.rb_home_retail == checkedId) {
            iv_home_goods_indicator_retail.setVisibility(View.VISIBLE);
            iv_home_goods_indicator_wholesale.setVisibility(View.INVISIBLE);
            goodsType = 0;
            requestAPI();
        } else if (R.id.rb_home_wholesale == checkedId) {
            iv_home_goods_indicator_retail.setVisibility(View.INVISIBLE);
            iv_home_goods_indicator_wholesale.setVisibility(View.VISIBLE);
            goodsType = 1;
            requestAPI();
        }
    }

    /*选择完地址*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventChoosePoiItem eventChoosePoiItem) {
        cityName = eventChoosePoiItem.poiItem.getCityName();
        poiName = eventChoosePoiItem.poiItem.getTitle();
        poiId = eventChoosePoiItem.poiItem.getPoiId();
        tvHomeLocation.setText(poiName);
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
                                if (null != tvHomeLocation) {
                                    PoiItem poiItem = pois.get(0);
                                    cityName = poiItem.getCityName();
                                    poiName = poiItem.getTitle();
                                    poiId = poiItem.getPoiId();
                                    tvHomeLocation.setText(poiItem.getTitle());
                                    //设置全局的定位属性
                                    PoiSearchUtil.setGlobalLocationByPoiItem(poiItem);
                                }
                            }
                        });
            }
        });
    }

    private void init() {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (goodsViewModel == null) {
            goodsViewModel = GoodsViewModel.getInstance(activity);
            goodsViewModel.getGoodsList().observe(this, new Observer<GoodsListBean>() {
                @Override
                public void onChanged(@Nullable GoodsListBean goodsListBean) {
                    updateUI(goodsListBean);
                }
            });
        }

        nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println(scrollY);
                nsv_home_bg.setTranslationY(-scrollY);
                nsv_home_bg.setBackgroundColor(Color.MAGENTA);
            }
        });
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println(recyclerView);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                System.out.println("dy=" + dy);
                if (dy > 0) {
                    //nested_scroll_view.setTranslationY(nested_scroll_view.getTranslationY() - dy);
                    //nested_scroll_view.scrollBy(0, dy);
                }
            }
        });

        /*if (recycler_view.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager glm = (GridLayoutManager) recycler_view.getLayoutManager();
            glm.setSpanCount(2);
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 0 ? 2 : 1;
                }
            });
        }*/


        homeAdapter = new HomeAdapter();
//        for (int i = 0; i < 20; i++) goodsAdapter.goodsList.add("");
        recycler_view.setAdapter(homeAdapter);

//        DisplayMetrics outMetrics = new DisplayMetrics();
//        getActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//        int widthPixels = outMetrics.widthPixels;
//        int heightPixels = outMetrics.heightPixels;
//        ViewGroup.LayoutParams params = recycler_view.getLayoutParams();
//        params.height = heightPixels;
//        recycler_view.setLayoutParams(params);
//        recycler_view.setBackgroundColor(Color.MAGENTA);
        recycler_view.setNestedScrollingEnabled(false);
    }


    private void requestAPI() {
        if (goodsViewModel != null) goodsViewModel.requestGoodsList(this, goodsType, 1, 10, 0, 0);
    }

    @MainThread
    private void updateUI(@Nullable GoodsListBean goodsListBean) {
        if (goodsListBean == null) return;
        GoodsListBean.Data data = goodsListBean.data;
        int current = data.current;
        int pages = data.pages;
        int total = data.total;
        List<GoodsListBean.Data.Goods> goodsList = data.goods;
        if (goodsType == 0) {
            retailGoodsList.addAll(goodsList);
            homeAdapter.homeList.clear();
            //homeAdapter.homeList.add(0, null);
            homeAdapter.homeList.addAll(retailGoodsList);
            homeAdapter.notifyDataSetChanged();
        } else if (goodsType == 1) {
            wholesaleGoodsList.addAll(goodsList);
            homeAdapter.homeList.clear();
//            homeAdapter.homeList.add(0, null);
            homeAdapter.homeList.addAll(wholesaleGoodsList);
            homeAdapter.notifyDataSetChanged();
        }
    }

    private void findViewById(@NonNull View view) {
        tv_home_share = view.findViewById(R.id.tv_home_share);
        tv_home_notice = view.findViewById(R.id.tv_home_notice);
        tvHomeLocation = view.findViewById(R.id.tv_home_location);
        nsv_home_bg = view.findViewById(R.id.nsv_home_bg);
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);

        iv_home_goods_indicator_retail = view.findViewById(R.id.iv_home_goods_indicator_retail);
        iv_home_goods_indicator_wholesale = view.findViewById(R.id.iv_home_goods_indicator_wholesale);
        iv_home_goods_retail = view.findViewById(R.id.iv_home_goods_retail);
        iv_home_goods_wholesale = view.findViewById(R.id.iv_home_goods_wholesale);
        rg_home_tab_bar = view.findViewById(R.id.rg_home_tab_bar);

        recycler_view = view.findViewById(R.id.recycler_view);
        tvHomeLocation.setOnClickListener(this);
        tv_home_share.setOnClickListener(this);
        tv_home_notice.setOnClickListener(this);
        rg_home_tab_bar.setOnCheckedChangeListener(this);
        rg_home_tab_bar.clearCheck();
    }

    private static class HomeAdapter extends RecyclerView.Adapter {
        private LayoutInflater inflater;
        final List<GoodsListBean.Data.Goods> homeList = new ArrayList<>();

        public HomeAdapter() {
            // homeList.add(0, null);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
//            if (viewType == 1) {
//                View itemView = inflater.inflate(R.layout.fragment_home_header, parent, false);
//                return new HomeHeaderHolder(itemView);
//            } else {
//                View itemView = inflater.inflate(R.layout.fragment_home_goods_item, parent, false);
//                return new GoodsHolder(itemView);
//            }
            View itemView = inflater.inflate(R.layout.fragment_home_goods_item, parent, false);
            return new GoodsHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            /*if (getItemViewType(position) != 1) {
                GoodsHolder goodsHolder = (GoodsHolder) holder;
                GoodsListBean.Data.Goods goods = homeList.get(position);
                goodsHolder.updateUI(goods);
            } else {
                holder.itemView.setVisibility(View.GONE);
            }*/
            GoodsHolder goodsHolder = (GoodsHolder) holder;
            GoodsListBean.Data.Goods goods = homeList.get(position);
            goodsHolder.updateUI(goods);
        }

        @Override
        public int getItemCount() {
            return homeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 1 : super.getItemViewType(position);
        }
    }

    private static class GoodsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_home_goods_url;
        private TextView tv_home_goods_name;
        private TextView tv_home_goods_price;

        GoodsHolder(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof GoodsListBean.Data.Goods) {
                int goodsId = ((GoodsListBean.Data.Goods) tag).goodsId;
                int goodsType = ((GoodsListBean.Data.Goods) tag).goodsType;
                Bundle extras = new Bundle();
                extras.putInt("home_goods_id", goodsId);
                extras.putInt("home_goods_type", goodsType);
                HRActivity.start(v.getContext(), extras, GoodsSPUFragment.class, GoodsEvaluateFragment.class, GoodsDetailFragment.class, GoodsSpecFragment.class, GoodsServiceFragment.class);
            }
        }

        @MainThread
        void updateUI(GoodsListBean.Data.Goods goods) {
            itemView.setTag(goods);
            String goodsImgUrl = goods.goodsImgUrl;
            String retailPrice = goods.retailPrice;
            String wholesalePrice = goods.wholesalePrice;
            String goodsName = goods.goodsName;
            int goodsType = goods.goodsType;
            String price = goodsType == 1 ? wholesalePrice : retailPrice;
            ImageApi.displayImage(iv_home_goods_url.getContext(), iv_home_goods_url, goodsImgUrl);
            tv_home_goods_name.setText(goodsName);
            tv_home_goods_price.setText("¥" + price);
        }

        private void findViewById(@NonNull View itemView) {
            iv_home_goods_url = itemView.findViewById(R.id.iv_home_goods_url);
            tv_home_goods_name = itemView.findViewById(R.id.tv_home_goods_name);
            tv_home_goods_price = itemView.findViewById(R.id.tv_home_goods_price);
        }
    }

    private static class HomeHeaderHolder extends RecyclerView.ViewHolder {

        private ImageView iv_home_header_1;
        private ImageView iv_home_header_2;
        private ImageView iv_home_header_3;
        private ImageView iv_home_header_4;

        HomeHeaderHolder(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        @MainThread
        void updateUI() {

        }

        private void findViewById(@NonNull View itemView) {
            iv_home_header_1 = itemView.findViewById(R.id.iv_home_header_1);
            iv_home_header_2 = itemView.findViewById(R.id.iv_home_header_2);
            iv_home_header_3 = itemView.findViewById(R.id.iv_home_header_3);
            iv_home_header_4 = itemView.findViewById(R.id.iv_home_header_4);
        }
    }
}