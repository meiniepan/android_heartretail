package com.idengyun.heartretail.model;

/**
 * 评价数据模型
 *
 * @author aLang
 */
public final class Evaluation {
    public String code;
    public String msg;

    public static class Data {
        public int comment_id;
        public int goods_id;
        public int user_id;
        public String user_name;
        public int user_level;
        public String comment_time;
        public String comment_star;
        public String contents;
        public String order_id;
        public String is_show;
        public int comment_counts;
        public int praise_rate;
    }
}
