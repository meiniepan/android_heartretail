package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.CommentListBean;

import java.util.List;

/**
 * @author Burning
 * @description:
 * @date :2020/3/4 0004 16:43
 */
public class EvaluateListAdapter extends BaseQuickAdapter<CommentListBean.CommentBean, BaseViewHolder> {
    public EvaluateListAdapter(int layoutResId, @Nullable List<CommentListBean.CommentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentListBean.CommentBean item) {
        helper.setText(R.id.tv_goods_title3, item.goodsTitle)
                .setText(R.id.tv_goods_spec3, item.specItemName);
        ImageView imageView = helper.getView(R.id.iv_goods_icon3);
        ImageApi.displayImage(mContext, imageView, item.goodsImg);
    }

}
