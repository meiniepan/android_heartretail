package com.idengyun.msgmodule.services;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.idengyun.msgmodule.beans.GeTuiBean;
import java.util.List;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //判断app进程是否存活
        if (isAppAlive(context, AppUtils.getAppPackageName())) {
            Bundle bundle = intent.getExtras();
            //通知栏中传过来的消息实体不能为空
            if (null==bundle) return;
            GeTuiBean geTuiBean = (GeTuiBean) bundle.getSerializable("GetuiBean");
            if (null==geTuiBean) return;

            // TODO: 2020-03-05 通过消息实体判断跳转到指定页面

        } else {
            //如果app进程已经被杀死，先重新启动app
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage(AppUtils.getAppPackageName());
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            launchIntent.putExtras(intent.getExtras());
            context.startActivity(launchIntent);
        }
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
