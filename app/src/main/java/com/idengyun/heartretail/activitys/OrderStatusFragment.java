package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.OderStatusListAdapter;
import com.idengyun.heartretail.beans.OrderListBean;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:订单状态详情 待付款, 代销中, 待发货, 待收货, 待评价
 * @date :2020/3/4 0004 13:25
 */
public class OrderStatusFragment extends BaseFragment {
    @BindView(R.id.rsr_order_status)
    RefreshStatusRecyclerView rsrOrderStatus;
    List<OrderStatusBean> mData = new ArrayList<>();
    int status;
    private int page = 1;
    private OderStatusListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        status = getBundle().getInt("status");
        initRefreshLoadMore();
        initUI();
        getData();

    }

    private void initRefreshLoadMore() {
        rsrOrderStatus.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mData.clear();
                page = 1;
                getData();
            }
        });
    }

    private void initUI() {
         adapter = new OderStatusListAdapter(getActivity(),R.layout.item_order_status, mData);
        rsrOrderStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        rsrOrderStatus.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderDetailActivity.start(getActivity(), mData.get(position));
            }
        });
    }

    private void getData() {
        Type type = new TypeToken<ApiBean<OrderListBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryOrderList())
                .activity(getActivity())
                .params("version", AppUtils.getAppVersionName())
                .params("page", page)
                .params("userId", HRUser.getId())
                .params("flag", status)
                .params("pageSize", 10)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<OrderListBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<OrderListBean>> response) {
                rsrOrderStatus.finishRefreshLoadMore();
                ApiBean<OrderListBean> body = response.body();
                if (body.data.orders != null && body.data.orders.size() > 0) {
                    mData.addAll(body.data.orders);
                } else {
                    if (page != 1) {
                        ToastUtils.showShort(R.string.load_more_end);
                    }
                }
                rsrOrderStatus.notifyDataSetChange();
            }

            @Override
            public void onError(Response<ApiBean<OrderListBean>> response) {
                super.onError(response);
                rsrOrderStatus.finishRefreshLoadMore();
            }
        });
    }

    @Override
    public void onDestroyView() {
        adapter.onDetachedFromRecyclerView(rsrOrderStatus.getRecyclerView());
        super.onDestroyView();
    }

}
