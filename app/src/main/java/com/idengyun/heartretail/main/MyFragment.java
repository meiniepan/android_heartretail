package com.idengyun.heartretail.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;

import java.util.ArrayList;

/**
 * 我的页面
 *
 * @author aLang
 */
public final class MyFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {
    private View layout_my_not_login;
    private View layout_my_unauthorized;
    private View layout_my_authorized;

    /* 编辑 分享 设置 */
    private View iv_my_pencil;
    private View iv_my_share;
    private View iv_my_setting;

    /* 用户信息 登录状态 是否认证 */
    private View iv_user_avatar;
    private View tv_user_name;
    private View tv_user_mobile;

    /* 余额相关 */
    private View tv_my_money_1;
    private View tv_my_money_2;
    private View tv_my_money_3;
    private View tv_my_account;

    /* 我的订单 */
    private View tv_my_all_orders;
    private View tv_my_order_1;
    private View tv_my_order_2;
    private View tv_my_order_3;
    private View tv_my_order_4;
    private View tv_my_order_5;

    /* 未登录相关 */
    private View tv_not_login_go_login;
    private View layout_not_login_certification;

    /* 未认证相关 */
    private View layout_unauthorized_go_authorized;

    /* 已认证相关 */
    private View layout_authorized_ok;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
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

        } else if (id == R.id.iv_user_avatar) {

        } else if (id == R.id.tv_my_account) {

        } else if (id == R.id.tv_my_all_orders) {

        } else if (id == R.id.tv_my_order_1) {

        } else if (id == R.id.tv_my_order_2) {

        } else if (id == R.id.tv_my_order_3) {
            layout_my_not_login.setVisibility(View.VISIBLE);
            layout_my_unauthorized.setVisibility(View.GONE);
            layout_my_authorized.setVisibility(View.GONE);
        } else if (id == R.id.tv_my_order_4) {
            layout_my_not_login.setVisibility(View.GONE);
            layout_my_unauthorized.setVisibility(View.VISIBLE);
            layout_my_authorized.setVisibility(View.GONE);
        } else if (id == R.id.tv_my_order_5) {
            layout_my_not_login.setVisibility(View.GONE);
            layout_my_unauthorized.setVisibility(View.GONE);
            layout_my_authorized.setVisibility(View.VISIBLE);
        }
    }

    private void findViewById(@NonNull View view) {
        layout_my_not_login = view.findViewById(R.id.layout_my_not_login);
        layout_my_unauthorized = view.findViewById(R.id.layout_my_unauthorized);
        layout_my_authorized = view.findViewById(R.id.layout_my_authorized);
        layout_my_not_login.setVisibility(View.VISIBLE);
        layout_my_unauthorized.setVisibility(View.GONE);
        layout_my_authorized.setVisibility(View.GONE);

        iv_my_pencil = view.findViewById(R.id.iv_my_pencil);
        iv_my_share = view.findViewById(R.id.iv_my_share);
        iv_my_setting = view.findViewById(R.id.iv_my_setting);

        iv_user_avatar = view.findViewById(R.id.iv_user_avatar);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_user_mobile = view.findViewById(R.id.tv_user_mobile);

        tv_my_money_1 = view.findViewById(R.id.tv_my_money_1);
        tv_my_money_2 = view.findViewById(R.id.tv_my_money_2);
        tv_my_money_3 = view.findViewById(R.id.tv_my_money_3);
        tv_my_account = view.findViewById(R.id.tv_my_account);

        tv_my_all_orders = view.findViewById(R.id.tv_my_all_orders);
        tv_my_order_1 = view.findViewById(R.id.tv_my_order_1);
        tv_my_order_2 = view.findViewById(R.id.tv_my_order_2);
        tv_my_order_3 = view.findViewById(R.id.tv_my_order_3);
        tv_my_order_4 = view.findViewById(R.id.tv_my_order_4);
        tv_my_order_5 = view.findViewById(R.id.tv_my_order_5);

        tv_not_login_go_login = view.findViewById(R.id.tv_not_login_go_login);
        layout_not_login_certification = view.findViewById(R.id.layout_not_login_certification);

        layout_unauthorized_go_authorized = view.findViewById(R.id.layout_unauthorized_go_authorized);

        layout_authorized_ok = view.findViewById(R.id.layout_authorized_ok);
    }
}