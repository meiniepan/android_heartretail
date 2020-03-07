package com.idengyun.heartretail.my;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;

/**
 * 账号管理界面
 *
 * @author aLang
 */
public final class AccountFragment extends BaseFragment implements View.OnClickListener {
    private View layout_account_pwd;
    private View layout_account_phone;
    private View layout_account_real;

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

    private void startRealVerifyActivity() {
        HRActivity.start(getContext(), RealFragment.class);
    }

    private void startPhoneBindActivity() {
        HRActivity.start(getContext(), PhoneFragment.class);
    }

    private void startPwdModifyActivity() {
        HRActivity.start(getContext(), PasswordFragment.class);
    }

    private void findViewById(View view) {
        layout_account_pwd = view.findViewById(R.id.layout_account_pwd);
        layout_account_phone = view.findViewById(R.id.layout_account_phone);
        layout_account_real = view.findViewById(R.id.layout_account_real);

        layout_account_pwd.setOnClickListener(this);
        layout_account_phone.setOnClickListener(this);
        layout_account_real.setOnClickListener(this);
    }
}
