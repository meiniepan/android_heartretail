package com.dengyun.baselibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dengyun.baselibrary.config.AppConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * <pre>
 * @titile  初始化的工具类，存放activity
 * @desc Created by seven on 2018/3/8.
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    static LinkedList<Activity> sActivityList = new LinkedList<>();

    private static ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param application Application
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void init(@NonNull final Application application) {
        Utils.sApplication = application;
        Utils.sApplication.registerActivityLifecycleCallbacks(mCallbacks);
        initOkGo(application,15000);
        AppLogUtil.initLog();
        initArouter(application);
        new Thread(new Runnable() {
            @Override
            public void run() {

                initX5Webview(application);
            }
        }).start();
    }

    private static void initArouter(Application application) {
        if(AppConfig.isDebug){
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
    }

    public static void initOkGo(Application application,long timeOut) {
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(timeOut, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        builder.sslSocketFactory(sslParams1.sSLSocketFactory,sslParams1.trustManager);
        OkGo.getInstance().init(application).setOkHttpClient(builder.build());
        /*DownloadManager.getInstance().setTargetFolder(getFilesDir().getAbsolutePath() + "/download");*/
    }

    private static void initX5Webview(Context context){
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        //x5内核初始化接口
        QbSdk.initX5Environment(context,null);
        /*QbSdk.initX5Environment(context, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }
        });*/
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    public static void setTopActivity(final Activity activity) {
//        if (activity.getClass() == PermissionActivity.class) return;
        if (sActivityList.contains(activity)) {
            if (!sActivityList.getLast().equals(activity)) {
                sActivityList.remove(activity);
                sActivityList.addLast(activity);
            }
        } else {
            sActivityList.addLast(activity);
        }
    }

    public static LinkedList<Activity> getActivityList() {
        return sActivityList;
    }
}
