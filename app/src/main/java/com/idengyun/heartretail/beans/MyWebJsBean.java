package com.idengyun.heartretail.beans;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.activitys.SomeWebActivity;
import com.idengyun.heartretail.widget.webset.JsControlPageInterface;
import com.idengyun.usermodule.LoginActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by seven on 2016/8/9.
 */

public class MyWebJsBean extends com.dengyun.baselibrary.utils.webview.WebJsBean {
    private JsControlPageInterface mJsControl;

    public MyWebJsBean(Activity activity) {
        super(activity);
    }

    public MyWebJsBean(Activity activity, JsControlPageInterface jsControlPageInterface) {
        super(activity);
        this.mJsControl = jsControlPageInterface;
    }

    /**
     * 跳转到一个新的wap页面
     *
     * @param url 跳转到的页面的url
     */
    @JavascriptInterface
    public void viewDetailLink(String url) {
        Logger.e("----viewDetailLink==#js abcd-" + url);
        SomeWebActivity.start(mActivity, url, "");
    }

    /**
     * 跳转到登录页面
     */
    @JavascriptInterface
    public void viewDetailLogin() {
        Logger.e("----viewDetailLogin==#js abcd-aaa");
        LoginActivity.start((BaseActivity) mActivity);
    }

    /**
     * 跳转到店铺详情页面
     *
     * @param storeId 店铺id
     */
    @JavascriptInterface
    public void viewDetailToStore(String storeId) {
        Logger.e("----==viewDetailToStore");

    }

    /**
     * 关闭当前页面
     */
    @JavascriptInterface
    public void viewDetailClose() {
        Logger.e("----==viewDetailClose");
        mActivity.finish();
    }

    /**
     * 主动调取刷新页面（重新load url）
     */
    @JavascriptInterface
    public void viewDetailReload() {
        Logger.e("----==viewDetailReload");
        if (null!=mJsControl) mJsControl.viewDetailReload();
    }

    /**
     * 禁止页面下拉刷新
     * 此方法在页面加载完成的时候调用
     */
    @JavascriptInterface
    public void viewDetailCanclePull() {
        Logger.e("----==viewDetailCanclePull");
        if (null!=mJsControl) mJsControl.viewDetailCanclePull();
    }

    /**
     * 通用wap页面刚打开时传输分享参数
     * 调用时机：wap页面加载完成时，如果右上角需要显示分享按钮，调用此方法将分享参数给客户端
     *
     * @param shareChannel 分享渠道  （必传）：0_1_2_4_13 分别对应新浪、微信、朋友圈、QQ、短信，中间用下划线连接，全渠道可以传“”
     * @param isBlock      是否要回调（必传） 0：不要回调；1：点击面板成功回调；2：友盟成功回调。
     *                     如果要回调的话，在分享完成时，客户端会调取wap端方法 viewShareButtonCallback(String shareChannel,String isSuccess)
     *                     第一个参数为分享渠道：0：新浪，1：微信，2：朋友圈，4：QQ，13：短信，第二个参数为String的true
     * @param title        分享标题  （必传）
     * @param describe     分享描述  （必传）
     * @param url          分享url   （必传）
     * @param imgUrl       分享出去的图片链接（必传，没有就传""）
     */
    @JavascriptInterface
    public void viewShareConfig(String shareChannel,
                                String isBlock,
                                String title,
                                String describe,
                                String url,
                                String imgUrl) {
        Logger.e("----viewShareConfig==" + "#shareChannel-" + shareChannel);
        Logger.e("----viewShareConfig==" + "#isBlock-" + isBlock);
        Logger.e("----viewShareConfig==" + "#title-" + title);
        Logger.e("----viewShareConfig==" + "#describe-" + describe);
        Logger.e("----viewShareConfig==" + "#url-" + url);
        Logger.e("----viewShareConfig==" + "#imgUrl-" + imgUrl);
        if (null!=mJsControl) mJsControl.viewShareConfig(shareChannel, isBlock, title, describe, url, imgUrl);
    }

    /**
     * wap实时调用分享功能
     *
     * @param shareChannel 分享渠道  （必传）：0_1_2_4_13 分别对应新浪、微信、朋友圈、QQ、短信，中间用下划线连接，全渠道可以传“”
     * @param isBlock      是否要回调（必传） 0：不要回调；1：点击面板成功回调；2：友盟成功回调。
     *                     如果要回调的话，在分享完成时，客户端会调取wap端方法 viewShareButtonCallback(String shareChannel,String isSuccess)
     *                     第一个参数为分享渠道：0：新浪，1：微信，2：朋友圈，4：QQ，13：短信，第二个参数为String的true
     * @param title        分享标题  （必传）
     * @param describe     分享描述  （必传）
     * @param url          分享url   （必传）
     * @param imgUrl       分享出去的图片链接（必传，没有就传""）
     */
    @JavascriptInterface
    public void viewShareButtonConfig(String shareChannel,
                                      String isBlock,
                                      String title,
                                      String describe,
                                      String url,
                                      String imgUrl) {
        //describe = "<p>简介：</p><p>1.nickname:wildma！</p><p>2.职业：android攻城狮</p>";
        //describe = "<p>简介：</p><p>1.nickname:wildma！</p><p>2.职业：<br />android攻城狮</p>";
        Logger.e("----viewShareButtonConfig==" + "#shareChannel-" + shareChannel);
        Logger.e("----viewShareButtonConfig==" + "#isBlock-" + isBlock);
        Logger.e("----viewShareButtonConfig==" + "#title-" + title);
        Logger.e("----viewShareButtonConfig==" + "#describe-" + describe);
        Logger.e("----viewShareButtonConfig==" + "#url-" + url);
        Logger.e("----viewShareButtonConfig==" + "#imgUrl-" + imgUrl);
        if(null!=mJsControl) mJsControl.viewShareButtonConfig(shareChannel, isBlock, title, describe, url, imgUrl);
    }
}


