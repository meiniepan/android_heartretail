package com.dengyun.baselibrary.utils.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.dengyun.baselibrary.utils.Utils;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @titile
 * @desc Created by seven on 2018/5/3.
 */

public class BaseWebViewClient extends WebViewClient {
    private WebViewOptions webViewOptions;
    public BaseWebViewClient(WebViewOptions webViewOptions){
        this.webViewOptions = webViewOptions;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if(null!=webViewOptions.getUrls()&&!webViewOptions.getUrls().contains(url)){
            webViewOptions.getUrls().add(url);
        }
        if (url.trim().startsWith("mqqwpa")) {
            view.stopLoading();
            try {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                webViewOptions.getActivity().startActivity(in);
                webViewOptions.getActivity().finish();
            } catch (Exception dd) {
                Toast.makeText(Utils.getApp(), "您未安装QQ客户端", Toast.LENGTH_SHORT).show();
                webViewOptions.getActivity().finish();
                dd.printStackTrace();
            }
        }else if(url.trim().startsWith("tel")){//特殊情况tel，调用系统的拨号软件拨号【<a href="tel:1111111111">1111111111</a>】
            view.stopLoading();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            webViewOptions.getActivity().startActivity(i);
        }else{
            super.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        return super.shouldOverrideUrlLoading(webView, s);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
        handler.proceed();  // 接受所有网站的证书
    }
}
