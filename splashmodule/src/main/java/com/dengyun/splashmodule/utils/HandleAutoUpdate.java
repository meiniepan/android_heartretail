package com.dengyun.splashmodule.utils;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.idengyun.updatelib.update.UpdateUtils;

import java.lang.ref.WeakReference;

/**
 * @Title 自动更新的handle
 * @Author: zhoubo
 * @CreateDate: 2019-05-07 14:36
 */
public class HandleAutoUpdate extends Handler {
    public final static int UPDATE_REQUEST = 0;//请求更新信息判断更新
    public final static int UPDATE_CHECK_BEAN = 1;//通过更新信息bean判断更新

    private WeakReference<FragmentActivity> weakActivity;
    public HandleAutoUpdate(FragmentActivity activity){
        super();
        this.weakActivity = new WeakReference<FragmentActivity>(activity);
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        FragmentActivity activity = weakActivity.get();
        if (null == activity) return;
        switch (msg.what){
            case UPDATE_REQUEST:
                UpdateUtils.checkUpdate(activity,false);
                break;
            case UPDATE_CHECK_BEAN:
                UpdateUtils.showUpdateDialog(activity.getSupportFragmentManager(), UpdateUtils.getUpdateBean());
                break;
        }
    }
}
