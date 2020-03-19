package com.idengyun.msgmodule.beans;

import java.util.List;

/**
 * 查询用户未读推送数量
 *
 * @author aLang
 */
public final class NoticeCountBean {
    public String code;
    public String msg;
    public List<Data> data;

    public static class Data {
        public int notifyGroup;
        public int counts;
    }
}
