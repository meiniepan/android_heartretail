package com.idengyun.heartretail.my.setting;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.PersonalDataBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 当前版本
 *
 * @author aLang
 */
public final class VersionFragment extends BaseFragment {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_version;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestAPI();
    }

    private void requestAPI() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryUserId())
                .fragment(this)
                .clazz(PersonalDataBean.class)
                .params("userId", HRUser.getId())
                .build();
        NetApi.<PersonalDataBean>getData(RequestMethod.GET, netOption, new JsonCallback<PersonalDataBean>(netOption) {
            @Override
            public void onSuccess(Response<PersonalDataBean> response) {
                updateUI(response.body().data);
            }
        });
    }

    @MainThread
    private void updateUI(PersonalDataBean.Data data) {
    }


    private void findViewById(View view) {
    }
}
