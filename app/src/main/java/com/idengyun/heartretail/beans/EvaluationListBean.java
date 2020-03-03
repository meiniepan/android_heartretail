package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @Title 评价列表实体类
 * @Author: zhoubo
 * @CreateDate: 2020-03-03 17:37
 */
public class EvaluationListBean {
    public List<Evaluation> evaluationList;
    public String commentCounts;
    public String praiseRate;

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
