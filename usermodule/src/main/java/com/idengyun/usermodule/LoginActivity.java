package com.idengyun.usermodule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.RegexUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.usermodule.beans.LoginBean;
import com.idengyun.usermodule.beans.RegisterBean;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

/**
 * 登录页面
 * 注册页面
 *
 * @author aLang
 */
@Route(path = (RouterPathConfig.user_LoginActivity))
public final class LoginActivity extends BaseActivity
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

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
    private TextView tv_verify_code;
    private EditText et_register_pwd;
    private CheckBox cb_register_eye;
    private EditText et_register_invite_code;
    private View tv_register;

    private SecondsTimer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById();
    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
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
        int id = v.getId();
        if (id == R.id.tv_forget_pwd) {
            startForgetPwdActivity();
        } else if (id == R.id.tv_login) {
            requestLogin();
        } else if (id == R.id.tv_verify_code) {
            sendVerifyCode();
        } else if (id == R.id.tv_register) {
            requestRegister();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        TransformationMethod method = isChecked ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance();
        if (id == R.id.cb_login_eye) {
            et_login_pwd.setTransformationMethod(method);
        } else if (id == R.id.cb_register_eye) {
            et_register_pwd.setTransformationMethod(method);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
    }

    /* 请求注册API */
    private void requestRegister() {
        if (!RegexUtils.isMobileSimple(et_register_mobile.getText())) {
            ToastUtils.showShort("请输入有效手机号码");
            return;
        }

        if (et_register_verify_code.length() < 1) {
            ToastUtils.showShort("请输入有效手机验证码");
            return;
        }

        if (et_register_pwd.length() < 1) {
            ToastUtils.showShort("请输入有效密码");
            return;
        }

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.register())
                .activity(this)
                .isShowDialog(true)
                .params("mobile", et_register_mobile.getText().toString())
                .params("identifyCode", et_register_verify_code.getText().toString())
                .params("pwd", et_register_pwd.getText().toString())
                .params("invitationCode", et_register_invite_code.getText().toString())
                .params("phoneImei", HRConst.PHONE_IMEI)
                .params("phoneType", HRConst.PHONE_TYPE)
                .params("appName", "app")
                .clazz(RegisterBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<RegisterBean>(netOption) {
            @Override
            public void onSuccess(Response<RegisterBean> response) {
                ToastUtils.showShort("注册成功");
                radio_group.check(R.id.rb_login);
                et_register_mobile.setText(null);
                et_register_verify_code.setText(null);
                et_register_pwd.setText(null);
                et_register_invite_code.setText(null);
            }
        });
    }

    /* 发送手机验证码API */
    private void sendVerifyCode() {
        if (!RegexUtils.isMobileSimple(et_register_mobile.getText())) {
            ToastUtils.showShort("请输入有效手机号码");
            return;
        }

        startTimer(tv_verify_code);

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getIdentifyCode())
                .activity(this)
                .params("mobile", et_register_mobile.getText().toString())
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

    /* 请求登录API */
    private void requestLogin() {
        if (!RegexUtils.isMobileSimple(et_login_mobile.getText())) {
            ToastUtils.showShort("请输入有效手机号码");
            return;
        }

        if (et_login_pwd.length() < 1) {
            ToastUtils.showShort("请输入有效密码");
            return;
        }

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.login())
                .activity(this)
                .isShowDialog(true)
                .params("mobile", et_login_mobile.getText().toString())
                .params("pwd", et_login_pwd.getText().toString())
                .params("phoneImei", HRConst.PHONE_IMEI)
                .clazz(LoginBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<LoginBean>(netOption) {
            @Override
            public void onSuccess(Response<LoginBean> response) {
                /* 保存用户信息 */
                LoginBean body = response.body();
                HRUser.saveLoginBean(body);
                if (body.data.isnewPhoneImei == 1) {
                    startVerifyActivity();
                } else {
                    startMainActivity();
                }
            }
        });
    }

    /* 开启忘记密码界面 */
    private void startForgetPwdActivity() {
        ModifyPwdActivity.start(getContext());
    }

    /* 开启设备认证界面 */
    private void startVerifyActivity() {
        VerifyDeviceActivity.start(getContext());
        finish();
    }

    /* 开启主界面 */
    private void startMainActivity() {
        ARouter.getInstance().build(RouterPathConfig.app_FirstActivity).navigation();
        finish();
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

    private void findViewById() {
        radio_group = findViewById(R.id.radio_group);
        tv_login_indicator = findViewById(R.id.tv_login_indicator);
        view_login_indicator = findViewById(R.id.view_login_indicator);
        tv_register_indicator = findViewById(R.id.tv_register_indicator);
        view_register_indicator = findViewById(R.id.view_register_indicator);

        layout_login_content = findViewById(R.id.layout_login_content);
        et_login_mobile = findViewById(R.id.et_login_mobile);
        et_login_pwd = findViewById(R.id.et_login_pwd);
        cb_login_eye = findViewById(R.id.cb_login_eye);
        tv_forget_pwd = findViewById(R.id.tv_forget_pwd);
        tv_login = findViewById(R.id.tv_login);

        layout_register_content = findViewById(R.id.layout_register_content);
        et_register_mobile = findViewById(R.id.et_register_mobile);
        et_register_verify_code = findViewById(R.id.et_register_verify_code);
        tv_verify_code = findViewById(R.id.tv_verify_code);
        et_register_pwd = findViewById(R.id.et_register_pwd);
        cb_register_eye = findViewById(R.id.cb_register_eye);
        et_register_invite_code = findViewById(R.id.et_register_invite_code);
        tv_register = findViewById(R.id.tv_register);

        radio_group.setOnCheckedChangeListener(this);
        radio_group.check(R.id.rb_login);

        et_login_mobile.setOnFocusChangeListener(this);
        et_login_pwd.setOnFocusChangeListener(this);
        /*et_login_mobile.addTextChangedListener(new OnTextChangedListener(et_login_mobile));
        et_login_pwd.addTextChangedListener(new OnTextChangedListener(et_login_pwd));*/
        cb_login_eye.setOnCheckedChangeListener(this);
        tv_forget_pwd.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        cb_login_eye.setChecked(false);

        et_register_mobile.setOnFocusChangeListener(this);
        et_register_verify_code.setOnFocusChangeListener(this);
        et_register_pwd.setOnFocusChangeListener(this);
        et_register_invite_code.setOnFocusChangeListener(this);
        tv_verify_code.setOnClickListener(this);
        cb_register_eye.setOnCheckedChangeListener(this);
       /* et_register_mobile.addTextChangedListener(new OnTextChangedListener(et_register_mobile));
        et_register_verify_code.addTextChangedListener(new OnTextChangedListener(et_register_verify_code));
        et_register_pwd.addTextChangedListener(new OnTextChangedListener(et_register_pwd));
        et_register_invite_code.addTextChangedListener(new OnTextChangedListener(et_register_invite_code));*/
        tv_register.setOnClickListener(this);
        cb_register_eye.setChecked(true);
    }

//    class OnTextChangedListener implements TextWatcher {
//        private EditText editText;
//
//        private CharSequence loginMobile;
//        private CharSequence loginPwd;
//        private CharSequence registerMobile;
//        private CharSequence registerVerifyCode;
//        private CharSequence registerPwd;
//        private CharSequence registerInviteCode;
//
//        public OnTextChangedListener(EditText editText) {
//            this.editText = editText;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            int id = editText.getId();
//            if (id == R.id.et_login_mobile) {
//                loginMobile = s;
//            } else if (id == R.id.et_login_pwd) {
//                loginPwd = s;
//            } else if (id == R.id.et_register_mobile) {
//                registerMobile = s;
//            } else if (id == R.id.et_register_verify_code) {
//                registerVerifyCode = s;
//            } else if (id == R.id.et_register_pwd) {
//                registerPwd = s;
//            } else if (id == R.id.et_register_invite_code) {
//                registerInviteCode = s;
//            }
//        }
//
//    }
}
