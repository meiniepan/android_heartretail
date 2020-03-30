package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/28 0028 16:28
 */
public class CommentListBean {
    public List<CommentBean> evaluationList;
    public class CommentBean{
        public int evaluationId;
        public String goodsImg;
        public String goodsTitle;
        public String specItemName;
    }
}
