package com.idengyun.heartretail.setting;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.setting.account.IdentityFragment;
import com.idengyun.heartretail.setting.account.PasswordFragment;
import com.idengyun.heartretail.setting.account.auth.AuthenticationFragment;
import com.idengyun.usermodule.HRUser;

/**
 * 账号管理
 *
 * @author aLang
 */
public final class AccountFragment extends BaseFragment implements View.OnClickListener {
    private View layout_account_pwd;
    private View layout_account_phone;
    private View layout_account_real;
    private TextView tv_account_mobile;
    private TextView tv_account_auth_state;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_account_manage;
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
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onClick(View v) {
        /* 修改密码 手机号码 实名认证 */
        if (layout_account_pwd == v) {
            startPwdModifyActivity();
        } else if (layout_account_phone == v) {
            startPhoneBindActivity();
        } else if (layout_account_real == v) {
            startRealVerifyActivity();
        }
    }

    @MainThread
    private void updateUI() {
        String mobile = HRUser.getMobile();
        if (mobile.length() == 11) {
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            tv_account_mobile.setText(mobile);
        }
        tv_account_auth_state.setText(HRUser.isAuthentication() ? "已认证" : "未认证");
    }

    private void startRealVerifyActivity() {
        HRActivity.start(getContext(), AuthenticationFragment.class);
    }

    private void startPhoneBindActivity() {
        HRActivity.start(getContext(), IdentityFragment.class);
    }

    private void startPwdModifyActivity() {
        HRActivity.start(getContext(), PasswordFragment.class);
    }

    private void findViewById(View view) {
        layout_account_pwd = view.findViewById(R.id.layout_account_pwd);
        layout_account_phone = view.findViewById(R.id.layout_account_phone);
        layout_account_real = view.findViewById(R.id.layout_account_real);
        tv_account_mobile = view.findViewById(R.id.tv_account_mobile);
        tv_account_auth_state = view.findViewById(R.id.tv_account_auth_state);

        layout_account_pwd.setOnClickListener(this);
        layout_account_phone.setOnClickListener(this);
        layout_account_real.setOnClickListener(this);
    }
}
