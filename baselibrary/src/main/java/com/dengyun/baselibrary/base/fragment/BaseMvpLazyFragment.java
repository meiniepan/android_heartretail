package com.dengyun.baselibrary.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.presenter.BaseLazyFragmentPresenter;
import com.dengyun.baselibrary.utils.AppLogUtil;

/**
 * @titile
 * @desc Created by seven on 2018/3/14.
 */

public abstract class BaseMvpLazyFragment<V, P extends BaseLazyFragmentPresenter<V>> extends BaseMvpFragment<V, P> {
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    //已经加载过一次
    private boolean hasLoadedOnce;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        onVisible();
    }

    @Deprecated
    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    /**
     * Fragment当前状态是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            /*可见*/
            isUIVisible = true;
            onVisible();
        } else {
            /*不可见*/
            isUIVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        if (!isViewCreated || !isUIVisible || hasLoadedOnce) {
            return;
        }
        hasLoadedOnce = true;
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    protected void lazyLoad() {
        onLazyInitView(getView());
        onLazyInitData();
    }

    protected void onLazyInitView(View view) {
        AppLogUtil.setFragmentLCLog(getMyTag(),"onLazyInitView");
        lazyInitView(view);
        mPresenter.onLazyInitView(view);
    }

    private void onLazyInitData() {
        AppLogUtil.setFragmentLCLog(getMyTag(),"onLazyInitData");
        lazyInitData();
    }

    protected abstract void lazyInitView(View view);

    protected abstract void lazyInitData();
}
