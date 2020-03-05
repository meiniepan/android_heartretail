package com.idengyun.msgmodule.beans;

import java.io.Serializable;

/**
 * Created by seven on 2016/5/18.
 */
public class GeTuiBean implements Serializable {
    // 0:未读；1：已读
    private int read_flag;

    // 推送分组(1：促销优惠 2：内容精选 3：服务通知 4：账户通知)
    private int notify_grouping;

    // 推送类型(-1:无效果 0:文本 1:URL链接/文章 3:订单详情 6:商品详情 7:拼团列表 8:火爆抢购列表 9:分销列表
    // 10:红包列表 11:视频详情 12:余额收入 13:余额支出)
    private String notify_type;

    // 模板类型(0:文本(标题+内容)1:图文(标题+图片)2:物流通知3:商品4:普通通知(文本+图片+副标题)5:文章6:视频7:评论)
    private String notify_template;

    // 推送标题(用于锁屏通知、气泡通知)
    private String content_title;

    // 推送副标题(用于锁屏通知、气泡通知)
    private String title;

    // 推送时间(例：2019-11-12 09:38:52)
    private String push_time;

    // 推送id
    private String notify_id;

    // 根据notify_type不同点击跳转界面不同，用于跳转使用(详见下方表格)[下划线+json]
    private String notify_type_detail;

    // 根据notify_template不同推送内容不同，用于自定义样式(详见下方表格)[下划线+json]
    private String notify_template_detail;

    // 消息过期时间（例：2019-12-12 09:38:52），1、若为空字符串：永久有效；2、若当前服务器时间>过期时间 则展示过期水印
    private String end_date;

    public int getRead_flag() {
        return read_flag;
    }

    public void setRead_flag(int read_flag) {
        this.read_flag = read_flag;
    }

    public int getNotify_grouping() {
        return notify_grouping;
    }

    public void setNotify_grouping(int notify_grouping) {
        this.notify_grouping = notify_grouping;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getNotify_template() {
        return notify_template;
    }

    public void setNotify_template(String notify_template) {
        this.notify_template = notify_template;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }


    public String getNotify_type_detail() {
        return notify_type_detail;
    }

    public void setNotify_type_detail(String notify_type_detail) {
        this.notify_type_detail = notify_type_detail;
    }

    public String getNotify_template_detail() {
        return notify_template_detail;
    }

    public void setNotify_template_detail(String notify_template_detail) {
        this.notify_template_detail = notify_template_detail;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

}
