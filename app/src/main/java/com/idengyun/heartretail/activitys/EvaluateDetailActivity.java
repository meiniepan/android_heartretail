package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.EvaluateDetailListAdapter;
import com.idengyun.commonmodule.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:
 * @date :2020/3/6 0006 9:06
 */
public class EvaluateDetailActivity extends BaseActivity {
    @BindView(R.id.iv_goods_icon)
    ImageView ivGoodsIcon;
    @BindView(R.id.tv_goods_title1)
    TextView tvGoodsTitle1;
    @BindView(R.id.tv_goods_spec1)
    TextView tvGoodsSpec1;
    @BindView(R.id.sr_evaluate_detail)
    StatusRecyclerView recyclerView;
    private EvaluateDetailListAdapter adapter;
    List<OrderStatusBean> mData = new ArrayList<>();


    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, EvaluateDetailActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initData();
        adapter = new EvaluateDetailListAdapter(R.layout.item_evaluate_detail, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        OrderStatusBean orderStatusBean = new OrderStatusBean();
        mData.add(orderStatusBean);
    }
}
