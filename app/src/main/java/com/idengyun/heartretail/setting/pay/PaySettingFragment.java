package com.idengyun.heartretail.setting.pay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;

/**
 * 设置界面
 *
 * @author aLang
 */
public final class PaySettingFragment extends BaseFragment implements View.OnClickListener {

    private View layout_pay_setting_modify_pwd;
    private View layout_pay_setting_forget_pwd;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pay_setting;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (true) {
            /* 验证码类型0注册1换新设备2修改密码3忘记密码4绑定新手机号5旧手机号身份验证6（提现审核）实名认证 7支付密码设置8忘记支付密码 */
            /* 0新增支付密码1修改支付密码2忘记支付密码 */
            PayPwdVerifyFragment.start(v.getContext(), 7, 0);
            return;
        }

        if (layout_pay_setting_modify_pwd == v) {
            HRActivity.start(getContext(), OldPayPwdCheckFragment.class);
        } else if (layout_pay_setting_forget_pwd == v) {
            PayPwdVerifyFragment.start(v.getContext(), 8, 2);
        }
    }

    private void findViewById(View view) {
        layout_pay_setting_modify_pwd = view.findViewById(R.id.layout_pay_setting_modify_pwd);
        layout_pay_setting_forget_pwd = view.findViewById(R.id.layout_pay_setting_forget_pwd);
        layout_pay_setting_modify_pwd.setOnClickListener(this);
        layout_pay_setting_forget_pwd.setOnClickListener(this);
    }
}
