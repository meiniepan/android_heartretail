package com.idengyun.heartretail.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.LogisticalMessageBean;
import com.idengyun.heartretail.beans.ShippingListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人：Burning
 * @创建时间：2020/3/12 16:50
 * @备注：
 */

public class LogisticalmsgAdapter extends BaseQuickAdapter<ShippingListBean.LogisticsTraces, BaseViewHolder> {
    public LogisticalmsgAdapter(Context context, List<ShippingListBean.LogisticsTraces> datas, int layoutId) {
        super(layoutId, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShippingListBean.LogisticsTraces item) {
        TextView tv_trace_info = helper.getView(R.id.tv_trace_info); //物流信息
        TextView tv_trace_time = helper.getView(R.id.tv_trace_time); //更新时间
        View v_bottom_line = helper.getView(R.id.v_bottom_line); //底部横线（最下面一条没有）
        View v_up_line = helper.getView(R.id.v_up_line); //左侧竖线 上面一小段（第一条没有）
        View v_down_line = helper.getView(R.id.v_down_line); //左侧竖线 下面一小段（最后一条没有）
        ImageView iv_state = helper.getView(R.id.iv_state); //圆球图片


        tv_trace_info.setText(item.AcceptStation);
        tv_trace_time.setText(item.AcceptTime);

        if (mData.size() > 1 && helper.getAdapterPosition() == mData.size() - 1) {
            v_bottom_line.setVisibility(View.GONE);
            v_down_line.setVisibility(View.GONE);
        } else {
            v_bottom_line.setVisibility(View.VISIBLE);
            v_down_line.setVisibility(View.VISIBLE);
        }

        if (helper.getAdapterPosition() == 0) {
            iv_state.setImageResource(R.drawable.logistic_msg_now);
            v_up_line.setVisibility(View.GONE);
            tv_trace_info.setTextColor(mContext.getResources().getColor(R.color.orange));
            v_bottom_line.setVisibility(View.VISIBLE);
            v_down_line.setVisibility(View.VISIBLE);
        } else {
            iv_state.setImageResource(R.drawable.logistics_past);
            v_up_line.setVisibility(View.VISIBLE);
            tv_trace_info.setTextColor(mContext.getResources().getColor(R.color.orange));
        }
        if (mData.size() == 1) {
            v_bottom_line.setVisibility(View.GONE);
            v_down_line.setVisibility(View.VISIBLE);
        }
    }

}
