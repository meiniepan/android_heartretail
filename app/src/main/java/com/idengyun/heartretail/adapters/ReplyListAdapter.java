package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.CommentDetailBean;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class ReplyListAdapter extends BaseQuickAdapter<CommentDetailBean.replyBean, BaseViewHolder> {
    public ReplyListAdapter(int layoutResId, @Nullable List<CommentDetailBean.replyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentDetailBean.replyBean item) {
        helper.setText(R.id.tv_reply_name, item.customerReplyName)
                .setText(R.id.tv_reply_time, item.customerReplyTime)
                .setText(R.id.tv_reply_content, item.customerReplyContent);
    }

}
