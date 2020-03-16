package com.idengyun.heartretail.my.setting.personal;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.UserNickBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 填写邀请码
 *
 * @author aLang
 */
public final class InviteCodeFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_invite_code;
    private View tv_invite_code_modify;

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
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeNick())
                .fragment(this)
                .clazz(UserNickBean.class)
                .params("userId", HRUser.getId())
                .params("nickName", HRUser.getNickname())
                .params("invitationCode", inviteCode)
                .build();
        NetApi.<UserNickBean>getData(netOption, new JsonCallback<UserNickBean>(netOption) {
            @Override
            public void onSuccess(Response<UserNickBean> response) {
                updateUI(response.body().data);
                HRUser.saveInviteCode(inviteCode);
                if (getActivity() != null) getActivity().onBackPressed();
            }
        });
    }

    @MainThread
    private void updateUI(UserNickBean.Data data) {

    }

    private void findViewById(View view) {
        et_invite_code = view.findViewById(R.id.et_invite_code);
        tv_invite_code_modify = view.findViewById(R.id.tv_invite_code_modify);

        tv_invite_code_modify.setOnClickListener(this);
    }
}
