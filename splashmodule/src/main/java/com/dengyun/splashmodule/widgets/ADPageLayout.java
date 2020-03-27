package com.dengyun.splashmodule.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.spconstants.SpUserConstants;
import com.dengyun.baselibrary.widgets.imageview.RatioImageView;
import com.dengyun.sadatalib.SAGetData;
import com.dengyun.splashmodule.R;
import com.dengyun.splashmodule.utils.ArouterToFirstUtil;
import com.dengyun.splashmodule.utils.LocalAdInfoUtils;
import com.idengyun.routermodule.IntentRouterUtils;

/**
 * @titile
 * @desc Created by seven on 2018/5/10.
 */

public class ADPageLayout extends FrameLayout {

    private static Integer duration = 5000;

    private RatioImageView imageview;//广告图
    private CountDownView cdvAd;    //倒计时按钮

    /**
     * @param durationTime  广告时间，单位毫秒 默认5000
     * @param adUrl         广告图url
     * @param defaultBitmapRes  广告占位图
     */
    @SuppressLint("RestrictedApi")
    public static ADPageLayout showADView(@NonNull Activity activity,
                                          @Nullable Integer durationTime,
                                          @Nullable String adUrl,
                                          @NonNull Integer defaultBitmapRes) {
        ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (null == contentView || 0 == contentView.getChildCount()) {
            throw new IllegalStateException("You should call showSplashView() after setContentView() in Activity instance");
        }
        if (null != durationTime) duration = durationTime;
        ADPageLayout adPageLayout = new ADPageLayout(activity);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        adPageLayout.loadImgUrl(activity,adUrl,defaultBitmapRes);
        contentView.addView(adPageLayout, param);
        return adPageLayout;
    }


    public ADPageLayout(Activity context) {
        this(context,null,0);
    }

    public ADPageLayout(Activity context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ADPageLayout(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAdPage(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ADPageLayout(Activity context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAdPage(context);
    }


    /**
     * 初始化广告页，设置view，设置时间，开始倒计时
     */
    void initAdPage(Activity activity) {
        /*GradientDrawable splashSkipButtonBg = new GradientDrawable();
        splashSkipButtonBg.setShape(GradientDrawable.OVAL);
        splashSkipButtonBg.setColor(Color.parseColor("#66333333"));*/
        LayoutParams imageViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(getAdView(activity), imageViewLayoutParams);
        //设置广告时间
        this.cdvAd.setDuration(duration);
        //广告开始倒计时
        cdvAd.start();
    }

    /**
     * 获取广告页的布局，修改布局时在此修改
     */
    private View getAdView(final Activity activity){
        View adView = LayoutInflater.from(activity).inflate(R.layout.splash_view_ad,null,false);
        imageview = adView.findViewById(R.id.imageview);
        cdvAd = adView.findViewById(R.id.cdv_ad);
        imageview.setRatio(1.5f);
        /*广告图片点击跳转web*/
        imageview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cdvAd.cancle();
                String redirect_url = LocalAdInfoUtils.getAdSkipUrl();
                if (!TextUtils.isEmpty(redirect_url)) {
                    skipToWeb(activity,redirect_url);
                } else {
                    skipToFirst(activity);
                }
            }
        });
        /*广告倒计时的监听*/
        cdvAd.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {
            @Override
            public void onStartCount() {
            }

            @Override
            public void onFinishCount() {
                skipToFirst(activity);
            }
        });
        /*点击跳过广告*/
        cdvAd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cdvAd.cancle();
                skipToFirst(activity);
            }
        });
        return adView;
    }

    /**
     * 跳转到首页
     */
    private void skipToFirst(Activity activity){
        ArouterToFirstUtil.skipToFirst();
        activity.finish();
    }

    /**
     * 跳转到web
     */
    private void skipToWeb(Activity activity,String url){
        SAGetData.saPopupClick("",url);
        int interest = SpUserConstants.getInterest();
        int whatFragment = interest <= 0 ? 0 : (interest - 1);
        ArouterToFirstUtil.skipToFirst();
        IntentRouterUtils.skipToSomeWeb(url);

        /*Intent intent1 = new Intent(activity, FirstActivity2.class);
        Intent intent2 = new Intent(activity, MySomewebActivity.class);
        intent2.putExtra("url",url);
        Intent[] intents = new Intent[2];
        intents[0] = intent1;
        intents[1] = intent2;
        activity.startActivities(intents);*/

        activity.finish();
    }

    /**
     * 加载广告图
     * @param adUrl
     */
    private void loadImgUrl(Activity activity,String adUrl,int defultPic){
        ImageApi.displayImage(activity,imageview,adUrl,defultPic,defultPic);
    }


    /**
     * 取消广告页（强更弹框出来的时候回用到）
     */
    public void dismissADView() {
        cdvAd.cancle();
        final ViewGroup parent = (ViewGroup) this.getParent();
        if (null != parent) {
            @SuppressLint("ObjectAnimatorBinding") ObjectAnimator animator = ObjectAnimator.ofFloat(ADPageLayout.this, "scale", 0.0f, 0.5f).setDuration(600);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    ADPageLayout.this.setAlpha(1.0f - 2.0f * cVal);
                    ADPageLayout.this.setScaleX(1.0f + cVal);
                    ADPageLayout.this.setScaleY(1.0f + cVal);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    parent.removeView(ADPageLayout.this);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    parent.removeView(ADPageLayout.this);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }
}
