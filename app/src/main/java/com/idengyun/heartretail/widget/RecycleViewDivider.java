package com.idengyun.heartretail.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @author Burning
 * @description:
 * @date :2019/5/29 0029 10:06
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private int space;
    private int horizontalSpace;
    private int verticalSpace;
    private int color = -1;
    private Drawable mDivider;
    private Paint mPaint;
    private int type;

    public int getColor() {
        return color;
    }

    public void setColor(@ColorRes int color) {
        this.color = color;
    }

    public RecycleViewDivider(Context context) {
        this.space = space;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(space);
    }

    public RecycleViewDivider(Context context, int space) {
        this.space = space;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(space);
    }

    public RecycleViewDivider(int space, int color) {
        this.space = space;
        this.color = color;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(space);
    }

    public RecycleViewDivider(int space, int color, int type) {
        this.space = space;
        this.color = color;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(space);
        this.type = type;
    }


    public RecycleViewDivider(int space, Drawable mDivider) {
        this.space = space;
        this.mDivider = mDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() != null) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager && !(parent.getLayoutManager() instanceof GridLayoutManager)) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    outRect.set(space, 0, space, 0);
                } else {
                    outRect.set(0, space, 0, space);
                }
            } else {
                outRect.set(space, space, space, space);
            }
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() != null) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager && !(parent.getLayoutManager() instanceof GridLayoutManager)) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    drawHorizontal(c, parent);
                } else {
                    drawVertical(c, parent);
                }
            } else {
                if (type == 0) {
                    drawGrideview(c, parent);
                } else {
                    drawGrideview1(c, parent);
                }
            }
        }
    }

    //绘制横向 item 分割线

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize-1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + space;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize-1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + space;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

        }
    }

    //绘制grideview item 分割线 不是填充满的
    private void drawGrideview(Canvas canvas, RecyclerView parent) {
        int hSapce = horizontalSpace == 0 ? space : horizontalSpace;
        int vSpace = verticalSpace == 0 ? space : verticalSpace;
        GridLayoutManager linearLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int childSize = parent.getChildCount();
        int other = parent.getChildCount() / linearLayoutManager.getSpanCount() - 1;
        if (other < 1) {
            other = 1;
        }
        other = other * linearLayoutManager.getSpanCount();
        if (parent.getChildCount() < linearLayoutManager.getSpanCount()) {
            other = parent.getChildCount();
        }
        int top, bottom, left, right, spancount;
        spancount = linearLayoutManager.getSpanCount() - 1;
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (i < other) {
                top = child.getBottom() + layoutParams.bottomMargin;
                bottom = top + vSpace;
                left = (layoutParams.leftMargin + hSapce) * (i + 1);
                right = child.getMeasuredWidth() * (i + 1) + left + hSapce * i;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
            if (i != spancount) {
                top = (layoutParams.topMargin + vSpace) * (i / linearLayoutManager.getSpanCount() + 1);
                bottom = (child.getMeasuredHeight() + vSpace) * (i / linearLayoutManager.getSpanCount() + 1) + vSpace;
                left = child.getRight() + layoutParams.rightMargin;
                right = left + hSapce;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            } else {
                spancount += 4;
            }
        }
    }

    /***/
    private void drawGrideview1(Canvas canvas, RecyclerView parent) {
        int hSapce = horizontalSpace == 0 ? space : horizontalSpace;
        int vSpace = verticalSpace == 0 ? space : verticalSpace;
        GridLayoutManager linearLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int childSize = parent.getChildCount();
        int top, bottom, left, right, spancount;
        spancount = linearLayoutManager.getSpanCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            //画横线
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + layoutParams.bottomMargin;
            bottom = top + vSpace;
            left = layoutParams.leftMargin + child.getPaddingLeft() + hSapce;
            right = child.getMeasuredWidth() * (i + 1) + left + hSapce * i;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            //画竖线
            top = (layoutParams.topMargin + vSpace) * (i / linearLayoutManager.getSpanCount() + 1);
            bottom = (child.getMeasuredHeight() + vSpace) * (i / linearLayoutManager.getSpanCount() + 1) + vSpace;
            left = child.getRight() + layoutParams.rightMargin;
            right = left + hSapce;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

            //画上缺失的线框
            if (i < spancount) {
                top = child.getTop() + layoutParams.topMargin;
                bottom = top + vSpace;
                left = (layoutParams.leftMargin + hSapce) * (i + 1);
                right = child.getMeasuredWidth() * (i + 1) + left + hSapce * i;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
            if (i % spancount == 0) {
                top = (layoutParams.topMargin + vSpace) * (i / linearLayoutManager.getSpanCount() + 1);
                bottom = (child.getMeasuredHeight() + vSpace) * (i / linearLayoutManager.getSpanCount() + 1) + vSpace;
                left = child.getLeft() + layoutParams.leftMargin;
                right = left + hSapce;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }

    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }
}
