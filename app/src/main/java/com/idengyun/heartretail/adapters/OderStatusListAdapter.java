package com.idengyun.heartretail.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.activitys.ChoosePayModeActivity;
import com.idengyun.heartretail.activitys.EvaluateDetailActivity;
import com.idengyun.heartretail.activitys.LogisticsDetailActivity;
import com.idengyun.heartretail.beans.ConfirmOrderRspBean;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.heartretail.widget.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class OderStatusListAdapter extends BaseQuickAdapter<OrderStatusBean, BaseViewHolder> {
    private final FragmentActivity mActivity;
    private HashMap<String, SecondsTimer> timerMap = new HashMap();
    public OderStatusListAdapter(FragmentActivity activity,int layoutResId, @Nullable List<OrderStatusBean> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusBean item) {
        initGoodsListRecyclerView(helper.getView(R.id.sr_goods), item.orderGoods);
        helper.setText(R.id.tv_order_status_order_id, "订单号"+item.orderId);
        LinearLayout ll_surplus_pay_time = helper.getView(R.id.ll_surplus_pay_time);
        LinearLayout ll_advanced_operate = helper.getView(R.id.ll_advanced_operate);
        TextView tv_order_status_status = helper.getView(R.id.tv_order_status_status);
        TextView tv_operate1 = helper.getView(R.id.tv_operate1);
        TextView tv_operate2 = helper.getView(R.id.tv_operate2);
        TextView tv_order_type = helper.getView(R.id.tv_order_type);
        TextView tv_total_pay = helper.getView(R.id.tv_total_pay);
        tv_total_pay.setText("¥" + item.orderAmount + "(含运费 ¥" + item.shippingPrice + ")");
        tv_order_type.setText(item.orderType==1?"零售订单":"批发订单");
        if (item.orderStatus == 0) {
            ll_surplus_pay_time.setVisibility(View.GONE);
            ll_advanced_operate.setVisibility(View.VISIBLE);
            tv_order_status_status.setText("已完成");
            tv_operate1.setText("查看物流");
            tv_operate2.setText("查看评价");
            tv_operate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogisticsDetailActivity.start(mContext,item.orderId);
                }
            });
            tv_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EvaluateDetailActivity.start(mContext,item.orderId);
                }
            });
        } else if (item.orderStatus == 1){
            ll_surplus_pay_time.setVisibility(View.VISIBLE);
            ll_advanced_operate.setVisibility(View.VISIBLE);
            tv_order_status_status.setText("待付款");
            tv_operate1.setText("取消订单");
            tv_operate2.setText("立即付款");
            tv_operate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(item.orderId,item.orderStatus);
                }
            });
            tv_operate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChoosePayModeActivity.start(mContext, item.orderId);
                }
            });
            TextView tv_order_status_time_h = helper.getView(R.id.tv_order_status_time_h);
            TextView tv_order_status_time_m = helper.getView(R.id.tv_order_status_time_m);
            TextView tv_order_status_time_s = helper.getView(R.id.tv_order_status_time_s);
            //todo 时间测试
            startTimer(item.orderId,item.orderStatus,3700,tv_order_status_time_h,tv_order_status_time_m,tv_order_status_time_s);
        }else if(item.orderStatus == 2){
            ll_surplus_pay_time.setVisibility(View.GONE);
            ll_advanced_operate.setVisibility(View.GONE);
            tv_order_status_status.setText("代销中");
        }else if(item.orderStatus == 3){
            ll_surplus_pay_time.setVisibility(View.GONE);
            ll_advanced_operate.setVisibility(View.GONE);
            tv_order_status_status.setText("待发货");
        }else if(item.orderStatus == 4){
            ll_surplus_pay_time.setVisibility(View.GONE);
            ll_advanced_operate.setVisibility(View.VISIBLE);
            tv_order_status_status.setText("待收货");
            tv_operate1.setText("查看物流");
            tv_operate2.setText("确认收货");
            tv_operate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogisticsDetailActivity.start(mContext,item.orderId);
                }
            });
        }else if(item.orderStatus == 5){
            ll_surplus_pay_time.setVisibility(View.GONE);
            ll_advanced_operate.setVisibility(View.GONE);
            tv_order_status_status.setText("待评价");
        }
    }

    private void initGoodsListRecyclerView(StatusRecyclerView recyclerView, List<OrderStatusBean.GoodsBean> goodsBeans) {
        OderStatusGoodsListAdapter adapter = new OderStatusGoodsListAdapter(R.layout.item_order_status_goods, goodsBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1),mContext.getResources().getColor(R.color.lineColor));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
    }

    private void startTimer(String orderId, int orderStatus, int senconds, TextView tvH, TextView tvM, TextView tvS) {
        if (timerMap.get(orderId+"")==null){
        SecondsTimer timer = new SecondsTimer(senconds, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    int h = (int) (secondsUntilFinished / 3600);
                    int m = (int) (secondsUntilFinished % 3600 / 60);
                    int s = (int) (secondsUntilFinished % 60);
                    tvH.setText(h+"");
                    tvM.setText(m+"");
                    tvS.setText(s+"");
                }

                @Override
                public void onFinish() {
                    cancelOrder(orderId,orderStatus);
                }
            });

        timer.start();
        timerMap.put(orderId+"", timer);}
    }

    private void cancelOrder(String orderId,int state) {
        HashMap map = new HashMap();
        map.put("version", AppUtils.getAppVersionName());
        map.put("orderId", orderId);
        map.put("state", state);
        map.put("userId", TextUtils.isEmpty(HRUser.getId())?"1":HRUser.getId());
        map.put("userName", TextUtils.isEmpty(HRUser.getId())?"1":HRUser.getNickname());
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeOrderState())
                .activity(mActivity)
                .params(map)
                .isShowDialog(true)
                .clazz(ApiBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean> response) {
                ApiBean<ConfirmOrderRspBean> body = response.body();
                ToastUtils.showShort("取消订单成功");
               notifyDataSetChanged();
            }

            @Override
            public void onError(Response<ApiBean> response) {
                super.onError(response);
            }
        });
    }

    private void cancelTimer(BaseViewHolder holder) {

        SecondsTimer timer = timerMap.get(mData.get(holder.getLayoutPosition()).orderId);
        if (timer!=null){
            timer.cancel();
            timerMap.remove(mData.get(holder.getLayoutPosition()).orderId);
        }
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        cancelTimer(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (timerMap.size() > 0) {
            for (String orderId : timerMap.keySet()
            ) {
                timerMap.get(orderId).cancel();
            }
        }
    }
}
