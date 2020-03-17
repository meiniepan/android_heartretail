package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.utils.SecondsTimer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description: 选择支付方式
 * @date :2020/3/3 0003 10:30
 */
public class ChoosePayModeActivity extends BaseActivity {
    @BindView(R.id.tv_order_total1)
    TextView tvOrderTotal1;
    @BindView(R.id.tv_residue_time)
    TextView tvResidueTime;
    @BindView(R.id.cb_pay)
    CheckBox cbPay;
    @BindView(R.id.cb_pay2)
    CheckBox cbPay2;
    @BindView(R.id.tv_account_balance)
    TextView tvAccountBalance;
    @BindView(R.id.cb_pay3)
    CheckBox cbPay3;
    @BindView(R.id.tv_confirm1)
    TextView tvConfirm1;
    @BindView(R.id.tv_h)
    TextView tvH;
    @BindView(R.id.tv_m)
    TextView tvM;
    @BindView(R.id.tv_s)
    TextView tvS;
    private SecondsTimer timer;


    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, ChoosePayModeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_mode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        startTimer();
    }

    private void startTimer() {
        if (timer == null) {
            timer = new SecondsTimer(24 * 60 * 60, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    int h = (int) (secondsUntilFinished / 3600);
                    int m = (int) (secondsUntilFinished % 3600 / 60);
                    int s = (int) (secondsUntilFinished % 60);
                    tvH.setText(h + "");
                    tvM.setText(m + "");
                    tvS.setText(s + "");
                }

                @Override
                public void onFinish() {
                    finish();
                }
            });

            timer.start();
        }
    }
    @OnClick({R.id.cb_pay, R.id.cb_pay2, R.id.cb_pay3, R.id.tv_confirm1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm1:
                PaySuccessActivity.start(this);
                finish();
                break;
            case R.id.cb_pay:
                cbPay.setChecked(true);
                cbPay2.setChecked(false);
                cbPay3.setChecked(false);
                break;
            case R.id.cb_pay2:
                cbPay.setChecked(false);
                cbPay2.setChecked(true);
                cbPay3.setChecked(false);
                break;
            case R.id.cb_pay3:
                cbPay.setChecked(false);
                cbPay2.setChecked(false);
                cbPay3.setChecked(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
