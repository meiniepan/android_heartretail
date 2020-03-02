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
public class StateFrameLayout extends FrameLayout {
    private static final String TAG = "MultipleStatusView";

    /*布局是FrameLayout，使用FrameLayout的LayoutParams*/
    private static final LayoutParams DEFAULT_LAYOUT_PARAMS =
            new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);

    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;

    private static final int NULL_RESOURCE_ID = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mContentView;
    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mContentViewResId;

    private int mViewStatus;
    private LayoutInflater mInflater;
    //    private OnClickListener mOnRetryClickListener;
    private OnClickListener mOnErrorRetryClickListener;
    private OnClickListener mOnEmptyRetryClickListener;
    private OnClickListener mOnNoNetRetryClickListener;

    //保存子view切换状态布局之前的显隐状态，使用view的tag保存
    private Map<Integer, Integer> viewVisibilityMap = new HashMap<>();
    //给添加的子view添加tag，tag向后++，这是最后一个tab的值+1
    private int viewLastTag;

    private final ArrayList<Integer> mOtherIds = new ArrayList<>();//保存状态布局的view的id

    public StateFrameLayout(Context context) {
        this(context, null);
    }

    public StateFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateRelativeLayout, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.StateRelativeLayout_emptyView, R.layout.base_empty_view);
        mErrorViewResId = a.getResourceId(R.styleable.StateRelativeLayout_errorView, R.layout.base_error_view);
        mLoadingViewResId = a.getResourceId(R.styleable.StateRelativeLayout_loadingView, R.layout.base_loading_view);
        mNoNetworkViewResId = a.getResourceId(R.styleable.StateRelativeLayout_noNetworkView, R.layout.base_no_network_view);
        mContentViewResId = a.getResourceId(R.styleable.StateRelativeLayout_contentView, NULL_RESOURCE_ID);
        a.recycle();
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContent();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView);
        if (null != mOtherIds) {
            mOtherIds.clear();
        }
        if (null != mOnErrorRetryClickListener) {
            mOnErrorRetryClickListener = null;
        }
        if (null != mOnEmptyRetryClickListener) {
            mOnEmptyRetryClickListener = null;
        }
        if (null != mOnNoNetRetryClickListener) {
            mOnNoNetRetryClickListener = null;
        }
        mInflater = null;
        if (null != viewVisibilityMap) {
            viewVisibilityMap.clear();
        }
    }

    /**
     * 获取当前状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }


    /**
     * 设置错误重试点击事件
     *
     * @param onErrorRetryClickListener 重试点击事件
     */
    public void setOnErrorRetryClickListener(OnClickListener onErrorRetryClickListener) {
        this.mOnErrorRetryClickListener = onErrorRetryClickListener;
    }

    /**
     * 设置空布局重试点击事件
     *
     * @param onEmptyRetryClickListener 重试点击事件
     */
    public void setOnEmptyRetryClickListener(OnClickListener onEmptyRetryClickListener) {
        this.mOnEmptyRetryClickListener = onEmptyRetryClickListener;
    }

    /**
     * 设置没网络重试点击事件
     *
     * @param onNoNetRetryClickListener 重试点击事件
     */
    public void setOnNoNetRetryClickListener(OnClickListener onNoNetRetryClickListener) {
        this.mOnNoNetRetryClickListener = onNoNetRetryClickListener;
    }

    /**
     * 显示空视图
     */
    public final void showEmpty() {
        showEmpty(mEmptyViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示空视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showEmpty(int layoutId, LayoutParams layoutParams) {
        showEmpty(inflateView(layoutId), layoutParams);
    }

    public final void showEmpty(int layoutId) {
        showEmpty(inflateView(layoutId), DEFAULT_LAYOUT_PARAMS);
    }

    public final void showEmpty(View view) {
        showEmpty(view, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示空视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showEmpty(View view, LayoutParams layoutParams) {
        checkNull(view, "Empty view is null!");
        mViewStatus = STATUS_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = view;
            if (mEmptyView.getId() == -1) {
                mEmptyView.setId(R.id.base_empty_view);
            }
            View emptyRetryView = mEmptyView.findViewById(R.id.empty_retry_view);
            if (null != mOnEmptyRetryClickListener && null != emptyRetryView) {
                emptyRetryView.setOnClickListener(mOnEmptyRetryClickListener);
            }
            mOtherIds.add(mEmptyView.getId());
            addView(mEmptyView, 0, layoutParams);
        }
        showViewById(mEmptyView.getId());
    }

    /**
     * 显示错误视图
     */
    public final void showError() {
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示错误视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showError(int layoutId, LayoutParams layoutParams) {
        showError(inflateView(layoutId), layoutParams);
    }

    public final void showError(int layoutId) {
        showError(inflateView(layoutId), DEFAULT_LAYOUT_PARAMS);
    }

    public final void showError(View view) {
        showError(view, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示错误视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showError(View view, LayoutParams layoutParams) {
        checkNull(view, "Error view is null!");
        mViewStatus = STATUS_ERROR;
        if (null == mErrorView) {
            mErrorView = view;
            if (mErrorView.getId() == -1) {
                mErrorView.setId(R.id.base_error_view);
            }
            View errorRetryView = mErrorView.findViewById(R.id.error_retry_view);
            if (null != mOnErrorRetryClickListener && null != errorRetryView) {
                errorRetryView.setOnClickListener(mOnErrorRetryClickListener);
            }
            mOtherIds.add(mErrorView.getId());
            addView(mErrorView, 0, layoutParams);
        }
        showViewById(mErrorView.getId());
    }

    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示加载中视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showLoading(int layoutId, LayoutParams layoutParams) {
        showLoading(inflateView(layoutId), layoutParams);
    }

    public final void showLoading(int layoutId) {
        showLoading(inflateView(layoutId), DEFAULT_LAYOUT_PARAMS);
    }

    public final void showLoading(View view) {
        showLoading(view, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示加载中视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showLoading(View view, LayoutParams layoutParams) {
        checkNull(view, "Loading view is null!");
        mViewStatus = STATUS_LOADING;
        if (null == mLoadingView) {
            mLoadingView = view;
            if (mLoadingView.getId() == -1) {
                mLoadingView.setId(R.id.base_loading_view);
            }
            mOtherIds.add(mLoadingView.getId());
            addView(mLoadingView, 0, layoutParams);
        }
        showViewById(mLoadingView.getId());
    }

    /**
     * 显示无网络视图
     */
    public final void showNoNetwork() {
        showNoNetwork(mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示无网络视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(int layoutId, LayoutParams layoutParams) {
        showNoNetwork(inflateView(layoutId), layoutParams);
    }

    public final void showNoNetwork(int layoutId) {
        showNoNetwork(inflateView(layoutId), DEFAULT_LAYOUT_PARAMS);
    }

    public final void showNoNetwork(View view) {
        showNoNetwork(view, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示无网络视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(View view, LayoutParams layoutParams) {
        checkNull(view, "No network view is null!");
        mViewStatus = STATUS_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = view;
            if (mNoNetworkView.getId() == -1) {
                mNoNetworkView.setId(R.id.base_no_network_view);
            }
            View noNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_retry_view);
            if (null != mOnNoNetRetryClickListener && null != noNetworkRetryView) {
                noNetworkRetryView.setOnClickListener(mOnNoNetRetryClickListener);
            }
            mOtherIds.add(mNoNetworkView.getId());
            addView(mNoNetworkView, 0, layoutParams);
        }
        showViewById(mNoNetworkView.getId());
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater.inflate(mContentViewResId, null);
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showContentView();
    }

    private void showContentView() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (null == view.getTag()) {
                view.setTag(viewLastTag);
                viewVisibilityMap.put(viewLastTag, view.getVisibility());
                viewLastTag++;
            }

            int viewStatus = viewVisibilityMap.get(view.getTag());//获取此View开始状态
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : viewStatus);
        }
    }

    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    private void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            //展现非内容布局的时候，将内容布局的view显示状态记录下来
            if (null == view.getTag()) {
                view.setTag(viewLastTag);
                viewLastTag++;
            }
            viewVisibilityMap.put((int) view.getTag(), view.getVisibility());
            /*展现非内容布局的时候，如果有toolbar，将toolbar也显示*/
            if (view instanceof Toolbar) {
                view.setVisibility(VISIBLE);
            } else {
                view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    private void clear(View... views) {
        if (null == views) {
            return;
        }
        try {
            for (View view : views) {
                if (null != view) {
                    removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
