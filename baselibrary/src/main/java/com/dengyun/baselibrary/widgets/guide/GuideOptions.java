package com.dengyun.baselibrary.widgets.guide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @titile
 * @desc Created by seven on 2018/5/15.
 */

public class GuideOptions {

    private final boolean isDebug;//是否开启debug模式
    private String spKey;//记录此功能引导是否已经显示过的key
    private Activity activity;

    private View targetView;//需要高亮的目标控件
    private View hintView;//自定义的引导布局
    private int hintViewRes;//自定义的引导布局
    private int hintImgRes;//设置图片资源，（可以继续设置和targetView的位置关系)
    private int hintScreenImgRes;//设置全屏的图片资源，（不能设置位置关系，图片占满全屏）
    private GuideView.Direction direction;//引导文案相对于targetView的位置，8个方向
    private GuideView.MyShape myShape;//高亮的形状，3种
    private int xOffset;//x轴的偏移量（相对于targetView中心坐标）
    private int yOffsey;//y轴的偏移量（相对于targetView中心坐标）
    private View.OnClickListener listener;//自定义布局的点击监听
    private boolean isDefultListener;//是否使用默认监听,默认为true
    private String hintText;//使用默认引导布局的文字
    private int textSize;//使用默认引导布局的文字大小
    private int textColor;//使用默认引导布局的文字颜色
    private int backgroundColor;//引导页的背景色
    private int radius;//绘制高亮圆形时的圆半径（不设置默认是包裹控件的半径）
    private int radiusPadding;//绘制高亮圆形时的圆半径相对于默认半径的大小，例如：设置20，则绘制半径为控件半径的+20，
    private int rectLeftPadding;//绘制高亮圆角矩形或者椭圆时，矩形左边距离控件左边的距离，默认0，即高亮左边为控件左边
    private int rectTopPadding;//绘制高亮圆角矩形或者椭圆时，矩形上边距离控件上边的距离，默认0，即高亮上边为控件上边
    private int rectRightPadding;//绘制高亮圆角矩形或者椭圆时，矩形右边距离控件右边的距离，默认0，即高亮右边为控件右边
    private int rectBottomPadding;//绘制高亮圆角矩形或者椭圆时，矩形下边距离控件下边的距离，默认0，即高亮下边为控件下边

    private Animation enterAnim;//guide入场动画
    private Animation exitAnim;//guide消失动画
    private int enterAnimRes;//guide入场动画res
    private int exitAnimRes;//guide消失动画res

    private boolean isStatusbarTransparent;//是否状态栏透明（沉浸式，内容顶入状态栏）

    /*这不是参数，只是用于获得GuideView，在这个参数列表类中不做参数传递*/
    private GuideView guideView;

    public static Builder newBuilder(Activity activity,String spKey) {
        return new Builder(activity,spKey);
    }

    private GuideOptions(Builder builder) {
        isDebug = builder.isDebug;
        spKey = builder.spKey;
        activity = builder.activity;
        targetView = builder.targetView;
        hintView = builder.hintView;
        hintViewRes = builder.hintViewRes;
        hintImgRes = builder.hintImgRes;
        hintScreenImgRes = builder.hintScreenImgRes;
        direction = builder.direction;
        myShape = builder.myShape;
        xOffset = builder.xOffset;
        yOffsey = builder.yOffsey;
        listener = builder.listener;
        isDefultListener = builder.isDefultListener;
        hintText = builder.hintText;
        textSize = builder.textSize;
        textColor = builder.textColor;
        backgroundColor = builder.backgroundColor;
        radius = builder.radius;
        radiusPadding = builder.radiusPadding;
        rectLeftPadding = builder.rectLeftPadding;
        rectTopPadding = builder.rectTopPadding;
        rectRightPadding = builder.rectRightPadding;
        rectBottomPadding = builder.rectBottomPadding;
        enterAnim = builder.enterAnim;
        exitAnim = builder.exitAnim;
        enterAnimRes = builder.enterAnimRes;
        exitAnimRes = builder.exitAnimRes;
        isStatusbarTransparent = builder.isStatusbarTransparent;

        createGuideView();

    }

    private void createGuideView() {
        guideView = new GuideView(activity,spKey);
        guideView.setDebug(isDebug);
        guideView.setTargetView(targetView);
        guideView.isStatusbarTransparent(isStatusbarTransparent);
//        guideView.setCustomGuideView(hintView);
        if(hintViewRes!=0){
            hintView = LayoutInflater.from(activity).inflate(hintViewRes,null);
        }
        if(null==hintView){
            if(0==hintScreenImgRes){
                hintView =  LayoutInflater.from(activity).inflate(R.layout.base_guide_known, null);
                TextView tv = (TextView) hintView.findViewById(R.id.base_tv_hint);
                ImageView iv = (ImageView) hintView.findViewById(R.id.base_iv_known);
                if(hintText!=null) tv.setText(hintText);
                if(0!=textSize) tv.setTextSize(textSize);
                if(0!=textColor) tv.setTextColor(textColor);
                if(0!=hintImgRes) iv.setImageResource(hintImgRes);
            }else {
                hintView =  LayoutInflater.from(activity).inflate(R.layout.base_guide_screen_img, null);
                ImageView ivGuideScreen = hintView.findViewById(R.id.iv_guide_screen);
                ivGuideScreen.setImageResource(hintScreenImgRes);
            }
        }
        guideView.setCustomGuideView(hintView);
        guideView.setDirection(direction);
        guideView.setShape(myShape);
        guideView.setOffsetX(xOffset);
        guideView.setOffsetY(yOffsey);
        guideView.setMyBackgroundColor(backgroundColor);
        guideView.setRadius(radius);
        guideView.setRadiusPadding(radiusPadding);
        guideView.setRectLeftPadding(rectLeftPadding);
        guideView.setRectTopPadding(rectTopPadding);
        guideView.setRectRightPadding(rectRightPadding);
        guideView.setRectBottomPadding(rectBottomPadding);

        if(null!=enterAnim){
            guideView.setEnterAnim(enterAnim);
        }else if(0!=enterAnimRes){
            Animation anim = AnimationUtils.loadAnimation(Utils.getApp(),enterAnimRes);
            guideView.setEnterAnim(anim);
        }

        if(null!=exitAnim){
            guideView.setExitAnim(exitAnim);
        }else if(0!=exitAnimRes){
            Animation anim = AnimationUtils.loadAnimation(Utils.getApp(),exitAnimRes);
            guideView.setExitAnim(anim);
        }

        if(null!=listener){
            guideView.setOnClickListener(listener);
        }
    }

