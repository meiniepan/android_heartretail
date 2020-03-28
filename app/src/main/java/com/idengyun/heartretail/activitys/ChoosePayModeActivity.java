package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.commonmodule.beans.OrderDetailBean;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.HashMap;

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
    private String orderId;


    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, ChoosePayModeActivity.class);
        starter.putExtra(Constants.ORDER_ID,orderId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_mode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
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
                doCommit();
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
    private void doCommit() {
        HashMap map = new HashMap();
        map.put("userId", TextUtils.isEmpty(HRUser.getId()) ? "1" : HRUser.getId());
        map.put("orderId", orderId);
        map.put("orderNo", "1");

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.pay())
                .activity(this)
                .params(map)
                .isShowDialog(true)
                .clazz(ApiBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean> response) {
                ToastUtils.showShort("支付成功");
                PaySuccessActivity.start(getContext());
                finish();
            }

            @Override
            public void onError(Response<ApiBean> response) {
                super.onError(response);
            }
        });
    }
    private void getData() {
        Type type = new TypeToken<ApiBean<OrderDetailBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryOrderDetail())
                .activity(this)
                .params("version", AppUtils.getAppVersionName())
                .params("userId", HRUser.getId())
                .params("orderId", TextUtils.isEmpty(orderId) ? "" : orderId)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<OrderDetailBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<OrderDetailBean>> response) {
//                orderNo = response.body().data.order.or
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
