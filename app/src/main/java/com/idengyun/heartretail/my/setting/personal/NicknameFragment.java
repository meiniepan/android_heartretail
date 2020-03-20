package com.idengyun.heartretail.my.setting.personal;

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
import com.idengyun.heartretail.model.response.UserNickBean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;
import com.idengyun.usermodule.HRUser;

/**
 * 修改昵称
 *
 * @author aLang
 */
public final class NicknameFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_nick_name;
    private View tv_nickname_modify;

    private SettingViewModel settingViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nick_modify;
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
                    HRUser.saveNickname(et_nick_name.getText().toString());
                    if (getActivity() != null) getActivity().onBackPressed();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (et_nick_name.length() < 1) {
            ToastUtils.showShort("请输入昵称");
            return;
        }
        modifyNickname(et_nick_name.getText().toString());
    }

    private void modifyNickname(final String nickname) {
        if (settingViewModel == null) return;
        settingViewModel.requestModifyNickname(this, nickname, HRUser.getInviteCode());
    }

    private void findViewById(View view) {
        et_nick_name = view.findViewById(R.id.et_nick_name);
        tv_nickname_modify = view.findViewById(R.id.tv_nickname_modify);

        tv_nickname_modify.setOnClickListener(this);
        et_nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_nickname_modify.setEnabled(s.length() > 0);
            }
        });
    }
}
