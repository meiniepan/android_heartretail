package com.dengyun.baselibrary.widgets.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.utils.ImageUtils;
import com.dengyun.baselibrary.utils.ScreenUtil;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ViewUtil;

/**
 * @titile  自定义toolbar，标题居中，左侧按钮返回，没有右侧按钮
 * @desc Created by seven on 2018/2/26.
 */

public class BaseToolBar extends Toolbar {
    //标题
    private TextView mToolbarTitle;
    //返回按钮
    private TextView mToolbarBack;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseToolBar(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        setContentInsetsRelative(0, 10);
        if(null==getBackground()){
            setDefultBgWithNoXMLSet();
        }
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.basetool_height));

        //添加标题view
        addTitleView(title_text_color,title_text_sizePx);

        /*左侧按钮没有隐藏*/
        if (!is_hide_left) {
//            setNavigationIcon(left_icon);
            addBackView(title_text_color,left_icon);
        }

        /*设置标题*/
        if (!TextUtils.isEmpty(title)) {
            mToolbarTitle.setText(title);
        }
        setElevation(25);
    }

    /**
     * 添加左侧返回view
     * @param title_text_color 文字颜色
     * @param left_icon 左侧返回按钮图片id
     */
    private void addBackView(int title_text_color,int left_icon) {
        mToolbarBack = new TextView(getContext());
        mToolbarBack.setTextColor(title_text_color);
        mToolbarBack.setTextSize(16);
        LayoutParams layoutParams1 =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.START);
        mToolbarBack.setLayoutParams(layoutParams1);
        mToolbarBack.setText("返回");
        ImageUtils.drawableLeft(mToolbarBack,left_icon,SizeUtils.dp2px(8));
        ViewUtil.setMargins(mToolbarBack,SizeUtils.dp2px(15),0,0,0);
        addView(mToolbarBack);
        //设置左侧按钮的点击事件
        mToolbarBack.setOnClickListener(v -> {
            if(getContext() instanceof Activity){
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        });
    }

    /**
     * 添加标题view
     * @param title_text_color  文字颜色
     * @param title_text_sizePx 文字大小
     */
    private void addTitleView(int title_text_color,int title_text_sizePx) {
        /*设置居中的title*/
        mToolbarTitle = new TextView(getContext());
        mToolbarTitle.setTextColor(title_text_color);
        mToolbarTitle.setTextSize(SizeUtils.px2sp(title_text_sizePx));
        mToolbarTitle.setSingleLine();
        mToolbarTitle.setMaxWidth(ScreenUtil.getScreenWidth()-SizeUtils.dp2px(80)*2);
        mToolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER);
        mToolbarTitle.setLayoutParams(layoutParams);
        addView(mToolbarTitle);
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
