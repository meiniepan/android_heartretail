package com.dengyun.splashmodule.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.widgets.VPSimpleAdapter;
import com.dengyun.splashmodule.R;
import com.dengyun.splashmodule.config.GuidePicData;
import com.dengyun.splashmodule.fragments.LeadFragment1;

import java.util.ArrayList;

/**
 * @titile
 * @desc Created by seven on 2018/5/10.
 */

public class GuidePageLayout extends FrameLayout {



    private static final int skipButtonSizeInDip = 36;
    private static final int skipButtonMarginInDip = 16;
    private Integer duration = 6;
    private static final int delayTime = 1000;   // 每隔1000 毫秒执行一次

    private boolean isActionBarShowing = true;
    private Activity mActivity = null;

    public GuidePageLayout(FragmentActivity context) {
        super(context);
        mActivity = context;
        initComponents(context);
    }

    public GuidePageLayout(FragmentActivity context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = context;
        initComponents(context);
    }

    public GuidePageLayout(FragmentActivity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = context;
        initComponents(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GuidePageLayout(FragmentActivity context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mActivity = context;
        initComponents(context);
    }

    private GradientDrawable splashSkipButtonBg = new GradientDrawable();

    void initComponents(FragmentActivity activity) {
        splashSkipButtonBg.setShape(GradientDrawable.OVAL);
        splashSkipButtonBg.setColor(Color.parseColor("#66333333"));
        LayoutParams imageViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(getGuideView(activity), imageViewLayoutParams);
    }

    /**
     * 获取引导页的布局，修改布局时在此修改
     */
    private View getGuideView(FragmentActivity activity){
        View guideView = LayoutInflater.from(activity).inflate(R.layout.splash_view_guide,null,false);
        ViewPager mVpGuide = guideView.findViewById(R.id.vp_welcome);
        int leadFragmentNum= GuidePicData.leadpic.length;
        ArrayList<Fragment> data = new ArrayList<>();
        for(int i=0;i<leadFragmentNum;i++){
            LeadFragment1 leadFragment = LeadFragment1.newInstance(i);
            data.add(leadFragment);
        }
        mVpGuide.setAdapter(new VPSimpleAdapter(activity.getSupportFragmentManager(),data));
        return guideView;
    }

    /**
     * 跳转到首页
     */
    private void skipToFirst(Activity activity){
        ARouter.getInstance().build(RouterPathConfig.app_FirstActivity).navigation();
    }

    /**
     * @param activity
     */
    @SuppressLint("RestrictedApi")
    public static void showGuideView(@NonNull FragmentActivity activity) {
        ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (null == contentView || 0 == contentView.getChildCount()) {
            throw new IllegalStateException("You should call showSplashView() after setContentView() in Activity instance");
        }

        GuidePageLayout guidePageLayout = new GuidePageLayout(activity);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*隐藏掉状态栏和activitybar*/
        if (activity instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (null != supportActionBar) {
                supportActionBar.setShowHideAnimationEnabled(false);
                guidePageLayout.isActionBarShowing = supportActionBar.isShowing();
                supportActionBar.hide();
            }
        } else if (activity instanceof Activity) {
            android.app.ActionBar actionBar = activity.getActionBar();
            if (null != actionBar) {
                guidePageLayout.isActionBarShowing = actionBar.isShowing();
                actionBar.hide();
            }
        }
        contentView.addView(guidePageLayout, param);
    }

    private void dismissADView() {
        final ViewGroup parent = (ViewGroup) this.getParent();
        if (null != parent) {
            @SuppressLint("ObjectAnimatorBinding") ObjectAnimator animator = ObjectAnimator.ofFloat(GuidePageLayout.this, "scale", 0.0f, 0.5f).setDuration(600);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    GuidePageLayout.this.setAlpha(1.0f - 2.0f * cVal);
                    GuidePageLayout.this.setScaleX(1.0f + cVal);
                    GuidePageLayout.this.setScaleY(1.0f + cVal);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    parent.removeView(GuidePageLayout.this);
                    showSystemUi();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    parent.removeView(GuidePageLayout.this);
                    showSystemUi();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    private void showSystemUi() {
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (mActivity instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
            if (null != supportActionBar) {
                if (isActionBarShowing) supportActionBar.show();
            }
        } else if (mActivity instanceof Activity) {
            android.app.ActionBar actionBar = mActivity.getActionBar();
            if (null != actionBar) {
                if (isActionBarShowing) actionBar.show();
            }
        }
    }
}
