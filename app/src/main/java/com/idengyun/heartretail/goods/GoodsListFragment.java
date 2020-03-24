package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.GoodsListBean;
import com.idengyun.heartretail.viewmodel.GoodsViewModel;
import com.idengyun.msgmodule.RVLoadMore;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表
 *
 * @author aLang
 */
public final class GoodsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recycler_view;
    private View tv_home_load_more;
    private GoodsViewModel goodsViewModel;
    private int goodsType = 0;

    /* 分页加载 */
    private int totalSize = 0;
    private int totalPage = 0;
    private int pageSize = 0;
    private int currentPage = 0;

    private GoodsAdapter goodsAdapter;
    private final RVLoadMore onScrollListener = new RVLoadMore() {
        @Override
        public void onLoadingMore(RVLoadMore listener) {
            onLoadMore();
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String tag = getTag();
        if ("零售区".equals(tag)) goodsType = 0;
        else if ("批发区".equals(tag)) goodsType = 1;
        observe();
        goodsAdapter = new GoodsAdapter();
        recycler_view.addOnScrollListener(onScrollListener);
        recycler_view.setAdapter(goodsAdapter);
        RecyclerView.LayoutManager lm = recycler_view.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            GridLayoutManager glm = (GridLayoutManager) lm;
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == glm.getChildCount() - 1 ? 2 : 1;
                }
            });
        }
        onRefresh();
    }

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) return;
        if (getUserVisibleHint()) {
            setUserVisibleHint(false);
            onRefresh();
        }
    }*/

    @Override
    public void onRefresh() {
        totalSize = 0;
        totalPage = 0;
        pageSize = 10;
        currentPage = 0;
        onScrollListener.setCanLoadMore(true);
        onLoadMore();
    }

    private void onLoadMore() {
        if (goodsViewModel == null) return;
        goodsViewModel.requestGoodsList(this, goodsType, currentPage + 1, pageSize, 0, 0);
    }

    private void observe() {
        if (goodsViewModel == null) {
            goodsViewModel = GoodsViewModel.getInstance(this);
            goodsViewModel.getGoodsList().observe(this, new Observer<GoodsListBean>() {
                @Override
                public void onChanged(@Nullable GoodsListBean goodsListBean) {
                    updateUI(goodsListBean);
                }
            });
        }
    }

    private void updateUI(@Nullable GoodsListBean goodsListBean) {
        onScrollListener.setLoadingMore(false);
        if (goodsListBean == null) return;
        GoodsListBean.Data data = goodsListBean.data;
        List<GoodsListBean.Data.Goods> goodsList = data.goods;

        totalSize = data.total;
        totalPage = data.pages;
        currentPage = data.current;
        onScrollListener.setCanLoadMore(currentPage < totalPage);

        if (currentPage == 1) goodsAdapter.goodsList.clear();
        goodsAdapter.goodsList.addAll(goodsList);
        if (!onScrollListener.isCanLoadMore()) {
            GoodsListBean.Data.Goods goods = new GoodsListBean.Data.Goods();
            goods.goodsId = -1;
            goodsAdapter.goodsList.add(goods);
        }
        goodsAdapter.notifyDataSetChanged();
        // tv_home_load_more.setVisibility(onScrollListener.isCanLoadMore() ? View.VISIBLE : View.GONE);
    }

    private void findViewById(View view) {
        recycler_view = view.findViewById(R.id.recycler_view);
        tv_home_load_more = view.findViewById(R.id.tv_home_load_more);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setFocusableInTouchMode(false);
        recycler_view.setFocusable(false);
        tv_home_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadMore();
            }
        });
    }

    private static class GoodsAdapter extends RecyclerView.Adapter {

        private final List<GoodsListBean.Data.Goods> goodsList = new ArrayList<>();
        private LayoutInflater inflater;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            if (-1 == viewType) {
                View itemView = inflater.inflate(R.layout.view_type_no_more, parent, false);
                return new RecyclerView.ViewHolder(itemView) {
                };
            }
            View itemView = inflater.inflate(R.layout.fragment_goods_list_item, parent, false);
            return new GoodsHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) != -1) {
                GoodsListBean.Data.Goods goods = goodsList.get(position);
                ((GoodsHolder) holder).updateUI(goods);
            }
        }

        @Override
        public int getItemCount() {
            return goodsList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (goodsList.get(position).goodsId == -1) return -1;
            return super.getItemViewType(position);
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
            tv_home_goods_price.setSelected(goodsType == 1);
        }

        private void findViewById(@NonNull View itemView) {
            iv_home_goods_url = itemView.findViewById(R.id.iv_home_goods_url);
            tv_home_goods_name = itemView.findViewById(R.id.tv_home_goods_name);
            tv_home_goods_price = itemView.findViewById(R.id.tv_home_goods_price);
        }
    }
}
