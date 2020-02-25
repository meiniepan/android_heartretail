package com.dengyun.baselibrary.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.base.presenter.BaseFragmentPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @titile
 * @desc Created by seven on 2018/2/26.
 */

public abstract class BaseMvpFragment<V, P extends BaseFragmentPresenter<V>> extends BaseFragment {
    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //创建presenter
        mPresenter = initPresenter();
        mPresenter.onAttach(context);
    }

    /**
     * 运行在onAttach之后
     * 可以接受别人传递过来的参数,实例化对象.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onCreate(savedInstanceState);
    }

    /**
     * 运行在onCreate之后
     * 生成view视图
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.onCreateView(inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter.attach((V) this);
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated(view, savedInstanceState);
    }

    /**
     * 运行在onCreateView之后
     * 加载数据
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        presenter.attach((V)this);
        super.onActivityCreated(savedInstanceState);
        mPresenter.onActivityCreated(savedInstanceState);
        //由于fragment生命周期比较复杂,所以Presenter在onCreateView创建视图之后再进行绑定,不然会报空指针异常
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onDetach() {
        mPresenter.onDetach();
        mPresenter.detach();
        super.onDetach();
    }

    /**
     * 创建prensenter
     */
    protected abstract P initPresenter();

    //下面两个是我们自己的生命周期
    @Override
    public void onInitViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onInitViews(view, savedInstanceState);
        mPresenter.onInitViews(view, savedInstanceState);
    }

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
