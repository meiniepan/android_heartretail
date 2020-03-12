package com.idengyun.heartretail.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.LogisticalStatusBean;

import java.util.ArrayList;

/**
 * @创建人：Burning
 * @创建时间：2020/3/12 16:50
 * @备注：
 */
public class LogisticalStatusAdapter extends BaseQuickAdapter<LogisticalStatusBean, BaseViewHolder> {

    public LogisticalStatusAdapter(Context context, ArrayList<LogisticalStatusBean> datas, int layoutId) {
        super(layoutId, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticalStatusBean item) {
        TextView tv_status = helper.getView(R.id.tv_status);
        ImageView iv_status = helper.getView(R.id.iv_status);
        TextView line_right = helper.getView(R.id.line_right);
        TextView line_left = helper.getView(R.id.line_left);
        tv_status.setText(item.statusName);
        line_left.setVisibility(View.VISIBLE);
        line_right.setVisibility(View.VISIBLE);
        if (helper.getAdapterPosition() == 0) {
            line_left.setVisibility(View.INVISIBLE);
        }
        if (helper.getAdapterPosition() == 3) {
            line_right.setVisibility(View.INVISIBLE);
        }

        if (item.isCurrentStatus == 1) {
            tv_status.setTextColor(Color.WHITE);
            //logistics_show
            tv_status.setBackgroundResource(R.drawable.logistics_show);
            iv_status.setImageResource(R.drawable.logistics_current);
        } else {
            tv_status.setTextColor(mContext.getResources().getColor(R.color.gray333));
            tv_status.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            iv_status.setImageResource(R.drawable.logistics_past);
        }
    }
}

