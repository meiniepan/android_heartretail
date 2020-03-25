package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.ShippingGoodsListAdapter;
import com.idengyun.heartretail.beans.ShippingListBean;
import com.idengyun.heartretail.widget.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:
 * @date :2020/3/6 0006 9:06
 */
public class CheckLogisticsActivity extends BaseActivity {
    @BindView(R.id.tv_shipping_order)
    TextView tvShippingOrder;
    @BindView(R.id.tv_shipping_name)
    TextView tvShippingName;
    @BindView(R.id.sr_shipping_goods)
    StatusRecyclerView recyclerView;
    List<ShippingListBean.Goods> goodsData = new ArrayList<>();
    private String orderId = "";
    private ShippingGoodsListAdapter adapter;

    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, CheckLogisticsActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_logistics;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        initUI();
        getData();
    }

    private void initUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1),getResources().getColor(R.color.lineColor));
        recyclerView.addItemDecoration(divider);
        adapter = new ShippingGoodsListAdapter(R.layout.item_order_status_goods, goodsData);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        Type type = new TypeToken<ApiBean<ShippingListBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryShippingOrderId())
                .activity(this)
                .params("orderId", orderId)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<ShippingListBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<ShippingListBean>> response) {
                ShippingListBean.ShippingBean data = response.body().data.shippings.get(0);
                goodsData.addAll(data.goods);
                recyclerView.notifyDataSetChange();
                tvShippingName.setText(data.shippingName + " " + data.shippingCode);
            }
        });
    }
}
