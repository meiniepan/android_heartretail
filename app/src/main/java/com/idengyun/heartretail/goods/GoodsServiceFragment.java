package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.GoodsDetailBean;
import com.idengyun.heartretail.viewmodel.GoodsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-服务
 *
 * @author aLang
 */
public final class GoodsServiceFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recycler_view;

    private ServiceAdapter serviceAdapter;
    private GoodsViewModel goodsViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_service;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observe();
        serviceAdapter = new ServiceAdapter();
        recycler_view.setAdapter(serviceAdapter);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) fragmentManager.beginTransaction().hide(this).commit();
    }

    @MainThread
    private void updateUI(@Nullable GoodsDetailBean goodsDetailBean) {
        if (goodsDetailBean == null) return;
        GoodsDetailBean.Data data = goodsDetailBean.data;
        List<GoodsDetailBean.Data.Rule> ruleList = data.ruleList;

        serviceAdapter.ruleList.clear();
        serviceAdapter.ruleList.addAll(ruleList);
        serviceAdapter.notifyDataSetChanged();
    }

    private void observe() {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (goodsViewModel == null) {
            goodsViewModel = GoodsViewModel.getInstance(activity);
            goodsViewModel.getGoodsDetail().observe(this, new Observer<GoodsDetailBean>() {
                @Override
                public void onChanged(@Nullable GoodsDetailBean goodsDetailBean) {
                    updateUI(goodsDetailBean);
                }
            });
        }
    }

    private void findViewById(@NonNull View view) {
        recycler_view = view.findViewById(R.id.recycler_view);
        view.setOnClickListener(this);
    }

    private static class ServiceAdapter extends RecyclerView.Adapter<ServiceHolder> {
        private LayoutInflater inflater;
        final ArrayList<GoodsDetailBean.Data.Rule> ruleList = new ArrayList<>();

        @NonNull
        @Override
        public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.fragment_goods_service_item, parent, false);
            return new ServiceHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
            holder.updateUI(ruleList.get(position));
        }

        @Override
        public int getItemCount() {
            return ruleList.size();
        }
    }

    private static class ServiceHolder extends RecyclerView.ViewHolder {

        private TextView tv_goods_service_title;
        private TextView tv_goods_service_content;

        private ServiceHolder(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        public void updateUI(GoodsDetailBean.Data.Rule rule) {
            String title = rule.title;
            String content = rule.content;
            tv_goods_service_title.setText(title);
            tv_goods_service_content.setText(Html.fromHtml(content));
        }

        private void findViewById(@NonNull View itemView) {
            tv_goods_service_title = itemView.findViewById(R.id.tv_goods_service_title);
            tv_goods_service_content = itemView.findViewById(R.id.tv_goods_service_content);
        }
    }
}
