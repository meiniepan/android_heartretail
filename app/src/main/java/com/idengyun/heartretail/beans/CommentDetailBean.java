package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/28 0028 16:28
 */
public class CommentDetailBean {
    public String goodsTitle;
    public String evaluationDate;//评价时间
    public String goodsImg;
    public String userName;
    public float evaluationStar;
    public String userHeadImg;
    public String evaluationContent;
    public String isShow;//是否展示0是 1否
    public List<replyBean> replyList;//客服回复集合

    public class replyBean{
        public String customerReplyName;
        public String customerReplyTime;
        public String customerReplyContent;
        public String customHeadImg;
    }
}
