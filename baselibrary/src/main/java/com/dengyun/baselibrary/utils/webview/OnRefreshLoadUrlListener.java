package com.dengyun.baselibrary.utils.webview;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.smtt.sdk.WebView;

/**
 * @Title 下拉刷新加载url的回调
 * @Desc: 可以在这里重写加载url的方法
 * @Author: zhoubo
 * @CreateDate: 2019-06-13 15:40
 */
public interface OnRefreshLoadUrlListener {
    void onRefreshLoadUrl(RefreshLayout refreshLayout, WebView webView,String url);
}
