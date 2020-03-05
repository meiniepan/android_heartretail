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
import android.widget.ImageView;

import com.dengyun.baselibrary.config.AppConfig;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.deal.NetDealConfig;
import com.dengyun.baselibrary.net.deal.NetDealGlobalCode;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.baselibrary.utils.activity.ActivityUtils;
import com.dengyun.downloadlibrary.download.DownloadUtil;

import com.dengyun.sharelibrary.utils.ShareUtil;
import com.dengyun.splashmodule.beans.MainUrlConstants;
import com.dengyun.splashmodule.utils.MyUpdateLoader;

import com.idengyun.heartretail.R;
import com.idengyun.updatelib.update.UpdateUtils;
import com.meituan.android.walle.WalleChannelReader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SensorsAnalyticsAutoTrackEventType;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataDynamicSuperProperties;
import com.umeng.commonsdk.UMConfigure;
import com.zhoubo07.bannerlib.banner.BannerConfig;
import com.zhoubo07.bannerlib.banner.BannerDisplayImageHolder;
import com.zhoubo07.bannerlib.banner.SimpleImageBannerBean;

import org.json.JSONObject;
import org.litepal.LitePalApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by seven on 2016/6/24.
 */
public class MyApplication extends LitePalApplication {
    // TODO: 2020-03-04 账号
    private static final String UMENG_APPKEY = "";
    private static final String WX_APPID = "";
    private static final String WX_APPSECRET = "";
    private static final String SINA_APPKEY = "";
    private static final String SINA_APPSECRET = "";
    private static final String SINA_REDIREC_URL = "";
    private static final String QQ_APPID = "";
    private static final String QQ_APPKEY = "";

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
            initUmengChannel();
            //设置全局需要处理的返回code
            setNetGlobalCode();
            //初始化升级模块
            initUpdateModule();
            //初始化下载模块
            initDownloadModule();
            initSADate();
            initShareModule();
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

    private void initSADate() {
        // 数据接收的 URL
        final String SA_SERVER_URL = "https://idata-dcs.idengyun.com/sa?project=xls";
        final String SA_SERVER_URL_TEXT = "https://idata-dcs.idengyun.com/sa?project=xlstest";

        //设置 SAConfigOptions，传入数据接收地址 SA_SERVER_URL
        SAConfigOptions saConfigOptions = new SAConfigOptions(AppConfig.isDebug ? SA_SERVER_URL_TEXT : SA_SERVER_URL);

        //通过 SAConfigOptions 设置神策 SDK，每个条件都非必须，开发者可根据自己实际情况设置，更多设置可参考 SAConfigOptions 类中方法注释
        saConfigOptions.setAutoTrackEventType(SensorsAnalyticsAutoTrackEventType.APP_CLICK | // 开启全埋点点击事件
                SensorsAnalyticsAutoTrackEventType.APP_START |      //开启全埋点启动事件
                SensorsAnalyticsAutoTrackEventType.APP_END |        //开启全埋点退出事件
                SensorsAnalyticsAutoTrackEventType.APP_VIEW_SCREEN)     //开启全埋点浏览事件
                .enableLog(AppConfig.isDebug)        //开启神策调试日志，默认关闭(调试时，可开启日志)。
                .enableHeatMap(false)   //设置点击图是否可用
                .enableHeatMapConfirmDialog(false) // 设置点击图提示对话框是否可用
                .enableVisualizedAutoTrack(false) //设置可视化全埋点是否可用
                .enableVisualizedAutoTrackConfirmDialog(false) //设置可视化全埋点提示对话框是否可用
                .enableTrackScreenOrientation(true) //是否开启屏幕方向采集
                .enableTrackAppCrash();     //开启 crash 采集
        //需要在主线程初始化神策 SDK
        SensorsDataAPI.startWithConfigOptions(this, saConfigOptions);
        //开启fragment自动收集
        SensorsDataAPI.sharedInstance().trackFragmentAppViewScreen();

        /*市场渠道 //初始化我们SDK后 调用这段代码，用于记录安装事件、渠道追踪。*/
        try {
            JSONObject properties = new JSONObject();
            //这里的 DownloadChannel 负责记录下载商店的渠道。
            String channel = WalleChannelReader.getChannel(this.getApplicationContext());
            properties.put("DownloadChannel", channel);
            SensorsDataAPI.sharedInstance().trackInstallation("AppInstall", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 初始化 SDK 后，设置动态公共属性
        SensorsDataAPI.sharedInstance().registerDynamicSuperProperties(new SensorsDataDynamicSuperProperties() {
            @Override
            public JSONObject getDynamicSuperProperties() {
                try {
                    // 比如 isLogin() 是用于获取用户当前的登录状态，SDK 会自动获取 getDynamicSuperProperties 中的属性添加到触发的事件中。
                    JSONObject jo = new JSONObject();
                    jo.put("platformType", "android");
                    return jo;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
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

    /**
     * 友盟初始化，传入渠道信息
     */
    private void initUmengChannel() {
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        UMConfigure.init(this, UMENG_APPKEY, channel, UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    /**
     * 分享模块初始化
     */
    private void initShareModule() {
        ShareUtil.initShare(this,
                R.mipmap.ic_launcher,// TODO: 2020-03-04 分享的默认图片(直角logo)
                WX_APPID,
                WX_APPSECRET,
                QQ_APPID,
                QQ_APPKEY,
                SINA_APPKEY,
                SINA_APPSECRET,
                SINA_REDIREC_URL);
    }

    private void initDownloadModule() {
        DownloadUtil.init(getFilesDir().getAbsolutePath() + "/download");
    }

    /**
     * 升级模块初始化
     */
    private void initUpdateModule() {
        // TODO: 2020-03-04 导航栏的小图标
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
                ImageApi.displayImage(context, iv, data.getBannerImageUrl(), data.getBannerImageDefult(), data.getBannerImageDefult());
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