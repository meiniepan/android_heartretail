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
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.OderStatusListAdapter;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

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
    List<OrderStatusBean> data = new ArrayList<>();
    int status;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        status = getBundle().getInt("status");
        initData();
        initUI();
        OderStatusListAdapter adapter = new OderStatusListAdapter(R.layout.item_order_status, data);
        rsrOrderStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        rsrOrderStatus.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderDetailActivity.start(getActivity(), data.get(position));
            }
        });
    }

    private void initUI() {
    }

    private void initData() {
        if (status == 0){
            getData();
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店"+status;
            data.add(orderStatusBean);
        }else if (status == 1){
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            goodsBean.goodsQuantity = 3;
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            goodsBeanList.add(goodsBean);
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店"+status;
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);
            data.add(orderStatusBean);
        } else if (status == 2) {
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            goodsBean.goodsQuantity = 3;
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店" + status;
            data.add(orderStatusBean);
        } else if (status == 3) {
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            goodsBean.goodsQuantity = 3;
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店" + status;
            data.add(orderStatusBean);
        } else if (status == 4) {
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            goodsBean.goodsQuantity = 3;
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店" + status;
            data.add(orderStatusBean);
        } else if (status == 5) {
            OrderStatusBean orderStatusBean = new OrderStatusBean();
            OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
            goodsBean.goodsTitle = "测试标题";
            goodsBean.goodsQuantity = 3;
            List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            orderStatusBean.goodsBeans = goodsBeanList;
            orderStatusBean.shopName = "什么什么店" + status;
            data.add(orderStatusBean);
        }
    }

    private void getData() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryOrderList())
                .activity(getActivity())
                .params("version", AppUtils.getAppVersionName())
                .params("page", 1)
                .params("userId", HRUser.getId())
                .params("flag", status)
                .params("pageSize", 10)
                .params("platform", HRConst.PLATFORM)
                .isShowDialog(true)
                .clazz(ApiBean.class)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean> response) {
                ApiBean body = response.body();
                if (null == body) {
                    ToastUtils.showLong("验证码发送失败");
                    return;
                }

                if (!"200".equals(body.code)) {
                    ToastUtils.showLong(body.msg);
                    return;
                }

                ToastUtils.showLong("验证码已发出");
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
