package com.idengyun.heartretail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.lzy.okgo.model.Response;

/**
 * 验证码API
 *
 * @author aLang
 */
public final class VerifyCodeViewModel extends ViewModel {

    public static VerifyCodeViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(VerifyCodeViewModel.class);
    }

    private final MutableLiveData<VerifyCodeBean> verifyCodeLiveData;

    public VerifyCodeViewModel() {
        super();
        verifyCodeLiveData = new MutableLiveData<>();
    }

    /* 发送验证码 */
    public void requestVerifyCode(Fragment fragment, String identifyType) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getIdentifyCode())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(VerifyCodeBean.class)
                .params("mobile", HRUser.getMobile())
                .params("identifyType", identifyType)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<VerifyCodeBean>(netOption) {
            @Override
            public void onSuccess(Response<VerifyCodeBean> response) {
                verifyCodeLiveData.setValue(response.body());
            }
        });
    }

    public LiveData<VerifyCodeBean> getVerifyCode() {
        return verifyCodeLiveData;
    }
}
