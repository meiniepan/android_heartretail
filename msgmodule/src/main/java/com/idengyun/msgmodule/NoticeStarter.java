package com.idengyun.msgmodule;

import android.content.Context;
import android.content.Intent;

/**
 * recordEvenType: 1、URL链接2、订单详情3、余额4、红包5、升级提醒6、富文本
 * 消息列表点击跳转事件
 */
public final class NoticeStarter {

    public static final String NAME_CONTENT_TYPE = "contentType";
    public static final String NAME_CONTENT = "content";

    /* URL链接 */
    public static void start1(Context context, int contentType, String content) {
        start(context, NoticeDetailActivity.class, contentType, content);
    }

    /* 订单详情 */
    public static void start2(Context context, int contentType, String content) {
        // TODO: 2020/3/31  订单详情
        start(context, null, contentType, content);
    }

    /* 余额 */
    public static void start3(Context context, int contentType, String content) {
        // TODO: 2020/3/31  余额
        start(context, null, contentType, content);
    }

    /* 红包 */
    public static void start4(Context context, int contentType, String content) {
        // TODO: 2020/3/31  红包
        start(context, null, contentType, content);
    }

    /* 升级提醒 */
    public static void start5(Context context, int contentType, String content) {
        // TODO: 2020/3/31  升级提醒
        start(context, null, contentType, content);
    }

    /* 富文本 */
    public static void start6(Context context, int contentType, String content) {
        start(context, NoticeDetailActivity.class, contentType, content);
    }

    private static void start(Context context, Class<?> cls, int contentType, String content) {
        Intent starter = new Intent(context, cls);
        starter.putExtra(NAME_CONTENT_TYPE, contentType);
        starter.putExtra(NAME_CONTENT, content);
        context.startActivity(starter);
    }

    private NoticeStarter() {
    }
}
