package com.idengyun.heartretail.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.dengyun.baselibrary.base.fragment.BaseFragment;
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
import com.idengyun.heartretail.notice.NoticeFragment;
import com.idengyun.heartretail.viewmodel.GoodsViewModel;
import com.idengyun.maplibrary.MyMapActivity;
import com.idengyun.maplibrary.beans.EventChoosePoiItem;
import com.idengyun.maplibrary.utils.AmapLocationWapper;
import com.idengyun.maplibrary.utils.PoiSearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * @author aLang
 */
public final class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View tv_home_share;
    private View tv_home_notice;
    //首页左上角定位的tv
    private TextView tvHomeLocation;
    private NestedScrollView nested_scroll_view;
    private RecyclerView recycler_view;

    private GoodsViewModel goodsViewModel;

    //定位功能的包装类
    private AmapLocationWapper amapLocationWapper;
    //定位（选择poi点）的城市名称、poi名称、poiId
    private String cityName, poiName, poiId;

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
        updateUI();
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
        requestAPI();
    }

    @Override
    public void onClick(View v) {
        if (tv_home_share == v) {
            ShareQRCodeActivity.start(getContext());
        } else if (tv_home_notice == v) {
            HRActivity.start(getContext(), NoticeFragment.class);
        } else if (tvHomeLocation == v) {
            if (TextUtils.isEmpty(cityName)) {
                ToastUtils.showShort("还没有定位成功");
            } else {
                MyMapActivity.start(getContext(), cityName, poiName, poiId);
            }
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
        if (getActivity() != null & goodsViewModel == null) {
            goodsViewModel = GoodsViewModel.getInstance(getActivity());
            goodsViewModel.getGoodsList().observe(this, new Observer<GoodsListBean>() {
                @Override
                public void onChanged(@Nullable GoodsListBean goodsListBean) {
                    updateUI(goodsListBean);
                }
            });
        }
    }


    private void requestAPI() {
        if (goodsViewModel != null) goodsViewModel.requestGoodsList(this, 0, 1, 10, 0, 0);
    }

    @MainThread
    private void updateUI(@Nullable GoodsListBean goodsListBean) {
        if (goodsListBean == null) return;
        GoodsListBean.Data data = goodsListBean.data;
        int current = data.current;
        int pages = data.pages;
        int total = data.total;
        List<GoodsListBean.Data.Goods> goodsList = data.goods;
    }

    @MainThread
    private void updateUI() {

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println(recyclerView);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                nested_scroll_view.scrollBy(0, dy);

                System.out.println("dy=" + dy);
                System.out.println(nested_scroll_view.getScrollY());
            }
        });

        GoodsAdapter goodsAdapter = new GoodsAdapter();
        for (int i = 0; i < 2; i++) goodsAdapter.goodsList.add("");
        recycler_view.setAdapter(goodsAdapter);
    }


    private void findViewById(@NonNull View view) {
        tv_home_share = view.findViewById(R.id.tv_home_share);
        tv_home_notice = view.findViewById(R.id.tv_home_notice);
        tvHomeLocation = view.findViewById(R.id.tv_home_location);
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);
        recycler_view = view.findViewById(R.id.recycler_view);
        tvHomeLocation.setOnClickListener(this);
        tv_home_share.setOnClickListener(this);
        tv_home_notice.setOnClickListener(this);
    }

    private class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder> {
        private LayoutInflater inflater;
        final List<String> goodsList = new ArrayList<>();

        @NonNull
        @Override
        public GoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.fragment_home_item, parent, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HRActivity.start(
                            getContext(),
                            null,
                            GoodsSPUFragment.class,
                            GoodsEvaluateFragment.class,
                            GoodsDetailFragment.class,
                            GoodsSpecFragment.class,
                            GoodsServiceFragment.class
                    );
                }
            });
            return new GoodsHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsHolder holder, int position) {
            holder.updateUI();
        }

        @Override
        public int getItemCount() {
            return goodsList.size();
        }

        private class GoodsHolder extends RecyclerView.ViewHolder {

            private ImageView iv_home_goods_url;
            private TextView tv_home_goods_name;
            private TextView tv_home_goods_price;

            GoodsHolder(@NonNull View itemView) {
                super(itemView);
                findViewById(itemView);
            }

            @MainThread
            void updateUI() {
                iv_home_goods_url.setImageResource(R.drawable.ic_home_red_packet);
                tv_home_goods_name.setText("这里是一段商品标题信息最多展示2行");
                tv_home_goods_price.setText("¥123.45");
            }

            private void findViewById(@NonNull View itemView) {
                iv_home_goods_url = itemView.findViewById(R.id.iv_home_goods_url);
                tv_home_goods_name = itemView.findViewById(R.id.tv_home_goods_name);
                tv_home_goods_price = itemView.findViewById(R.id.tv_home_goods_price);
            }
        }

    }
}