package com.sobot.chat.dengy.viewHolder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sobot.chat.activity.SobotChatActivity;
import com.sobot.chat.api.model.ZhiChiMessageBase;
import com.sobot.chat.dengy.utils.SpliceUtil;
import com.sobot.chat.utils.BitmapUtil;
import com.sobot.chat.utils.CommonUtils;
import com.sobot.chat.utils.ResourceUtils;
import com.sobot.chat.viewHolder.base.MessageHolderBase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 订单咨询项目
 */
public class OrderMessageHolder extends MessageHolderBase {
    TextView sobot_order_num;//订单编号
    TextView order_status;//订单状态
    ImageView iv_pic;//图片
    TextView tv_title;//商品名字
    TextView tv_lable;//商品价格
    TextView goods_time;//时间
    TextView send_order;//发送按钮
    RelativeLayout order_main;//订单总布局
    private boolean mIsSend = false;

    /**
     * @param isSend true/false   带发送按钮/不带发送按钮
     */
    public OrderMessageHolder(Context context, View convertView, boolean isSend) {
        super(context, convertView);
        sobot_order_num = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "sobot_order_num"));
        order_status = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "order_status"));
        iv_pic = (ImageView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "sobot_goods_pic"));
        tv_title = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "sobot_goods_title"));
        tv_lable = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "sobot_goods_label"));
        goods_time = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "goods_time"));
        send_order = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "send_order"));
        order_main = (RelativeLayout) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "order_item"));
        mIsSend = isSend;
    }

    @Override
    public void bindData(final Context context, final ZhiChiMessageBase message) {
        String msg = "";
        if (mIsSend) {
            msg = message.getUrl();
        } else {
            msg = message.getAnswer().getMsg();
        }
        String replace = "{" + msg.replace("[", "\"").replace("]", "\"")
                .replace("##", ",")
                .replace("&lt;br/&gt;", ",")
                .replace("<br/>", ",")
                .replace("\\n", ",")
                .replace("\n", ",") + "}";

        JSONObject jsonObject = null;
        String name = "";
        String order_num = "";
        String title = "";
        String status = "";
        String pic = "";
        double lable = 0;
        String time = "";
        try {
            jsonObject = new JSONObject(replace);
            name = jsonObject.getString("消息类型");
            if ("订单".equals(name)) {
                status = jsonObject.getString("订单状态");
                time = jsonObject.getString("下单时间");
                lable = jsonObject.getDouble("商品价格");
                order_num = jsonObject.getString("订单编号");
            } else {
                order_num = jsonObject.getString("售后编号");
                status = jsonObject.getString("售后状态");
                time = jsonObject.getString("申请时间");
                lable = jsonObject.getDouble("退款金额");
            }
            title = jsonObject.getString("商品名称");
            pic = jsonObject.getString("商品首图");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(order_num)) {
            sobot_order_num.setText("订单号：" + order_num);
            order_status.setText(TextUtils.isEmpty(status) ? "" : status);
            tv_title.setText(TextUtils.isEmpty(title) ? "" : title);
            tv_lable.setText(lable == 0 ? "" : "¥ " + SpliceUtil.getTwoDouble(lable));
            goods_time.setText(TextUtils.isEmpty(time) ? "" : time);

            if (!TextUtils.isEmpty(pic)) {
                iv_pic.setVisibility(View.VISIBLE);
                int drawable = ResourceUtils.getIdByName(context, "drawable",
                        "sobot_icon_consulting_default_pic");
                BitmapUtil.display(context, CommonUtils.encode(pic), iv_pic, drawable, drawable);
            } else {
                iv_pic.setImageResource(ResourceUtils.getIdByName(context, "drawable",
                        "sobot_icon_consulting_default_pic"));
            }
        } else {
            reminde_time_Text.setText("错误的订单消息格式");
            order_main.setVisibility(View.GONE);
        }
        if (mIsSend) {
            send_order.setVisibility(View.VISIBLE);
            send_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SobotChatActivity) context).sendOrderContent(TextUtils.isEmpty(message.getUrl()) ? "" : message.getUrl());
                }
            });
        }


    }
}
