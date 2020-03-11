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
import com.idengyun.heartretail.model.response.GoodsEvaluateBean;
import com.idengyun.heartretail.model.response.GoodsListBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 商品列表视图模型
 *
 * @author aLang
 */
public final class GLViewModel extends ViewModel {

    public static void observe(@NonNull FragmentActivity activity,
                               @NonNull Fragment fragment,
                               @NonNull Observer<GoodsListBean.Data> observer) {
        ViewModelProviders.of(activity).get(GLViewModel.class).getGoodsListData().observe(fragment, observer);
    }

    public static GLViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(GLViewModel.class);
    }

    private final MutableLiveData<GoodsListBean.Data> liveData = new MutableLiveData<>();

    private int totalPageSize = -1;
    private int totalPage = -1;
    private int pageSize = 10;
    private int page = 0;
    private boolean loadMore = true;

    /* 请求商品评价 */
    public void requestGoodsListAPI(Fragment fragment, int goodsType, int sortType, int sortStyle) {
        if (!loadMore) return;

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.evaluationList())
                .fragment(fragment)
                .clazz(GoodsListBean.class)
                .params("userId", HRUser.getId())
                .params("goodsType", goodsType)
                .params("page", page + 1)
                .params("pageSize", pageSize)
                .params("sortType", sortType)
                .params("sortStyle", sortStyle)
                .build();
        NetApi.<GoodsListBean>getData(netOption, new JsonCallback<GoodsListBean>(netOption) {
            @Override
            public void onSuccess(Response<GoodsListBean> response) {
                GoodsListBean.Data data = response.body().data;

                totalPageSize = data.total;
                totalPage = (int) Math.ceil(1D * totalPageSize / pageSize);
                loadMore = ++page < totalPage;

                liveData.postValue(data);
            }
        });
    }

    private void refresh(Fragment fragment, int goodsType, int sortType, int sortStyle) {
        totalPageSize = -1;
        totalPage = -1;
        pageSize = 10;
        page = 0;
        loadMore = true;
        requestGoodsListAPI(fragment, goodsType, sortType, sortStyle);
    }

    private LiveData<GoodsListBean.Data> getGoodsListData() {
        return liveData;
    }

    public boolean isFirstPage() {
        return page == 1;
    }

    public boolean isLastPage() {
        return page == totalPage;
    }

    public int getTotalPageSize() {
        return totalPageSize;
    }

    public boolean isLoadMore() {
        return loadMore;
    }
}
