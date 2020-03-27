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
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.beans.ProtocolBean;
import com.lzy.okgo.model.Response;

import java.util.Set;

/**
 * 协议模块API
 *
 * @author aLang
 */
public final class ProtocolViewModel extends ViewModel {

    public static ProtocolViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(ProtocolViewModel.class);
    }

    private final MutableLiveData<ProtocolBean> protocolListLiveData;

    public ProtocolViewModel() {
        super();
        protocolListLiveData = new MutableLiveData<>();
    }

    /* 查询协议 */
    public void requestProtocolList(Fragment fragment, Set<String> protocolKeySet) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.protocolDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(ProtocolBean.class)
                .params("protocolKeys", protocolKeySet)
                .build();
        NetApi.getData(netOption, new JsonCallback<ProtocolBean>(netOption) {
            @Override
            public void onSuccess(Response<ProtocolBean> response) {
                protocolListLiveData.setValue(response.body());
            }
        });
    }

    public LiveData<ProtocolBean> getProtocolList() {
        return protocolListLiveData;
    }
}
