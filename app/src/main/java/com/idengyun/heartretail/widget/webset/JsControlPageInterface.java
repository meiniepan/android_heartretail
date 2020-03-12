package com.idengyun.heartretail.widget.webset;

/**
 * @Title Js控制页面的交互方法接口
 * @Author: zhoubo
 * @CreateDate: 2019-11-20 18:36
 */
public interface JsControlPageInterface {

    /**
     * 主动调取刷新页面（重新load url）
     */
    void viewDetailReload();

    /**
     * 禁止页面下拉刷新
     * 此方法在页面加载完成的时候调用
     */
    void viewDetailCanclePull();

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
    void viewShareConfig(String shareChannel,
                         String isBlock,
                         String title,
                         String describe,
                         String url,
                         String imgUrl);

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
    void viewShareButtonConfig(String shareChannel,
                               String isBlock,
                               String title,
                               String describe,
                               String url,
                               String imgUrl);

}
