package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.OderStatusGoodsListAdapter;
import com.idengyun.heartretail.beans.ConfirmOrderReqBean;
import com.idengyun.heartretail.beans.ConfirmOrderRspBean;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.heartretail.model.response.GoodsDetailBean;
import com.idengyun.heartretail.shop.ShopListActivity;
import com.idengyun.heartretail.widget.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:确认订单
 * @date :2020-02-29 11:16
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.rl_shop)
    RelativeLayout rlShop;
    @BindView(R.id.ll_shop_choose)
    LinearLayout llShopChoose;
    @BindView(R.id.ll_no_shop)
    LinearLayout llNoShop;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.tv_total_pay1)
    TextView tvTotalPay1;
    @BindView(R.id.tv_order_freight1)
    TextView tvOrderFreight1;
    @BindView(R.id.tv_order_red_packet_balance)
    TextView tvOrderRedPacketBalance;
    @BindView(R.id.cb_red_packet_switch)
    CheckBox cbRedPacketSwitch;
    @BindView(R.id.tv_should_pay1)
    TextView tvShouldPay1;
    @BindView(R.id.cb_protocol)
    CheckBox cbProtocol;
    @BindView(R.id.tv_protocol1)
    TextView tvProtocol1;
    @BindView(R.id.tv_should_pay_bottom)
    TextView tvShouldPayBottom;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.sr_order_confirm)
    StatusRecyclerView recyclerView;

    public static void start(Context context, GoodsDetailBean data) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order_retail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        rlShop.setVisibility(View.GONE);
        llNoShop.setVisibility(View.VISIBLE);
        initRecycler();
    }

    private void initRecycler() {
        List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
        OrderStatusBean.GoodsBean goodsBean = new OrderStatusBean.GoodsBean();
        goodsBeanList.add(goodsBean);
        OderStatusGoodsListAdapter adapter = new OderStatusGoodsListAdapter(R.layout.item_order_status_goods, goodsBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1), getResources().getColor(R.color.lineColor));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.ll_shop_choose, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop_choose:
//                ShopListActivity.start(this);
                Intent intent = new Intent(this, ShopListActivity.class);
                startActivityForResult(intent, 1);

                break;
            case R.id.tv_commit:
                doCommit();
                break;
        }
    }

    private void doCommit() {
        if (!cbProtocol.isChecked()) {
            ToastUtils.showShort("请先同意协议");
            return;
        }
        Type type = new TypeToken<ApiBean<ConfirmOrderRspBean>>() {
        }.getType();
        HashMap map = new HashMap();
        map.put("version", AppUtils.getAppVersionName());
        map.put("userId", HRUser.getId());
        map.put("mobile", "");
        map.put("consignee", "");
        map.put("shopId", 0);
        map.put("userRemark", "");
        map.put("isUsePacket", "");
        map.put("orderType", 0);
        map.put("goodsType", 0);
        map.put("goodsSkuEntityList", new ConfirmOrderReqBean());
        map.put("platform", HRConst.PLATFORM);
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.commitOrder())
                .activity(this)
                .params(map)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean<ConfirmOrderRspBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<ConfirmOrderRspBean>> response) {
                ApiBean<ConfirmOrderRspBean> body = response.body();
                ToastUtils.showShort("提交成功");
                ChoosePayModeActivity.start(getContext());
            }

            @Override
            public void onError(Response<ApiBean<ConfirmOrderRspBean>> response) {
                super.onError(response);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            String result = data.getStringExtra("result");
            rlShop.setVisibility(View.VISIBLE);
            llNoShop.setVisibility(View.GONE);
            tvTag3.setText(result);
        }
    }
}
