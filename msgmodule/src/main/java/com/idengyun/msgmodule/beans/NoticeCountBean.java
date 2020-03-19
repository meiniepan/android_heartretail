package com.idengyun.msgmodule.beans;

/**
 * 查询用户未读推送数量
 *
 * @author aLang
 */
public final class NoticeCountBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public int notifyGroup;
        public int counts;
    }
}
