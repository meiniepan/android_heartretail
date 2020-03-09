package com.idengyun.heartretail.bases;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * 基本页封装-父类继承
 *
 * @author aLang
 */
public abstract class PagerParentFragment extends PagerChildFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int pagerCount;
    private ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
    private ArrayList<PagerChildFragment> fragments = new ArrayList<>();
    private ArrayList<TabLayout.OnTabSelectedListener> onTabSelectedListeners = new ArrayList<>();
    private ArrayList<ViewPager.OnPageChangeListener> onPageChangeListeners = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();

        viewPager.setCurrentItem(position, false);

        Fragment fragment = fragments.get(position);
        if (!fragment.getUserVisibleHint()) {
            fragment.setUserVisibleHint(true);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int position = tab.getPosition();

        Fragment fragment = fragments.get(position);
        if (fragment.getUserVisibleHint()) {
            fragment.setUserVisibleHint(false);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if (tab != null) tab.select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @NonNull
    public abstract TabLayout getTabLayout();

    @NonNull
    public abstract ViewPager getViewPager();

    /* 获取总页数 */
    public abstract int getPagerCount();

    /**
     * @param position Position within this adapter
     * @return Return the Fragment associated with a specified position.
     */
    @NonNull
    public abstract TabLayout.Tab getTab(int position);

    /**
     * @param position Position within this adapter
     * @return Return the Fragment associated with a specified position.
     */
    @NonNull
    public abstract PagerChildFragment getFragment(int position);

    private void init() {
        tabLayout = getTabLayout();
        viewPager = getViewPager();

        resetPage();

        /* 添加监听器 */
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);

        /* 获取总页数 */
        pagerCount = getPagerCount();

        /* 获取Tab Fragment 监听器列表 */
        for (int i = 0; i < pagerCount; i++) {
            TabLayout.Tab tab = getTab(i);
            PagerChildFragment fragment = getFragment(i);
            TabLayout.OnTabSelectedListener onTabSelectedListener = fragment;
            ViewPager.OnPageChangeListener onPageChangeListener = fragment;

            tabs.add(tab);
            fragments.add(fragment);
            onTabSelectedListeners.add(onTabSelectedListener);
            onPageChangeListeners.add(onPageChangeListener);

            tabLayout.addTab(tab, i, false);
            if (onTabSelectedListener != null) {
                tabLayout.addOnTabSelectedListener(onTabSelectedListener);
            }
            if (onPageChangeListener != null) {
                viewPager.addOnPageChangeListener(onPageChangeListener);
            }
        }

        /* 设置适配器 */
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return pagerCount;
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };
        viewPager.setAdapter(adapter);
    }

    private void resetPage() {
        tabLayout.removeAllTabs();
        tabLayout.clearOnTabSelectedListeners();
        viewPager.setAdapter(null);
        viewPager.clearOnPageChangeListeners();
    }
}
