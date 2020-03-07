package com.idengyun.heartretail.my;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;

/**
 * 设置界面
 *
 * @author aLang
 */
public final class SettingFragment extends BaseFragment implements View.OnClickListener {

    private View layout_personal;
    private View layout_account;
    private View layout_version;
    private View layout_agreement;
    private View layout_logout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
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
        /* 个人资料 账号管理 当前版本 用户协议 退出登录 */
        if (layout_personal == v) {
            startPersonalActivity();
        } else if (layout_account == v) {
            startAccountActivity();
        } else if (layout_version == v) {
            startVersionActivity();
        } else if (layout_agreement == v) {
            startAgreementActivity();
        } else if (layout_logout == v) {
            logout();
        }
    }

    private void logout() {
        // TODO: 2020/3/6
        ToastUtils.showShort("待做...");
    }

    private void startAgreementActivity() {
        // TODO: 2020/3/6
        ToastUtils.showShort("待做...");
    }

    private void startVersionActivity() {
        // TODO: 2020/3/6
        ToastUtils.showShort("待做...");
    }

    private void startAccountActivity() {
        HRActivity.start(getContext(), AccountFragment.class);
    }

    private void startPersonalActivity() {
        HRActivity.start(getContext(), PersonalFragment.class);
    }

    private void findViewById(View view) {
        layout_personal = view.findViewById(R.id.layout_personal);
        layout_account = view.findViewById(R.id.layout_account);
        layout_version = view.findViewById(R.id.layout_version);
        layout_agreement = view.findViewById(R.id.layout_agreement);
        layout_logout = view.findViewById(R.id.layout_logout);

        layout_personal.setOnClickListener(this);
        layout_account.setOnClickListener(this);
        layout_version.setOnClickListener(this);
        layout_agreement.setOnClickListener(this);
        layout_logout.setOnClickListener(this);
    }
}
