package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.GoodsDetailBean;
import com.idengyun.heartretail.model.response.ProtocolsBean;
import com.lzy.okgo.model.Response;

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
        serviceAdapter = new ServiceAdapter();
        recycler_view.setAdapter(serviceAdapter);

        GDViewModel.observe(getActivity(), this, new Observer<GoodsDetailBean.Data>() {
            @Override
            public void onChanged(@Nullable GoodsDetailBean.Data data) {
                if (data == null) return;
                List<GoodsDetailBean.Data.Protocol> protocolList = data.protocolList;
                if (protocolList != null) {
                    int[] protocolIds = new int[protocolList.size()];
                    for (int i = 0; i < protocolList.size(); i++) {
                        GoodsDetailBean.Data.Protocol protocol = protocolList.get(i);
                        protocolIds[i] = protocol.protocolId;
                    }
                    if (protocolIds.length > 0) requestAPI(protocolIds);
                }
            }
        });
    }

    private void requestAPI(int[] protocolIds) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.protocolDetail())
                .fragment(this)
                .clazz(ProtocolsBean.class)
                .params("protocolIds", protocolIds)
                .build();
        NetApi.<ProtocolsBean>getData(netOption, new JsonCallback<ProtocolsBean>(netOption) {
            @Override
            public void onSuccess(Response<ProtocolsBean> response) {
                List<ProtocolsBean.Data> data = response.body().data;
                updateUI(data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) fragmentManager.beginTransaction().hide(this).commit();
    }

    private void updateUI(List<ProtocolsBean.Data> data) {
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
