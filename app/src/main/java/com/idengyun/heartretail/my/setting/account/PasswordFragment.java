package com.idengyun.heartretail.my.setting.account;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.activity.ActivityUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.PwdModifyBean;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.LoginActivity;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

/**
 * 修改密码界面
 *
 * @author aLang
 */
public final class PasswordFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_pwd_mobile;
    private EditText et_pwd_verify_code;
    private TextView tv_pwd_verify_code;
    private EditText et_pwd_new_pwd;
    private CheckBox cb_pwd_eye;
    private View tv_pwd_contact_service;
    private View tv_pwd_confirm;

    private SecondsTimer timer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pwd_modify;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String mobile = HRUser.getMobile();
        tv_pwd_mobile.setText(mobile);
        if (mobile.length() == 11) {
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            tv_pwd_mobile.setText(mobile);
        }
    }

    @Override
    public void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (tv_pwd_verify_code == v) {
            sendVerifyCode();
        } else if (tv_pwd_contact_service == v) {
            // TODO: 2020/3/9
        } else if (tv_pwd_confirm == v) {
            modifyPwd();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        TransformationMethod method = isChecked ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance();
        et_pwd_new_pwd.setTransformationMethod(method);
    }

    private void modifyPwd() {
        if (et_pwd_verify_code.length() < 1) {
            ToastUtils.showShort("请输入有效验证码");
            return;
        }

        if (et_pwd_new_pwd.length() < 1) {
            ToastUtils.showShort("请输入有效密码");
            return;
        }

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changePwd())
                .fragment(this)
                .clazz(PwdModifyBean.class)
                .params("mobile", HRUser.getMobile())
                .params("identifyCode", et_pwd_verify_code.getText().toString())
                .params("pwd", et_pwd_new_pwd.getText().toString())
                .build();
        NetApi.<PwdModifyBean>getData(netOption, new JsonCallback<PwdModifyBean>(netOption) {
            @Override
            public void onSuccess(Response<PwdModifyBean> response) {
                ToastUtils.showLong("密码修改成功");
                logout();
            }
        });
    }

    private void logout() {
        /* 退出登录清栈操作 */
        String mobile = HRUser.getMobile();
        HRUser.clear();
        HRUser.saveMobile(mobile);
        ActivityUtils.finishAllActivities();
        LoginActivity.start(getContext());
    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        startTimer(tv_pwd_verify_code);

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getIdentifyCode())
                .fragment(this)
                .params("mobile", HRUser.getMobile())
                .params("identifyType", HRConst.IDENTIFY_TYPE_2)
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
        tv_pwd_mobile = view.findViewById(R.id.tv_pwd_mobile);
        et_pwd_verify_code = view.findViewById(R.id.et_pwd_verify_code);
        tv_pwd_verify_code = view.findViewById(R.id.tv_pwd_verify_code);
        et_pwd_new_pwd = view.findViewById(R.id.et_pwd_new_pwd);
        cb_pwd_eye = view.findViewById(R.id.cb_pwd_eye);
        tv_pwd_contact_service = view.findViewById(R.id.tv_pwd_contact_service);
        tv_pwd_confirm = view.findViewById(R.id.tv_pwd_confirm);

        tv_pwd_verify_code.setOnClickListener(this);
        cb_pwd_eye.setOnCheckedChangeListener(this);
        tv_pwd_contact_service.setOnClickListener(this);
        tv_pwd_confirm.setOnClickListener(this);
    }
}
