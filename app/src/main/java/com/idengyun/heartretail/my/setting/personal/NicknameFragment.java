package com.idengyun.heartretail.my.setting.personal;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.UserNickBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 修改昵称界面
 *
 * @author aLang
 */
public final class NicknameFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_nick_name;

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
        et_nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                requestAPI();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void requestAPI() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeNick())
                .fragment(this)
                .clazz(UserNickBean.class)
                .params("userId", HRUser.getId())
                .params("nickName", et_nick_name.getText().toString())
                .build();
        NetApi.<UserNickBean>getData(netOption, new JsonCallback<UserNickBean>(netOption) {
            @Override
            public void onSuccess(Response<UserNickBean> response) {
                updateUI(response.body().data);
            }
        });
    }

    @MainThread
    private void updateUI(UserNickBean.Data data) {
        if (getActivity() != null) getActivity().onBackPressed();
    }

    private void findViewById(View view) {
        et_nick_name = view.findViewById(R.id.et_nick_name);
    }
}
