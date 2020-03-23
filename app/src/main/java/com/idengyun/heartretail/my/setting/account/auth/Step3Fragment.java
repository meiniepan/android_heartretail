package com.idengyun.heartretail.my.setting.account.auth;

import android.content.Intent;
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
public final class Step3Fragment extends BaseFragment implements View.OnClickListener {

    private View tv_real_go_auth;

    /* 身份证和银行卡正反面本地文件路径 */
    private String idCardTrueUrl;
    private String idCardFalseUrl;
    private String bankCardTrueUrl;
    private String bankCardFalseUrl;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_real_step_3;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        idCardTrueUrl = intent.getStringExtra("id_card_true_url");
    }

    @Override
    public void onClick(View v) {

    }

    private void findViewById(View view) {
        tv_real_go_auth = view.findViewById(R.id.tv_real_go_auth);
    }
}
