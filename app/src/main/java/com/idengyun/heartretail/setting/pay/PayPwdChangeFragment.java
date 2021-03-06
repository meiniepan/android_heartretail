package com.idengyun.heartretail.setting.pay;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.Bean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;

/**
 * 修改支付密码-设置新支付密码
 *
 * @author aLang
 */
public final class PayPwdChangeFragment extends BaseFragment implements View.OnClickListener {

    public static void start(Context context, int type) {
        Bundle extras = new Bundle();
        extras.putInt("pay_pwd_change_type", type);
        HRActivity.start(context, extras, PayPwdChangeFragment.class);
    }

    private View v_dot_1;
    private View v_dot_2;
    private View v_dot_3;
    private View v_dot_4;
    private View v_dot_5;
    private View v_dot_6;
    private EditText et_pay_pwd;
    private SettingViewModel settingViewModel;
    /* 0新增支付密码1修改支付密码2忘记支付密码 */
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pay_pwd_change;
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
        type = activity.getIntent().getIntExtra("pay_pwd_change_type", -1);
        setVisibility(0);
        observe();
    }

    @Override
    public void onClick(View v) {
        if (et_pay_pwd == v) showInput(et_pay_pwd);
        else hideInput(et_pay_pwd);
    }

    private void observe() {
        if (settingViewModel == null) {
            settingViewModel = SettingViewModel.getInstance(this);
            settingViewModel.getPayPwdChange().observe(this, new Observer<Bean>() {
                @Override
                public void onChanged(@Nullable Bean bean) {
                    HRActivity.finish(getActivity());
                }
            });
        }
    }

    private void changePayPwd() {
        if (et_pay_pwd.length() < 6) {
            ToastUtils.showShort("请设置新6位支付密码");
            return;
        }

        if (type == -1) return;
        if (settingViewModel == null) return;
        settingViewModel.requestChangePayPwd(this, et_pay_pwd.getText().toString(), type);
    }

    private void showInput(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    private void hideInput(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void findViewById(View view) {
        v_dot_1 = view.findViewById(R.id.v_dot_1);
        v_dot_2 = view.findViewById(R.id.v_dot_2);
        v_dot_3 = view.findViewById(R.id.v_dot_3);
        v_dot_4 = view.findViewById(R.id.v_dot_4);
        v_dot_5 = view.findViewById(R.id.v_dot_5);
        v_dot_6 = view.findViewById(R.id.v_dot_6);
        et_pay_pwd = view.findViewById(R.id.et_pay_pwd);
        et_pay_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                setVisibility(length);
                if (s.length() == 6) changePayPwd();
            }
        });

        view.setOnClickListener(this);
        et_pay_pwd.requestFocus();
        et_pay_pwd.postDelayed(new Runnable() {
            @Override
            public void run() {
                showInput(et_pay_pwd);
            }
        }, 200L);
    }

    private void setVisibility(int length) {
        switch (length) {
            case 1:
                v_dot_1.setVisibility(View.VISIBLE);
                v_dot_2.setVisibility(View.INVISIBLE);
                v_dot_3.setVisibility(View.INVISIBLE);
                v_dot_4.setVisibility(View.INVISIBLE);
                v_dot_5.setVisibility(View.INVISIBLE);
                v_dot_6.setVisibility(View.INVISIBLE);
                break;
            case 2:
                v_dot_1.setVisibility(View.VISIBLE);
                v_dot_2.setVisibility(View.VISIBLE);
                v_dot_3.setVisibility(View.INVISIBLE);
                v_dot_4.setVisibility(View.INVISIBLE);
                v_dot_5.setVisibility(View.INVISIBLE);
                v_dot_6.setVisibility(View.INVISIBLE);
                break;
            case 3:
                v_dot_1.setVisibility(View.VISIBLE);
                v_dot_2.setVisibility(View.VISIBLE);
                v_dot_3.setVisibility(View.VISIBLE);
                v_dot_4.setVisibility(View.INVISIBLE);
                v_dot_5.setVisibility(View.INVISIBLE);
                v_dot_6.setVisibility(View.INVISIBLE);
                break;
            case 4:
                v_dot_1.setVisibility(View.VISIBLE);
                v_dot_2.setVisibility(View.VISIBLE);
                v_dot_3.setVisibility(View.VISIBLE);
                v_dot_4.setVisibility(View.VISIBLE);
                v_dot_5.setVisibility(View.INVISIBLE);
                v_dot_6.setVisibility(View.INVISIBLE);
                break;
            case 5:
                v_dot_1.setVisibility(View.VISIBLE);
                v_dot_2.setVisibility(View.VISIBLE);
                v_dot_3.setVisibility(View.VISIBLE);
                v_dot_4.setVisibility(View.VISIBLE);
                v_dot_5.setVisibility(View.VISIBLE);
                v_dot_6.setVisibility(View.INVISIBLE);
                break;
            case 6:
                v_dot_1.setVisibility(View.VISIBLE);
                v_dot_2.setVisibility(View.VISIBLE);
                v_dot_3.setVisibility(View.VISIBLE);
                v_dot_4.setVisibility(View.VISIBLE);
                v_dot_5.setVisibility(View.VISIBLE);
                v_dot_6.setVisibility(View.VISIBLE);
                break;
            default:
                v_dot_1.setVisibility(View.INVISIBLE);
                v_dot_2.setVisibility(View.INVISIBLE);
                v_dot_3.setVisibility(View.INVISIBLE);
                v_dot_4.setVisibility(View.INVISIBLE);
                v_dot_5.setVisibility(View.INVISIBLE);
                v_dot_6.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
