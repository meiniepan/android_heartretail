package com.dengyun.baselibrary.utils.webview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by suntianyang on 2017/9/2.
 */

public class BaseWebChromeClient extends WebChromeClient {


    private Activity activity;
    private BaseToolBar toolBar;
    private WebView webView;
    private ProgressBar progressBar;
//    private ArrayList<String> urls;

    /**
     * 视频全屏参数
     */
    protected  final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    public   View customView;
    private  FrameLayout fullscreenContainer;
    private  IX5WebChromeClient.CustomViewCallback customViewCallback;

    public BaseWebChromeClient(WebViewOptions webViewOptions) {
        this.activity = webViewOptions.getActivity();
        this.toolBar = webViewOptions.getToolbar();
        this.webView = webViewOptions.getWebView();
        this.progressBar = webViewOptions.getProgressBar();
//        this.urls = webViewOptions.getUrls();
    }


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if(null!=progressBar){
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                if (View.GONE == progressBar.getVisibility()) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
        }

        super.onProgressChanged(view, newProgress);
    }
    @Override
    public void onReceivedTitle(WebView view, String title) {
        if(null!=toolBar){
            WebViewSetTitleUtil.setWVTitle(toolBar,title);
        }
        super.onReceivedTitle(view, title);
    }

    /*** 视频播放相关的方法 **/
    @Override
    public View getVideoLoadingProgressView() {
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return frameLayout;
    }

    @Override
    public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        showCustomView(activity,view, callback);
    }

    @Override
    public void onHideCustomView() {
        hideCustomView(activity,webView);
    }

    /**
     * 视频播放全屏
     **/
    private  void showCustomView(Activity context,View view, IX5WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        context.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) context.getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(context);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(context,false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    public   void hideCustomView(Activity context,WebView webView) {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(context,true);
        FrameLayout decor = (FrameLayout) context.getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    private  void setStatusBarVisibility(Activity context,boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        context.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
