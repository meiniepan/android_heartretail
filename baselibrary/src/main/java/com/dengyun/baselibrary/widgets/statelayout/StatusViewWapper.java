package com.dengyun.baselibrary.widgets.statelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title 状态布局view的包装属性类
 * @Author: zhoubo
 * @CreateDate: 2020-03-23 10:39
 */
public class StatusViewWapper {
    private static final String TAG = "MultipleStatusView";

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

    private int emptyViewDrawableRes;
    private String emptyViewText;

    private int errorViewDrawableRes;
    private String errorViewText;

    private String loadingViewText;

    private int mViewStatus;
    private LayoutInflater mInflater;


    //保存子view切换状态布局之前的显隐状态，使用view的tag保存
    private Map<Integer, Integer> viewVisibilityMap = new HashMap<>();
    //布局中有toolbar，做状态切换布局的时候，置于toolbar下面
    private int toolbarId;
    //给添加的子view添加tag，tag向后++，这是最后一个tab的值+1
    private int viewLastTag;

    private final ArrayList<Integer> mOtherIds = new ArrayList<>();//保存状态布局的view的id


    private IStatusView iStatusView;
    private ViewGroup statusParent;

    /**
     * @param statusParent 状态父布局
     * @param iStatusView  由相应的状态父布局实现的接口：用于添加的逻辑
     */
    public StatusViewWapper(ViewGroup statusParent, IStatusView iStatusView) {
        this.statusParent = statusParent;
        this.iStatusView = iStatusView;
    }


