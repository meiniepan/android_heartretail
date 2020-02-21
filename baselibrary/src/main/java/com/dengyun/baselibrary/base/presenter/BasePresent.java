package com.dengyun.baselibrary.base.presenter;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * @titile
 * @desc Created by seven on 2018/2/26.
 */

public abstract class BasePresent<V> {
    /*public Reference<V> view;  //View接口类型的弱引用

    public void attach(V view) {
        this.view = new WeakReference<V>(view);
    }

    public void detach() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }*/

    public V mView;  //View接口类型的弱引用


    public void attach(V view) {
        this.mView = view;
    }

    public void detach() {
        mView = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached(){
        return mView!= null;
    }

    public void onRefreshData(RefreshLayout refreshLayout){}

    public void onLoadMoreData(RefreshLayout refreshLayout){}
}
