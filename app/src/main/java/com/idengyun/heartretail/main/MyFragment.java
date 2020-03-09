package com.idengyun.heartretail.main;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.utils.SizeUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.bases.PagerChildFragment;
import com.idengyun.heartretail.my.SettingFragment;

/**
 * 我的页面
 *
 * @author aLang
 */
public final class MyFragment extends PagerChildFragment implements View.OnClickListener {
    /* 头部背景色 */
    private View iv_my_not_login_bg;
    private View iv_my_login_bg;

    /* 编辑 分享 设置 */
    private View layout_my_title;
    private View iv_my_pencil;
    private View iv_my_share;
    private View iv_my_setting;

    /* 用户信息 登录状态 是否认证 */
    private View layout_my_user;
    private View iv_my_user_avatar;
    private View tv_my_user_name;
    private View tv_my_user_mobile;
    private View iv_my_user_logo;
    private View tv_my_go_login;

    /* 余额相关 */
    private View layout_my_money;
    private View tv_my_money_1;
    private View tv_my_money_2;
    private View tv_my_money_3;
    private View tv_my_account;

    /* 未登录相关 未认证相关 已认证相关 */
    private View layout_my_not_login;
    private View layout_my_unauthorized;
    private View layout_my_authorized;

    /* 我的订单 */
    private View layout_my_order;
    private View tv_my_all_orders;
    private View tv_my_order_1;
    private View tv_my_order_2;
    private View tv_my_order_3;
    private View tv_my_order_4;
    private View tv_my_order_5;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateUI(true, false);
        iv_my_setting.setOnClickListener(this);
        tv_my_order_3.setOnClickListener(this);
        tv_my_order_4.setOnClickListener(this);
        tv_my_order_5.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_my_pencil) {

        } else if (id == R.id.iv_my_share) {

        } else if (id == R.id.iv_my_setting) {
            HRActivity.start(getContext(), SettingFragment.class);
        } else if (id == R.id.iv_my_user_avatar) {

        } else if (id == R.id.tv_my_account) {

        } else if (id == R.id.tv_my_all_orders) {

        } else if (id == R.id.tv_my_order_1) {

        } else if (id == R.id.tv_my_order_2) {

        } else if (id == R.id.tv_my_order_3) {
            updateUI(false, false);
        } else if (id == R.id.tv_my_order_4) {
            updateUI(true, false);
        } else if (id == R.id.tv_my_order_5) {
            updateUI(true, true);
        }
    }

    @MainThread
    private void updateUI(boolean isLogin, boolean isAuthorized) {
        if (isLogin) {
            iv_my_not_login_bg.setVisibility(View.GONE);
            iv_my_login_bg.setVisibility(View.VISIBLE);

            iv_my_pencil.setVisibility(View.VISIBLE);
            iv_my_share.setVisibility(View.VISIBLE);

            iv_my_user_logo.setVisibility(View.VISIBLE);
            tv_my_go_login.setVisibility(View.GONE);

            layout_my_money.setVisibility(View.VISIBLE);

            layout_my_not_login.setVisibility(View.GONE);
            if (isAuthorized) {
                layout_my_unauthorized.setVisibility(View.GONE);
                layout_my_authorized.setVisibility(View.VISIBLE);
            } else {
                layout_my_unauthorized.setVisibility(View.VISIBLE);
                layout_my_authorized.setVisibility(View.GONE);
            }

            setTopMargin(layout_my_order, 325F);
        } else {
            iv_my_not_login_bg.setVisibility(View.VISIBLE);
            iv_my_login_bg.setVisibility(View.GONE);

            iv_my_pencil.setVisibility(View.GONE);
            iv_my_share.setVisibility(View.GONE);

            iv_my_user_logo.setVisibility(View.GONE);
            tv_my_go_login.setVisibility(View.VISIBLE);

            layout_my_money.setVisibility(View.GONE);

            layout_my_not_login.setVisibility(View.VISIBLE);
            layout_my_unauthorized.setVisibility(View.GONE);
            layout_my_authorized.setVisibility(View.GONE);

            setTopMargin(layout_my_order, 245F);
        }
    }

    /* unit is dp */
    private void setTopMargin(View v, float dpValue) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) params).topMargin = SizeUtils.dp2px(dpValue);
            v.setLayoutParams(params);
        }
    }

    private void findViewById(@NonNull View view) {
        iv_my_not_login_bg = view.findViewById(R.id.iv_my_not_login_bg);
        iv_my_login_bg = view.findViewById(R.id.iv_my_login_bg);

        layout_my_title = view.findViewById(R.id.layout_my_title);
        iv_my_pencil = view.findViewById(R.id.iv_my_pencil);
        iv_my_share = view.findViewById(R.id.iv_my_share);
        iv_my_setting = view.findViewById(R.id.iv_my_setting);

        layout_my_user = view.findViewById(R.id.layout_my_user);
        iv_my_user_avatar = view.findViewById(R.id.iv_my_user_avatar);
        tv_my_user_name = view.findViewById(R.id.tv_my_user_name);
        tv_my_user_mobile = view.findViewById(R.id.tv_my_user_mobile);
        iv_my_user_logo = view.findViewById(R.id.iv_my_user_logo);
        tv_my_go_login = view.findViewById(R.id.tv_my_go_login);

        layout_my_money = view.findViewById(R.id.layout_my_money);
        tv_my_money_1 = view.findViewById(R.id.tv_my_money_1);
        tv_my_money_2 = view.findViewById(R.id.tv_my_money_2);
        tv_my_money_3 = view.findViewById(R.id.tv_my_money_3);
        tv_my_account = view.findViewById(R.id.tv_my_account);

        layout_my_not_login = view.findViewById(R.id.layout_my_not_login);
        layout_my_unauthorized = view.findViewById(R.id.layout_my_unauthorized);
        layout_my_authorized = view.findViewById(R.id.layout_my_authorized);

        layout_my_order = view.findViewById(R.id.layout_my_order);
        tv_my_all_orders = view.findViewById(R.id.tv_my_all_orders);
        tv_my_order_1 = view.findViewById(R.id.tv_my_order_1);
        tv_my_order_2 = view.findViewById(R.id.tv_my_order_2);
        tv_my_order_3 = view.findViewById(R.id.tv_my_order_3);
        tv_my_order_4 = view.findViewById(R.id.tv_my_order_4);
        tv_my_order_5 = view.findViewById(R.id.tv_my_order_5);
    }
}