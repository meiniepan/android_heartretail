package com.idengyun.heartretail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;

/**
 * 登录页面
 * 注册页面
 *
 * @author aLang
 */
public final class LoginFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radio_group;
    private TextView tv_login_indicator;
    private View view_login_indicator;
    private TextView tv_register_indicator;
    private View view_register_indicator;
    private View layout_login_content;
    private View layout_register_content;
    private View tv_login;
    private View tv_register;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_login) {
            tv_login_indicator.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_register_indicator.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            view_login_indicator.setVisibility(View.VISIBLE);
            view_register_indicator.setVisibility(View.INVISIBLE);
            layout_login_content.setVisibility(View.VISIBLE);
            layout_register_content.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);
            tv_register.setVisibility(View.GONE);
        } else if (checkedId == R.id.rb_register) {
            tv_login_indicator.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv_register_indicator.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            view_login_indicator.setVisibility(View.INVISIBLE);
            view_register_indicator.setVisibility(View.VISIBLE);
            layout_login_content.setVisibility(View.GONE);
            layout_register_content.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);
            tv_register.setVisibility(View.VISIBLE);
        } else {
            tv_login_indicator.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv_register_indicator.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            view_login_indicator.setVisibility(View.INVISIBLE);
            view_register_indicator.setVisibility(View.INVISIBLE);
            layout_login_content.setVisibility(View.GONE);
            layout_register_content.setVisibility(View.GONE);
            tv_login.setVisibility(View.GONE);
            tv_register.setVisibility(View.GONE);
        }
    }

    private void findViewById(@NonNull View view) {
        radio_group = view.findViewById(R.id.radio_group);
        tv_login_indicator = view.findViewById(R.id.tv_login_indicator);
        view_login_indicator = view.findViewById(R.id.view_login_indicator);
        tv_register_indicator = view.findViewById(R.id.tv_register_indicator);
        view_register_indicator = view.findViewById(R.id.view_register_indicator);
        layout_login_content = view.findViewById(R.id.layout_login_content);
        layout_register_content = view.findViewById(R.id.layout_register_content);
        tv_login = view.findViewById(R.id.tv_login);
        tv_register = view.findViewById(R.id.tv_register);

        radio_group.setOnCheckedChangeListener(this);
        radio_group.check(R.id.rb_login);
    }
}
