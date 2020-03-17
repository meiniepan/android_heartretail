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
import com.idengyun.heartretail.model.response.RedPacketBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 红包模块API
 *
 * @author aLang
 */
public final class RedPacketViewModel extends ViewModel {

    public static RedPacketViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(RedPacketViewModel.class);
    }

    private final MutableLiveData<RedPacketBean> redPacketDetailLiveData;

    public RedPacketViewModel() {
        super();
        redPacketDetailLiveData = new MutableLiveData<>();
    }

    /* 查询红包详情 */
    public void requestRedPacketDetail(Fragment fragment, int page, int pageSize) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.packetDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(RedPacketBean.class)
                .params("userId", HRUser.getId())
                .params("page", page)
                .params("pageSize", pageSize)
                .build();
        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<RedPacketBean>(netOption) {
            @Override
            public void onSuccess(Response<RedPacketBean> response) {
                redPacketDetailLiveData.setValue(response.body());
            }
        });
    }

    public LiveData<RedPacketBean> getRedPacketDetail() {
        return redPacketDetailLiveData;
    }
}
