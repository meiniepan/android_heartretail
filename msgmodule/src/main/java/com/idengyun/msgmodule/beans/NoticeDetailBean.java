package com.idengyun.msgmodule.beans;

/**
 * 推送消息详情查询
 *
 * @author aLang
 */
public final class NoticeDetailBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public int status;
        public int contentType;
        public String content;
        public String pushTime;
        public String title;
    }
}
