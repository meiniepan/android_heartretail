package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.SystemUIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020/3/20 0020 11:28
 */
public class MyBalanceActivity extends BaseActivity {
    @BindView(R.id.tv_account_balance2)
    TextView tvAccountBalance2;
    @BindView(R.id.ll_balance_withdraw)
    LinearLayout llBalanceWithdraw;
    @BindView(R.id.ll_bill_record)
    LinearLayout llBillRecord;
    @BindView(R.id.tv_wait_grant)
    TextView tvWaitGrant;
    @BindView(R.id.tv_total_due)
    TextView tvTotalDue;
    @BindView(R.id.layout_my_order)
    CardView layoutMyOrder;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyBalanceActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_balance;
    }

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        SystemUIHelper.applySystemUI(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_balance_withdraw, R.id.ll_bill_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_balance_withdraw:
                break;
            case R.id.ll_bill_record:
                break;
        }
    }
}
