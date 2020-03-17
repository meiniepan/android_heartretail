package com.idengyun.heartretail.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.OderDetailGoodsListAdapter;
import com.idengyun.heartretail.beans.ConfirmOrderRspBean;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020/3/5 0005 10:35
 */
public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    View backView;
    @BindView(R.id.ll_dl_detail)
    LinearLayout llDlDetail;
    @BindView(R.id.tv_order_detail_order_id)
    TextView tvOrderId;
    @BindView(R.id.sr_goods1)
    StatusRecyclerView srGoods;
    @BindView(R.id.tv_customer_service)
    TextView tvCustomerService;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;
    @BindView(R.id.tv_order_freight)
    TextView tvOrderFreight;
    @BindView(R.id.tv_order_red_packet_deduction)
    TextView tvOrderRedPacketDeduction;
    @BindView(R.id.tv_order_should_total_pay)
    TextView tvOrderShouldTotalPay;
    @BindView(R.id.tv_order_id2)
    TextView tvOrderId2;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_protocol)
    TextView tvOrderProtocol;
    @BindView(R.id.tv_buyer_msg)
    TextView tvBuyerMsg;
    @BindView(R.id.tv_should_pay)
    TextView tvShouldPay;
    @BindView(R.id.tv_bottom_operate1)
    TextView tvBottomOperate1;
    @BindView(R.id.tv_bottom_operate2)
    TextView tvBottomOperate2;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.tv_residue_time1)
    TextView tvResidueTime;
    private SecondsTimer timer;
    OrderStatusBean dataSource;
    OrderStatusBean data;
    private String orderId;

    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        getData();
        startTimer();
    }

    private void getData() {
        Type type = new TypeToken<ApiBean<OrderStatusBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryOrderDetail())
                .activity(this)
                .params("version", AppUtils.getAppVersionName())
                .params("userId", HRUser.getId())
                .params("orderId", TextUtils.isEmpty(orderId)?"":orderId)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<OrderStatusBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<OrderStatusBean>> response) {
                ApiBean<OrderStatusBean> body = response.body();
                data = body.data;
                if (data != null) {
                    initUI();
                }
            }
        });
    }

    private void initUI() {
        tvOrderId.setText("订单号" + orderId);
        tvOrderId2.setText(orderId);
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<OrderStatusBean.GoodsBean> list = new ArrayList<>();
        OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
        goodsBean.goodsName = "这里是一段商品标题信息，最多展示2行，超出后显";
        goodsBean.skuItemvalue = "白色";
        goodsBean.goodsNum = 1;
        goodsBean.goodsPrice = "370.35";
        list.add(goodsBean);
        OderDetailGoodsListAdapter adapter = new OderDetailGoodsListAdapter(R.layout.item_order_detail_goods, list);
        srGoods.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1), getResources().getColor(R.color.lineColor));
        srGoods.addItemDecoration(divider);
        srGoods.setAdapter(adapter);
    }

    private void startTimer() {
        if (timer == null) {
            timer = new SecondsTimer(24 * 60 * 60, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    int h = (int) (secondsUntilFinished / 3600);
                    int m = (int) (secondsUntilFinished % 3600 / 60);
                    int s = (int) (secondsUntilFinished % 60);
                    tvResidueTime.setText(h + "小时" + m + "分" + s + "秒");
                }

                @Override
                public void onFinish() {
                    finish();
                }
            });

            timer.start();
        }
    }

    @OnClick({R.id.back, R.id.ll_dl_detail, R.id.tv_customer_service, R.id.tv_order_id_copy, R.id.tv_bottom_operate1, R.id.tv_bottom_operate2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_dl_detail:
                break;
            case R.id.tv_order_id_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvOrderId2.getText());
                ToastUtils.showShort("复制成功");
                break;
            case R.id.tv_customer_service:
                ToastUtils.showShort("功能暂未开通");
                break;
            case R.id.tv_bottom_operate1:
                if (tvBottomOperate1.getText().equals("取消订单")){
                    cancelOrder();
                }
                break;
            case R.id.tv_bottom_operate2:
                if (tvBottomOperate2.getText().equals("立即付款")){
                    payNow();
                }
                break;
        }
    }

    private void payNow() {
        ChoosePayModeActivity.start(this,orderId);
    }

    private void cancelOrder() {
        HashMap map = new HashMap();
        map.put("version", AppUtils.getAppVersionName());
        map.put("orderId", orderId);
        map.put("state", 1);
        map.put("userId", TextUtils.isEmpty(HRUser.getId())?"1":HRUser.getId());
        map.put("userName", TextUtils.isEmpty(HRUser.getId())?"1":HRUser.getNickname());
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeOrderState())
                .activity(this)
                .params(map)
                .isShowDialog(true)
                .clazz(ApiBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean> response) {
                ApiBean<ConfirmOrderRspBean> body = response.body();
                ToastUtils.showShort("取消订单成功");
                finish();
            }

            @Override
            public void onError(Response<ApiBean> response) {
                super.onError(response);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
