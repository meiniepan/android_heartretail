package com.idengyun.heartretail.my.setting.account;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.RealVerifyBean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.utils.SecondsTimer;

/**
 * 实名认证界面
 *
 * @author aLang
 */
@Deprecated
public final class RealFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_real_name;
    private EditText et_real_nationality;
    private EditText et_real_id_type;
    private EditText et_real_id_num;
    private EditText et_real_bank_card_num;
    private EditText et_real_reserved_mobile;
    private EditText et_real_verify_code;
    private TextView tv_real_verify_code;
    private TextView tv_real_go_auth;

    private SecondsTimer timer;
    private SettingViewModel settingViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_real_verify;
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
        if (settingViewModel == null) {
            settingViewModel = SettingViewModel.getInstance(activity);
            settingViewModel.getRealVerify().observe(this, new Observer<RealVerifyBean>() {
                @Override
                public void onChanged(@Nullable RealVerifyBean realVerifyBean) {
                    updateUI(realVerifyBean);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (tv_real_verify_code == v) {
            sendVerifyCode();
        } else if (tv_real_go_auth == v) {
            toAuth();
        }
    }

    private void toAuth() {
        if (settingViewModel == null) return;
        settingViewModel.requestRealVerify(this,
                et_real_reserved_mobile.getText().toString(),
                et_real_nationality.getText().toString(),
                et_real_id_type.getText().toString(),
                et_real_id_num.getText().toString(),
                et_real_bank_card_num.getText().toString(),
                et_real_verify_code.getText().toString());
    }

    @MainThread
    private void updateUI(@Nullable RealVerifyBean realVerifyBean) {
        if (realVerifyBean == null) return;
        RealVerifyBean.Data data = realVerifyBean.data;
    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        startTimer(tv_real_verify_code);

//        if (verifyCodeViewModel == null) return;
//        verifyCodeViewModel.requestVerifyCode(this, HRConst.IDENTIFY_TYPE_0);
    }

    private void startTimer(final TextView textView) {
        textView.setEnabled(false);

        if (timer == null) {
            timer = new SecondsTimer(60L, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    textView.setText(secondsUntilFinished + "s后重发");
                }

                @Override
                public void onFinish() {
                    textView.setText("获取验证码");
                    textView.setEnabled(true);
                }
            });
        }

        timer.start();
    }

    private void cancelTimer() {
        if (timer != null) timer.cancel();
    }

    private void findViewById(View view) {
        et_real_name = view.findViewById(R.id.et_real_name);
        et_real_nationality = view.findViewById(R.id.et_real_nationality);
        et_real_id_type = view.findViewById(R.id.et_real_id_type);
        et_real_id_num = view.findViewById(R.id.et_real_id_num);
        et_real_bank_card_num = view.findViewById(R.id.et_real_bank_card_num);
        et_real_reserved_mobile = view.findViewById(R.id.et_real_reserved_mobile);
        et_real_verify_code = view.findViewById(R.id.et_real_verify_code);
        tv_real_verify_code = view.findViewById(R.id.tv_real_verify_code);
        tv_real_go_auth = view.findViewById(R.id.tv_real_go_auth);

        tv_real_verify_code.setOnClickListener(this);
        tv_real_go_auth.setOnClickListener(this);
    }
}
