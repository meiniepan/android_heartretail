package com.idengyun.heartretail;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 心零售项目主页
 *
 * @author aLang
 */
public final class MainFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        setup(tabLayout, viewPager);
    }

    private void setup(TabLayout tabLayout, ViewPager viewPager) {
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
        new MainCallback(tabLayout, viewPager).setup(pageInfos, mainAdapter);
    }
}

/**
 * 适配器
 *
 * @author aLang
 */
final class MainPagerAdapter extends FragmentPagerAdapter {
    private List<PageInfo> pageInfos;

    MainPagerAdapter(FragmentManager fm, List<PageInfo> pageInfos) {
        super(fm);
        this.pageInfos = pageInfos;
    }

    @Override
    public Fragment getItem(int i) {
        PageInfo info = pageInfos.get(i);
        if (info.fragment == null) {
            info.fragment = createFragment(i);
        }
        return info.fragment;
    }

    @Override
    public int getCount() {
        return pageInfos.size();
    }

    private Fragment createFragment(int position) {
        final Fragment fragment;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new RedPacketFragment();
                break;
            case 2:
                fragment = new MyFragment();
                break;
            default:
                fragment = new Fragment();
        }
        fragment.setUserVisibleHint(false);
        return fragment;
    }
}

/**
 * 回调
 *
 * @author aLang
 */
final class MainCallback implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    MainCallback(TabLayout tabLayout, ViewPager viewPager) {
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        reset();
    }

    void setup(List<PageInfo> items, MainPagerAdapter mainAdapter) {
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
}

/**
 * 代表一页信息
 *
 * @author aLang
 */
final class PageInfo {
    int position;
    Fragment fragment;
    String tabText;
    @DrawableRes
    int tabIcon;
    TabLayout.OnTabSelectedListener onTabSelectedListener;
    ViewPager.OnPageChangeListener onPageChangeListener;
}