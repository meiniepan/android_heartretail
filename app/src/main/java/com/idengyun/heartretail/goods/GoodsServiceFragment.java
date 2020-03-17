package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
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
import com.idengyun.heartretail.model.response.GoodsDetailBean;
import com.idengyun.heartretail.model.response.ProtocolsBean;
import com.idengyun.heartretail.viewmodel.AgreeViewModel;
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
    private AgreeViewModel agreeViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_service;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    List<Integer> protocolIds;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceAdapter = new ServiceAdapter();
        recycler_view.setAdapter(serviceAdapter);

        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (goodsViewModel == null) {
            goodsViewModel = GoodsViewModel.getInstance(activity);
            goodsViewModel.getGoodsDetail().observe(this, new Observer<GoodsDetailBean>() {
                @Override
                public void onChanged(@Nullable GoodsDetailBean goodsDetailBean) {
                    if (goodsDetailBean == null) return;
                    GoodsDetailBean.Data data = goodsDetailBean.data;
                    if (data == null) return;
                    List<GoodsDetailBean.Data.Protocol> protocolList = data.protocolList;
                    if (protocolList != null) {
                        protocolIds = new ArrayList<>();
                        for (int i = 0; i < protocolList.size(); i++) {
                            GoodsDetailBean.Data.Protocol protocol = protocolList.get(i);
                            protocolIds.add(protocol.protocolId);
                        }
                    }
                }
            });
        }
        if (agreeViewModel == null) {
            agreeViewModel = AgreeViewModel.getInstance(activity);
            agreeViewModel.getAgreeList().observe(this, new Observer<ProtocolsBean>() {
                @Override
                public void onChanged(@Nullable ProtocolsBean protocolsBean) {
                    updateUI(protocolsBean);
                }
            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (protocolIds != null && !protocolIds.isEmpty()) requestAPI(protocolIds);
        }
    }

    private void requestAPI(List<Integer> protocolIds) {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        agreeViewModel.requestAgreeList(this, protocolIds);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) fragmentManager.beginTransaction().hide(this).commit();
    }

    private void updateUI(@Nullable ProtocolsBean protocolsBean) {
        if (protocolsBean == null) return;
        List<ProtocolsBean.Data> data = protocolsBean.data;
        serviceAdapter.serviceItems.addAll(data);
        serviceAdapter.notifyDataSetChanged();
    }

    private void findViewById(@NonNull View view) {
        recycler_view = view.findViewById(R.id.recycler_view);
        view.setOnClickListener(this);
    }

    private static class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {
        private LayoutInflater inflater;
        final ArrayList<ProtocolsBean.Data> serviceItems = new ArrayList<>();

        @NonNull
        @Override
        public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.fragment_goods_service_item, parent, false);
            return new ServiceHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
            holder.updateUI(serviceItems.get(position));
        }

        @Override
        public int getItemCount() {
            return serviceItems.size();
        }

        private static class ServiceHolder extends RecyclerView.ViewHolder {

            private TextView tv_goods_service_title;
            private TextView tv_goods_service_content;

            public ServiceHolder(@NonNull View itemView) {
                super(itemView);
                findViewById(itemView);
            }

            public void updateUI(ProtocolsBean.Data data) {
                String protocolName = data.protocolName;
                String protocolContent = data.protocolContent;
                tv_goods_service_title.setText(protocolName);
                tv_goods_service_content.setText(Html.fromHtml(protocolContent));
            }

            private void findViewById(@NonNull View itemView) {
                tv_goods_service_title = itemView.findViewById(R.id.tv_goods_service_title);
                tv_goods_service_content = itemView.findViewById(R.id.tv_goods_service_content);
            }
        }
    }
}