    public GuideView getGuideView(){
        return this.guideView;
    }

    public View.OnClickListener getListener(){
        return this.listener;
    }

    public boolean isDefultListener(){
        return isDefultListener;
    }


    public static final class Builder {
        private boolean isDebug;
        private String spKey;
        private Activity activity;
        private View targetView;
        private View hintView;
        private int hintViewRes;
        private int hintImgRes;//设置图片资源，（可以继续设置和targetView的位置关系)
        private int hintScreenImgRes;//设置全屏的图片资源，（不能设置位置关系，图片占满全屏）
        private GuideView.Direction direction;
        private GuideView.MyShape myShape;
        private int xOffset;
        private int yOffsey;
        private View.OnClickListener listener;
        private boolean isDefultListener = true;
        private String hintText;
        private int textSize;
        private int textColor;
        private int backgroundColor;
        private int radius;
        private int radiusPadding;
        private int rectLeftPadding;//绘制高亮圆角矩形或者椭圆时，矩形左边距离控件左边的距离，默认0，即高亮左边为控件左边
        private int rectTopPadding;//绘制高亮圆角矩形或者椭圆时，矩形上边距离控件上边的距离，默认0，即高亮上边为控件上边
        private int rectRightPadding;//绘制高亮圆角矩形或者椭圆时，矩形右边距离控件右边的距离，默认0，即高亮右边为控件右边
        private int rectBottomPadding;//绘制高亮圆角矩形或者椭圆时，矩形下边距离控件下边的距离，默认0，即高亮下边为控件下边
        private Animation enterAnim;
        private Animation exitAnim;
        private int enterAnimRes;
        private int exitAnimRes;
        private boolean isStatusbarTransparent;//是否状态栏透明（沉浸式，内容顶入状态栏）

        private Builder(Activity activity, String spKey) {
            this.activity = activity;
            this.spKey = spKey;
        }

        public Builder() {
        }

        public Builder isDebug(boolean val) {
            isDebug = val;
            return this;
        }

        public Builder spKey(String val) {
            spKey = val;
            return this;
        }

        public Builder activity(Activity val) {
            activity = val;
            return this;
        }

        public Builder targetView(View val) {
            targetView = val;
            return this;
        }

        public Builder hintView(View val) {
            hintView = val;
            return this;
        }

        public Builder hintViewRes(int val) {
            hintViewRes = val;
            return this;
        }

        public Builder hintImgRes(int val) {
            hintImgRes = val;
            return this;
        }

        public Builder hintScreenImgRes(int val) {
            hintScreenImgRes = val;
            return this;
        }

        public Builder direction(GuideView.Direction val) {
            direction = val;
            return this;
        }

        public Builder myShape(GuideView.MyShape val) {
            myShape = val;
            return this;
        }

        public Builder xOffset(int val) {
            xOffset = val;
            return this;
        }

        public Builder yOffsey(int val) {
            yOffsey = val;
            return this;
        }

        public Builder listener(View.OnClickListener val) {
            listener = val;
            return this;
        }

        public Builder isDefultListener(boolean val){
            isDefultListener = val;
            return this;
        }

        public Builder hintText(String val) {
            hintText = val;
            return this;
        }

        public Builder textSize(int val) {
            textSize = val;
            return this;
        }

        public Builder textColor(int val) {
            textColor = val;
            return this;
        }

        public Builder backgroundColor(int val) {
            backgroundColor = val;
            return this;
        }

        public Builder radius(int val) {
            radius = val;
            return this;
        }

        public Builder radiusPadding(int val) {
            radiusPadding = val;
            return this;
        }

        public Builder rectLeftPadding(int val) {
            rectLeftPadding = val;
            return this;
        }

        public Builder rectTopPadding(int val) {
            rectTopPadding = val;
            return this;
        }

        public Builder rectRightPadding(int val) {
            rectRightPadding = val;
            return this;
        }

        public Builder rectBottomPadding(int val) {
            rectBottomPadding = val;
            return this;
        }

        public Builder enterAnim(Animation val) {
            enterAnim = val;
            return this;
        }

        public Builder exitAnim(Animation val) {
            exitAnim = val;
            return this;
        }

        public Builder enterAnimRes(int val) {
            enterAnimRes = val;
            return this;
        }

        public Builder exitAnimRes(int val) {
            exitAnimRes = val;
            return this;
        }
        public Builder isStatusbarTransparent(boolean val) {
            isStatusbarTransparent = val;
            return this;
        }


        public GuideOptions build() {
            return new GuideOptions(this);
        }
    }
}
