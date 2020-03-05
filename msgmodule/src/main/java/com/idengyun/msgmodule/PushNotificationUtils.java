package com.idengyun.msgmodule;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.dengyun.baselibrary.utils.Utils;
import com.idengyun.msgmodule.beans.GeTuiBean;
import com.idengyun.msgmodule.services.NotificationReceiver;

/**
 * @Title 推送的通知工具类
 * @Desc: 适配8.0的通知栏问题，9.0暂没有适配
 * @Author: zhoubo
 * @CreateDate: 2020年03月05日16:09:05
 */
public class PushNotificationUtils {
    private String channelId = "hr_push";
    private String channelName = "消息推送";
    //推送id
    private int notificationId = 305;

    private NotificationManager mManager;

    public PushNotificationUtils() {
        if (Build.VERSION.SDK_INT >= 26) createChannels();
    }

    /**
     * 发送通知
     */
    public void sendNotification(GeTuiBean geTuiBean) {
        Notification notification = getNotificationBuilder(geTuiBean).build();
        getManager().notify(notificationId, notification);
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        getManager().cancel(notificationId);
    }

    /*-----------------------------------------------------------------------------------------------*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
//            channel.canBypassDnd();//可否绕过，请勿打扰模式
        //闪光
        channel.enableLights(true);
        //指定闪光是的灯光颜色
        channel.setLightColor(Color.RED);
        //是否允许震动
        channel.enableVibration(true);
        //锁屏显示通知
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        //桌面laucher消息角标
        channel.canShowBadge();
        //获取系统通知响铃声音配置
        //channel.getAudioAttributes();
        //获取通知渠道组
        channel.getGroup();
        //设置可以绕过，请勿打扰模式
        channel.setBypassDnd(true);
//        channel.setVibrationPattern(new long[]{100, 100, 200});//震动的模式，震3次，第一次100，第二次100，第三次200毫秒
        //是否会闪光
        channel.shouldShowLights();

        /*Uri soundUri = Uri.parse("android.resource://" + AppUtils.getAppPackageName()
                + "/" + R.raw.notifysound);
        channel.setSound(soundUri,null);*/

        //通知管理者创建的渠道
        getManager().createNotificationChannel(channel);
    }

    private NotificationCompat.Builder getNotificationBuilder(GeTuiBean geTuiBean) {
        /*Uri soundUri = Uri.parse("android.resource://" + AppUtils.getAppPackageName()
                + "/" + R.raw.notifysound);*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Utils.getApp(), channelId)
                .setContentTitle(geTuiBean.getContent_title())
                .setTicker("心零售有新通知")
//                .setContentText(content)
                .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE)
                // TODO: 2020-03-05 状态栏上的小图标
                .setSmallIcon(R.drawable.ic_camera)  //状态栏上的通知小图标，必须设置
                .setOnlyAlertOnce(true) //设置是否只通知一次，这个效果主要体现在震动、提示音、悬挂通知上
                .setAutoCancel(true)   //允许用户点击删除按钮删除
                .setOngoing(false)       //允许滑动删除
//                .setSound(soundUri)
                .setContentText(geTuiBean.getTitle());

        //设置点击通知栏的动作为启动另外一个广播
        Intent broadcastIntent = new Intent(Utils.getApp(), NotificationReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("GetuiBean", geTuiBean);
        broadcastIntent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(Utils.getApp(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        return builder;
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) Utils.getApp().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}
