package com.idengyun.msgmodule;

import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.msgmodule.beans.NoticeCountBean;
import com.idengyun.msgmodule.beans.NoticeStatusBean;
import com.idengyun.msgmodule.viewmodel.NoticeViewModel;

import java.util.List;

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

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            requestNoticeCount();
            requestNoticeUpdateStatus();
        }
    };

    private NoticeFragment fragment0;
    private NoticeFragment fragment1;
    private NoticeFragment fragment2;

    private TabLayout tab_layout;
    private SparseArray<TabHolder> noticeCountArray = new SparseArray<>();
    private NoticeViewModel noticeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(receiver, new IntentFilter());
        setContentView(R.layout.activity_notice);
        init();
        observe();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        TabHolder holder = noticeCountArray.get(position);
        holder.onTabSelected();
        showFragment(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        TabHolder holder = noticeCountArray.get(position);
        holder.onTabUnselected();
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void observe() {
        if (noticeViewModel == null) {
            noticeViewModel = NoticeViewModel.getInstance(this);
            noticeViewModel.getNoticeCount().observe(this, new Observer<NoticeCountBean>() {
                @Override
                public void onChanged(@Nullable NoticeCountBean noticeCountBean) {
                    updateNoticeCount(noticeCountBean);
                }
            });
            noticeViewModel.getNoticeStatus().observe(this, new Observer<NoticeStatusBean>() {
                @Override
                public void onChanged(@Nullable NoticeStatusBean noticeStatusBean) {

                }
            });
        }
    }

    @MainThread
    private void updateNoticeCount(@Nullable NoticeCountBean noticeCountBean) {
        if (noticeCountBean == null) return;
        List<NoticeCountBean.Data> dataList = noticeCountBean.data;
        for (NoticeCountBean.Data data : dataList) {
            TabHolder holder = noticeCountArray.get(data.notifyGroup);
            if (holder != null) holder.updateNoticeCount(data.counts);
        }
    }

    private void requestNoticeUpdateStatus() {
        if (noticeViewModel != null) noticeViewModel.requestNoticeUpdateStatus(this, -1, 1);
    }

    private void requestNoticeCount() {
        if (noticeViewModel != null) noticeViewModel.requestNoticeCount(this);
    }

    private void init() {
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

        tab_layout = findViewById(R.id.tab_layout);
        tab_layout.clearOnTabSelectedListeners();
        tab_layout.removeAllTabs();
        String[] tabs = new String[]{"促销优惠", "账户通知", "服务通知"};
        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = tab_layout.newTab();
            TabHolder holder = new TabHolder(tab, tabs[i]);
            noticeCountArray.append(i, holder);
            tab_layout.addTab(tab, i, false);
        }
        tab_layout.addOnTabSelectedListener(this);
        TabLayout.Tab tab = tab_layout.getTabAt(0);
        if (tab != null) tab.select();
    }

    private void showFragment(int position) {
        if (position == 0) {
            showFragment0();
        } else if (position == 1) {
            showFragment1();
        } else if (position == 2) {
            showFragment2();
        }
    }

    private void showFragment2() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment2)
                .hide(fragment0)
                .hide(fragment1)
                .commit();

        /*fragment0.setUserVisibleHint(false);
        fragment1.setUserVisibleHint(false);
        fragment2.setUserVisibleHint(true);*/
    }

    private void showFragment1() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment1)
                .hide(fragment0)
                .hide(fragment2)
                .commit();

        /*fragment0.setUserVisibleHint(false);
        fragment1.setUserVisibleHint(true);
        fragment2.setUserVisibleHint(false);*/
    }

    private void showFragment0() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment0)
                .hide(fragment1)
                .hide(fragment2)
                .commit();

        /*fragment0.setUserVisibleHint(true);
        fragment1.setUserVisibleHint(false);
        fragment2.setUserVisibleHint(false);*/
    }

    private final static class TabHolder {

        private final TabLayout.Tab tab;
        private final View customView;
        private final TextView tv_msg_tab_text;
        private final View iv_msg_tab_indicator_line;
        private final View iv_msg_tab_indicator_dot;
        private final TextView tv_notice_count;

        public TabHolder(@NonNull TabLayout.Tab tab, String text) {
            this.tab = tab;
            tab.setCustomView(R.layout.notice_tab);
            customView = tab.getCustomView();
            assert customView != null;
            tv_msg_tab_text = customView.findViewById(R.id.tv_msg_tab_text);
            iv_msg_tab_indicator_line = customView.findViewById(R.id.iv_msg_tab_indicator_line);
            iv_msg_tab_indicator_dot = customView.findViewById(R.id.iv_msg_tab_indicator_dot);
            tv_notice_count = customView.findViewById(R.id.tv_notice_count);

            tv_msg_tab_text.setText(text);
            iv_msg_tab_indicator_line.setVisibility(View.INVISIBLE);
            iv_msg_tab_indicator_dot.setVisibility(View.INVISIBLE);
        }

        public void onTabSelected() {
            tv_msg_tab_text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
            iv_msg_tab_indicator_line.setVisibility(View.VISIBLE);
            iv_msg_tab_indicator_dot.setVisibility(View.VISIBLE);
        }

        public void onTabUnselected() {
            tv_msg_tab_text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f);
            iv_msg_tab_indicator_line.setVisibility(View.INVISIBLE);
            iv_msg_tab_indicator_dot.setVisibility(View.INVISIBLE);
        }

        public void updateNoticeCount(int noticeCount) {
            tv_notice_count.setText(noticeCount + "+");
            tv_notice_count.setVisibility(noticeCount > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
