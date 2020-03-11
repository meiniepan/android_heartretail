package com.idengyun.heartretail.my.setting;

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
        if (layout_pay_setting_modify_pwd == v) {
            startPersonalActivity();
        } else if (layout_pay_setting_forget_pwd == v) {
            startAccountActivity();
        }
    }

    private void startAccountActivity() {
        HRActivity.start(getContext(), AccountFragment.class);
    }

    private void startPersonalActivity() {
        HRActivity.start(getContext(), PersonalFragment.class);
    }

    private void findViewById(View view) {
        layout_pay_setting_modify_pwd = view.findViewById(R.id.layout_pay_setting_modify_pwd);
        layout_pay_setting_forget_pwd = view.findViewById(R.id.layout_pay_setting_forget_pwd);
    }
}
