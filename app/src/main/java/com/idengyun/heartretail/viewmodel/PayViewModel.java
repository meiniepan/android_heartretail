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
import com.idengyun.heartretail.beans.BalanceBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 支付模块API
 *
 * @author aLang
 */
public final class PayViewModel extends ViewModel {

    public static PayViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(PayViewModel.class);
    }

    private final MutableLiveData<BalanceBean> balanceLiveData;

    public PayViewModel() {
        super();
        balanceLiveData = new MutableLiveData<>();
    }

    /* 查询我的余额信息 */
    public void requestBalance(Fragment fragment) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.balanceInfo())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(BalanceBean.class)
                .params("userId", HRUser.getId())
                .build();
        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<BalanceBean>(netOption) {
            @Override
            public void onSuccess(Response<BalanceBean> response) {
                balanceLiveData.setValue(response.body());
            }
        });
    }

    public LiveData<BalanceBean> getBalance() {
        return balanceLiveData;
    }
}
