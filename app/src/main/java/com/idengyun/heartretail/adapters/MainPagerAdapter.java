package com.idengyun.heartretail.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.idengyun.heartretail.main.HomeFragment;
import com.idengyun.heartretail.main.MyFragment;
import com.idengyun.heartretail.main.RedPacketFragment;
import com.idengyun.heartretail.model.PageInfo;

import java.util.List;

/**
 * 适配器
 *
 * @author aLang
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<PageInfo> pageInfos;

    public MainPagerAdapter(FragmentManager fm, List<PageInfo> pageInfos) {
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