package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.AwardIncomeListAdapter;
import com.idengyun.commonmodule.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:
 * @date :2020/3/16 0016 16:53
 */
public class AwardDetailActivity extends BaseActivity {
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_expend)
    TextView tvExpend;
    @BindView(R.id.sr_award_detail)
    StatusRecyclerView recyclerView;
    List<OrderStatusBean> mData = new ArrayList<>();
    private AwardIncomeListAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, AwardDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_award_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initData();
        adapter = new AwardIncomeListAdapter(R.layout.item_award_income, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EvaluateDetailActivity.start(getContext(), "");
            }
        });
    }

    private void initData() {
        OrderStatusBean orderStatusBean = new OrderStatusBean();
        mData.add(orderStatusBean);
        mData.add(orderStatusBean);
        mData.add(orderStatusBean);
    }

}
