package com.dengyun.baselibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dengyun.baselibrary.R;

/**
 * @Title 可以设置drawableLeft/Top/Right/Bottom大小的TextView
 * @Desc: 可以同时设置上下左右不同的尺寸
 * @Author: zhoubo
 * @CreateDate: 2019-10-30 10:17
 */
public class TextImageView extends android.support.v7.widget.AppCompatTextView {
    private int mLeftWidth;
    private int mLeftHeight;
    private int mTopWidth;
    private int mTopHeight;
    private int mRightWidth;
    private int mRightHeight;
    private int mBottomWidth;
    private int mBottomHeight;

    public TextImageView(Context context) {
        this(context,null,0);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextImageView);
        mLeftWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableLeftWidth, 0);
        mLeftHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableLeftHeight, 0);
        mTopWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableTopWidth, 0);
        mTopHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableTopHeight, 0);
        mRightWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableRightWidth, 0);
        mRightHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableRightHeight, 0);
        mBottomWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableBottomWidth, 0);
        mBottomHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableBottomHeight, 0);
        typedArray.recycle();
        setDrawablesSize();
    }

    private void setDrawablesSize() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        for (int i = 0; i < compoundDrawables.length; i++) {
            switch (i) {
                case 0:
                    setDrawableBounds(compoundDrawables[0], mLeftWidth, mLeftHeight);
                    break;
                case 1:
                    setDrawableBounds(compoundDrawables[1], mTopWidth, mTopHeight);
                    break;
                case 2:
                    setDrawableBounds(compoundDrawables[2], mRightWidth, mRightHeight);
                    break;
                case 3:
                    setDrawableBounds(compoundDrawables[3], mBottomWidth, mBottomHeight);
                    break;
                default:
                    break;
            }
        }
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }

    //设置上下左右的四个图片的宽高
    private void setDrawableBounds(Drawable drawable, int width, int height) {
        if (drawable != null) {
            drawable.setBounds(0, 0, width, height);
            Rect bounds = drawable.getBounds();
            //高宽只给一个值时，自适应
            if (bounds.right != 0 || bounds.bottom != 0) {
                //计算图片的高/宽比例
                double scale = ((double) drawable.getIntrinsicHeight()) / ((double) drawable.getIntrinsicWidth());
                if (bounds.right == 0) {
                    bounds.right = (int) (bounds.bottom / scale);
                    drawable.setBounds(bounds);
                }
                if (bounds.bottom == 0) {
                    bounds.bottom = (int) (bounds.right * scale);
                    drawable.setBounds(bounds);
                }
            }
        }
    }

    /**
     * 添加左侧图片，宽高为xml中设置的宽高，没有设置默认图片高度
     * @param drawable 设置的图片
     */
    public void drawableLeft(Drawable drawable){
        drawableLeft(drawable,mLeftWidth,mLeftHeight);
    }

    /**
     * 添加左侧图片，指定宽高
     * @param drawable 设置的图片
     * @param width    设置图片宽度
     * @param height    设置图片高度
     */
    public void drawableLeft(Drawable drawable,int width,int height){
        setDrawableBounds(drawable,width,height);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(drawable,compoundDrawables[1],compoundDrawables[2],compoundDrawables[3]);
    }

    /**
     * 添加顶部图片，宽高为xml中设置的宽高，没有设置默认图片高度
     * @param drawable 设置的图片
     */
    public void drawableTop(Drawable drawable){
        drawableTop(drawable,mTopWidth,mTopHeight);
    }

    /**
     * 添加顶部图片，指定宽高
     * @param drawable 设置的图片
     * @param width    设置图片宽度
     * @param height    设置图片高度
     */
    public void drawableTop(Drawable drawable,int width,int height){
        setDrawableBounds(drawable,width,height);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0],drawable,compoundDrawables[2],compoundDrawables[3]);
    }

    /**
     * 添加右侧图片，宽高为xml中设置的宽高，没有设置默认图片高度
     * @param drawable 设置的图片
     */
    public void drawableRight(Drawable drawable){
        drawableRight(drawable,mRightWidth,mRightHeight);
    }

    /**
     * 添加右侧图片，指定宽高
     * @param drawable 设置的图片
     * @param width    设置图片宽度
     * @param height    设置图片高度
     */
    public void drawableRight(Drawable drawable,int width,int height){
        setDrawableBounds(drawable,width,height);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0],compoundDrawables[1],drawable,compoundDrawables[3]);
    }

    /**
     * 添加底部图片，宽高为xml中设置的宽高，没有设置默认图片高度
     * @param drawable 设置的图片
     */
    public void drawableBottom(Drawable drawable){
        drawableBottom(drawable,mBottomWidth,mBottomHeight);
    }

    /**
     * 添加底部图片，指定宽高
     * @param drawable 设置的图片
     * @param width    设置图片宽度
     * @param height    设置图片高度
     */
    public void drawableBottom(Drawable drawable,int width,int height){
        setDrawableBounds(drawable,width,height);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0],compoundDrawables[1],compoundDrawables[2],drawable);
    }
}
