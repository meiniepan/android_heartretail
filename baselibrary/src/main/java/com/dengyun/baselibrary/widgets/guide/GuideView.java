package com.dengyun.baselibrary.widgets.guide;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.baselibrary.utils.bar.StatusBarUtil;
import com.orhanobut.logger.Logger;

/**
 * @titile
 * @desc Created by seven on 2018/5/15.
 */

public class GuideView extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private String spFileName = "guide";
    private String spKey;
    private Activity context;

    //偏移量
    private int offsetX, offsetY;
    //圆半径
    private int radius;
    //圆半径相对控件半径的大小：例如：设置20，则绘制半径为控件半径的+20，
    private int radiusPadding;
    private int rectLeftPadding;//绘制高亮圆角矩形或者椭圆时，矩形左边距离控件左边的距离，默认0，即高亮左边为控件左边
    private int rectTopPadding;//绘制高亮圆角矩形或者椭圆时，矩形上边距离控件上边的距离，默认0，即高亮上边为控件上边
    private int rectRightPadding;//绘制高亮圆角矩形或者椭圆时，矩形右边距离控件右边的距离，默认0，即高亮右边为控件右边
    private int rectBottomPadding;//绘制高亮圆角矩形或者椭圆时，矩形下边距离控件下边的距离，默认0，即高亮下边为控件下边
    //需要高亮的View
    private View targetView;
    //自定义View显示提示文案
    private View customGuideView;
    //targetView圆心 [0]:x坐标，[1]:y坐标
    private int[] center;
    //背景色和透明度
    private int backgroundColor;
    //相对于targetView的位置，一共8个方向
    private Direction direction;
    //高亮的形状，一共3种
    private MyShape myShape;
    //是否开启debug模式，开启后每次都显示
    private boolean isDebug;
    //是否已经测量过
    private boolean isMeasured;
    //进入动画
    private Animation enterAnim;
    //消失动画
    private Animation exitAnim;
    //是否状态栏透明（沉浸式，内容顶入状态栏）
    private boolean isStatusbarTransparent;

    public GuideView(Activity context, String spFileKey) {
        super(context);
        this.spKey = spFileKey;
        this.context = context;
        setFocusableInTouchMode(false);
    }

    /**
     * 定义GuideView相对于targetView的方位，共八种。不设置则默认在targetView下方
     */
    public enum Direction {
        LEFT, TOP, RIGHT, BOTTOM,
        LEFT_TOP, LEFT_BOTTOM,
        RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 定义目标控件的形状，共3种。圆形，椭圆，带圆角的矩形（可以设置圆角大小），不设置则默认是圆形
     */
    public enum MyShape {
        CIRCULAR, ELLIPSE, RECTANGULAR
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d("onDraw");
        if (!isMeasured&&targetView!=null) {
            return;
        }
        drawBackground(canvas);
    }

    @Override
    public void onGlobalLayout() {
        Logger.d("onGlobalLayout");
        if (isMeasured) {
            return;
        }

        if (targetView.getHeight() > 0 && targetView.getWidth() > 0) {
            isMeasured = true;
        }

        // 获取targetView的中心坐标
        if (center == null) {
            // 获取右上角坐标
            int[] location = new int[2];
            targetView.getLocationInWindow(location);
            center = new int[2];
            // 获取中心坐标
            center[0] = location[0] + targetView.getWidth() / 2;
            center[1] = location[1] + targetView.getHeight() / 2 - (isStatusbarTransparent ? 0 :StatusBarUtil.getStatusBarHeight(context));
        }
        // 获取targetView外切圆半径
        if (radius == 0) {
            radius = getTargetViewRadius()+radiusPadding;
        }
        // 添加GuideView
        createHintView();
    }

    /**
     * 获得targetView 的半径
     */
    private int getTargetViewRadius() {
        if (isMeasured) {
            int[] size = getTargetViewSize();
            int x = size[0];
            int y = size[1];

            return (int) (Math.sqrt(x * x + y * y) / 2);
        }
        return -1;
    }

    /**
     * 获得targetView 的宽高，如果未测量，返回｛-1， -1｝
     */
    private int[] getTargetViewSize() {
        int[] location = {-1, -1};
        if (isMeasured || null == targetView) {
            location[0] = targetView.getWidth();
            location[1] = targetView.getHeight();
        }
        return location;
    }

    /**
     * 重置变量
     */
    public void restoreState() {
        Log.v(spFileName, "restoreState");
        offsetX = offsetY = 0;
        radius = 0;
        mCirclePaint = null;
        mBackgroundPaint = null;
        isMeasured = false;
        center = null;
        porterDuffXfermode = null;
        bitmap = null;
        temp = null;
    }

    /**
     * @return 是否以前显示过
     */
    private boolean hasShown() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, spKey, false);
    }

    /**
     * 隐藏遮盖布局
     */
    public void hide() {
        if (customGuideView != null) {
            if (null != targetView) {
                targetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
            this.removeAllViews();
//            ((FrameLayout) ((Activity) context).getWindow().getDecorView()).removeView(this);
            getWindowsContentView().removeView(this);
            restoreState();
        }
    }

    public boolean show() {
        if (isDebug) {
            //debug模式下每次都显示此引导
            showHint();
            return true;
        } else {
            if (!hasShown()) {
                showHint();
                SharedPreferencesUtil.saveData(Utils.getApp(), spFileName, spKey, true);
                return true;
            }
            return false;
        }
    }

    private void showHint() {
        if (targetView != null) {
            targetView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }else if(customGuideView!=null){
            addCustomViewWithFullscreen();
        }
        this.setBackgroundResource(R.color.transparent);
//        ((FrameLayout) ((Activity) context).getWindow().getDecorView()).addView(this);
        getWindowsContentView().addView(this);

        this.setFocusable(true);
        if(null!=enterAnim){
            startAnimation(enterAnim);
        }
    }

    private ViewGroup getWindowsContentView(){
        return context.getWindow().getDecorView().findViewById(android.R.id.content);
    }


    private void addCustomViewWithFullscreen(){
        // Tips布局参数
        LayoutParams guideViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        guideViewParams.setMargins(offsetX, offsetY, -offsetX, -offsetY);
        this.addView(customGuideView, guideViewParams);
    }


    /**
     * 提示文案的位置关系（相对于targetView）的LayoutParams （不包含高亮）
     */
    private void createHintView() {
        // Tips布局参数
        if (null == customGuideView) {
            return;
        }
        if (direction != null) {
            int width = this.getWidth();
            int height = this.getHeight();
            /*int width = ScreenUtil.getScreenWidth();
            int height = ScreenUtil.getScreenHeight();*/

            int left = 0;
            int right = 0;
            int top = 0;
            int bottom = 0;

            if(myShape!=null){
                switch (myShape) {
                    case CIRCULAR://圆形
                        left = center[0] - radius;
                        right = center[0] + radius;
                        top = center[1] - radius;
                        bottom = center[1] + radius;
                        break;
                    case ELLIPSE://椭圆
                    case RECTANGULAR://圆角矩形
                        left = center[0] - targetView.getWidth() / 2 - rectLeftPadding;                              //左边
                        top = center[1] - targetView.getHeight() / 2 - rectTopPadding;                              //上边
                        right = center[0] + targetView.getWidth() / 2 + rectRightPadding;                             //右边
                        bottom = center[1] + targetView.getHeight() / 2 + rectBottomPadding;
                        break;
                    default:
                }
            }


            LayoutParams hintViewParentParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            switch (direction) {
                case TOP:
                    this.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                    hintViewParentParams.setMargins(offsetX,0,0, height-top-offsetY);
//                    hintViewParentParams.setMargins(offsetX, offsetY - height + top, -offsetX, height - top - offsetY);
                    break;
                case LEFT:
                    this.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                    hintViewParentParams.setMargins(0,offsetY,width-left-offsetX,0);
//                    hintViewParentParams.setMargins(offsetX - width + left, top + offsetY, width - left - offsetX, -top - offsetY);
                    break;
                case BOTTOM:
                    this.setGravity(Gravity.CENTER_HORIZONTAL);
                    hintViewParentParams.setMargins(offsetX,bottom + offsetY,0,0);
//                    hintViewParentParams.setMargins(offsetX, bottom + offsetY, -offsetX, -bottom - offsetY);
                    break;
                case RIGHT:
                    this.setGravity(Gravity.CENTER_VERTICAL);
                    hintViewParentParams.setMargins(right+offsetX,offsetY,0,0);
//                    hintViewParentParams.setMargins(right + offsetX, top + offsetY, -right - offsetX, -top - offsetY);
                    break;
                case LEFT_TOP:
                    this.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                    hintViewParentParams.setMargins(0,0,width-left-offsetX,height-top-offsetY);
//                    hintViewParentParams.setMargins(offsetX - width + left, offsetY - height + top, width - left - offsetX, height - top - offsetY);
                    break;
                case LEFT_BOTTOM:
                    this.setGravity(Gravity.RIGHT);
                    hintViewParentParams.setMargins(0,bottom + offsetY,width-left-offsetX,0);
//                    hintViewParentParams.setMargins(offsetX - width + left, bottom + offsetY, width - left - offsetX, -bottom - offsetY);
                    break;
                case RIGHT_TOP:
                    this.setGravity(Gravity.BOTTOM);
                    hintViewParentParams.setMargins(right+offsetX,0,0,height-top-offsetY);
//                    hintViewParentParams.setMargins(right + offsetX, offsetY - height + top, -right - offsetX, height - top - offsetY);
                    break;
                case RIGHT_BOTTOM:
                    hintViewParentParams.setMargins(right+offsetX,bottom + offsetY,0,0);
//                    hintViewParentParams.setMargins(right + offsetX, bottom + offsetY, -right - offsetX, -top - offsetY);
                    break;
            }
            /*FrameLayout customHintViewParent = new FrameLayout(context);
            customHintViewParent.setLayoutParams(hintViewParentParams);
            customHintViewParent.addView(customGuideView);
            customHintViewParent.setBackgroundColor(context.getResources().getColor(R.color.base_red08d));
            this.addView(customHintViewParent);
            Log.d("=====tt22=","addParentView");*/
            this.addView(customGuideView, hintViewParentParams);
        } else {
            addCustomViewWithFullscreen();
        }
    }

    //绘制前景bitmap
    private Bitmap bitmap;
    //Canvas,绘制bitmap
    private Canvas temp;
    //透明圆形画笔
    private Paint mCirclePaint;
    //背景色画笔
    private Paint mBackgroundPaint;
    //绘图层叠模式
    private PorterDuffXfermode porterDuffXfermode;

    /**
     * 绘制背景
     */
    private void drawBackground(Canvas canvas) {
        // 先绘制bitmap，再将bitmap绘制到屏幕
        bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        temp = new Canvas(bitmap);

        // 背景画笔
        mBackgroundPaint = new Paint();
        if (backgroundColor != 0) {
            mBackgroundPaint.setColor(backgroundColor);
        } else {
            mBackgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.base_guide_shadow));
        }
        mBackgroundPaint.setAlpha(220);

        // 绘制屏幕背景
        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), mBackgroundPaint);
        if (null != targetView) {
            // targetView 的透明圆形画笔
            if (mCirclePaint == null) mCirclePaint = new Paint();
            porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);// 或者CLEAR
            mCirclePaint.setXfermode(porterDuffXfermode);
            mCirclePaint.setAntiAlias(true);
            if (myShape != null) {
                RectF oval = new RectF();
                switch (myShape) {
                    case CIRCULAR://圆形
                        temp.drawCircle(center[0], center[1], radius, mCirclePaint);//绘制圆形
                        break;
                    case ELLIPSE://椭圆
                        //RectF对象
                        oval.left = center[0] - targetView.getWidth() / 2 - rectLeftPadding;                              //左边
                        oval.top = center[1] - targetView.getHeight() / 2 - rectTopPadding;                              //上边
                        oval.right = center[0] + targetView.getWidth() / 2 + rectRightPadding;                             //右边
                        oval.bottom = center[1] + targetView.getHeight() / 2 + rectBottomPadding;                           //下边
                        temp.drawOval(oval, mCirclePaint);                   //绘制椭圆
                        break;
                    case RECTANGULAR://圆角矩形
                        //RectF对象
                        oval.left = center[0] - targetView.getWidth() / 2 - rectLeftPadding;                              //左边
                        oval.top = center[1] - targetView.getHeight() / 2 - rectTopPadding;                              //上边
                        oval.right = center[0] + targetView.getWidth() / 2 + rectRightPadding;                             //右边
                        oval.bottom = center[1] + targetView.getHeight() / 2 + rectBottomPadding;                           //下边
                        temp.drawRoundRect(oval, 10, 10, mCirclePaint);                               //绘制圆角矩形
                        break;
                    default:
                }
            } else {
                temp.drawCircle(center[0], center[1], radius, mCirclePaint);//绘制圆形
            }
        }
        // 绘制到屏幕
        canvas.drawBitmap(bitmap, 0, 0, mBackgroundPaint);
        bitmap.recycle();
        this.setFocusable(true);
    }

    public void setTargetView(View targetView) {
        this.targetView = targetView;
    }

    public void setCustomGuideView(View customGuideView) {
        this.customGuideView = customGuideView;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setShape(MyShape shape) {
        this.myShape = shape;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void setMyBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRadiusPadding(int radiusPadding){
        this.radiusPadding = radiusPadding;
    }

    public void setRectLeftPadding(int radiusPadding){
        this.rectLeftPadding = radiusPadding;
    }

    public void setRectTopPadding(int radiusPadding){
        this.rectTopPadding = radiusPadding;
    }

    public void setRectRightPadding(int radiusPadding){
        this.rectRightPadding = radiusPadding;
    }

    public void setRectBottomPadding(int radiusPadding){
        this.rectBottomPadding = radiusPadding;
    }

    public void setEnterAnim(Animation anim){
        this.enterAnim = anim;
    }

    public void setExitAnim(Animation anim){
        this.exitAnim = anim;
    }

    public void isStatusbarTransparent(boolean isStatusbarTransparent){
        this.isStatusbarTransparent = isStatusbarTransparent;
    }

    public Animation getEnterAnim(){
        return enterAnim;
    }

    public Animation getExitAnim(){
        return exitAnim;
    }

}
