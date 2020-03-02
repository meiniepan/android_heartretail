package com.dengyun.splashmodule.activitys;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.base.dialog.BaseDialogFragment;
import com.dengyun.baselibrary.base.dialog.SimpleDialog;
import com.dengyun.baselibrary.base.dialog.listener.DialogViewHolder;
import com.dengyun.baselibrary.base.dialog.listener.OnCancelListener;
import com.dengyun.baselibrary.base.dialog.listener.OnConfirmListener;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.net.util.NetworkUtils;
import com.dengyun.baselibrary.spconstants.SpStartConstants;
import com.dengyun.baselibrary.spconstants.SpUserConstants;
import com.dengyun.baselibrary.utils.bar.StatusBarUtil;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.baselibrary.widgets.CountDownTimerUtil;
import com.dengyun.splashmodule.R;
import com.dengyun.splashmodule.listeners.OnLoadMainUrlsListener;
import com.dengyun.splashmodule.utils.LocalAdInfoUtils;
import com.dengyun.splashmodule.widgets.ADPageLayout;
import com.dengyun.splashmodule.widgets.GuidePageLayout;
import com.idengyun.routermodule.ARouterInstance;
import com.idengyun.updatelib.bean.UpdateBean;
import com.idengyun.updatelib.update.OnLoadUpdateInfoCallback;
import com.idengyun.updatelib.update.UpdateUtils;
import com.orhanobut.logger.Logger;

/**
 * @Title 闪屏页的基类，
 * @Desc: 闪屏的逻辑在这个类里，其他的逻辑（存储主配置url、其他逻辑）在派生类中
 * @Author: zhoubo
 * @CreateDate: 2019-08-24 10:46
 */
public abstract class BaseSplashActivity extends BaseActivity {

    private static final int PAGE_SPLASH = 0; //闪屏页
    private static final int PAGE_AD = 1;     //广告页
    private static final int PAGE_GUIDE = 2;  //引导页
    private static final int PAGE_FINISH = 3;  //页面已经关闭（关闭有延迟，所以这期间需要有个状态判断）

    private int splashTime = 3;//单独的闪屏页的时间
    private int adMinTime = 1; //如果广告图已经加载好（或者加载较快），最短多久可以跳转到广告页
    private int currentTime = 0;//当前秒数（从刚进入闪屏页开始）

    private boolean isMustUpdate = false;//是否强制更新

    private boolean isAdSuccess = false;//是否已经加载好广告图
    private boolean isFirstOrUpdateInstall = false;//是否是首次加载或者升级安装加载
    private int pageState = PAGE_SPLASH;//页面状态：0：闪屏页，1：广告页，2：引导页
    private String ad_imgurl;//本地广告图的url
    private boolean isConnectedNet = false;//网络是否连接了

    private CountDownTimerUtil timer = new CountDownTimerUtil(splashTime * 1000 + 100, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int remainTime = (int) (millisUntilFinished / 1000);//剩下的时间
            currentTime = splashTime - remainTime;
            Logger.d("----闪屏页计时="+currentTime+"s");
            if(isAdSuccess&&!isFirstOrUpdateInstall&&currentTime>adMinTime){
                //广告缓存成功&&不是首次加载&&当前时间大于1s，，打开广告页面
                Logger.d("倒计时间隔判断广告有缓存，跳到广告");
                openAdPage();
                timer.cancel();
            } else if(isFirstOrUpdateInstall&&currentTime>adMinTime){
                //首次加载&&当前时间大于1s，，打开引导页面
                openGuidePage();
                timer.cancel();
            }
        }

