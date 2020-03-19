package com.idengyun.msgmodule.beans;

import java.util.List;

/**
 * 推送消息列表查询模型
 *
 * @author aLang
 */
public final class NoticeListBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public int notifyGroup;
        public int current;
        public int pages;
        public int total;
        public List<Content> contentList;

        public static class Content {
            public int messageId;
            public int status;
            public String content;
            public String pushTime;
            public int contentType;
            public String title;

            public static class Type0 {
                public String contentTitle;
                public String contentDetail;
            }

            public static class Type1 {
                public String contentTitle;
                public String contentDetail;
            }

            public static class Type2 {
                public String contentTitle;
                public String contentDetail;
                public List<Goods> goodsList;

                public static class Goods {
                    public String imgUrl;
                    public String specKeyName;
                    public String goodsNumStr;
                    public String goodsName;
                }
            }

            public static class Type4 {
                public String contentTitle;
                public String contentDetail;
                public String imgUrl;
            }

            public static class Type8 {
                public String contentTitle;
                public String contentDetail;
                public String imgUrl;
            }

            public static class Type10 {
                public String contentTitle;
                public String contentDetail;
                public String subTitle;
                public String imgUrl;
            }

            public static class Type11 {
                public String contentDetail;
                public String imgUrl;
            }
        }
    }
}
