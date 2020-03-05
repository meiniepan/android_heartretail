package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:全部订单
 * @date :2020/3/4 0004 11:42
 */
public class OrderListActivity extends BaseActivity {
    @BindView(R.id.tl_order_index)
    TabLayout tlOrderIndex;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;

    private MyOrderPagerAdapter mPagerAdapter;
    private List<Fragment> mFragments;   //tablayout标题对应的fragment
    private List<String> mTitles;      //tablayout 标题
    private int whatFlag;

    public static void start(Context context, int whatFlag) {
        Intent starter = new Intent(context, OrderListActivity.class);
        starter.putExtra("whatFlag", whatFlag);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initWhatTabSelect();
        initData();
        setData();
    }

    private void initWhatTabSelect() {
        whatFlag = getIntent().getIntExtra("whatFlag", 0);
    }

    private void setData() {
        vpOrder.setAdapter(mPagerAdapter);
        tlOrderIndex.setupWithViewPager(vpOrder);
        vpOrder.setOffscreenPageLimit(5);
        tlOrderIndex.setScrollBarSize(6);
        for (int i = 0; i < tlOrderIndex.getTabCount(); i++) {
            TabLayout.Tab tab = tlOrderIndex.getTabAt(i);
            if (tab != null) {
                //tab.setCustomView(mPagerAdapter.getTabView(i));
                //tab.setCustomView(inflater.inflate(R.layout.fragment_waitpay,null));
                if (tab.getCustomView() != null) {
                    View tabView = (View) tab.getCustomView().getParent();
                    tabView.setTag(i);
                    tabView.setOnClickListener(mTabOnClickListener);
                }
            }
        }

        if (whatFlag == 0) {
            vpOrder.setCurrentItem(0);
        } else if (whatFlag == 1) {
            vpOrder.setCurrentItem(1);
        } else if (whatFlag == 2) {
            vpOrder.setCurrentItem(2);
        } else if (whatFlag == 3) {
            vpOrder.setCurrentItem(3);
        } else if (whatFlag == 4) {
            vpOrder.setCurrentItem(4);
        } else if (whatFlag == 5) {
            vpOrder.setCurrentItem(5);
        }
    }

    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            TabLayout.Tab tab = tlOrderIndex.getTabAt(pos);
            if (tab != null) {
                tab.select();
            }
        }
    };

    private void initData() {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("全部");
        mTitles.add("待付款");
        mTitles.add("代销中");
        mTitles.add("待发货");
        mTitles.add("待收货");
        mTitles.add("待评价");
        for (int i = 0; i < mTitles.size(); i++) {
            OrderStatusFragment fragment = new OrderStatusFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("status", i);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        mPagerAdapter = new MyOrderPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
    }

    private class MyOrderPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public MyOrderPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;

        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public int getTabView(int position) {
            return mTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
