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
import com.idengyun.heartretail.beans.GoodsDetailBean;
import com.idengyun.heartretail.beans.GoodsEvaluateBean;
import com.idengyun.heartretail.beans.GoodsListBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 商品模块API
 *
 * @author aLang
 */
public final class GoodsViewModel extends ViewModel {

    public static GoodsViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(GoodsViewModel.class);
    }

    public static GoodsViewModel getInstance(@NonNull Fragment fragment) {
        return ViewModelProviders.of(fragment).get(GoodsViewModel.class);
    }

    private final MutableLiveData<GoodsListBean> goodsListLiveData;
    private final MutableLiveData<GoodsDetailBean> goodsDetailLiveData;
    private final MutableLiveData<GoodsEvaluateBean> goodsEvaluateLiveData;

    public GoodsViewModel() {
        super();
        goodsListLiveData = new MutableLiveData<>();
        goodsDetailLiveData = new MutableLiveData<>();
        goodsEvaluateLiveData = new MutableLiveData<>();
    }

    /* 商品列表 */
    public void requestGoodsList(Fragment fragment, int goodsType, int page, int pageSize, int sortType, int sortStyle) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.goodsList())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(GoodsListBean.class)
                .params("userId", HRUser.getId())
                .params("goodsType", goodsType)
                .params("page", page)
                .params("pageSize", pageSize)
                .params("sortType", sortType)
                .params("sortStyle", sortStyle)
                .build();
        NetApi.getData(netOption, new JsonCallback<GoodsListBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsListBean> response) {
                goodsListLiveData.setValue(response.body());
            }
        });
    }

    /* 商品详情 */
    public void requestGoodsDetail(Fragment fragment, int goodsId, int goodsType) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.goodsDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(GoodsDetailBean.class)
                .params("goodsId", goodsId)
                .params("userId", HRUser.getId())
                .params("goodsType", goodsType)// 0零售1批发
                .build();
        NetApi.getData(netOption, new JsonCallback<GoodsDetailBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsDetailBean> response) {
                goodsDetailLiveData.setValue(response.body());
            }
        });
    }

    /* 商品评价 */
    public void requestGoodsEvaluate(Fragment fragment, int goodsId, int page, int pageSize) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.evaluationList())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(GoodsEvaluateBean.class)
                .params("goodsId", goodsId)
                .params("page", page + 1)
                .params("pageSize", pageSize)
                .build();
        NetApi.getData(netOption, new JsonCallback<GoodsEvaluateBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsEvaluateBean> response) {
                goodsEvaluateLiveData.setValue(response.body());
            }
        });
    }

    public LiveData<GoodsListBean> getGoodsList() {
        return goodsListLiveData;
    }

    public LiveData<GoodsDetailBean> getGoodsDetail() {
        return goodsDetailLiveData;
    }

    public LiveData<GoodsEvaluateBean> getGoodsEvaluate() {
        return goodsEvaluateLiveData;
    }

    /*MediatorLiveData<Integer> liveDataMerger = new MediatorLiveData<>();
    MutableLiveData<Integer> liveData1 = new MutableLiveData<>();
    MutableLiveData<Integer> liveData2 = new MutableLiveData<>();

    public void fun(LifecycleOwner owner) {
        liveDataMerger.addSource(liveData1, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                System.out.println("+++++++++liveData1 = " + integer);
                liveDataMerger.setValue(integer);
            }
        });
        liveDataMerger.addSource(liveData2, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                System.out.println("--------liveData2 = " + integer);
                liveDataMerger.setValue(integer);
            }
        });
        liveData1.setValue(127);
        liveData2.setValue(128);
        liveDataMerger.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                System.out.println("=======liveDataMerger = " + integer);
            }
        });
    }*/
}
