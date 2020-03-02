package com.idengyun.heartretail.model;

import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * 代表一页信息
 *
 * @author aLang
 */
public class PageInfo {
    public int position;
    public Fragment fragment;
    public String tabText;
    @DrawableRes
    public int tabIcon;
    public TabLayout.OnTabSelectedListener onTabSelectedListener;
    public ViewPager.OnPageChangeListener onPageChangeListener;
}