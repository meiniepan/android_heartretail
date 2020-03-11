package com.idengyun.heartretail.my.setting.account;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

/**
 * 身份验证界面
 *
 * @author aLang
 */
public final class IdentityFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_identity_mobile;
    private EditText et_identity_verify_code;
    private TextView tv_identity_verify_code;
    private View tv_identity_contact_service;
    private View tv_identity_next;

    private SecondsTimer timer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_id_verify;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String mobile = HRUser.getMobile();
        tv_identity_mobile.setText(mobile);
        if (mobile.length() == 11) {
            mobile = mobile.substring(0, 4) + "****" + mobile.substring(8, 10);
            tv_identity_mobile.setText(mobile);
        }
    }

    @Override
    public void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (tv_identity_verify_code == v) {
            sendVerifyCode();
        } else if (tv_identity_contact_service == v) {
            // TODO: 2020/3/9
        } else if (tv_identity_next == v) {
            HRActivity.start(getActivity(), PhoneFragment.class);
        }
    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        startTimer(tv_identity_verify_code);

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getIdentifyCode())
                .fragment(this)
                .params("mobile", HRUser.getMobile())
                .params("identifyType", HRConst.IDENTIFY_TYPE_0)
                .isShowDialog(true)
                .clazz(VerifyCodeBean.class)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<VerifyCodeBean>(netOption) {
            @Override
            public void onSuccess(Response<VerifyCodeBean> response) {
                ToastUtils.showLong("验证码已发出");
            }
        });
    }

    @MainThread
    private void updateUI(VerifyCodeBean.Data data) {

    }

    private void startTimer(TextView textView) {
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
        tv_identity_mobile = view.findViewById(R.id.tv_identity_mobile);
        et_identity_verify_code = view.findViewById(R.id.et_identity_verify_code);
        tv_identity_verify_code = view.findViewById(R.id.tv_identity_verify_code);
        tv_identity_contact_service = view.findViewById(R.id.tv_identity_contact_service);
        tv_identity_next = view.findViewById(R.id.tv_identity_next);

        tv_identity_mobile.setOnClickListener(this);
        tv_identity_contact_service.setOnClickListener(this);
        tv_identity_next.setOnClickListener(this);
    }
}
