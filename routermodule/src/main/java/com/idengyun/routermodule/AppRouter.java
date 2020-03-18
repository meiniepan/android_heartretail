package com.idengyun.routermodule;

import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.idengyun.commonmodule.beans.OrderListBean;

import java.util.List;

/**
 * @Title app模块的通信解开
 * @Author: zhoubo
 * @CreateDate: 2019-05-07 17:56
 */
public interface AppRouter extends IProvider {

    /**
     * 异步请求订单列表
     * @param status  订单状态：
     * @param page      请求页码
     * @param onRequestOrderListListener 异步回调
     */
    void getOrderListAsyn(FragmentActivity activity,int status, int page, OnRequestOrderListListener onRequestOrderListListener);



    interface OnRequestOrderListListener{
        void onResultOrderList(OrderListBean resultOrderListBean);
        void onError();
    }
}
