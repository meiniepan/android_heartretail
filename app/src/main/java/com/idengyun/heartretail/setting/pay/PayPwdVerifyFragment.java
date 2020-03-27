package com.idengyun.heartretail.setting.pay;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.MobileBindBean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.utils.SecondsTimer;

/**
 * 第一次设置支付密码 or 忘记支付密码
 *
 * @author aLang
 */
public final class PayPwdVerifyFragment extends BaseFragment implements View.OnClickListener {

    public static void start(Context context, int identifyType, int type) {
        Bundle extras = new Bundle();
        extras.putInt("verify_code_type", identifyType);
        extras.putInt("pay_pwd_change_type", type);
        HRActivity.start(context, extras, PayPwdVerifyFragment.class);
    }

    private TextView tv_identity_mobile;
    private EditText et_identity_verify_code;
    private TextView tv_identity_verify_code;
    private View tv_identity_contact_service;
    private View tv_identity_next;

    private SecondsTimer timer;
    private SettingViewModel settingViewModel;
    /* 验证码类型0注册1换新设备2修改密码3忘记密码4绑定新手机号5旧手机号身份验证6（提现审核）实名认证 7支付密码设置8忘记支付密码 */
    private int identifyType;
    /* 0新增支付密码1修改支付密码2忘记支付密码 */
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_first_pay_pwd_check;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        identifyType = activity.getIntent().getIntExtra("verify_code_type", -1);
        type = activity.getIntent().getIntExtra("pay_pwd_change_type", -1);
        observe();

        String mobile = HRUser.getMobile();
        if (mobile.length() == 11) {
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            tv_identity_mobile.setText(mobile);
        }
    }

    @Override
    public void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (tv_identity_verify_code == v) {
            sendVerifyCode();
        } else if (tv_identity_contact_service == v) {
            // TODO: 2020/3/9
        } else if (tv_identity_next == v) {
            nextStep();
        }
    }

    private void observe() {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (settingViewModel == null) {
            settingViewModel = SettingViewModel.getInstance(activity);
            settingViewModel.getVerifyCode().observe(this, new Observer<VerifyCodeBean>() {
                @Override
                public void onChanged(@Nullable VerifyCodeBean verifyCodeBean) {
                    ToastUtils.showLong("验证码已发出");
                }
            });
            settingViewModel.getIdentityVerify().observe(this, new Observer<MobileBindBean>() {
                @Override
                public void onChanged(@Nullable MobileBindBean mobileBindBean) {
                    /* 0新增支付密码1修改支付密码2忘记支付密码 */
                    PayPwdChangeFragment.start(getContext(), type);
                    HRActivity.finish(getActivity());
                }
            });
        }
    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        startTimer(tv_identity_verify_code);

        if (settingViewModel == null) return;
        settingViewModel.requestVerifyCode(this, "" + identifyType);
    }

    private void nextStep() {
        if (et_identity_verify_code.length() < 1) {
            ToastUtils.showShort("请输入有效验证码");
            return;
        }
        if (settingViewModel != null) {
            settingViewModel.requestIdentityVerify(this, et_identity_verify_code.getText().toString());
        }
    }

    private void startTimer(TextView textView) {
        textView.setEnabled(false);

        if (timer == null) {
            timer = new SecondsTimer(60L, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    textView.setText(secondsUntilFinished + "s后重发");
                }

                @Override
                public void onFinish() {
                    textView.setText("获取验证码");
                    textView.setEnabled(true);
                }
            });
        }

        timer.start();
    }

    private void cancelTimer() {
        if (timer != null) timer.cancel();
    }

    private void findViewById(View view) {
        tv_identity_mobile = view.findViewById(R.id.tv_identity_mobile);
        et_identity_verify_code = view.findViewById(R.id.et_identity_verify_code);
        tv_identity_verify_code = view.findViewById(R.id.tv_identity_verify_code);
        tv_identity_contact_service = view.findViewById(R.id.tv_identity_contact_service);
        tv_identity_next = view.findViewById(R.id.tv_identity_next);

        tv_identity_mobile.setOnClickListener(this);
        tv_identity_verify_code.setOnClickListener(this);
        tv_identity_contact_service.setOnClickListener(this);
        tv_identity_next.setOnClickListener(this);
        et_identity_verify_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_identity_next.setEnabled(s.length() > 5);
            }
        });
    }
}
