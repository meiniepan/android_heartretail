package com.idengyun.heartretail.my.setting.personal;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.UserAvatarBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 修改头像
 *
 * @author aLang
 */
public final class AvatarFragment extends BaseFragment implements View.OnClickListener {

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

    }

    private void modifyAvatar(final String avatarUrl) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeIcon())
                .fragment(this)
                .clazz(UserAvatarBean.class)
                .params("url", avatarUrl)
                .params("userId", HRUser.getId())
                .build();
        NetApi.<UserAvatarBean>getData(netOption, new JsonCallback<UserAvatarBean>(netOption) {
            @Override
            public void onSuccess(Response<UserAvatarBean> response) {
                updateUI(response.body().data);
                HRUser.saveAvatar(avatarUrl);
                if (getActivity() != null) getActivity().onBackPressed();
            }
        });
    }

    @MainThread
    private void updateUI(UserAvatarBean.Data data) {

    }

    private void findViewById(@NonNull View view) {

    }
}
