package com.idengyun.heartretail.main;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.activitys.AwardDetailActivity;
import com.idengyun.heartretail.activitys.MyBalanceActivity;
import com.idengyun.heartretail.activitys.MyEvaluateActivity;
import com.idengyun.heartretail.activitys.OrderListActivity;
import com.idengyun.heartretail.activitys.ShareQRCodeActivity;
import com.idengyun.heartretail.beans.BalanceBean;
import com.idengyun.heartretail.setting.SettingFragment;
import com.idengyun.heartretail.viewmodel.PayViewModel;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.LoginActivity;
import com.idengyun.usermodule.VerifyDeviceActivity;
import com.sobot.chat.utils.InformationUtil;

/**
 * 我的页面
 *
 * @author aLang
 */
public final class MyFragment extends BaseFragment implements View.OnClickListener {
    /* 头部背景色 */
    private View iv_my_not_login_bg;
    private View iv_my_login_bg;

    /* 编辑 分享 设置 */
    private View iv_my_setting;

    /* 用户信息 登录状态 是否认证 */
    private View layout_my_user;
    private ImageView iv_my_user_avatar;
    private TextView tv_my_user_name;
    private TextView tv_my_user_mobile;
    private View iv_my_user_logo;
    private View tv_my_go_login;

    /* 余额相关 */
    private View layout_my_money;
    private TextView tv_my_money_1;
    private TextView tv_my_money_2;
    private TextView tv_my_money_3;
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

    private View layout_my_evaluation;
    private View layout_my_help;
    private View layout_my_customer_service;
    private PayViewModel payViewModel;

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
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (payViewModel == null) {
            payViewModel = PayViewModel.getInstance(activity);
            payViewModel.getBalance().observe(this, new Observer<BalanceBean>() {
                @Override
                public void onChanged(@Nullable BalanceBean balanceBean) {
                    updateUI(balanceBean);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(HRUser.isLogin(), HRUser.isAuthentication());
        if (HRUser.isLogin()) requestAPI();
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();

        if (!HRUser.isLogin()) {
            LoginActivity.start(context);
            return;
        }

        if (HRUser.isNewDevice()) {
            VerifyDeviceActivity.start(context);
            return;
        }

        if (iv_my_setting == v) {
            HRActivity.start(context, SettingFragment.class);
        } else if (tv_my_go_login == v) {
            LoginActivity.start(context);
        } else if (tv_my_account == v) {
            MyBalanceActivity.start(context);
        } else if (iv_my_user_logo == v) {
            ShareQRCodeActivity.start(context);
        } else if (tv_my_all_orders == v) {
            OrderListActivity.start(context, 0);
        } else if (tv_my_order_1 == v) {
            OrderListActivity.start(context, 1);
        } else if (tv_my_order_2 == v) {
            OrderListActivity.start(context, 2);
        } else if (tv_my_order_3 == v) {
            OrderListActivity.start(context, 3);
        } else if (tv_my_order_4 == v) {
            OrderListActivity.start(context, 4);
        } else if (tv_my_order_5 == v) {
            OrderListActivity.start(context, 5);
        } else if (layout_my_evaluation == v) {
            MyEvaluateActivity.start(context);
        } else if (layout_my_help == v) {
            // HelpCenterActivity.start(context);
            // OrderDetailActivity.start(context, "");
            AwardDetailActivity.start(context);
        } else if (layout_my_customer_service == v) {
            new InformationUtil(context).startSobot();
        }
    }

    private void requestAPI() {
        if (payViewModel == null) return;
        payViewModel.requestBalance(this);
    }

    @MainThread
    private void updateUI(@Nullable BalanceBean balanceBean) {
        if (balanceBean == null) return;
        BalanceBean.Data data = balanceBean.data;
        BalanceBean.Data.Balance balance = data.balance;
        String canExchange = balance.canExchange;
        String total = balance.total;
        String willSend = balance.willSend;

        tv_my_money_1.setText(total);
        tv_my_money_2.setText(willSend);
        tv_my_money_3.setText(canExchange);
    }

    @MainThread
    private void updateUI(boolean isLogin, boolean isAuthorized) {
        if (isLogin) {
            ImageApi.displayImage(iv_my_user_avatar.getContext(), iv_my_user_avatar, HRUser.getAvatar());
            tv_my_user_name.setText(HRUser.getNickname());
            tv_my_user_mobile.setText(HRUser.getMobile());

            iv_my_not_login_bg.setVisibility(View.GONE);
            iv_my_login_bg.setVisibility(View.VISIBLE);

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
        } else {
            iv_my_not_login_bg.setVisibility(View.VISIBLE);
            iv_my_login_bg.setVisibility(View.GONE);

            iv_my_user_logo.setVisibility(View.GONE);
            tv_my_go_login.setVisibility(View.VISIBLE);

            layout_my_money.setVisibility(View.GONE);

            layout_my_not_login.setVisibility(View.VISIBLE);
            layout_my_unauthorized.setVisibility(View.GONE);
            layout_my_authorized.setVisibility(View.GONE);
        }
    }

    private void findViewById(@NonNull View view) {
        iv_my_not_login_bg = view.findViewById(R.id.iv_my_not_login_bg);
        iv_my_login_bg = view.findViewById(R.id.iv_my_login_bg);

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

        layout_my_evaluation = view.findViewById(R.id.layout_my_evaluation);
        layout_my_help = view.findViewById(R.id.layout_my_help);
        layout_my_customer_service = view.findViewById(R.id.layout_my_customer_service);

        iv_my_setting.setOnClickListener(this);
        tv_my_go_login.setOnClickListener(this);
        tv_my_account.setOnClickListener(this);
        iv_my_user_logo.setOnClickListener(this);

        tv_my_all_orders.setOnClickListener(this);
        tv_my_order_1.setOnClickListener(this);
        tv_my_order_2.setOnClickListener(this);
        tv_my_order_3.setOnClickListener(this);
        tv_my_order_3.setOnClickListener(this);
        tv_my_order_4.setOnClickListener(this);
        tv_my_order_5.setOnClickListener(this);

        layout_my_evaluation.setOnClickListener(this);
        layout_my_help.setOnClickListener(this);
        layout_my_customer_service.setOnClickListener(this);
    }
}