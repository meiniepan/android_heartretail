package com.idengyun.msgmodule.beans;

import java.util.List;

/**
 * 推送消息列表查询模型
 * 通知事件类型 1、URL链接2、订单详情3、余额4、红包5、升级提醒6、富文本
 * 内容类型(0:文本(标题+内容) 1:图文(标题+图片) 2:物流通知 3:商品 4:普通通知(文本+图片+副标题) 5:文章 6:视频 7:评论 8：(标题+图片地址+链接地址)9:（标题+内容+链接地址）10：取货通知（标题+副标题+图片地址+内容）11:（图片+内容）)
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
            public int recordEventType;

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