        @Override
        public void onFinish() {
            Logger.d("----闪屏页计时结束="+currentTime+"s");
            //倒计时结束就跳转到首页（不是第一次打开）或者引导页（首次安装或者升级安装）
            skipFirst();
        }
    };

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    @Override
    public void onBeforSetContentView(@Nullable Bundle savedInstanceState) {
        super.onBeforSetContentView(savedInstanceState);
        this.getWindow().getDecorView().setBackground(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity_welcome;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //首先判断有没有联网，没联网的换提示弹框联网，联网才会往下进行
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                isConnectedNet = checkoutNet(BaseSplashActivity.this);
                if(isConnectedNet){
                    timer.start();
                    initIfFirst();
                    //检查更新接口
                    initCheckUpdate();
                    loadMainAndAdUrls(new MyOnLoadMainAndAdUrlsListener());
                }
            }
        });
    }

    public abstract void loadMainAndAdUrls(OnLoadMainUrlsListener onLoadMainUrlsListener);

    /**
     * 判断是否是新安装
     */
    private void initIfFirst() {
        //是否走引导页
        if (SpStartConstants.isFirstStart()) {
            //本地没有isfirststart字段，新安装app
            isFirstOrUpdateInstall = true;
        } else {
            String sp_ver_num = SpStartConstants.getLocalVersionName();
            if (!sp_ver_num.equals("") &&
                    !sp_ver_num.equals(AppUtils.getAppVersionName())) {
                //不是首次安装，但是版本号不同了（升级安装），
                isFirstOrUpdateInstall = true;
            } else {
                //正常启动或者同版本覆盖
                initAdPic();
            }
        }
    }

    /**
     * 预加载广告图
     */
    private void initAdPic() {
        ad_imgurl = LocalAdInfoUtils.getAdPic();
        if (TextUtils.isEmpty(ad_imgurl)) {
            return;
        }
        loadAdUrl(ad_imgurl);
    }

    private void initCheckUpdate() {
        // TODO: 2020-02-29 热修复没有添加
//        ARouterInstance.getAppRouter().checkHotfix();
        UpdateUtils.requestUpdateInfo(this, false, new OnLoadUpdateInfoCallback() {
            @Override
            public void onLoadSuccess(UpdateBean updateBean) {
                if (updateBean.getIsUpdate()==1){//有更新
                    if (updateBean.getIsForce()==1){//强制更新
                        isMustUpdate = true;
                        timer.cancel();//取消闪屏页的倒计时进入广告、引导、首页
                        if (null!=adPageLayout) adPageLayout.dismissADView();//如果已经进入广告，取消广告
                        UpdateUtils.showUpdateDialog(getSupportFragmentManager(),updateBean);
                    }else {//非强制更新
                    }
                }
            }
        });
    }

    /**
     * 主配置请求成功后处理广告url
     */
    private void dealAdUrlWithNetSuccess(String httpAdUrl) {
        if(TextUtils.isEmpty(httpAdUrl)){
            //请求下来的广告信息为空，清空本地的广告信息；
            isAdSuccess = false;
            LocalAdInfoUtils.removeAdInfo();
        }else if(httpAdUrl.equals(ad_imgurl)){
            //请求下来的广告信息和之前相同，不操作
        }else {
            //请求下来的广告信息和之前不同，
            isAdSuccess = false;
            loadAdUrl(httpAdUrl);
        }
    }

    /**
     * 加载广告图url，只缓存不显示
     */
    private void loadAdUrl(String httpAdUrl) {
        Glide.with(this)
                .load(httpAdUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(!isFirstOrUpdateInstall){
                            //不是首次启动，
                            isAdSuccess = true;
                            if(currentTime>adMinTime){
                                //当前闪屏页显示秒数已经大于1s
                                Logger.d("预加载广告完成，调到广告");
                                openAdPage();
                            }
                        }
                        return false;
                    }
                })
                .preload();
    }

    private ADPageLayout adPageLayout;
    /**
     * 打开广告页
     */
    private void openAdPage(){
        if(isMustUpdate || pageState!=PAGE_SPLASH){
            return;
        }
        pageState = PAGE_AD;
        adPageLayout = ADPageLayout.showADView(this,5000,ad_imgurl, R.drawable.splash_pic_defult);
    }

    /**
     * 打开引导页
     */
    private void openGuidePage(){
        if(isMustUpdate || pageState!=PAGE_SPLASH){
            return;
        }
        pageState = PAGE_GUIDE;
        GuidePageLayout.showGuideView(this);
        SpStartConstants.saveIsFirstStart();
        SpStartConstants.saveLocalVersionName();
    }

    /**
     * 直接跳到FirstActivity
     */
    private void skipFirst(){
        if(isMustUpdate || pageState!=PAGE_SPLASH){
            return;
        }
        pageState = PAGE_FINISH;
        int interest = SpUserConstants.getInterest();
        int whatFragment = interest <= 0 ? 0 : (interest - 1);
        ARouter.getInstance().build(RouterPathConfig.app_FirstActivity).withInt("whatFragment",whatFragment).navigation();
        finish();
    }

    private SimpleDialog noNetDialog;

    /**
     * 检查网络是否连接，没连接就弹框
     * @return  网络是否连接
     */
    private boolean checkoutNet(FragmentActivity activity){
        boolean isConnected = NetworkUtils.isConnected();
        if(!isConnected){
            if(null==noNetDialog){
                noNetDialog = SimpleDialog.newInstance().setTitle("没有网络")
                        .setMessage("请检查您的网络设置")
                        .setCancelText("退出")
                        .setConfirmText("去设置")
                        .setConfirmListener(new OnConfirmListener() {
                            @Override
                            public void onConfirm(DialogViewHolder holder, BaseDialogFragment dialog) {
                                Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                                startActivity(intent);
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel(DialogViewHolder holder, BaseDialogFragment dialog) {
                                finish();
                            }
                        });
            }
            if(!noNetDialog.isAdded()){
                noNetDialog.show(activity.getSupportFragmentManager());
            }
        }
        return isConnected;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        if (!isTaskRoot()) finish();
        super.onCreate(savedInstanceState, persistentState);
    }

    /*屏蔽返回按钮*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    public class MyOnLoadMainAndAdUrlsListener implements OnLoadMainUrlsListener {

        @Override
        public void setAdUrl(String adUrl) {
            dealAdUrlWithNetSuccess(adUrl);
        }
    }

}
