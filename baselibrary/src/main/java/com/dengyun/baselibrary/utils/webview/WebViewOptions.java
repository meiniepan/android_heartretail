package com.dengyun.baselibrary.utils.webview;

import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;

import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;

/**
 * @titile
 * @desc Created by seven on 2018/5/2.
 */

public class WebViewOptions {
    private final WebView webView;
    private final WebJsBean webJsBean;
    private final FragmentActivity activity;
    private final SmartRefreshLayout refreshLayout;
    private final ProgressBar progressBar;
    private final BaseToolBar toolbar;
    private ArrayList<String> urls = new ArrayList<>();

    private WebViewOptions(Builder builder) {
        webView = builder.webView;
        webJsBean = builder.webJsBean;
        activity = builder.activity;
        refreshLayout = builder.refreshLayout;
        progressBar = builder.progressBar;
        toolbar = builder.toolbar;
//        urls = builder.urls;
    }

    public static Builder newBuilder(FragmentActivity activity, WebView webView) {
        return new Builder(activity,webView);
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public WebView getWebView() {
        return webView;
    }

    public WebJsBean getWebJsBean() {
        return webJsBean;
    }

    public SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public BaseToolBar getToolbar() {
        return toolbar;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public static final class Builder {
        private WebView webView;
        private WebJsBean webJsBean;
        private FragmentActivity activity;
        private SmartRefreshLayout refreshLayout;
        private ProgressBar progressBar;
        private BaseToolBar toolbar;
//        private ArrayList<String> urls;

        private Builder(FragmentActivity activity, WebView webView) {
            this.activity = activity;
            this.webView = webView;
        }

        public Builder webView(WebView val) {
            webView = val;
            return this;
        }

        public Builder webJsBean(WebJsBean val) {
            webJsBean = val;
            return this;
        }

        public Builder activity(FragmentActivity val) {
            activity = val;
            return this;
        }


        public Builder refreshLayout(SmartRefreshLayout val) {
            refreshLayout = val;
            return this;
        }

        public Builder progressBar(ProgressBar val) {
            progressBar = val;
            return this;
        }

        public Builder toolbar(BaseToolBar val) {
            toolbar = val;
            return this;
        }

        /*public Builder urls(ArrayList<String> val) {
            urls = val;
            return this;
        }*/

        public WebViewOptions build() {
            return new WebViewOptions(this);
        }
    }
}
