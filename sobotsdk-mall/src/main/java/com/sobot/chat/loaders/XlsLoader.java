package com.sobot.chat.loaders;

import android.support.v4.app.FragmentActivity;

import com.idengyun.commonmodule.beans.OrderListBean;
import com.idengyun.routermodule.ARouterInstance;
import com.idengyun.routermodule.AppRouter;

/**
 * @Title 心零售的加载器
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 16:00
 */
public class XlsLoader implements SobotLoader {
    @Override
    public void requestOrderList(FragmentActivity activity, int page, final OnOrderLoaderListener onOrderLoaderListener) {
        ARouterInstance.getAppRouter().getOrderListAsyn(activity, 0, page, new AppRouter.OnRequestOrderListListener() {
            @Override
            public void onResultOrderList(OrderListBean resultOrderListBean) {

            }

            @Override
            public void onError() {
                onOrderLoaderListener.onError();
            }
        });
    }

    @Override
    public void requestAfterOrderList(FragmentActivity activity, int page, OnAfterOrderLoaderListener onAfterOrderLoaderListener) {

    }
}
