package com.idengyun.heartretail.model.response;

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
            public int commentId;
            public int goodsId;
            public int userId;
            public String userName;
            public int userLevel;
            public String commentTime;
            public int commentStar;
            public String contents;
            public String orderId;
            public int isShow;
            public String userImgUrl;

        }
    }
}
