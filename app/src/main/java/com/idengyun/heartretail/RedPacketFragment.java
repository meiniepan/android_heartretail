package com.idengyun.heartretail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;

/**
 * 红包页面
 *
 * @author aLang
 */
public final class RedPacketFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_red_packet;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
