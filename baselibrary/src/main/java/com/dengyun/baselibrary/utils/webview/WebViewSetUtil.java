package com.dengyun.baselibrary.utils.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;

import com.dengyun.baselibrary.config.AppConfig;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by seven on 2016/10/12.
 */

public class WebViewSetUtil {

    public static void setWebView(final WebViewOptions webViewOptions) {
        setWebView(webViewOptions,null);
    }

    public static void setWebView(final WebViewOptions webViewOptions,OnRefreshLoadUrlListener onRefreshLoadUrlListener) {
        setWebViewFormat(webViewOptions);

        //设置刷新框架    刷新的时候判断urls
        if (null != webViewOptions.getRefreshLayout()) {
            webViewOptions.getRefreshLayout().setEnableLoadMore(false);
            webViewOptions.getRefreshLayout().setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    webViewOptions.getRefreshLayout().finishRefresh();
                    clearWebViewCache(webViewOptions.getActivity(),webViewOptions.getWebView());
                    if (null != webViewOptions.getUrls() && webViewOptions.getUrls().size() > 0) {
                        if(null!=onRefreshLoadUrlListener){
                            onRefreshLoadUrlListener.onRefreshLoadUrl(refreshLayout,
                                    webViewOptions.getWebView(),
                                    webViewOptions.getUrls().get(webViewOptions.getUrls().size() - 1));
                        }else {
                            webViewOptions.getWebView().loadUrl(webViewOptions.getUrls().get(webViewOptions.getUrls().size() - 1));
                        }
                    }

                }
            });
        }

    }

    private static void setWebViewFormat(WebViewOptions webViewOptions) {
        setSAWebToApp(webViewOptions.getWebView());
        setWebViewAttr( webViewOptions.getActivity(),webViewOptions.getWebView(),webViewOptions.getWebJsBean());
        setWebViewTouchHorizontal(webViewOptions.getWebView());
    }

    private static void setWebViewAttr(Activity context, WebView webView, WebJsBean webJsBean) {
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不用缓存加载
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);// 允许访问文件
        webView.getSettings().setSupportZoom(true); // 支持缩放
        webView.getSettings().setAppCacheEnabled(false);
//        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if(null!=webJsBean){
            webView.addJavascriptInterface(webJsBean, "native");
        }
    }

    private static void setSAWebToApp(WebView webView) {
        if (AppConfig.isSAPush) {
            SensorsDataAPI.sharedInstance().showUpX5WebView(webView);
        }
    }

    public static void clearWebViewCache(Activity activity, WebView webView) {
        CookieSyncManager.createInstance(activity);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

//       webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
        webView.clearHistory();
    }

    private static void setWebViewTouchHorizontal(WebView webView) {
        webView.setOnTouchListener(new View.OnTouchListener() {
            private float startx;
            private float starty;
            private float offsetx;
            private float offsety;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        startx = motionEvent.getX();
                        starty = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetx = Math.abs(motionEvent.getX() - startx);
                        offsety = Math.abs(motionEvent.getY() - starty);
                        if (offsetx > offsety) {
                            view.getParent().requestDisallowInterceptTouchEvent(true);//子控件滑动
                        } else {
                            view.getParent().requestDisallowInterceptTouchEvent(false);//父控件滑动
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
