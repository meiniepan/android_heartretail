package com.dengyun.baselibrary.widgets.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * @Title tab切换Fragment
 * @Desc:
 * @Author: zhoubo
 * @CreateDate: 2018/11/14 6:48 PM
 */
public class TabFragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /** Fragment切换数组 */
    private ArrayList<Fragment> mFragments;
    /** 当前选中的Tab */
    private int mCurrentTabIndex = -1;

    public TabFragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
    }

    /** 界面切换控制 */
    public void setCurrentTab(int index) {
        if(index==mCurrentTabIndex){
            return;
        }
        android.support.v4.app.FragmentTransaction transaction = mFragmentManager.beginTransaction();

        Fragment targetFragment = mFragments.get(index);
        Fragment currentFragment = mCurrentTabIndex<0?null:mFragments.get(mCurrentTabIndex);
        if (!targetFragment.isAdded()) {
            transaction.add(mContainerViewId, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        transaction.commitAllowingStateLoss();
        mCurrentTabIndex = index;
    }

    public int getCurrentTabIndex() {
        return mCurrentTabIndex;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTabIndex);
    }
}
