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
import com.idengyun.heartretail.model.response.ProtocolsBean;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * 协议模块API
 *
 * @author aLang
 */
public final class AgreeViewModel extends ViewModel {

    public static AgreeViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(AgreeViewModel.class);
    }

    private final MutableLiveData<ProtocolsBean> agreeListLiveData;

    public AgreeViewModel() {
        super();
        agreeListLiveData = new MutableLiveData<>();
    }

    /* 查询协议 */
    public void requestAgreeList(Fragment fragment, List<Integer> protocolIds) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.protocolDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(ProtocolsBean.class)
                .params("protocolIds", protocolIds)
                .build();
        NetApi.getData(netOption, new JsonCallback<ProtocolsBean>(netOption) {
            @Override
            public void onSuccess(Response<ProtocolsBean> response) {
                agreeListLiveData.setValue(response.body());
            }
        });
    }

    public LiveData<ProtocolsBean> getAgreeList() {
        return agreeListLiveData;
    }
}