    public void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateRelativeLayout, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.StateRelativeLayout_emptyView, R.layout.srv_view_status_empty);
        mErrorViewResId = a.getResourceId(R.styleable.StateRelativeLayout_errorView, R.layout.srv_view_status_error);
        mLoadingViewResId = a.getResourceId(R.styleable.StateRelativeLayout_loadingView, R.layout.srv_view_status_progress);
        mNoNetworkViewResId = a.getResourceId(R.styleable.StateRelativeLayout_noNetworkView, R.layout.base_no_network_view);
        mContentViewResId = a.getResourceId(R.styleable.StateRelativeLayout_contentView, NULL_RESOURCE_ID);

        emptyViewDrawableRes = a.getResourceId(R.styleable.StateRelativeLayout_emptyViewDrawable, 0);
        emptyViewText = a.getString(R.styleable.StateRelativeLayout_emptyViewText);
        errorViewDrawableRes = a.getResourceId(R.styleable.StateRelativeLayout_errorViewDrawable, 0);
        errorViewText = a.getString(R.styleable.StateRelativeLayout_errorViewText);
        loadingViewText = a.getString(R.styleable.StateRelativeLayout_loadingViewText);

        a.recycle();
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        if (null != statusParent) {
            if (null != mEmptyView) statusParent.removeView(mEmptyView);
            if (null != mErrorView) statusParent.removeView(mErrorView);
            if (null != mLoadingView) statusParent.removeView(mLoadingView);
            if (null != mNoNetworkView) statusParent.removeView(mNoNetworkView);
        }
        if (null != mOtherIds) {
            mOtherIds.clear();
        }
        mInflater = null;
        if (null != viewVisibilityMap) {
            viewVisibilityMap.clear();
        }
    }

    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    /**
     * 获取当前状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }

    /**
     * 显示空视图
     */
    public final void showEmpty() {
        showEmpty(mEmptyViewResId);
    }

    /**
     * 显示空视图，只有第一次设置的view生效，之后再设置以第一次为准
     *
     * @param layoutId 自定义布局文件
     */
    public final void showEmpty(int layoutId) {
        showEmpty(inflateView(layoutId));
    }

    /**
     * 显示空视图
     *
     * @param view 自定义视图
     */
    public final void showEmpty(View view) {
        checkNull(view, "Empty view is null!");
        mViewStatus = STATUS_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = view;
            if (mEmptyView.getId() == -1) {
                mEmptyView.setId(R.id.base_empty_view);
            }
            mOtherIds.add(mEmptyView.getId());

            //添加空布局
            iStatusView.setLayoutParamsWithToolbar(toolbarId);
            iStatusView.addInnerStatusView(mEmptyView);
            //设置空布局的文案
            setEmptyChildText(R.id.tv_empty, emptyViewText);
            //设置空布局的图片
            setEmptyChildImg(R.id.iv_empty, emptyViewDrawableRes);
        }
        showViewById(mEmptyView.getId());
    }


    /**
     * 设置空布局的文案
     *
     * @param emptyChildViewId 空布局中的textview的id
     * @param emptyText        设置的文案
     */
    public void setEmptyChildText(int emptyChildViewId, String emptyText) {
        if (null != mEmptyView) {
            TextView tv_empty = mEmptyView.findViewById(emptyChildViewId);
            if (null != tv_empty && null != emptyText) {
                tv_empty.setText(emptyText);
            }
        }
    }

    /**
     * 设置空布局的图片
     *
     * @param emptyChildViewId 空布局中的ImageView的id
     * @param imgResId         设置的图片
     */
    public void setEmptyChildImg(int emptyChildViewId, int imgResId) {
        if (null != mEmptyView) {
            ImageView imageView = mEmptyView.findViewById(emptyChildViewId);
            if (null != imageView && imgResId != 0) {
                imageView.setImageResource(imgResId);
            }
        }
    }

    /**
     * 设置空布局的子view的监听
     *
     * @param emptyChildViewId 空布局的子view的id
     * @param onClickListener  布局中子view的点击监听
     */
    public void setOnEmptyChildClickListener(int emptyChildViewId, View.OnClickListener onClickListener) {
        if (null != mEmptyView) {
            View childView = mEmptyView.findViewById(emptyChildViewId);
            if (null != childView && null != onClickListener) {
                childView.setOnClickListener(onClickListener);
            }
        }
    }


    /**
     * 显示错误视图
     */
    public final void showError() {
        showError(mErrorViewResId);
    }

    /**
     * 显示错误视图，只有第一次设置的view生效，之后再设置以第一次为准
     *
     * @param layoutId 自定义布局文件
     */
    public final void showError(int layoutId) {
        showError(inflateView(layoutId));
    }

    /**
     * 显示错误视图
     *
     * @param view 自定义视图
     */
    public final void showError(View view) {
        checkNull(view, "Error view is null!");
        mViewStatus = STATUS_ERROR;
        if (null == mErrorView) {
            mErrorView = view;
            if (mErrorView.getId() == -1) {
                mErrorView.setId(R.id.base_error_view);
            }
            mOtherIds.add(mErrorView.getId());

            //添加错误布局
            iStatusView.setLayoutParamsWithToolbar(toolbarId);
            iStatusView.addInnerStatusView(mErrorView);
            //设置错误布局的文案
            setErrorChildText(R.id.tv_error, errorViewText);
            //设置错误布局的图片
            setErrorChildImg(R.id.iv_error, errorViewDrawableRes);
        }
        showViewById(mErrorView.getId());
    }

    /**
     * 设置错误布局的文案
     *
     * @param errorChildViewId 错误布局中的textview的id
     * @param errorText        设置的文案
     */
    public void setErrorChildText(int errorChildViewId, String errorText) {
        if (null != mErrorView) {
            TextView tv_error = mErrorView.findViewById(errorChildViewId);
            if (null != tv_error && null != errorText) {
                tv_error.setText(errorText);
            }
        }
    }

    /**
     * 设置错误布局的图片
     *
     * @param errorChildViewId 错误布局中的ImageView的id
     * @param imgResId         设置的图片
     */
    public void setErrorChildImg(int errorChildViewId, int imgResId) {
        if (null != mErrorView) {
            ImageView imageView = mErrorView.findViewById(errorChildViewId);
            if (null != imageView && imgResId != 0) {
                imageView.setImageResource(imgResId);
            }
        }
    }

    /**
     * 设置错误布局的子view的监听
     *
     * @param errorChildViewId 错误布局的子view的id
     * @param onClickListener  布局中子view的点击监听
     */
    public void setOnErrorChildClickListener(int errorChildViewId, View.OnClickListener onClickListener) {
        if (null != mErrorView) {
            View childView = mErrorView.findViewById(errorChildViewId);
            if (null != childView && null != onClickListener) {
                childView.setOnClickListener(onClickListener);
            }
        }
    }


    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        showLoading(mLoadingViewResId);
    }

    /**
     * 显示加载中视图，只有第一次设置的view生效，之后再设置以第一次为准
     *
     * @param layoutId 自定义布局文件
     */
    public final void showLoading(int layoutId) {
        showLoading(inflateView(layoutId));
    }

    /**
     * 显示加载中视图
     *
     * @param view 自定义视图
     */
    public final void showLoading(View view) {
        checkNull(view, "Loading view is null!");
        mViewStatus = STATUS_LOADING;
        if (null == mLoadingView) {
            mLoadingView = view;
            if (mLoadingView.getId() == -1) {
                mLoadingView.setId(R.id.base_loading_view);
            }
            mOtherIds.add(mLoadingView.getId());

            //添加加载中布局
            iStatusView.setLayoutParamsWithToolbar(toolbarId);
            iStatusView.addInnerStatusView(mLoadingView);
            //设置加载中的文案
            setLoadingChildText(R.id.tv_loading, loadingViewText);
        }
        showViewById(mLoadingView.getId());
    }

    /**
     * 设置加载布局的文案
     *
     * @param loadingChildViewId 加载布局中的textview的id
     * @param loadingText        设置的文案
     */
    public void setLoadingChildText(int loadingChildViewId, String loadingText) {
        if (null != mLoadingView) {
            TextView tv_loading = mLoadingView.findViewById(loadingChildViewId);
            if (null != tv_loading && null != loadingText) {
                tv_loading.setText(loadingText);
            }
        }
    }

    /**
     * 设置加载布局的子view的监听
     *
     * @param loadingChildViewId 加载布局的子view的id
     * @param onClickListener    布局中子view的点击监听
     */
    public void setOnLoadingChildClickListener(int loadingChildViewId, View.OnClickListener onClickListener) {
        if (null != mLoadingView) {
            View childView = mLoadingView.findViewById(loadingChildViewId);
            if (null != childView && null != onClickListener) {
                childView.setOnClickListener(onClickListener);
            }
        }
    }

    /**
     * 显示无网络视图
     */
    public final void showNoNetwork() {
        showNoNetwork(mNoNetworkViewResId);
    }

    /**
     * 显示无网络视图，只有第一次设置的view生效，之后再设置以第一次为准
     *
     * @param layoutId 自定义布局文件
     */
    public final void showNoNetwork(int layoutId) {
        showNoNetwork(inflateView(layoutId));
    }

    /**
     * 显示无网络视图
     *
     * @param view 自定义视图
     */
    public final void showNoNetwork(View view) {
        checkNull(view, "No network view is null!");
        mViewStatus = STATUS_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = view;
            if (mNoNetworkView.getId() == -1) {
                mNoNetworkView.setId(R.id.base_no_network_view);
            }
            mOtherIds.add(mNoNetworkView.getId());

            // 添加无网络布局
            iStatusView.setLayoutParamsWithToolbar(toolbarId);
            iStatusView.addInnerStatusView(mNoNetworkView);
        }
        showViewById(mNoNetworkView.getId());
    }

    /**
     * 设置无网络布局的子view的监听
     *
     * @param noNetChildViewId 无网络布局的子view的id
     * @param onClickListener  布局中子view的点击监听
     */
    public void setOnNoNetChildClickListener(int noNetChildViewId, View.OnClickListener onClickListener) {
        if (null != mNoNetworkView) {
            View childView = mNoNetworkView.findViewById(noNetChildViewId);
            if (null != childView && null != onClickListener) {
                childView.setOnClickListener(onClickListener);
            }
        }
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater.inflate(mContentViewResId, null);
            iStatusView.addInnerStatusView(mContentView);
        }
        showContentView();
    }

    private void showContentView() {
        final int childCount = statusParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = statusParent.getChildAt(i);
            if (toolbarId == 0 && view instanceof Toolbar) {
                toolbarId = view.getId();
                iStatusView.setLayoutParamsWithToolbar(toolbarId);
            }

            if (null == view.getTag()) {
                view.setTag(viewLastTag);
                viewVisibilityMap.put(viewLastTag, view.getVisibility());
                viewLastTag++;
            }

            int viewStatus = viewVisibilityMap.get(view.getTag());//获取此View开始状态
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : viewStatus);
        }
    }


    private void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    private void showViewById(int viewId) {
        final int childCount = statusParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = statusParent.getChildAt(i);
            //展现非内容布局的时候，将内容布局的view显示状态记录下来
            if (null == view.getTag()) {
                view.setTag(viewLastTag);
                viewLastTag++;
            }
            viewVisibilityMap.put((int) view.getTag(), view.getVisibility());
            /*展现非内容布局的时候，如果有toolbar，将toolbar也显示*/
            if (view instanceof Toolbar) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
            }
        }
    }


}
