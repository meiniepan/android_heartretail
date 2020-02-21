package com.dengyun.baselibrary.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dengyun.baselibrary.base.lifecycle.ActivityLifecycle;

/**
 * @titile
 * @desc Created by seven on 2018/5/23.
 */

public abstract class BaseActivityPresenter<V> extends BasePresent<V> implements ActivityLifecycle {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onInitViews(Bundle savedInstanceState) {

    }

}
