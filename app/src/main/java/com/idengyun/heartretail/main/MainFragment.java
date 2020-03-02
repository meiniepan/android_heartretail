package com.idengyun.heartretail.main;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.MainPagerAdapter;
import com.idengyun.heartretail.model.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 心零售项目主页
 *
 * @author aLang
 */
public final class MainFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reset();

        String[] tabTexts = {"首页", "邀请返利", "我的"};
        ArrayList<PageInfo> pageInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PageInfo pageInfo = new PageInfo();
            pageInfo.position = i;
            pageInfo.fragment = null;
            pageInfo.tabText = tabTexts[i];
            pageInfo.tabIcon = View.NO_ID;
            pageInfo.onTabSelectedListener = null;
            pageInfo.onPageChangeListener = null;
            pageInfos.add(pageInfo);
        }
        MainPagerAdapter mainAdapter = new MainPagerAdapter(getChildFragmentManager(), pageInfos);

        setup(pageInfos, mainAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position, false);

        if (tab.getTag() instanceof PageInfo) {
            PageInfo pageInfo = (PageInfo) tab.getTag();
            Fragment fragment = pageInfo.fragment;
            if (fragment != null && !fragment.getUserVisibleHint()) {
                fragment.setUserVisibleHint(true);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getTag() instanceof PageInfo) {
            PageInfo pageInfo = (PageInfo) tab.getTag();
            Fragment fragment = pageInfo.fragment;
            if (fragment != null && fragment.getUserVisibleHint()) {
                fragment.setUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        TabLayout.Tab tab = tabLayout.getTabAt(i);
        if (tab != null) tab.select();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void findViewById(@NonNull View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
    }

    private void setup(List<PageInfo> items, MainPagerAdapter mainAdapter) {
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);

        for (PageInfo info : items) {
            if (info.onTabSelectedListener != null)
                tabLayout.addOnTabSelectedListener(info.onTabSelectedListener);
            if (info.onPageChangeListener != null)
                viewPager.addOnPageChangeListener(info.onPageChangeListener);
        }

        for (PageInfo pageInfo : items) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setTag(pageInfo);
            tab.setText(pageInfo.tabText);
            tabLayout.addTab(tab, pageInfo.position, false);
        }

        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(mainAdapter);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        if (tab != null) tab.select();
        else viewPager.setCurrentItem(0, false);
    }

    private void reset() {
        if (tabLayout.getTabCount() > 0) tabLayout.removeAllTabs();
        tabLayout.clearOnTabSelectedListeners();

        if (viewPager.getAdapter() != null) viewPager.setAdapter(null);
        viewPager.clearOnPageChangeListeners();
    }
}

