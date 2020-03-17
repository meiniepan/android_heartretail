package com.idengyun.heartretail.my.setting.personal;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRSession;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.UserNickBean;
import com.idengyun.usermodule.HRUser;

/**
 * 修改昵称
 *
 * @author aLang
 */
public final class NicknameFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_nick_name;
    private View tv_nickname_modify;

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
        HRSession.session_04(this, nickname, HRUser.getInviteCode(), new Observer<UserNickBean.Data>() {
            @Override
            public void onChanged(@Nullable UserNickBean.Data data) {
                HRUser.saveNickname(nickname);
                if (getActivity() != null) getActivity().onBackPressed();
            }
        });
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
