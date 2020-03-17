package com.sobot.chat.dengy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sobot.chat.R;
import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.sobot.chat.dengy.bean.OrderGoodsBean;
import com.sobot.chat.dengy.utils.SpliceUtil;
import com.sobot.chat.utils.BitmapUtil;
import com.sobot.chat.widget.BorderImageView;

import java.util.List;


/**
 * Created by liupeng on 16/6/13.
 */

public class SobotOrderAdapter extends SimpleSwipeableAdapter<OrderGoodsBean, SobotOrderAdapter.ViewHolder> {
    private List<OrderGoodsBean> mList;
    private Context mContext;

    public SobotOrderAdapter(Context context, List<OrderGoodsBean> list) {
        super(list);
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public SobotOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sobot_order_item, parent, false);
        return new SobotOrderAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(SobotOrderAdapter.ViewHolder holder, int position) {
        OrderGoodsBean bean = mList.get(position);

        holder.goods_time.setText(bean.createDate);//时间
        holder.sobot_order_num.setText("订单号：" + bean.orderNo);//订单号=
        holder.order_status.setText(bean.customStatus);//订单状态
        BitmapUtil.display(mContext, bean.firstImg, holder.sobot_goods_pic);//商品首图
        holder.sobot_goods_title.setText(bean.goodsName);//商品标题
        holder.sobot_goods_label.setText("¥ " + SpliceUtil.getTwoDouble(bean.price));//商品价格
    }


    public static class ViewHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder {

        TextView sobot_order_num;
        TextView order_status;
        BorderImageView sobot_goods_pic;
        TextView sobot_goods_title;
        TextView sobot_goods_label;
        TextView goods_time;

        public ViewHolder(View itemView) {
            super(itemView);
            sobot_order_num = (TextView) itemView.findViewById(R.id.sobot_order_num);
            order_status = (TextView) itemView.findViewById(R.id.order_status);
            sobot_goods_pic = (BorderImageView) itemView.findViewById(R.id.sobot_goods_pic);
            sobot_goods_title = (TextView) itemView.findViewById(R.id.sobot_goods_title);
            sobot_goods_label = (TextView) itemView.findViewById(R.id.sobot_goods_label);
            goods_time = (TextView) itemView.findViewById(R.id.goods_time);
        }
    }
}