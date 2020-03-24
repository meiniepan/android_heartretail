package com.dengyun.baselibrary.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.base.dialog.loading.LoadingDialog1;
import com.dengyun.baselibrary.base.view.BaseFragmentView;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.rx.RxManager;
import com.dengyun.baselibrary.utils.AppLogUtil;
import com.dengyun.baselibrary.utils.ObjectUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.widgets.statelayout.StateFrameLayout;
import com.dengyun.baselibrary.widgets.statelayout.StateLinearLayout;
import com.dengyun.baselibrary.widgets.statelayout.StateRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @titile
 * @desc Created by seven on 2018/2/26.
 */

public abstract class BaseFragment extends Fragment implements BaseFragmentView {

    protected Context mContext;//activity的上下文对象
    protected Bundle mBundle;
    private boolean hasBus = false;
    private Unbinder unbinder;
    private LoadingDialog1 loadingDialog;
    private String TAG = "BaseFragment";//本fragment的tag，在onAttach方法中重置，为当前Fragment的包名+类名路径
    private View contentView;

    /**
     * 绑定activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = ObjectUtils.getClassPath(this);
        mContext = context;
        AppLogUtil.setFragmentLCLog(TAG,"onAttach");

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
        AppLogUtil.setFragmentLCLog(TAG,"onCreate");
        //获取bundle,并保存起来
        if (savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle("bundle");
        } else {
            mBundle = getArguments() == null ? new Bundle() : getArguments();
        }
    }

    /**
     * 运行在onCreate之后
     * 生成view视图
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLogUtil.setFragmentLCLog(TAG,"onCreateView");
        View view = inflater.inflate(getLayoutId(), container, false);
        initBinds(view);
        initCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppLogUtil.setFragmentLCLog(TAG,"onViewCreated");
        this.contentView = view;
        onInitViews(view,savedInstanceState);
//        onInitData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLogUtil.setFragmentLCLog(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        AppLogUtil.setFragmentLCLog(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        AppLogUtil.setFragmentLCLog(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppLogUtil.setFragmentLCLog(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        AppLogUtil.setFragmentLCLog(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppLogUtil.setFragmentLCLog(TAG,"onDestroyView");
        //本页的网络请求取消
        NetApi.cancelTag(TAG);
        //本页的observable取消
        RxManager.getInstance().clear(TAG);
        //本页的view解除绑定
        unBindsView();
        //本页的eventbus解除注册
        if (hasBus) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLogUtil.setFragmentLCLog(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AppLogUtil.setFragmentLCLog(TAG,"onDetach");
    }

    @Override
    public String getMyTag() {
        return TAG==null? ObjectUtils.getClassPath(this):TAG;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBundle != null) {
            outState.putBundle("bundle", mBundle);
        }
    }

    public <T extends View> T findViewById(int id) {
        if (null != contentView) return contentView.findViewById(id);
        return null;
    }

    /**
     * @return  设置fragment的布局
     */
    public abstract int getLayoutId();
    /**
     * 初始化Fragment应有的视图
     * @return
     */
    public abstract void initViews(@NonNull View view, @Nullable Bundle savedInstanceState);

    public void onInitViews(@NonNull View view, @Nullable Bundle savedInstanceState){
        AppLogUtil.setFragmentLCLog(TAG,"onInitViews");
        initViews(view, savedInstanceState);
    }

    /**
     * 一般不用重写，参数等同于onCreateView
     */
    public void initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){}
    /**
     * 绑定view的操作
     */
    protected  void initBinds(View view){
        unbinder = ButterKnife.bind(this,view);
    }

    /**
     * butterknife 解绑view
     */
    protected void unBindsView(){
        if (this.unbinder != null) {
            this.unbinder.unbind();
            this.unbinder = null;
        }
    }

    /**
     * 业务操作
     */
//    public abstract void initData();

