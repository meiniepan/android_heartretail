package com.idengyun.heartretail.main;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.goods.GoodsDetailFragment;
import com.idengyun.heartretail.goods.GoodsEvaluateFragment;
import com.idengyun.heartretail.goods.GoodsInfoFragment;
import com.idengyun.heartretail.goods.GoodsServiceFragment;
import com.idengyun.heartretail.goods.GoodsSpecFragment;
import com.idengyun.heartretail.model.response.GoodsListBean;
import com.idengyun.heartretail.notice.NoticeFragment;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

/**
 * 首页
 *
 * @author aLang
 */
public final class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View tv_home_share;
    private View tv_home_notice;
    private NestedScrollView nested_scroll_view;
    private RecyclerView recycler_view;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateUI();
    }

    @Override
    public void onClick(View v) {
        if (tv_home_share == v) {

        } else if (tv_home_notice == v) {
            HRActivity.start(getContext(), NoticeFragment.class);
        }
    }

    private void requestAPI() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.goodsList())
                .fragment(this)
                .clazz(GoodsListBean.class)
                .params("userId", HRUser.getId())
                .params("goodsType", 0)
                .params("page", 1)
                .params("pageSize", 10)
                .params("sortType", 0)
                .params("sortStyle", 0)
                .build();
        NetApi.<GoodsListBean>getData(netOption, new JsonCallback<GoodsListBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsListBean> response) {
                updateUI(response.body().data);
            }
        });
    }

    @MainThread
    private void updateUI(GoodsListBean.Data data) {
    }

    @MainThread
    private void updateUI() {
        tv_home_notice.setOnClickListener(this);

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
        for (int i = 0; i < 2; i++) goodsAdapter.items.add("");
        recycler_view.setAdapter(goodsAdapter);
    }


    private void findViewById(@NonNull View view) {
        tv_home_share = view.findViewById(R.id.tv_home_share);
        tv_home_notice = view.findViewById(R.id.tv_home_notice);
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);
        recycler_view = view.findViewById(R.id.recycler_view);
    }

    private class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder> {
        ArrayList<String> items = new ArrayList<>();
        private LayoutInflater inflater;

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
                            GoodsInfoFragment.class,
                            GoodsEvaluateFragment.class,
                            GoodsSpecFragment.class,
                            GoodsServiceFragment.class,
                            GoodsDetailFragment.class
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
            return items.size();
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