package com.dengyun.baselibrary.base.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * @titile  View层基类
 * @desc Created by seven on 2018/2/24.
 */

public interface BaseView {
    void showProgress();
    void hideProgress();
    void toast(String msg);
    void toast(int msgId);
    void toastLong(String msg);
    void toastLong(int msgId);
    Context getContext();
    FragmentActivity getMyActivity();
    String getMyTag();
    void showEmpty();
    void showError();
    void showNoNet();
    void showEmpty(View emptyView);
    void showError(View errorView);
    void showNoNet(View noNetView);
    void showContent();
    int getViewStatus();
    void setOnErrorRetryClickListener(View.OnClickListener onErrorRetryClickListener);
    void setOnEmptyRetryClickListener(View.OnClickListener onEmptyRetryClickListener);
    void setOnNoNetRetryClickListener(View.OnClickListener onNoNetRetryClickListener);
}
