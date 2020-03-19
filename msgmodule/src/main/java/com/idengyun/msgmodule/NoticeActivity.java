package com.idengyun.msgmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;

/**
 * 消息模块-主页
 *
 * @author aLang
 */
public final class NoticeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, NoticeActivity.class);
        // starter.putExtra();
        context.startActivity(starter);
    }

    private NoticeFragment fragment0;
    private NoticeFragment fragment1;
    private NoticeFragment fragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position == 0) {
            showFragment0();
        } else if (position == 1) {
            showFragment1();
        } else if (position == 2) {
            showFragment2();
        }
        View view = tab.getCustomView();
        assert view != null;
        TextView tv_msg_tab_text = view.findViewById(R.id.tv_msg_tab_text);
        View iv_msg_tab_indicator_line = view.findViewById(R.id.iv_msg_tab_indicator_line);
        View iv_msg_tab_indicator_dot = view.findViewById(R.id.iv_msg_tab_indicator_dot);
        tv_msg_tab_text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
        iv_msg_tab_indicator_line.setVisibility(View.VISIBLE);
        iv_msg_tab_indicator_dot.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        assert view != null;
        TextView tv_msg_tab_text = view.findViewById(R.id.tv_msg_tab_text);
        View iv_msg_tab_indicator_line = view.findViewById(R.id.iv_msg_tab_indicator_line);
        View iv_msg_tab_indicator_dot = view.findViewById(R.id.iv_msg_tab_indicator_dot);
        tv_msg_tab_text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f);
        iv_msg_tab_indicator_line.setVisibility(View.INVISIBLE);
        iv_msg_tab_indicator_dot.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void init() {
        setContentView(R.layout.activity_notice);

        fragment0 = new NoticeFragment();
        fragment1 = new NoticeFragment();
        fragment2 = new NoticeFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, fragment0, "促销优惠")
                .add(R.id.content, fragment1, "账户通知")
                .add(R.id.content, fragment2, "服务通知")
                .hide(fragment0)
                .hide(fragment1)
                .hide(fragment2)
                .commit();

        TabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.clearOnTabSelectedListeners();
        tab_layout.removeAllTabs();
        String[] tabs = new String[]{"促销优惠", "账户通知", "服务通知"};
        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = tab_layout.newTab();
            tab.setCustomView(R.layout.notice_tab);
            View customView = tab.getCustomView();
            if (customView != null) {
                TextView tv_msg_tab_text = customView.findViewById(R.id.tv_msg_tab_text);
                View iv_msg_tab_indicator_line = customView.findViewById(R.id.iv_msg_tab_indicator_line);
                View iv_msg_tab_indicator_dot = customView.findViewById(R.id.iv_msg_tab_indicator_dot);
                tv_msg_tab_text.setText(tabs[i]);
                iv_msg_tab_indicator_line.setVisibility(View.INVISIBLE);
                iv_msg_tab_indicator_dot.setVisibility(View.INVISIBLE);
            }
            tab_layout.addTab(tab, i, false);
        }
        tab_layout.addOnTabSelectedListener(this);
        TabLayout.Tab tab = tab_layout.getTabAt(0);
        if (tab != null) tab.select();
    }

    private void showFragment2() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment2)
                .hide(fragment0)
                .hide(fragment1)
                .commit();

        fragment0.setUserVisibleHint(false);
        fragment1.setUserVisibleHint(false);
        fragment2.setUserVisibleHint(true);
    }

    private void showFragment1() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment1)
                .hide(fragment0)
                .hide(fragment2)
                .commit();

        fragment0.setUserVisibleHint(false);
        fragment1.setUserVisibleHint(true);
        fragment2.setUserVisibleHint(false);
    }

    private void showFragment0() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment0)
                .hide(fragment1)
                .hide(fragment2)
                .commit();

        fragment0.setUserVisibleHint(true);
        fragment1.setUserVisibleHint(false);
        fragment2.setUserVisibleHint(false);
    }
}
