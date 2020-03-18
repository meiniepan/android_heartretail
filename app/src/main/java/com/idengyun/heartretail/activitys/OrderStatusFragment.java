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
import com.idengyun.commonmodule.beans.OrderListBean;
import com.idengyun.commonmodule.beans.OrderStatusBean;
import com.idengyun.heartretail.interfaces.ITimer;
import com.idengyun.routermodule.ARouterInstance;
import com.idengyun.routermodule.AppRouter;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:订单状态详情 待付款, 代销中, 待发货, 待收货, 待评价
 * @date :2020/3/4 0004 13:25
 */
public class OrderStatusFragment extends BaseFragment implements ITimer {
    @BindView(R.id.rsr_order_status)
    RefreshStatusRecyclerView rsrOrderStatus;
    List<OrderStatusBean> mData = new ArrayList<>();
    int status;
    private int page = 1;
    private OderStatusListAdapter adapter;
    private HashMap<String, SecondsTimer> timerMap = new HashMap();

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
                doRefresh();
            }
        });
    }

    private void doRefresh() {
        mData.clear();
        page = 1;
        getData();
    }

    private void initUI() {
         adapter = new OderStatusListAdapter(this,R.layout.item_order_status, mData);
        rsrOrderStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        rsrOrderStatus.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderDetailActivity.start(getActivity(), mData.get(position).orderId);
            }
        });
    }

    private void getData() {
        ARouterInstance.getAppRouter().getOrderListAsyn(getMyActivity(), status, page, new AppRouter.OnRequestOrderListListener() {
            @Override
            public void onResultOrderList(OrderListBean resultOrderListBean) {
                if (resultOrderListBean.orders != null && resultOrderListBean.orders.size() > 0) {
                    mData.addAll(resultOrderListBean.orders);
                    setTimerList(resultOrderListBean.orders);
                } else {
                    if (page != 1) {
                        ToastUtils.showShort(R.string.load_more_end);
                    }
                }
                rsrOrderStatus.notifyDataSetChange();
            }

            @Override
            public void onError() {
                rsrOrderStatus.finishRefreshLoadMore();
            }
        });
    }

    private void setTimerList(List<OrderStatusBean> orders) {
        for (OrderStatusBean bean : orders
        ) {
            SecondsTimer timer = null;
            if (!timerMap.containsKey(bean.orderId)) {
                timer = new SecondsTimer(bean.leftTime, null);
                timer.start();
                timerMap.put(bean.orderId, timer);
            }
        }
        adapter.setTimerMap(timerMap);
    }

    @Override
    public void onDestroyView() {
        if (timerMap.size() > 0) {
            for (String orderId : timerMap.keySet()
            ) {
                timerMap.get(orderId).cancel();
            }
        }
        super.onDestroyView();
    }

    @Override
    public void workOnFinish() {
        doRefresh();
    }
}
