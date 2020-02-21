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

import com.dengyun.baselibrary.utils.webview.BaseWebChromeClient;
import com.dengyun.baselibrary.utils.webview.BaseWebViewClient;
import com.dengyun.baselibrary.utils.webview.OnRefreshLoadUrlListener;
import com.dengyun.baselibrary.utils.webview.WebJsBean;
import com.dengyun.baselibrary.utils.webview.WebViewBackSetUtil;
import com.dengyun.baselibrary.utils.webview.WebViewOptions;
import com.dengyun.baselibrary.utils.webview.WebViewSetUtil;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;

/**
 * @titile
 * @desc Created by seven on 2018/5/18.
 */

public abstract class BaseCustomSomewebActivity extends BaseActivity {

    private WebView webviewSomeweb;
    private ProgressBar progressSomeweb;
    private BaseToolBar toolbarSomeweb;
    private SmartRefreshLayout refreshSomeweb;
    //private boolean isBackClose = false;//回退时是否直接关闭（可能有业务需要）,false:回退时正常判断canGoBack，true：返回直接关闭页面
    /*读取本地文件相关*/
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private WebViewOptions webViewOptions;

    public abstract BaseToolBar getBaseToolbar();
    public abstract SmartRefreshLayout getRefreshSomeweb();
    public abstract WebView getWebView();
    public abstract ProgressBar getProgressBar();

    /**
     * @return  设置交互的webJsBean
     */
    public abstract WebJsBean getWebJsBean();

    /**
     * 加载url之前设置数据
     */
    public abstract void setDataBeforLoadUrl();

    /**
     * @return  刚进入此webview加载的url
     */
    public abstract String getWebUrl();

    @Override
    protected void onInitViews(Bundle savedInstanceState) {
        toolbarSomeweb = getBaseToolbar();
        refreshSomeweb = getRefreshSomeweb();
        webviewSomeweb = getWebView();
        progressSomeweb = getProgressBar();

        if (null!=toolbarSomeweb){
            toolbarSomeweb.setLeftButtonOnClickLinster(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        webViewOptions = WebViewOptions.newBuilder(this, webviewSomeweb)
                .refreshLayout(refreshSomeweb)
                .progressBar(progressSomeweb)
                .toolbar(toolbarSomeweb)
                .webJsBean(getWebJsBean())
                .build();
        WebViewSetUtil.setWebView(webViewOptions, new OnRefreshLoadUrlListener() {
            @Override
            public void onRefreshLoadUrl(RefreshLayout refreshLayout, WebView webView, String url) {
                setLoadUrlMethod(webView,url);
            }
        });

        setWebViewClient();
        setWebChromeClient();
        setDataBeforLoadUrl();
        setLoadUrlMethod(webviewSomeweb,getWebUrl());

        super.onInitViews(savedInstanceState);
    }

    //设置WebViewClient   设置urls集合
    //设置判断跳转qq  设置pagefinish    设置PageStarted，装载urls
    public void setWebViewClient(){
        getWebView().setWebViewClient(new BaseWebViewClient(webViewOptions));
    }

    //设置WebChromeClient
    //设置标题  设置progress  设置全屏播放
    public void setWebChromeClient(){
        getWebView().setWebChromeClient(new TakeFileWebChromeClient(webViewOptions));
    }

    /**
     * 加载url的方式，如果添加请求头或者其他的加载方式，重写此方法
     * @param webView
     * @param url
     */
    public void setLoadUrlMethod(WebView webView, String url){
        webView.loadUrl(url);
    }

    /**
     * @return  获取此webview加载的url队列，注：请勿随意设置此队列
     */
    public ArrayList<String> getUrls(){
        return webViewOptions.getUrls();
    }

    /**
     * @param url   将前面的页面全部关闭并且重新加载一个新的url，例如未登录转为登录
     */
    public void clearAllAndReload(String url){
        webViewOptions.getUrls().clear();
        WebViewSetUtil.clearWebViewCache(getWebViewOptions().getActivity(),getWebViewOptions().getWebView());
        setLoadUrlMethod(getWebViewOptions().getWebView(),url);
    }

    /**
     * 重新刷新页面
     */
    public void reloadRefresh(){
        /*if (null != getWebViewOptions().getRefreshLayout()) {
            getWebViewOptions().getRefreshLayout().autoRefresh();
        }*/
        setLoadUrlMethod(getWebView(),getUrls().get(getUrls().size()-1));
    }

    public WebViewOptions getWebViewOptions(){
        return webViewOptions;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }

    @Override
    public void onBackPressed() {
        /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
        WebViewBackSetUtil.goBack(this, webviewSomeweb, webViewOptions.getUrls());
    }

    @Override
    protected void onDestroy() {
        webviewSomeweb.loadUrl("about:blank");
        super.onDestroy();
    }


    /*解决照相问题的WebChromeClient*/
    class TakeFileWebChromeClient extends BaseWebChromeClient {
        public TakeFileWebChromeClient(WebViewOptions webViewOptions) {
            super(webViewOptions);
        }

        //For Android 4.1
        @Override
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            openImageChooserActivity();
        }

        // For Android 5.0+
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }

        private void openImageChooserActivity() {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
        }
    }
}
