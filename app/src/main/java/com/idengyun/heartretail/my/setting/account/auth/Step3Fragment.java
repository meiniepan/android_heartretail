package com.idengyun.heartretail.my.setting.account.auth;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.RealVerifyBean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.utils.SecondsTimer;

/**
 * 步骤3
 *
 * @author aLang
 */
public final class Step3Fragment extends BaseFragment implements View.OnClickListener {

    private EditText et_real_name;
    private EditText et_real_nationality;
    private EditText et_real_type;
    private EditText et_real_id_card;
    private EditText et_real_bank_card;
    private EditText et_real_reserved_mobile;
    private EditText et_real_verify_code;
    private TextView tv_real_verify_code;
    private View tv_real_go_auth;

    /* 身份证和银行卡正反面本地文件路径 */
    private String idCardTrueUrl;
    private String idCardFalseUrl;
    private String bankCardTrueUrl;
    private String bankCardFalseUrl;

    private SecondsTimer timer;
    private SettingViewModel settingViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_real_step_3;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observe();
        Intent intent = getActivity().getIntent();
        idCardTrueUrl = intent.getStringExtra("id_card_true_url");
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
            requestRealVerify();
        }
    }

    private void observe() {
        final FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (settingViewModel == null) {
            settingViewModel = SettingViewModel.getInstance(activity);
            settingViewModel.getVerifyCode().observe(this, new Observer<VerifyCodeBean>() {
                @Override
                public void onChanged(@Nullable VerifyCodeBean verifyCodeBean) {
                    ToastUtils.showLong("验证码已发出");
                }
            });
            settingViewModel.getRealVerify().observe(this, new Observer<RealVerifyBean>() {
                @Override
                public void onChanged(@Nullable RealVerifyBean realVerifyBean) {
                    activity.setResult(Activity.RESULT_OK);
                    activity.finish();
                }
            });
        }
    }

    /* 请求实名认证 */
    private void requestRealVerify() {
        if (settingViewModel == null) return;
        //settingViewModel.requestRealVerify(this,);
    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        startTimer(tv_real_verify_code);

        if (settingViewModel == null) return;
        settingViewModel.requestVerifyCode(this, HRConst.IDENTIFY_TYPE_0);
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
        et_real_type = view.findViewById(R.id.et_real_type);
        et_real_id_card = view.findViewById(R.id.et_real_id_card);
        et_real_bank_card = view.findViewById(R.id.et_real_bank_card);
        et_real_reserved_mobile = view.findViewById(R.id.et_real_reserved_mobile);
        et_real_verify_code = view.findViewById(R.id.et_real_verify_code);
        tv_real_verify_code = view.findViewById(R.id.tv_real_verify_code);
        tv_real_go_auth = view.findViewById(R.id.tv_real_go_auth);
        tv_real_go_auth.setOnClickListener(this);
    }
}
