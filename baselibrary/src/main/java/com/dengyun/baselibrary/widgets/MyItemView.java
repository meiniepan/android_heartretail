package com.dengyun.baselibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.utils.SizeUtils;


/**
 * Created by seven on 2017/5/9.
 */

public class MyItemView extends LinearLayout {

    private TextView tvRight;

    public MyItemView(Context context) {
        this(context, null,0);
    }

    public MyItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.base_view_myitem,this);
        RelativeLayout rlViewitem = (RelativeLayout) view.findViewById(R.id.rl_viewitem);//条目布局
        TextView tvLeft = (TextView) view.findViewById(R.id.tv_viewitem_left);//左文字
        //右文字
        tvRight = (TextView) view.findViewById(R.id.tv_viewitem_right);
        TextView tvLineTop = (TextView) view.findViewById(R.id.tv_item_linetop);
        TextView tvLinebottom = (TextView) view.findViewById(R.id.tv_item_linebottom);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyItemView);
        String left_text = ta.getString(R.styleable.MyItemView_left_text);
        String right_text = ta.getString(R.styleable.MyItemView_right_text);
        float left_textSizePX = ta.getDimensionPixelSize(R.styleable.MyItemView_left_textSize, 0);
        float right_textSizePX = ta.getDimensionPixelSize(R.styleable.MyItemView_right_textSize, 0);
        float all_textSizePX = ta.getDimensionPixelSize(R.styleable.MyItemView_all_textSize, 0);
        int left_textColor = ta.getColor(R.styleable.MyItemView_left_textColor, 0);
        int right_textColor = ta.getColor(R.styleable.MyItemView_right_textColor, 0);
        int left_pic = ta.getResourceId(R.styleable.MyItemView_left_pic,0);
        int right_pic = ta.getResourceId(R.styleable.MyItemView_right_pic,0);
        int itemHeightPX = ta.getDimensionPixelSize(R.styleable.MyItemView_item_height,0);
        int item_background_color = ta.getColor(R.styleable.MyItemView_item_backgroundcolor, 0);
        int item_background = ta.getResourceId(R.styleable.MyItemView_item_background,0);
        boolean show_topline = ta.getBoolean(R.styleable.MyItemView_show_topline,false);
        boolean show_bottomline = ta.getBoolean(R.styleable.MyItemView_show_bottomline,false);
        int line_color = ta.getColor(R.styleable.MyItemView_line_color, 0);
        int left_marginPX = ta.getDimensionPixelSize(R.styleable.MyItemView_left_margin, 0);
        int right_marginPX = ta.getDimensionPixelSize(R.styleable.MyItemView_right_margin, 0);
        ta.recycle();

        if(!TextUtils.isEmpty(left_text)){
            tvLeft.setText(left_text);
        }
        if(!TextUtils.isEmpty(right_text)){
            tvRight.setText(right_text);
        }

        if(all_textSizePX!=0){

            tvLeft.setTextSize(SizeUtils.px2dp(all_textSizePX));
            tvRight.setTextSize(SizeUtils.px2dp(all_textSizePX));
        }else {
            if(left_textSizePX!=0){
                tvLeft.setTextSize(SizeUtils.px2dp(left_textSizePX));
            }
            if(right_textSizePX!=0){
                tvRight.setTextSize(SizeUtils.px2dp(right_textSizePX));
            }
        }

        if(left_textColor!=0){
            tvLeft.setTextColor(left_textColor);
        }
        if(right_textColor!=0){
            tvRight.setTextColor(right_textColor);
        }

        if(left_pic!=0){
            Drawable topDrawable = getResources().getDrawable(left_pic);
            //Drawable topDrawable = getResources().getDrawable(R.mipmap.bt_menu_find_choose);
            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
            tvLeft.setCompoundDrawablePadding(SizeUtils.dp2px(12));
            tvLeft.setCompoundDrawables(topDrawable, null, null, null);
        }

        if(right_pic!=0){
            Drawable topDrawable = getResources().getDrawable(right_pic);
            //Drawable topDrawable = getResources().getDrawable(R.mipmap.bt_menu_find_choose);
            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
            tvRight.setCompoundDrawablePadding(SizeUtils.dp2px(12));
            tvRight.setCompoundDrawables(null, null, topDrawable, null);
        }
        if(itemHeightPX!=0){
            LayoutParams layoutParams = (LayoutParams) rlViewitem.getLayoutParams();
            layoutParams.height = itemHeightPX;
            rlViewitem.setLayoutParams(layoutParams);
        }
        if(item_background_color!=0){
            rlViewitem.setBackgroundColor(item_background_color);
        }
        if(item_background!=0){
            rlViewitem.setBackgroundResource(item_background);
        }

        if(show_topline){
            tvLineTop.setVisibility(VISIBLE);
        }
        if(show_bottomline){
            tvLinebottom.setVisibility(VISIBLE);
        }
        if(line_color!=0){
            tvLineTop.setTextColor(line_color);
            tvLinebottom.setTextColor(line_color);
        }

        if(left_marginPX!=0){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvLeft.getLayoutParams();
            layoutParams.setMargins(left_marginPX,0,0,0);
            tvLeft.setLayoutParams(layoutParams);
        }

        if(right_marginPX!=0){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvRight.getLayoutParams();
            layoutParams.setMargins(0,0,right_marginPX,0);
            tvRight.setLayoutParams(layoutParams);
        }

    }

    public void setRightText(String rightStr){
        tvRight.setText(rightStr);
    }
    public void setRightCorlor(int color){
        tvRight.setTextColor(color);
    }
    public void setRightTextSize(float textSize){
        tvRight.setTextSize(textSize);
    }
}
