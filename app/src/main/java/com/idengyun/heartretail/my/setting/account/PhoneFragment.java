package com.idengyun.heartretail.my.setting.account;

import android.os.Bundle;
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
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

/**
 * 绑定新手机界面
 *
 * @author aLang
 */
public final class PhoneFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_phone_mobile;
    private EditText et_phone_verify_code;
    private TextView tv_phone_verify_code;
    private View tv_phone_next;

    private SecondsTimer timer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_phone_bind;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        startTimer(tv_phone_verify_code);

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
        et_phone_mobile = view.findViewById(R.id.et_phone_mobile);
        et_phone_verify_code = view.findViewById(R.id.et_phone_verify_code);
        tv_phone_verify_code = view.findViewById(R.id.tv_phone_verify_code);
        tv_phone_next = view.findViewById(R.id.tv_phone_next);
    }
}
