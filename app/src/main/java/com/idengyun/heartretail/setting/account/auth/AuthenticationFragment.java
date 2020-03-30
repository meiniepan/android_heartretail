package com.idengyun.heartretail.setting.account.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.HRUser;

/**
 * 是否认证
 *
 * @author aLang
 */
public final class AuthenticationFragment extends BaseFragment implements View.OnClickListener {

    private View layout_auth_id_card_info;
    private View layout_auth_bank_card_info;
    private View iv_auth_yes_or_no;
    private View layout_account_authorized;
    private View layout_account_unauthorized;
    private View tv_to_auth_steps;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authorized;
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
        if (layout_auth_id_card_info == v) {
            HRActivity.start(getContext(), IDCardInfoFragment.class);
        } else if (layout_auth_bank_card_info == v) {
            HRActivity.start(getContext(), BankCardInfoFragment.class);
        } else if (tv_to_auth_steps == v) {
            HRActivity.start(getContext(), Step1Fragment.class);
        }
        // HRActivity.start(getContext(), Step1Fragment.class);
    }

    private void findViewById(View view) {
        layout_auth_id_card_info = view.findViewById(R.id.layout_auth_id_card_info);
        layout_auth_bank_card_info = view.findViewById(R.id.layout_auth_bank_card_info);
        iv_auth_yes_or_no = view.findViewById(R.id.iv_auth_yes_or_no);
        layout_account_authorized = view.findViewById(R.id.layout_account_authorized);
        layout_account_unauthorized = view.findViewById(R.id.layout_account_unauthorized);
        tv_to_auth_steps = view.findViewById(R.id.tv_to_auth_steps);

        layout_auth_id_card_info.setOnClickListener(this);
        layout_auth_bank_card_info.setOnClickListener(this);
        tv_to_auth_steps.setOnClickListener(this);
        boolean authenticated = HRUser.isAuthentication();
        iv_auth_yes_or_no.setSelected(authenticated);
        layout_account_authorized.setVisibility(authenticated ? View.VISIBLE : View.GONE);
        layout_account_unauthorized.setVisibility(!authenticated ? View.VISIBLE : View.GONE);
    }
}
