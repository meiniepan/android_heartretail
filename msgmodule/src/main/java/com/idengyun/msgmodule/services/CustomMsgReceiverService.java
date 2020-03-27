package com.idengyun.msgmodule.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dengyun.baselibrary.utils.GsonConvertUtil;
import com.idengyun.msgmodule.NoticeActivity;
import com.idengyun.msgmodule.PushNotificationUtils;
import com.idengyun.msgmodule.beans.GeTuiBean;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * @Title 自定义接收消息的服务
 * @Desc: 用于接收CID、透传消息以及其他推送服务事件
 * @Author: zhoubo
 * @CreateDate: 2020-03-05 15:38
 */
public class CustomMsgReceiverService extends GTIntentService {

    public CustomMsgReceiverService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理方式，
        receiveMessage(msg);
    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }


    /**
     * @param msg 接收透传消息的处理
     */
    private void receiveMessage(GTTransmitMessage msg) {
        // 1：获取透传消息
        byte[] payload = msg.getPayload();
        if (null == payload) return;
        String data = new String(payload);

        // 2：接收的消息转为本地消息实体
        GeTuiBean geTuiBean = GsonConvertUtil.fromJson(data, GeTuiBean.class);
        geTuiBean.setRead_flag(0);

        // 3：消息存本地
        // TODO: 2020-03-05 数据库存储

        // 4：发送全局广播
        EventBus.getDefault().post(geTuiBean);
        sendBroadcast(new Intent(NoticeActivity.ACTION_ON_NOTICE_ARRIVED));

        // 5：发送通知栏
        new PushNotificationUtils().sendNotification(geTuiBean);
    }

}