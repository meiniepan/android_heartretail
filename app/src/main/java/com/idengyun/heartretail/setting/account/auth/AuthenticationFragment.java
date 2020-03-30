package com.idengyun.heartretail.setting.account.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.HRUser;

/**
 * 是否认证
 *
 * @author aLang
 */
public final class AuthenticationFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_real_avatar;
    private TextView tv_real_name;
    private TextView tv_real_mobile;
    private View iv_auth_yes_or_no;

    private View layout_auth_id_card_info;
    private View layout_auth_bank_card_info;
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
    public void onResume() {
        super.onResume();
        updateUI();
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

    private void updateUI() {
        ImageApi.displayImage(iv_real_avatar.getContext(), iv_real_avatar, HRUser.getAvatar());
        tv_real_name.setText(HRUser.getNickname());
        // tv_real_mobile.setText(HRUser.getMobile());
        boolean authenticated = HRUser.isAuthentication();
        iv_auth_yes_or_no.setSelected(authenticated);
        layout_account_authorized.setVisibility(authenticated ? View.VISIBLE : View.GONE);
        layout_account_unauthorized.setVisibility(!authenticated ? View.VISIBLE : View.GONE);
        String mobile = HRUser.getMobile();
        if (mobile.length() == 11) {
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            tv_real_mobile.setText(mobile);
        }
    }

    private void findViewById(View view) {
        iv_real_avatar = view.findViewById(R.id.iv_real_avatar);
        tv_real_name = view.findViewById(R.id.tv_real_name);
        tv_real_mobile = view.findViewById(R.id.tv_real_mobile);
        iv_auth_yes_or_no = view.findViewById(R.id.iv_auth_yes_or_no);
        layout_auth_id_card_info = view.findViewById(R.id.layout_auth_id_card_info);
        layout_auth_bank_card_info = view.findViewById(R.id.layout_auth_bank_card_info);
        layout_account_authorized = view.findViewById(R.id.layout_account_authorized);
        layout_account_unauthorized = view.findViewById(R.id.layout_account_unauthorized);
        tv_to_auth_steps = view.findViewById(R.id.tv_to_auth_steps);

        layout_auth_id_card_info.setOnClickListener(this);
        layout_auth_bank_card_info.setOnClickListener(this);
        tv_to_auth_steps.setOnClickListener(this);
    }
}
