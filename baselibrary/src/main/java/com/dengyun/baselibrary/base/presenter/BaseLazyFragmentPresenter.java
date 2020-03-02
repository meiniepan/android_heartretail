package com.dengyun.baselibrary.base.presenter;

import android.view.View;

import com.dengyun.baselibrary.base.lifecycle.LazyFragmentLifecycle;

/**
 * @titile
 * @desc Created by seven on 2018/5/24.
 */

public  abstract class BaseLazyFragmentPresenter<V> extends BaseFragmentPresenter<V> implements LazyFragmentLifecycle {
    @Override
    public void onLazyInitView(View view) {

    }
}
