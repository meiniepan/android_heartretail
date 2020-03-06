package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.OderStatusGoodsListAdapter;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;

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
    @BindView(R.id.sr_goods)
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
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
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
    TextView tvBottomOperate2;@BindView(R.id.tv_order_status_shop_name)
    TextView tvShopName;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    OrderStatusBean data;

    public static void start(Context context, OrderStatusBean data) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra(Constants.ORDER_STATUS_BEAN, data);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRecyclerView();
        tvShopName.setText(data.shopName);
    }

    private void initRecyclerView() {
        data = getIntent().getParcelableExtra(Constants.ORDER_STATUS_BEAN);
        OderStatusGoodsListAdapter adapter = new OderStatusGoodsListAdapter(R.layout.item_order_status_goods, data.goodsBeans);
        srGoods.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1), getResources().getColor(R.color.lineColor));
        srGoods.addItemDecoration(divider);
        srGoods.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.ll_dl_detail, R.id.tv_customer_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_dl_detail:
                break;
            case R.id.tv_customer_service:
                break;
        }
    }
}