//    public void onInitData(){
//        AppLogUtil.setFragmentLCLog(TAG,"onInitData");
//        initData();
//    }

    /**
     * 类似Activity的OnBackgress
     * fragment进行回退
     */
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    public void gotoActivity(Class<?> clz){
        Intent intent = new Intent(getContext(),clz);
        startActivity(intent);
    }

    /**
     * 绑定EventBus
     */
    public void registBus() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        hasBus = true;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public FragmentActivity getMyActivity() {
        return getActivity();
    }

    @Override
    public Fragment getMyFragment() {
        return this;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void showProgress() {
        if(null==loadingDialog){
            loadingDialog = new LoadingDialog1();
        }
        loadingDialog.show(this);
    }

    @Override
    public void hideProgress() {
        if (loadingDialog != null && loadingDialog.getShowsDialog()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void toast(int msgId) {
        ToastUtils.showShort(msgId);
    }

    @Override
    public void toastLong(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void toastLong(int msgId) {
        ToastUtils.showShort(msgId);
    }

    /**
     * 展示数据为空时的布局
     */
    @Override
    public void showEmpty() {
        showEmpty(null);
    }

    @Override
    public void showEmpty(View emptyView) {
        if (null == contentView) {
            return;
        }
        if (contentView instanceof StateRelativeLayout) {
            if (null == emptyView) {
                ((StateRelativeLayout) contentView).showEmpty();
            } else {
                ((StateRelativeLayout) contentView).showEmpty(emptyView);
            }
        } else if (contentView instanceof StateLinearLayout) {
            if (null == emptyView) {
                ((StateLinearLayout) contentView).showEmpty();
            } else {
                ((StateLinearLayout) contentView).showEmpty(emptyView);
            }
        } else if (contentView instanceof StateFrameLayout) {
            if (null == emptyView) {
                ((StateFrameLayout) contentView).showEmpty();
            } else {
                ((StateFrameLayout) contentView).showEmpty(emptyView);
            }
        }
    }

    /**
     * 展示请求错误的布局
     */
    @Override
    public void showError() {
        showError(null);
    }

    @Override
    public void showError(View errorView) {
        if (null == contentView) {
            return;
        }
        if (contentView instanceof StateRelativeLayout) {
            if (null == errorView) {
                ((StateRelativeLayout) contentView).showError();
            } else {
                ((StateRelativeLayout) contentView).showError(errorView);
            }
        } else if (contentView instanceof StateLinearLayout) {
            if (null == errorView) {
                ((StateLinearLayout) contentView).showError();
            } else {
                ((StateLinearLayout) contentView).showError(errorView);
            }
        } else if (contentView instanceof StateFrameLayout) {
            if (null == errorView) {
                ((StateFrameLayout) contentView).showError();
            } else {
                ((StateFrameLayout) contentView).showError(errorView);
            }
        }
    }

    /**
     * 展示没有网络的布局
     */
    @Override
    public void showNoNet() {
        showNoNet(null);
    }

    @Override
    public void showNoNet(View noNetView) {
        if (null == contentView) {
            return;
        }
        if (contentView instanceof StateRelativeLayout) {
            if (null == noNetView) {
                ((StateRelativeLayout) contentView).showNoNetwork();
            } else {
                ((StateRelativeLayout) contentView).showNoNetwork(noNetView);
            }
        } else if (contentView instanceof StateLinearLayout) {
            if (null == noNetView) {
                ((StateLinearLayout) contentView).showNoNetwork();
            } else {
                ((StateLinearLayout) contentView).showNoNetwork(noNetView);
            }
        } else if (contentView instanceof StateFrameLayout) {
            if (null == noNetView) {
                ((StateFrameLayout) contentView).showNoNetwork();
            } else {
                ((StateFrameLayout) contentView).showNoNetwork(noNetView);
            }
        }
    }

    /**
     * 展示内容布局，（用于当前已经展示空或者错误布局时，此方法切换为内容布局）
     */
    @Override
    public void showContent(){
        if(null==contentView){
            return;
        }
        if(contentView instanceof StateRelativeLayout){
            ((StateRelativeLayout) contentView).showContent();
        }else if(contentView instanceof StateLinearLayout){
            ((StateLinearLayout) contentView).showContent();
        }else if(contentView instanceof StateFrameLayout){
            ((StateFrameLayout) contentView).showContent();
        }
    }

    /**
     * @return  当前布局状态（加载中、错误、空布局、没网络）
     */
    @Override
    public int getViewStatus() {
        if(null==contentView){
            return -1;
        }
        int viewStatus = -1;
        if(contentView instanceof StateRelativeLayout){
            viewStatus = ((StateRelativeLayout) contentView).getViewStatus();
        }else if(contentView instanceof StateLinearLayout){
            viewStatus = ((StateLinearLayout) contentView).getViewStatus();
        }else if(contentView instanceof StateFrameLayout){
            viewStatus = ((StateFrameLayout) contentView).getViewStatus();
        }
        return viewStatus;
    }

}
