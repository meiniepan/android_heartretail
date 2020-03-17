package com.sobot.chat.loaders;

import android.support.v4.app.FragmentActivity;

/**
 * @Title 客服的针对项目的加载器
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 15:50
 */
public interface SobotLoader {

    /**
     * 请求订单列表数据
     *
     * @param page                  请求页码
     * @param onOrderLoaderListener 回调
     */
    void requestOrderList(FragmentActivity activity, int page, final OnOrderLoaderListener onOrderLoaderListener);

    /**
     * 请求售后订单列表数据
     *
     * @param page                  请求页码
     * @param onAfterOrderLoaderListener 回调
     */
    void requestAfterOrderList(FragmentActivity activity, int page, final OnAfterOrderLoaderListener onAfterOrderLoaderListener);
}
