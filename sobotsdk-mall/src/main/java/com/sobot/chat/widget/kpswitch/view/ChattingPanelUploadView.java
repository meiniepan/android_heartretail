package com.sobot.chat.widget.kpswitch.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sobot.chat.activity.SobotChatActivity;
import com.sobot.chat.utils.SharedPreferencesUtil;
import com.sobot.chat.utils.ZhiChiConstant;

/**
 * 聊天面板   上传
 */
public class ChattingPanelUploadView extends BaseChattingPanelView implements View.OnClickListener {

    private LinearLayout sobot_custom_bottom;
    private LinearLayout sobot_robot_bottom;
    private TextView sobot_btn_picture;
    //    private TextView sobot_btn_take_picture;
    private TextView sobot_btn_satisfaction;
    private TextView sobot_robot_btn_leavemsg;
    private TextView sobot_robot_btn_satisfaction;
    private TextView sobot_btn_order;// TODO: 2017/10/25 订单
    private TextView after_sale_order;// TODO: 2017/11/29 售后


    public ChattingPanelUploadView(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        return View.inflate(context, getResLayoutId("sobot_upload_layout"), null);
    }

    @Override
    public void initData() {
        sobot_custom_bottom = (LinearLayout) getRootView().findViewById(getResId("sobot_custom_bottom"));
        sobot_robot_bottom = (LinearLayout) getRootView().findViewById(getResId("sobot_robot_bottom"));
        sobot_btn_picture = (TextView) getRootView().findViewById(getResId("sobot_btn_picture"));
//        sobot_btn_take_picture = (TextView) getRootView().findViewById(getResId("sobot_btn_take_picture"));
        sobot_btn_satisfaction = (TextView) getRootView().findViewById(getResId("sobot_btn_satisfaction"));
        sobot_robot_btn_leavemsg = (TextView) getRootView().findViewById(getResId("sobot_robot_btn_leavemsg"));
        sobot_robot_btn_satisfaction = (TextView) getRootView().findViewById(getResId("sobot_robot_btn_satisfaction"));
        sobot_btn_order = (TextView) getRootView().findViewById(getResId("sobot_btn_order"));// TODO: 2017/10/25 订单
        after_sale_order = (TextView) getRootView().findViewById(getResId("after_sale_order"));// TODO: 2017/11/29 售后


        int leaveMsg = SharedPreferencesUtil.getIntData(context, ZhiChiConstant.sobot_msg_flag, ZhiChiConstant.sobot_msg_flag_open);
        sobot_robot_btn_leavemsg.setVisibility(leaveMsg == ZhiChiConstant.sobot_msg_flag_close ? View.INVISIBLE : View.VISIBLE);
        sobot_btn_picture.setOnClickListener(this);
//        sobot_btn_take_picture.setOnClickListener(this);
        sobot_btn_satisfaction.setOnClickListener(this);
        sobot_robot_btn_leavemsg.setOnClickListener(this);
        sobot_robot_btn_satisfaction.setOnClickListener(this);
        sobot_btn_order.setOnClickListener(this);// TODO: 2017/10/30 订单
        after_sale_order.setOnClickListener(this);// TODO: 2017/12/13 售后订单
    }

    @Override
    public String getRootViewTag() {
        return "ChattingPanelUploadView";
    }

    @Override
    public void onClick(View v) {
        SobotChatActivity ac = (SobotChatActivity) context;
        if (v.getId() == getResId("sobot_btn_picture")) {
            //图库
            ac.btnPicture();
        }

//        if (v.getId() == getResId("sobot_btn_take_picture")) {
//            //拍照
//            ac.btnCameraPicture();
//        }

        if (v.getId() == getResId("sobot_btn_satisfaction") || v.getId() == getResId("sobot_robot_btn_satisfaction")) {
            //评价客服或机器人
            ac.btnSatisfaction();
        }

        if (v.getId() == getResId("sobot_robot_btn_leavemsg")) {
            //留言
            ac.startToPostMsgActivty(false);
        }
        if (v.getId() == getResId("sobot_btn_order")) {// TODO: 2017/10/25 订单按钮
            //订单
            ac.btnOrderfaction();
        }
        if (v.getId() == getResId("after_sale_order")) {// TODO: 2017/11/29 售后
            //售后
            ac.btnAfterOrderfaction();
        }
    }

    @Override
    public void onViewStart(Bundle bundle) {
        if (bundle.getInt("current_client_model") == ZhiChiConstant.client_model_robot) {
            sobot_robot_bottom.setVisibility(View.VISIBLE);
            sobot_custom_bottom.setVisibility(View.GONE);
        } else {
            sobot_robot_bottom.setVisibility(View.GONE);
            sobot_custom_bottom.setVisibility(View.VISIBLE);

            if ("0".equals(bundle.getString("after_order"))){
                after_sale_order.setVisibility(View.GONE);
            }else {
                 after_sale_order.setVisibility(View.VISIBLE);
            }

            if ("0".equals(bundle.getString("order"))) {
                sobot_btn_order.setVisibility(View.GONE);
            }else {
                sobot_btn_order.setVisibility(View.VISIBLE);
            }

        }
    }
}