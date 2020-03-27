package com.idengyun.heartretail.setting.personal;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.UserNickBean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;
import com.idengyun.usermodule.HRUser;

/**
 * 填写邀请码
 *
 * @author aLang
 */
public final class InviteCodeFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_invite_code;
    private View tv_invite_code_modify;
    private SettingViewModel settingViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_save_invite_code;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (settingViewModel == null) {
            settingViewModel = SettingViewModel.getInstance(activity);
            settingViewModel.getModifyNickname().observe(this, new Observer<UserNickBean>() {
                @Override
                public void onChanged(@Nullable UserNickBean userNickBean) {
                    HRUser.saveInviteCode(et_invite_code.getText().toString());
                    if (getActivity() != null) getActivity().onBackPressed();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (et_invite_code.length() < 1) {
            ToastUtils.showShort("请填写邀请码");
            return;
        }
        modifyInviteCode(et_invite_code.getText().toString());
    }

    private void modifyInviteCode(final String inviteCode) {
        if (settingViewModel == null) return;
        settingViewModel.requestModifyNickname(this, HRUser.getNickname(), inviteCode);
    }

    private void findViewById(View view) {
        et_invite_code = view.findViewById(R.id.et_invite_code);
        tv_invite_code_modify = view.findViewById(R.id.tv_invite_code_modify);

        tv_invite_code_modify.setOnClickListener(this);
        et_invite_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_invite_code_modify.setEnabled(s.length() > 0);
            }
        });
    }
}
