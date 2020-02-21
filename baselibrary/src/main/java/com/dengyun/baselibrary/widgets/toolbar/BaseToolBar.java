package com.dengyun.baselibrary.widgets.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.utils.SizeUtils;

/**
 * @titile  自定义toolbar，标题居中，左侧按钮返回，没有右侧按钮
 * @desc Created by seven on 2018/2/26.
 */

public class BaseToolBar extends Toolbar {
    //标题
    private TextView mToolbarTitle;

    public BaseToolBar(Context context) {
        this(context, null);
    }

    public BaseToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseToolBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPopupTheme(R.style.Theme_BaseToolBar_Menu);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseToolBar);
        String title = ta.getString(R.styleable.BaseToolBar_toolbar_title);
        int left_icon = ta.getResourceId(R.styleable.BaseToolBar_left_icon, R.drawable.base_back_toolbar);
        boolean is_hide_left = ta.getBoolean(R.styleable.BaseToolBar_is_hide_left, false);
        int title_text_sizePx = ta.getDimensionPixelSize(R.styleable.BaseToolBar_title_text_size, (int)getResources().getDimension(R.dimen.basetool_textsize));
        int title_text_color = ta.getColor(R.styleable.BaseToolBar_title_text_color,getResources().getColor(R.color.basetoobar_textcolor));
        ta.recycle();
        setContentInsetsRelative(10, 10);
        if(null==getBackground()){
            setDefultBgWithNoXMLSet();
        }
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.basetool_height));

        /*设置居中的title*/
        mToolbarTitle = new TextView(getContext());
        mToolbarTitle.setTextColor(title_text_color);
        mToolbarTitle.setTextSize(SizeUtils.px2sp(title_text_sizePx));
        mToolbarTitle.setSingleLine();
        mToolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER);
        mToolbarTitle.setLayoutParams(layoutParams);
        addView(mToolbarTitle);

        /*左侧按钮没有隐藏*/
        if (!is_hide_left) {
            setNavigationIcon(left_icon);
        }

        /*设置标题*/
        if (!TextUtils.isEmpty(title)) {
            mToolbarTitle.setText(title);
        }


        //设置左侧按钮的点击事件
        setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getContext() instanceof Activity){
                    Activity activity = (Activity) getContext();
                    activity.finish();
                }
            }
        });
    }

    /**
     * 设置左侧按钮icon
     * @param icon
     */
    public void setLeftButtonIcon(int icon) {
        setNavigationIcon(icon);
    }

    //设置左侧按钮监听事件
    public void setLeftButtonOnClickLinster(OnClickListener linster) {
        setNavigationOnClickListener(linster);
    }

    @Override
    public void setTitle(CharSequence title) {
        mToolbarTitle.setText(title);
    }

    @Override
    public void setTitleTextColor(int color) {
        mToolbarTitle.setTextColor(color);
    }

    public void setTitleTextSize(float textSize){
        mToolbarTitle.setTextSize(textSize);
    }

    public TextView getTitleTextView(){
        return mToolbarTitle;
    }

    /**
     * 如果在xml中没有设置bg，添加默认bg
     */
    protected void setDefultBgWithNoXMLSet(){
        setBackgroundColor(getResources().getColor(R.color.basetoobar_bgcolor));
    }
}
