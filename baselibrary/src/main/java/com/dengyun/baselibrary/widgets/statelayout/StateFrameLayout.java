package com.dengyun.baselibrary.widgets.statelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dengyun.baselibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @titile 取代FrameLayout布局的状态布局
 * @desc Created by seven on 2018/4/17.
 */

@SuppressWarnings("unused")
public class StateFrameLayout extends FrameLayout implements IStatusView{
    /*布局是FrameLayout，使用FrameLayout的LayoutParams*/
    private static final LayoutParams DEFAULT_LAYOUT_PARAMS =
            new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);

    private StatusViewWapper statusViewWapper;

    public StateFrameLayout(Context context) {
        this(context, null);
    }

    public StateFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        statusViewWapper = new StatusViewWapper(this,this);
        statusViewWapper.initAttr(context,attrs,defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContent();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null!=statusViewWapper){
            statusViewWapper.clear();
        }
    }

    /**
     * 获取当前状态
     */
    public int getViewStatus() {
        if (null!=statusViewWapper){
            return statusViewWapper.getViewStatus();
        }
        return 0;
    }

    /**
     * 显示空视图
     */
    public final void showEmpty() {
        if (null!=statusViewWapper){
            statusViewWapper.showEmpty();
        }
    }

    public final void showEmpty(int layoutId) {
        if (null!=statusViewWapper){
            statusViewWapper.showEmpty(layoutId);
        }
    }

    public final void showEmpty(View view) {
        if (null!=statusViewWapper){
            statusViewWapper.showEmpty(view);
        }
    }

    /**
     * 设置空布局的文案
     * @param emptyChildViewId 空布局中的textview的id
     * @param emptyText 设置的文案
     */
    public void setEmptyChildText(int emptyChildViewId,String emptyText){
        if (null!=statusViewWapper){
            statusViewWapper.setEmptyChildText(emptyChildViewId, emptyText);
        }
    }

    /**
     * 设置空布局的图片
     * @param emptyChildViewId 空布局中的ImageView的id
     * @param imgResId  设置的图片
     */
    public void setEmptyChildImg(int emptyChildViewId,int imgResId){
        if (null!=statusViewWapper){
            statusViewWapper.setEmptyChildImg(emptyChildViewId, imgResId);
        }
    }

    /**
     * 设置空布局的子view的监听
     * @param emptyChildViewId 空布局的子view的id
     * @param onClickListener 布局中子view的点击监听
     */
    public void setOnEmptyChildClickListener(int emptyChildViewId, OnClickListener onClickListener) {
        if (null!=statusViewWapper){
            statusViewWapper.setOnEmptyChildClickListener(emptyChildViewId, onClickListener);
        }
    }

    /**
     * 显示错误视图
     */
    public final void showError() {
        if (null!=statusViewWapper){
            statusViewWapper.showError();
        }
    }

    public final void showError(int layoutId) {
        if (null!=statusViewWapper){
            statusViewWapper.showError(layoutId);
        }
    }

    public final void showError(View view) {
        if (null!=statusViewWapper){
            statusViewWapper.showError(view);
        }
    }

    /**
     * 设置错误布局的文案
     * @param errorChildViewId 错误布局中的textview的id
     * @param errorText 设置的文案
     */
    public void setErrorChildText(int errorChildViewId,String errorText){
        if (null!=statusViewWapper){
            statusViewWapper.setErrorChildText(errorChildViewId, errorText);
        }
    }

    /**
     * 设置错误布局的图片
     * @param errorChildViewId 错误布局中的ImageView的id
     * @param imgResId  设置的图片
     */
    public void setErrorChildImg(int errorChildViewId,int imgResId){
        if (null!=statusViewWapper){
            statusViewWapper.setErrorChildImg(errorChildViewId, imgResId);
        }
    }

    /**
     * 设置错误布局的子view的监听
     * @param errorChildViewId 错误布局的子view的id
     * @param onClickListener 布局中子view的点击监听
     */
    public void setOnErrorChildClickListener(int errorChildViewId, OnClickListener onClickListener) {
        if (null!=statusViewWapper){
            statusViewWapper.setOnErrorChildClickListener(errorChildViewId, onClickListener);
        }
    }

    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        if (null!=statusViewWapper){
            statusViewWapper.showLoading();
        }
    }

    public final void showLoading(int layoutId) {
        if (null!=statusViewWapper){
            statusViewWapper.showLoading(layoutId);
        }
    }

    public final void showLoading(View view) {
        if (null!=statusViewWapper){
            statusViewWapper.showLoading(view);
        }
    }

    /**
     * 设置加载布局的文案
     * @param loadingChildViewId 加载布局中的textview的id
     * @param loadingText 设置的文案
     */
    public void setLoadingChildText(int loadingChildViewId,String loadingText){
        if (null!=statusViewWapper){
            statusViewWapper.setLoadingChildText(loadingChildViewId, loadingText);
        }
    }

    /**
     * 设置加载布局的子view的监听
     * @param loadingChildViewId 加载布局的子view的id
     * @param onClickListener 布局中子view的点击监听
     */
    public void setOnLoadingChildClickListener(int loadingChildViewId, OnClickListener onClickListener) {
        if (null!=statusViewWapper){
            statusViewWapper.setOnLoadingChildClickListener(loadingChildViewId, onClickListener);
        }
    }

    /**
     * 显示无网络视图
     */
    public final void showNoNetwork() {
        if (null!=statusViewWapper){
            statusViewWapper.showNoNetwork();
        }
    }

    public final void showNoNetwork(int layoutId) {
        if (null!=statusViewWapper){
            statusViewWapper.showNoNetwork(layoutId);
        }
    }

    public final void showNoNetwork(View view) {
        if (null!=statusViewWapper){
            statusViewWapper.showNoNetwork(view);
        }
    }

    public void setOnNoNetChildClickListener(int noNetChildViewId, OnClickListener onClickListener) {
        if (null!=statusViewWapper){
            statusViewWapper.setOnNoNetChildClickListener(noNetChildViewId, onClickListener);
        }
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        if (null!=statusViewWapper){
            statusViewWapper.showContent();
        }
    }


    @Override
    public void setLayoutParamsWithToolbar(int toolbarId) {
    }

    @Override
    public void addInnerStatusView(View innerStatusView) {
        addView(innerStatusView, 0, DEFAULT_LAYOUT_PARAMS);
    }
}
