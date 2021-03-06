package com.idengyun.heartretail.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.idengyun.heartretail.R;
import com.idengyun.heartretail.bases.PagerChildFragment;
import com.idengyun.heartretail.bases.PagerParentFragment;

/**
 * 心零售项目主页
 *
 * @author aLang
 */
//public final class MainFragment extends PagerParentFragment {
//    private TabLayout tab_layout;
//    private ViewPager view_pager;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_main;
//    }
//
//    @Override
//    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        findViewById(view);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        updateUI();
//    }
//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        super.onTabSelected(tab);
//        View view = tab.getCustomView();
//        assert view != null;
//        TextView tv_msg_tab_text = view.findViewById(R.id.tv_msg_tab_text);
//        View iv_msg_tab_indicator_line = view.findViewById(R.id.iv_msg_tab_indicator_line);
//        View iv_msg_tab_indicator_dot = view.findViewById(R.id.iv_msg_tab_indicator_dot);
//        tv_msg_tab_text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
//        iv_msg_tab_indicator_line.setVisibility(View.VISIBLE);
//        iv_msg_tab_indicator_dot.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//        super.onTabUnselected(tab);
//        View view = tab.getCustomView();
//        assert view != null;
//        TextView tv_msg_tab_text = view.findViewById(R.id.tv_msg_tab_text);
//        View iv_msg_tab_indicator_line = view.findViewById(R.id.iv_msg_tab_indicator_line);
//        View iv_msg_tab_indicator_dot = view.findViewById(R.id.iv_msg_tab_indicator_dot);
//        tv_msg_tab_text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f);
//        iv_msg_tab_indicator_line.setVisibility(View.INVISIBLE);
//        iv_msg_tab_indicator_dot.setVisibility(View.INVISIBLE);
//    }
//
//    @NonNull
//    @Override
//    public TabLayout getTabLayout() {
//        return tab_layout;
//    }
//
//    @NonNull
//    @Override
//    public ViewPager getViewPager() {
//        return view_pager;
//    }
//
//    @Override
//    public int getPagerCount() {
//        return 3;
//    }
//
//    @NonNull
//    @Override
//    public TabLayout.Tab getTab(int position) {
//        TabLayout.Tab tab = tab_layout.newTab();
//        tab.setCustomView(R.layout.notice_tab);
//        switch (position) {
//            case 1:
//                updateCustomView(tab.getCustomView(), "邀请返利");
//                break;
//            case 2:
//                updateCustomView(tab.getCustomView(), "我的");
//                break;
//            default:
//                updateCustomView(tab.getCustomView(), "首页");
//                break;
//        }
//        return tab;
//    }
//
//    @NonNull
//    @Override
//    public PagerChildFragment getFragment(int position) {
//        switch (position) {
//            case 1:
//                return new RedPacketFragment();
//            case 2:
//                return new MyFragment();
//            default:
//                return new HomeFragment();
//        }
//    }
//
//    private void updateUI() {
//        view_pager.setOffscreenPageLimit(2);
//
//        /* 默认选中第0个位置 */
//        view_pager.setCurrentItem(0, false);
//        TabLayout.Tab tab = tab_layout.getTabAt(0);
//        if (tab != null) tab.select();
//    }
//
//    private void updateCustomView(View customView, CharSequence text) {
//        if (customView != null) {
//            TextView tv_msg_tab_text = customView.findViewById(R.id.tv_msg_tab_text);
//            View iv_msg_tab_indicator_line = customView.findViewById(R.id.iv_msg_tab_indicator_line);
//            View iv_msg_tab_indicator_dot = customView.findViewById(R.id.iv_msg_tab_indicator_dot);
//            tv_msg_tab_text.setText(text);
//            iv_msg_tab_indicator_line.setVisibility(View.INVISIBLE);
//            iv_msg_tab_indicator_dot.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    private void findViewById(@NonNull View view) {
//        tab_layout = view.findViewById(R.id.tab_layout);
//        view_pager = view.findViewById(R.id.view_pager);
//    }
//
//}

