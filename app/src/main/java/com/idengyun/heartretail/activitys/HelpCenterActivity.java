package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:帮助中心
 * @date :2020/3/6 0006 16:00
 */
public class HelpCenterActivity extends BaseActivity {
    @BindView(R.id.ll_fh_time)
    LinearLayout llFhTime;

    public static void start(Context context) {
        Intent starter = new Intent(context, HelpCenterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }


    @OnClick({R.id.ll_fh_time, R.id.ll_logistics_about, R.id.ll_goods_about, R.id.ll_refund_about, R.id.ll_freight_about, R.id.ll_pay_about, R.id.ll_activity_about, R.id.ll_discount_coupon, R.id.ll_merchant_in, R.id.ll_service_complaint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_fh_time:
                SendGoodsTimeActivity.start(this);
                break;
            case R.id.ll_logistics_about:
                break;
            case R.id.ll_goods_about:
                break;
            case R.id.ll_refund_about:
                break;
            case R.id.ll_freight_about:
                break;
            case R.id.ll_pay_about:
                break;
            case R.id.ll_activity_about:
                break;
            case R.id.ll_discount_coupon:
                break;
            case R.id.ll_merchant_in:
                break;
            case R.id.ll_service_complaint:
                break;
        }
    }
}
