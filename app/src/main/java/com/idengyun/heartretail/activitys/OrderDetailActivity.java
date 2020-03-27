package com.idengyun.heartretail.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.commonmodule.beans.OrderDetailBean;
import com.idengyun.commonmodule.beans.OrderStatusBean;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.HRUIHelper;
import com.idengyun.heartretail.adapters.OderDetailGoodsListAdapter;
import com.idengyun.heartretail.beans.ConfirmOrderRspBean;
import com.idengyun.routermodule.RouterPathConfig;
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
@Route(path = (RouterPathConfig.app_OrderDetail))
public class OrderDetailActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener {
    @BindView(R.id.fl_second_title2)
    View flBackView2;
    @BindView(R.id.tv_order_detail_back2)
    View backView2;

    @BindView(R.id.toolbar_title_order_detail)
    BaseToolBar toolBar;
    @BindView(R.id.ll_shop_choose2)
    CardView llShopChoose;
    @BindView(R.id.ll_dl_detail)
    CardView llDlDetail;
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
    @BindView(R.id.tv_order_should_total_pay_tag)
    TextView tvOrderShouldTotalPayTag;
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
    @BindView(R.id.tv_should_pay_tag)
    TextView tvShouldPayTag;
    @BindView(R.id.tv_should_pay)
    TextView tvShouldPay;
    @BindView(R.id.tv_bottom_operate1)
    TextView tvBottomOperate1;
    @BindView(R.id.tv_bottom_operate2)
    TextView tvBottomOperate2;
    @BindView(R.id.ll_protocol_)
    LinearLayout llProtocol;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ll_pay_about2)
    LinearLayout llPayAbout;
    @BindView(R.id.tv_residue_time1_tag)
    TextView tvResidueTimeTag;
    @BindView(R.id.tv_residue_time1)
    TextView tvResidueTime;
    @BindView(R.id.tv_status_name)
    TextView tvStatusName;
    @BindView(R.id.tv_pay_code)
    TextView tvPayCode;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_pay_time2)
    TextView tvPayTime;
    @BindView(R.id.nsv_order_detail)
    NestedScrollView nestedScrollView;
    private SecondsTimer timer;
    OrderStatusBean dataSource;
    OrderDetailBean.OrderDetailBeanBody data;
    private String orderId;
    private int dimension;
    private int orderStatus = 0;
    List<OrderDetailBean.GoodsBean> goodsData = new ArrayList<>();
    OderDetailGoodsListAdapter adapter;

    public static void start(Context context, String orderId, int status) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        starter.putExtra(Constants.ORDER_STATUS, status);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase();
    }

    private void initBase() {
        dimension = SizeUtils.dp2px(64f);
        HRUIHelper.applySystemUI(this);
        nestedScrollView.setOnScrollChangeListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        orderStatus = getIntent().getIntExtra(Constants.ORDER_STATUS, 0);
        initStatus();
        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
         adapter = new OderDetailGoodsListAdapter(R.layout.item_order_detail_goods, goodsData);
        srGoods.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1), getResources().getColor(R.color.lineColor));
        srGoods.addItemDecoration(divider);
        srGoods.setAdapter(adapter);
    }

    private void initStatus() {
        if (orderStatus == 5 || orderStatus == 7) {
            tvStatusName.setText("已完成");
            llDlDetail.setVisibility(View.GONE);
            tvBottomOperate1.setText("查看物流");
            tvBottomOperate2.setVisibility(View.GONE);
            tvShouldPay.setVisibility(View.INVISIBLE);
            tvShouldPayTag.setVisibility(View.INVISIBLE);
            tvBottomOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckLogisticsActivity.start(getContext(), orderId);
                }
            });
        } else if (orderStatus == 0) {
            tvResidueTimeTag.setVisibility(View.VISIBLE);
            tvResidueTime.setVisibility(View.VISIBLE);
            llShopChoose.setVisibility(View.GONE);
            llPayAbout.setVisibility(View.GONE);
            llProtocol.setVisibility(View.VISIBLE);
            tvStatusName.setText("待付款");
            tvOrderShouldTotalPayTag.setText("应付总额");
            tvBottomOperate1.setText("取消订单");
            tvBottomOperate2.setText("立即付款");
            tvBottomOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder();
                }
            });
            tvBottomOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChoosePayModeActivity.start(getContext(), orderId);
                }
            });
        } else if (orderStatus == 3) {
            tvStatusName.setText("代销中");
        } else if (orderStatus == 1) {
            tvStatusName.setText("待发货");
            llBottom.setVisibility(View.GONE);
        } else if (orderStatus == 2) {
            tvStatusName.setText("待收货");
            tvBottomOperate1.setText("查看物流");
            tvBottomOperate2.setText("确认收货");
            tvBottomOperate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckLogisticsActivity.start(getContext(), orderId);
                }
            });
            tvBottomOperate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("暂未开通");
                }
            });
        } else if (orderStatus == 4) {
            tvStatusName.setText("待评价");
        }
    }

    private void getData() {
        Type type = new TypeToken<ApiBean<OrderDetailBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryOrderDetail())
                .activity(this)
                .params("version", AppUtils.getAppVersionName())
                .params("userId", HRUser.getId())
                .params("orderId", TextUtils.isEmpty(orderId) ? "" : orderId)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<OrderDetailBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<OrderDetailBean>> response) {
                data = response.body().data.order;
                if (data != null) {
                    initUI(data);
                }
            }
        });
    }

    private void initUI(OrderDetailBean.OrderDetailBeanBody data) {
        if (orderStatus == 0) {
            startTimer();
            tvOrderProtocol.setText("《" + data.proxySalesName + "》");
            tvShouldPay.setText("¥" + data.orderAmount);
        } else {
            tvPayCode.setText(data.payOrderId);
            tvPayType.setText(data.payCode);
            tvPayTime.setText(data.payTime);
        }
        tvOrderId.setText("订单号" + orderId);
        tvOrderId2.setText(orderId);
        tvOrderTotal.setText("¥" + data.totalAmount);
        tvOrderFreight.setText("¥" + data.shippingPrice);
        tvOrderRedPacketDeduction.setText("¥" + data.couponPrice);
        tvOrderShouldTotalPay.setText("¥" + data.orderAmount);
        tvOrderTime.setText(data.createTime);

        tvBuyerMsg.setText(data.userRemark);
        setRecyclerView(data);
    }

    private void setRecyclerView(OrderDetailBean.OrderDetailBeanBody data) {
        goodsData.addAll(data.orderGoods);
        srGoods.notifyDataSetChange();
    }

    private void startTimer() {
        if (timer == null) {
            timer = new SecondsTimer(data.leftTime, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    int h = (int) (secondsUntilFinished / 3600);
                    int m = (int) (secondsUntilFinished % 3600 / 60);
                    int s = (int) (secondsUntilFinished % 60);
                    tvResidueTime.setText(h + "小时" + m + "分" + s + "秒");
                }

                @Override
                public void onFinish() {

                }
            });

            timer.start();
        }
    }

    @OnClick({R.id.tv_order_detail_back2, R.id.ll_dl_detail, R.id.tv_customer_service, R.id.tv_order_id_copy, R.id.tv_bottom_operate1, R.id.tv_bottom_operate2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order_detail_back2:
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
                if (tvBottomOperate1.getText().equals("取消订单")) {
                    cancelOrder();
                }
                break;
            case R.id.tv_bottom_operate2:
                if (tvBottomOperate2.getText().equals("立即付款")) {
                    payNow();
                }
                break;
        }
    }

    private void payNow() {
        ChoosePayModeActivity.start(this, orderId);
    }

    private void cancelOrder() {
        HashMap map = new HashMap();
        map.put("version", AppUtils.getAppVersionName());
        map.put("orderId", orderId);
        map.put("state", 1);
        map.put("userId", TextUtils.isEmpty(HRUser.getId()) ? "1" : HRUser.getId());
        map.put("userName", TextUtils.isEmpty(HRUser.getId()) ? "1" : HRUser.getNickname());
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
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {

        // System.out.println("scrollY=" + scrollY + "\t" + "oldScrollY=" + oldScrollY);
        if (i1 < dimension) {
            flBackView2.setVisibility(View.VISIBLE);
            toolBar.setVisibility(View.GONE);
        } else {
            flBackView2.setVisibility(View.GONE);
            toolBar.setVisibility(View.VISIBLE);
        }
    }
}
