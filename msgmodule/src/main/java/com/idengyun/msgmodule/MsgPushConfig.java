package com.idengyun.msgmodule;

import com.dengyun.baselibrary.utils.Utils;
import com.idengyun.msgmodule.services.CustomMsgReciveService;
import com.idengyun.msgmodule.services.CustomPushService;

/**
 * @Title 消息推送的配置工具类
 * @Author: zhoubo
 * @CreateDate: 2020-03-05 15:33
 */
public class MsgPushConfig {

    /**
     * 初始化推送服务
     * 我们建议开发者在主进程的Application.onCreate()、Activity.onCreate()方法中初始化个推SDK。多次调用SDK初始化并无影响。
     */
    public static void initPush(){
        //先初始化推送服务
        com.igexin.sdk.PushManager.getInstance().initialize(Utils.getApp(), CustomPushService.class);

        //在个推SDK初始化后，注册消息接收类：
        com.igexin.sdk.PushManager.getInstance().registerPushIntentService(Utils.getApp(), CustomMsgReciveService.class);
    }
}
