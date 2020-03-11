package com.idengyun.heartretail.my.setting.account.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;

/**
 * 步骤3
 *
 * @author aLang
 */
public final class Step1Fragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_real_step_1;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void findViewById(View view) {
    }
}
