package com.idengyun.heartretail.setting.account.auth;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.KVRealVerify;
import com.idengyun.heartretail.beans.RealVerifyBean;
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
    private KVRealVerify kvRealVerify = new KVRealVerify();

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
            kvRealVerify.name = null;
            kvRealVerify.sex = null;
            kvRealVerify.nation = null;
            kvRealVerify.userBirthday = null;
            kvRealVerify.address = null;
            kvRealVerify.number = null;
            kvRealVerify.authority = null;
            kvRealVerify.timeLimit = null;

            kvRealVerify.bankCardNo = null;
            kvRealVerify.bankMobile = null;
            kvRealVerify.userBankName = null;
            kvRealVerify.userBankFullName = null;
            kvRealVerify.userBankCity = null;
            kvRealVerify.userBankProvince = null;
            kvRealVerify.bankCardType = null;
            kvRealVerify.cardType = null;

            kvRealVerify.userId = null;
            kvRealVerify.frontUrl = null;
            kvRealVerify.backUrl = null;
            kvRealVerify.bankUrl = null;
            kvRealVerify.userCertificateType = null;
            kvRealVerify.frontOrderNo = null;
            kvRealVerify.backOrderNo = null;
            kvRealVerify.identifyCode = null;

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
        settingViewModel.requestRealVerify(this, kvRealVerify);
    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        startTimer(tv_real_verify_code);

        if (settingViewModel == null) return;
        settingViewModel.requestVerifyCode(this, "6");
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

    private class OnTextChangedListener implements TextWatcher {
        private EditText editText;

        private CharSequence loginMobile;
        private CharSequence loginPwd;
        private CharSequence registerMobile;
        private CharSequence registerVerifyCode;
        private CharSequence registerPwd;
        private CharSequence registerInviteCode;

        public OnTextChangedListener(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int id = editText.getId();
            if (id == R.id.et_login_mobile) {
                loginMobile = s;
            } else if (id == R.id.et_login_pwd) {
                loginPwd = s;
            } else if (id == R.id.et_register_mobile) {
                registerMobile = s;
            } else if (id == R.id.et_register_verify_code) {
                registerVerifyCode = s;
            } else if (id == R.id.et_register_pwd) {
                registerPwd = s;
            } else if (id == R.id.et_register_invite_code) {
                registerInviteCode = s;
            }
        }

    }
}
