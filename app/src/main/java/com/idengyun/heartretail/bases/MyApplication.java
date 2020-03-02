package com.idengyun.heartretail.bases;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.dengyun.baselibrary.config.AppConfig;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.deal.NetDealConfig;
import com.dengyun.baselibrary.net.deal.NetDealGlobalCode;
import com.dengyun.baselibrary.spconstants.SpUserConstants;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.baselibrary.utils.activity.ActivityUtils;
import com.dengyun.downloadlibrary.download.DownloadUtil;

import com.dengyun.splashmodule.beans.MainUrlConstants;
import com.dengyun.splashmodule.utils.MyUpdateLoader;

import com.idengyun.heartretail.R;
import com.idengyun.updatelib.update.UpdateUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.zhoubo07.bannerlib.banner.BannerConfig;
import com.zhoubo07.bannerlib.banner.BannerDisplayImageHolder;
import com.zhoubo07.bannerlib.banner.SimpleImageBannerBean;

import org.litepal.LitePalApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by seven on 2016/6/24.
 */
public class MyApplication extends LitePalApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getCurProcessName(this);
        if (!TextUtils.isEmpty(processName) && processName.equals("com.idengyun.heartretail")) {
            //当前应用的初始化

            //去除9.0手机调取系统api弹框的问题
            disableAPIDialog();
            //全局的app引用以及初始化common，以及栈管理
            Utils.init(this);
            //友盟统计初始化
//            initUmengChannel();
            //设置全局需要处理的返回code
            setNetGlobalCode();
            //初始化升级模块
            initUpdateModule();
            //初始化下载模块
            initDownloadModule();
//            initSADate();
//            initShareModule();
            //Android7.0相机权限问题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            initSmartRefreshHeaderFooter();
            //初始化banner配置
            initBannerConfig();
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private void initDownloadModule() {
        DownloadUtil.init(getFilesDir().getAbsolutePath() + "/download");
    }

    private void initUpdateModule() {
        UpdateUtils.init(this, AppConfig.isDebug, R.mipmap.ic_launcher, new MyUpdateLoader());
    }

    private void initSmartRefreshHeaderFooter() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(getApplicationContext());
                header.setPrimaryColor(getResources().getColor(R.color.white));
                header.setAccentColor(getResources().getColor(R.color.black));
                return header;
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(getApplicationContext());
            }
        });
    }

    private void setNetGlobalCode() {
        NetDealConfig.setDealGlobalCodeUtil(new NetDealGlobalCode() {
            @Override
            public void dealGlobalCode(NetOption netOption, String code, String message) {
                if (code.equals("10014")) {
                    /*token过期*/
                    // TODO: 2020-02-29 token 过期

                } else if (code.equals("10017")) {
                    //强制升级
                    //如果接口是主配置接口，不做处理（因为请求主配置的时候也请求了请求更新信息的接口）
                    if (netOption.getUrl().equals(MainUrlConstants.MAINHTTP)) return;
                    //先取消掉所有的请求
                    NetApi.cancelAll();
                    //开始请求更新信息接口
                    FragmentActivity topActivity = ActivityUtils.getFragmentActivity(ActivityUtils.getTopActivity());
                    if (null != topActivity) UpdateUtils.checkUpdate(topActivity, true);
                }
            }
        });
    }


    private void initBannerConfig() {
        BannerConfig.setBannerDisplayImageHolder(new BannerDisplayImageHolder() {
            @Override
            public void disPlayImage(Context context, ImageView iv, SimpleImageBannerBean data) {
                ImageApi.displayImage(context,iv,data.getBannerImageUrl(),data.getBannerImageDefult(),data.getBannerImageDefult());
            }
        });

    }

    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog() {
        if (Build.VERSION.SDK_INT < 28) return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}