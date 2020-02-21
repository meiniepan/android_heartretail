package com.dengyun.baselibrary.utils.webview;

import android.support.v4.app.FragmentActivity;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;

/**
 * @titile  设置webview的返回设置
 * @desc Created by seven on 2018/5/3.
 */

public class WebViewBackSetUtil {
    public static void goBack(FragmentActivity activity,
                              WebView webView,
                              ArrayList<String> urls){
        /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
        //super.onBackPressed();
        WebChromeClient webChromeClient = webView.getWebChromeClient();
        if(webChromeClient instanceof BaseWebChromeClient && ((BaseWebChromeClient) webChromeClient).customView!= null){
            ((BaseWebChromeClient) webChromeClient).hideCustomView(activity, webView);
        } else if (webView.canGoBack() ) {
            if(urls.size()>1){
                //urls队列中有后退web，返回时goback
                webView.goBack();
                urls.remove(urls.size() - 1);
            }else {
                //urls队列中没有有后退web，返回时直接close
                urls.clear();
                urls = null;
                activity.finish();
            }
        } else {
            urls.clear();
            urls = null;
            activity.finish();
        }
    }
}
