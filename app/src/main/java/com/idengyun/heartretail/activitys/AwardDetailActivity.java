package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.base.dialog.BaseDialog;
import com.dengyun.baselibrary.base.dialog.BaseDialogFragment;
import com.dengyun.baselibrary.base.dialog.ViewConvertListener;
import com.dengyun.baselibrary.base.dialog.listener.DialogViewHolder;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.widgets.toolbar.BaseOneRightToolBar;
import com.idengyun.commonmodule.beans.OrderStatusBean;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.AwardExpendListAdapter;
import com.idengyun.heartretail.adapters.AwardIncomeListAdapter;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020/3/16 0016 16:53
 */
public class AwardDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_title_award)
    BaseOneRightToolBar toolBar;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_expend)
    TextView tvExpend;
    @BindView(R.id.sr_award_detail)
    StatusRecyclerView recyclerView;
    List<OrderStatusBean> mDataIncome = new ArrayList<>();
    List<OrderStatusBean> mDataExpend = new ArrayList<>();
    private AwardIncomeListAdapter adapterIncome;
    private AwardExpendListAdapter adapterExpend;

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
        toolBar.setOnRightButtonClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog.init().setLayoutId(R.layout.dialog_withdraw_spec)
                        .setWidthMarginDp(30)
                        .show(getSupportFragmentManager());
            }
        });
        initData();
        adapterIncome = new AwardIncomeListAdapter(R.layout.item_award_income, mDataIncome);
        adapterExpend = new AwardExpendListAdapter(R.layout.item_award_expend, mDataIncome);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterIncome);
        adapterIncome.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                EvaluateDetailActivity.start(getContext(), "");
            }
        });
    }

    private void initData() {
        OrderStatusBean orderStatusBean = new OrderStatusBean();
        mDataIncome.add(orderStatusBean);
        mDataIncome.add(orderStatusBean);
        mDataIncome.add(orderStatusBean);
    }


    @OnClick({R.id.tv_income, R.id.tv_expend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_income:
                tvIncome.setBackgroundResource(R.drawable.shape_white_tab_corner_rec);
                tvExpend.setBackground(new BitmapDrawable());
                recyclerView.setAdapter(adapterIncome);
                break;
            case R.id.tv_expend:
                tvExpend.setBackgroundResource(R.drawable.shape_white_tab_corner_rec);
                tvIncome.setBackground(new BitmapDrawable());
                recyclerView.setAdapter(adapterExpend);
                break;
        }
    }
}
