package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.EvaluateListAdapter;
import com.idengyun.heartretail.beans.OrderStatusBean;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:
 * @date :2020/3/6 0006 9:06
 */
public class MyEvaluateActivity extends BaseActivity {
    @BindView(R.id.sr_evaluate)
    StatusRecyclerView recyclerView;
    List<OrderStatusBean> mData = new ArrayList<>();
    private EvaluateListAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyEvaluateActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_evaluate;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initData();
        adapter = new EvaluateListAdapter(R.layout.item_evaluate, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EvaluateDetailActivity.start(getContext());
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
