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
 * 商品咨询项目
 */
public class ProductMessageHolder extends MessageHolderBase {
    TextView tv_title;//商品名字
    ImageView iv_pic;//图片
    TextView tv_lable;//价格
    TextView send_product;//发送按钮
    RelativeLayout product_main;//总体布局
    private boolean mIsSend = false;

    /**
     * @param isSend true/false   带发送按钮/不带发送按钮
     */
    public ProductMessageHolder(Context context, View convertView, boolean isSend) {
        super(context, convertView);
        iv_pic = (ImageView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "sobot_goods_pic"));
        tv_title = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "sobot_goods_title"));
        tv_lable = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "sobot_goods_label"));
        send_product = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "send_product"));
        product_main = (RelativeLayout) convertView.findViewById(ResourceUtils.getIdByName(context, "id",
                "product_item"));
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
        String title = "";
        double lable = 0;
        String pic = "";
        try {
            jsonObject = new JSONObject(replace);
            title = jsonObject.getString("商品名称");
            lable = jsonObject.getDouble("商品价格");
            pic = jsonObject.getString("商品首图");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(title) && lable != 0) {

            tv_title.setText(title);
            tv_lable.setVisibility(View.VISIBLE);
            tv_lable.setText("¥ " + SpliceUtil.getTwoDouble(lable));
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
            reminde_time_Text.setText("错误的商品消息格式");
            product_main.setVisibility(View.GONE);
        }
        if (mIsSend) {
            send_product.setVisibility(View.VISIBLE);
            send_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SobotChatActivity) context).sendOrderContent(TextUtils.isEmpty(message.getUrl()) ? "" : message.getUrl());
                }
            });
        }

    }

}
