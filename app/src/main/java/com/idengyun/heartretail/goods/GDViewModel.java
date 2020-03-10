package com.idengyun.heartretail.goods;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.model.response.GoodsDetailBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 商品详情视图模型
 *
 * @author aLang
 */
public final class GDViewModel extends ViewModel {

    public static void observe(@NonNull FragmentActivity activity,
                               @NonNull Fragment fragment,
                               @NonNull Observer<GoodsDetailBean.Data> observer) {
        ViewModelProviders.of(activity).get(GDViewModel.class).getGoodsDetailData().observe(fragment, observer);
    }

    public static GDViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(GDViewModel.class);
    }

    private final MutableLiveData<GoodsDetailBean.Data> liveData = new MutableLiveData<>();

    /* 请求商品详情 */
    public void requestGoodsDetailAPI(Fragment fragment, String goodsId, int goodsType) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.goodsDetail())
                .fragment(fragment)
                .clazz(GoodsDetailBean.class)
                .params("goodsId", goodsId)
                .params("userId", HRUser.getId())
                .params("goodsType", goodsType)// 0零售1批发
                .build();
        NetApi.<GoodsDetailBean>getData(netOption, new JsonCallback<GoodsDetailBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsDetailBean> response) {
                GoodsDetailBean.Data data = response.body().data;
                liveData.postValue(data);
            }
        });
    }

    private LiveData<GoodsDetailBean.Data> getGoodsDetailData() {
        return liveData;
    }
}
