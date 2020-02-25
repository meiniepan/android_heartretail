package com.dengyun.baselibrary.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dengyun.baselibrary.base.presenter.BaseActivityPresenter;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @titile
 * @desc Created by seven on 2018/2/26.
 */

public abstract class BaseMvpActivity<V,P extends BaseActivityPresenter<V>> extends BaseActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach((V)this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mPresenter.detach();
        OkGo.getInstance().cancelTag(this);
        super.onDestroy();
    }

    protected abstract P initPresenter();

    @Override
    protected void onInitViews(Bundle savedInstanceState) {
        super.onInitViews(savedInstanceState);
        mPresenter.onInitViews(savedInstanceState);
    }

    /*@Override
    protected void onInitData() {
        super.onInitData();
        mPresenter.onInitData();
    }*/

    protected void bindRefreshLoadMoreToP(SmartRefreshLayout smartRefreshLayout){
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onLoadMoreData(refreshLayout);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onRefreshData(refreshLayout);
            }
        });
    }

    protected void bindRefreshToP(SmartRefreshLayout smartRefreshLayout){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onRefreshData(refreshLayout);
            }
        });
    }

    protected void bindLoadMoreToP(SmartRefreshLayout smartRefreshLayout){
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onLoadMoreData(refreshLayout);
            }
        });
    }

}
