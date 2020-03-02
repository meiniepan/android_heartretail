package com.dengyun.baselibrary.base.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.utils.webview.BaseWebChromeClient;
import com.dengyun.baselibrary.utils.webview.BaseWebViewClient;
import com.dengyun.baselibrary.utils.webview.OnRefreshLoadUrlListener;
import com.dengyun.baselibrary.utils.webview.WebJsBean;
import com.dengyun.baselibrary.utils.webview.WebViewBackSetUtil;
import com.dengyun.baselibrary.utils.webview.WebViewOptions;
import com.dengyun.baselibrary.utils.webview.WebViewSetUtil;
import com.dengyun.baselibrary.widgets.toolbar.BaseOneRightToolBar;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;

/**
 * @titile  webview的页面
 * @desc Created by seven on 2018/5/3.
 */
public abstract class BaseSomewebActivity extends BaseCustomSomewebActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.base_activity_someweb;
    }

    @Override
    public BaseOneRightToolBar getBaseToolbar() {
        return findViewById(R.id.toolbar_someweb);
    }

    @Override
    public SmartRefreshLayout getRefreshSomeweb() {
        return findViewById(R.id.refresh_someweb);
    }

    @Override
    public WebView getWebView() {
        return findViewById(R.id.webview_someweb);
    }

    @Override
    public ProgressBar getProgressBar() {
        return findViewById(R.id.progress_someweb);
    }
}
