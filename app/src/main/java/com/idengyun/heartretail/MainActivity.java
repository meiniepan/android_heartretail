package com.idengyun.heartretail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.idengyun.heartretail.main.HomeFragment;
import com.idengyun.heartretail.main.MyFragment;
import com.idengyun.heartretail.main.RedPacketFragment;

/**
 * APP主界面
 *
 * @author aLang
 */
@Route(path = (RouterPathConfig.app_FirstActivity))
public final class MainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private HomeFragment homeFragment;
    private RedPacketFragment redPacketFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    private void init() {
        SystemUIHelper.applySystemUI(this);

        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        redPacketFragment = new RedPacketFragment();
        myFragment = new MyFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, homeFragment, HomeFragment.class.getName())
                .add(R.id.content, redPacketFragment, RedPacketFragment.class.getName())
                .add(R.id.content, myFragment, MyFragment.class.getName())
                .hide(homeFragment)
                .hide(redPacketFragment)
                .hide(myFragment)
                .commit();


        TabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.clearOnTabSelectedListeners();
        tab_layout.removeAllTabs();
        int[] resIds = new int[]{R.drawable.selector_main_home, R.drawable.selector_main_red_packet, R.drawable.selector_main_my};
        String[] tabs = new String[]{"首 页", "红 包", "我 的"};
        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = tab_layout.newTab();
            tab.setCustomView(R.layout.activity_main_tab);
            View customView = tab.getCustomView();
            if (customView != null) {
                ImageView iv_main_tab = customView.findViewById(R.id.iv_main_tab);
                TextView tv_main_tab = customView.findViewById(R.id.tv_main_tab);
                iv_main_tab.setImageResource(resIds[i]);
                tv_main_tab.setText(tabs[i]);
            }
            tab_layout.addTab(tab, i, false);
        }
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    showHomeFragment();
                } else if (position == 1) {
                    showRedPacketFragment();
                } else if (position == 2) {
                    showMyFragment();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayout.Tab tab = tab_layout.getTabAt(0);
        if (tab != null) tab.select();
    }

    private void showMyFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(myFragment)
                .hide(homeFragment)
                .hide(redPacketFragment)
                .commit();

        /*homeFragment.setUserVisibleHint(false);
        redPacketFragment.setUserVisibleHint(false);
        myFragment.setUserVisibleHint(true);*/
    }

    private void showRedPacketFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(redPacketFragment)
                .hide(homeFragment)
                .hide(myFragment)
                .commit();

        /*homeFragment.setUserVisibleHint(false);
        redPacketFragment.setUserVisibleHint(true);
        myFragment.setUserVisibleHint(false);*/
    }

    private void showHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(homeFragment)
                .hide(redPacketFragment)
                .hide(myFragment)
                .commit();

        /*homeFragment.setUserVisibleHint(true);
        redPacketFragment.setUserVisibleHint(false);
        myFragment.setUserVisibleHint(false);*/
    }
}
