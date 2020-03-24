package com.dengyun.baselibrary.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.base.dialog.loading.LoadingDialog1;
import com.dengyun.baselibrary.base.view.BaseActivityView;
import com.dengyun.baselibrary.config.AppConfig;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.rx.RxManager;
import com.dengyun.baselibrary.utils.AppLogUtil;
import com.dengyun.baselibrary.utils.ObjectUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.bar.StatusBarUtil;
import com.dengyun.baselibrary.widgets.statelayout.StateFrameLayout;
import com.dengyun.baselibrary.widgets.statelayout.StateLinearLayout;
import com.dengyun.baselibrary.widgets.statelayout.StateRelativeLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @titile
 * @desc Created by seven on 2018/2/24.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseActivityView {
    protected FragmentActivity activity;
    private boolean hasBus = false;
    private Fragment currentV4Fragment;
    private Unbinder unbinder;
    private LoadingDialog1 loadingDialog;
    private String TAG = "BaseActivity";//本activity的tag，在onCreate方法中重置，为当前activity的包名+类名路径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = ObjectUtils.getClassPath(this);
        activity = this;
        AppLogUtil.setActivityLCLog(TAG,"onCreate");
        onBeforSetContentView(savedInstanceState);
        setStatusBar();
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }

        initBinds();
        onInitViews(savedInstanceState);
//        onInitData();
    }

    @Override
    protected void onStart() {
        AppLogUtil.setActivityLCLog(TAG,"onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLogUtil.setActivityLCLog(TAG,"onResume");
        if(AppConfig.isUmengPush){
            MobclickAgent.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppLogUtil.setActivityLCLog(TAG,"onPause");
        if(AppConfig.isUmengPush){
            MobclickAgent.onPause(this);
        }
    }

    private boolean isDestroyed = false;
    private void destroy()  {
        if (isDestroyed) {
            return;
        }
        isDestroyed = true;
        //回收资源
        onRelease();
    }

    /**
     * 释放资源
     */
    protected void onRelease(){
        AppLogUtil.setActivityLCLog(TAG,"onRelease");
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
    }


    @Override
    protected void onStop() {
        AppLogUtil.setActivityLCLog(TAG,"onStop");
        super.onStop();
        //在此生命周期中回收资源
        if (isFinishing()) {
            destroy();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLogUtil.setActivityLCLog(TAG,"onDestroy");
        //需要在onDestroy方法中进一步检测是否回收资源等。
        destroy();
    }

    @Override
    public String getMyTag() {
        return TAG==null? ObjectUtils.getClassPath(this):TAG;
    }

    private View getContentView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }


    /**
     * @return 设置当前activity的layoutid
     */
    protected abstract int getLayoutId();

    /**
     * 绑定view的操作
     */
    protected void initBinds() {
        unbinder = ButterKnife.bind(activity);
    }

    /**
     * butterknife 解绑view
     */
    protected void unBindsView() {
        if (this.unbinder != null) {
            this.unbinder.unbind();
            this.unbinder = null;
        }
    }

    /**
     * @param savedInstanceState 初始化view的操作，在此方法实现
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * @param savedInstanceState
     */
    protected void onInitViews(Bundle savedInstanceState){
        AppLogUtil.setActivityLCLog(TAG,"onInitViews");
        initViews(savedInstanceState);
    }

    /**
     * 初始化数据的方法，在此实现
     */
//    protected  void initData(){};

    /*protected void onInitData(){
        AppLogUtil.setActivityLCLog(TAG,"onInitData");
        initData();
    }*/

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    /**
     * 设置状态栏，改变状态栏重写此方法
     */
    public void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.black));
    }

    /**
     * 在setContentView之前设置东西
     */
    public void onBeforSetContentView(@Nullable Bundle savedInstanceState) {
    }


    /**
     * 跳转activity（不关闭本页面，无参数）
     */
    public void gotoActivity(Class<?> clz) {
        Intent intent = new Intent(activity, clz);
        startActivity(intent);
    }

    /**
     * Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentV4Fragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            //targetFragment.onChange();
        }
        if (currentV4Fragment != null
                && currentV4Fragment.isVisible()) {
            transaction.hide(currentV4Fragment);
        }
        currentV4Fragment = targetFragment;
        transaction.commit();
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

    /**
     * 展示进度圈
     */
    @Override
    public void showProgress() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog1();
        }
        loadingDialog.show(this);
    }

    /**
     * 隐藏进度圈
     */
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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public FragmentActivity getMyActivity() {
        return activity;
    }

    private View contentView;

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
            contentView = getContentView();
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
            contentView = getContentView();
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
            contentView = getContentView();
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
    public void showContent() {
        if (null == contentView) {
            contentView = getContentView();
        }
        if (contentView instanceof StateRelativeLayout) {
            ((StateRelativeLayout) contentView).showContent();
        } else if (contentView instanceof StateLinearLayout) {
            ((StateLinearLayout) contentView).showContent();
        } else if (contentView instanceof StateFrameLayout) {
            ((StateFrameLayout) contentView).showContent();
        }
    }

    /**
     * @return 当前布局状态（加载中、错误、空布局、没网络）
     */
    @Override
    public int getViewStatus() {
        if (null == contentView) {
            contentView = getContentView();
        }
        int viewStatus = -1;
        if (contentView instanceof StateRelativeLayout) {
            viewStatus = ((StateRelativeLayout) contentView).getViewStatus();
        } else if (contentView instanceof StateLinearLayout) {
            viewStatus = ((StateLinearLayout) contentView).getViewStatus();
        } else if (contentView instanceof StateFrameLayout) {
            viewStatus = ((StateFrameLayout) contentView).getViewStatus();
        }
        return viewStatus;
    }
}
