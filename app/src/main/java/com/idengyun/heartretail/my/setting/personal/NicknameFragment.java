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
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeNick())
                .fragment(this)
                .clazz(UserNickBean.class)
                .params("userId", HRUser.getId())
                .params("nickName", nickname)
                .build();
        NetApi.<UserNickBean>getData(netOption, new JsonCallback<UserNickBean>(netOption) {
            @Override
            public void onSuccess(Response<UserNickBean> response) {
                updateUI(response.body().data);
                HRUser.saveNickname(nickname);
                if (getActivity() != null) getActivity().onBackPressed();
            }
        });
    }

    @MainThread
    private void updateUI(UserNickBean.Data data) {

    }

    private void findViewById(View view) {
        et_nick_name = view.findViewById(R.id.et_nick_name);
        tv_nickname_modify = view.findViewById(R.id.tv_nickname_modify);

        tv_nickname_modify.setOnClickListener(this);
    }
}
