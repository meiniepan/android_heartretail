package com.idengyun.heartretail.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.activity.ActivityUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.MainActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.setting.pay.PaySettingFragment;
import com.idengyun.updatelib.update.UpdateUtils;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.LoginActivity;

/**
 * 设置界面
 *
 * @author aLang
 */
public final class SettingFragment extends BaseFragment implements View.OnClickListener {

    private View layout_setting_personal;
    private View layout_setting_account;
    private View layout_setting_pay;
    private View layout_setting_version;
    private View iv_setting_new;
    private TextView tv_setting_version;
    private View layout_setting_agreement;
    private View layout_setting_logout;

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
        iv_setting_new.setVisibility(UpdateUtils.isRequestAndHasNew() ? View.VISIBLE : View.INVISIBLE);
        tv_setting_version.setText(AppUtils.getAppVersionName());
        tv_setting_version.setCompoundDrawables(null, null, null, null);
    }

    @Override
    public void onClick(View v) {
        /* 个人资料 账号管理 当前版本 用户协议 退出登录 */
        if (layout_setting_personal == v) {
            startPersonalActivity();
        } else if (layout_setting_account == v) {
            startAccountActivity();
        } else if (layout_setting_pay == v) {
            startPaySettingActivity();
        } else if (layout_setting_version == v) {
            if (UpdateUtils.isRequestAndHasNew()) UpdateUtils.checkUpdate(getActivity(), true);
        } else if (layout_setting_agreement == v) {
            startAgreementActivity();
        } else if (layout_setting_logout == v) {
            logout();
        }
    }

    private void logout() {
        HRUser.clear();
        ActivityUtils.finishAllActivities();
        MainActivity.start(getContext());
        LoginActivity.start(getContext());
    }

    private void startAgreementActivity() {
        HRActivity.start(getActivity(), AgreeListFragment.class);
    }

    private void startPaySettingActivity() {
        HRActivity.start(getContext(), PaySettingFragment.class);
    }

    private void startAccountActivity() {
        HRActivity.start(getContext(), AccountFragment.class);
    }

    private void startPersonalActivity() {
        HRActivity.start(getContext(), PersonalFragment.class);
    }

    private void findViewById(View view) {
        layout_setting_personal = view.findViewById(R.id.layout_setting_personal);
        layout_setting_account = view.findViewById(R.id.layout_setting_account);
        layout_setting_pay = view.findViewById(R.id.layout_setting_pay);
        layout_setting_version = view.findViewById(R.id.layout_setting_version);
        iv_setting_new = view.findViewById(R.id.iv_setting_new);
        tv_setting_version = view.findViewById(R.id.tv_setting_version);
        layout_setting_agreement = view.findViewById(R.id.layout_setting_agreement);
        layout_setting_logout = view.findViewById(R.id.layout_setting_logout);

        layout_setting_personal.setOnClickListener(this);
        layout_setting_account.setOnClickListener(this);
        layout_setting_pay.setOnClickListener(this);
        layout_setting_version.setOnClickListener(this);
        layout_setting_agreement.setOnClickListener(this);
        layout_setting_logout.setOnClickListener(this);
    }
}
