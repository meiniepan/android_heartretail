package com.idengyun.heartretail.bases;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dengyun.baselibrary.base.fragment.BaseFragment;

/**
 * 基本页封装-子类继承
 *
 * @author aLang
 */
public abstract class PagerChildFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
