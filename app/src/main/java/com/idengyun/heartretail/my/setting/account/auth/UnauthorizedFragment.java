package com.idengyun.heartretail.my.setting.account.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.utils.SecondsTimer;

/**
 * 未认证
 *
 * @author aLang
 */
public final class UnauthorizedFragment extends BaseFragment {


    private SecondsTimer timer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_unauthorized;
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
