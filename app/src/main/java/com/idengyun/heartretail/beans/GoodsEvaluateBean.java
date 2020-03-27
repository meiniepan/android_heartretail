package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * 商品评价API 响应体
 *
 * @author aLang
 */
public final class GoodsEvaluateBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public List<Evaluation> evaluationList;
        public String praiseRate;
        public int current;
        public int pages;
        public int total;

        public static class Evaluation {
            public int evaluationId;
            public int goodsId;
            public int userId;
            public String userName;
            public int userLevel;
            public String evaluationDate;
            public int evaluationStar;
            public String evaluationContent;
            public String orderId;
            public int isShow;
            public String userHeadImg;
            public String customerReplyName;
            public String customerReplyTime;
            public String customerReplyContent;

        }
    }
}
