package com.idengyun.heartretail.login;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;

/**
 * 登录页面
 * 注册页面
 *
 * @author aLang
 */
public final class LoginFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /* 标签指示器相关 */
    private RadioGroup radio_group;
    private TextView tv_login_indicator;
    private View view_login_indicator;
    private TextView tv_register_indicator;
    private View view_register_indicator;

    /* 登录相关 */
    private View layout_login_content;
    private EditText et_login_mobile;
    private EditText et_login_pwd;
    private CheckBox cb_login_eye;
    private View tv_forget_pwd;
    private View tv_login;

    /* 注册相关 */
    private View layout_register_content;
    private EditText et_register_mobile;
    private EditText et_register_verify_code;
    private View tv_verify_code;
    private EditText et_register_pwd;
    private EditText cb_register_eye;
    private EditText et_invite_code;
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    private void findViewById(@NonNull View view) {
        radio_group = view.findViewById(R.id.radio_group);
        tv_login_indicator = view.findViewById(R.id.tv_login_indicator);
        view_login_indicator = view.findViewById(R.id.view_login_indicator);
        tv_register_indicator = view.findViewById(R.id.tv_register_indicator);
        view_register_indicator = view.findViewById(R.id.view_register_indicator);

        layout_login_content = view.findViewById(R.id.layout_login_content);
        et_login_mobile = view.findViewById(R.id.et_login_mobile);
        et_login_pwd = view.findViewById(R.id.et_login_pwd);
        cb_login_eye = view.findViewById(R.id.cb_login_eye);
        tv_forget_pwd = view.findViewById(R.id.tv_forget_pwd);
        tv_login = view.findViewById(R.id.tv_login);

        layout_register_content = view.findViewById(R.id.layout_register_content);
        et_register_mobile = view.findViewById(R.id.et_register_mobile);
        et_register_verify_code = view.findViewById(R.id.et_register_verify_code);
        tv_verify_code = view.findViewById(R.id.tv_verify_code);
        et_register_pwd = view.findViewById(R.id.et_register_pwd);
        cb_register_eye = view.findViewById(R.id.cb_register_eye);
        et_invite_code = view.findViewById(R.id.et_invite_code);
        tv_register = view.findViewById(R.id.tv_register);

        radio_group.setOnCheckedChangeListener(this);
        radio_group.check(R.id.rb_login);

//        et_login_mobile.addTextChangedListener(this);
//        et_login_pwd.addTextChangedListener(this);
        cb_login_eye.setOnCheckedChangeListener(this);
        tv_forget_pwd.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }
}
