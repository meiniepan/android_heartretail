package com.idengyun.usermodule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.RegexUtils;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.usermodule.beans.BLogin;
import com.idengyun.usermodule.beans.BRegister;
import com.idengyun.usermodule.beans.BVerify;
import com.idengyun.usermodule.beans.KVLogin;
import com.idengyun.usermodule.beans.KVRegister;
import com.idengyun.usermodule.beans.KVVerify;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

import java.util.Map;

/**
 * 登录页面
 * 注册页面
 *
 * @author aLang
 */
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
//        int id = v.getId();
//        switch (id) {
//            case R.id.et_login_mobile:
//                if (!hasFocus && !RegexUtils.isMobileSimple(et_login_mobile.getEditableText())) {
//                    et_login_mobile.requestFocus();
//                    ToastUtils.showShort("请输入正确的手机号");
//                }
//                break;
//            case R.id.et_login_pwd:
//                if (!hasFocus && et_login_pwd.length() < 1) {
//                    et_login_pwd.requestFocus();
//                    ToastUtils.showShort("请输入密码");
//                }
//                break;
//            case R.id.et_register_mobile:
//                if (!hasFocus && !RegexUtils.isMobileSimple(et_register_mobile.getEditableText())) {
//                    et_register_mobile.requestFocus();
//                    ToastUtils.showShort("请输入正确的手机号");
//                }
//                break;
//            case R.id.et_register_verify_code:
//                if (!hasFocus && et_register_verify_code.length() < 1) {
//                    et_register_verify_code.requestFocus();
//                    ToastUtils.showShort("请输入验证码");
//                }
//                break;
//            case R.id.et_register_pwd:
//                if (!hasFocus && et_register_pwd.length() < 1) {
//                    et_register_pwd.requestFocus();
//                    ToastUtils.showShort("请输入密码");
//                }
//                break;
//            case R.id.et_register_invite_code:
//                // do nothing
//                break;
//            default:
//                // do nothing
//                break;
//        }
    }

    /* 请求注册API */
    private void requestRegister() {
        if (!RegexUtils.isMobileSimple(et_register_mobile.getEditableText())) {
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

        Map<String, Object> map = new KVRegister(
                et_register_mobile.getEditableText().toString(),
                HRConst.VERSION,
                et_register_verify_code.getEditableText().toString(),
                et_register_pwd.getEditableText().toString(),
                et_register_invite_code.getEditableText().toString(),
                HRConst.PHONE_IMEI,
                HRConst.PHONE_TYPE,
                HRConst.PLATFORM,
                HRConst.APP_NAME
        ).toMap();

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getRegisterUrl())
                .activity(this)
                .isShowDialog(true)
                .params(map)
                .clazz(BRegister.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<BRegister>(netOption) {
            @Override
            public void onSuccess(Response<BRegister> response) {
                if (response.code() != 200) {
                    return;
                }

                BRegister body = response.body();
                if (body == null) {
                    return;
                }

                if (!"200".equals(body.code)) {
                    return;
                }

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
        if (RegexUtils.isMobileSimple(et_register_mobile.getEditableText())) {
            ToastUtils.showShort("请输入有效手机号码");
            return;
        }

        startTimer();

        String query = new KVVerify(
                et_register_mobile.getEditableText().toString(),
                HRConst.IDENTIFY_TYPE_0,
                HRConst.VERSION,
                HRConst.PLATFORM
        ).toQuery();
        String url = SpMainConfigConstants.getVerifyUrl();
        url += query;

        NetOption netOption = NetOption.newBuilder(url)
                .activity(this)
                .isShowDialog(true)
                .clazz(BVerify.class)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<BVerify>(netOption) {
            @Override
            public void onSuccess(Response<BVerify> response) {
                if (200 != response.code()) {
                    ToastUtils.showLong("验证码发送失败");
                    return;
                }

                BVerify body = response.body();
                if (null == body) {
                    ToastUtils.showLong("验证码发送失败");
                    return;
                }

                if (!"200".equals(body.code)) {
                    ToastUtils.showLong(body.msg);
                    return;
                }

                ToastUtils.showLong("验证码已发出");
            }
        });
    }

    /* 请求登录API */
    private void requestLogin() {
        if (!RegexUtils.isMobileSimple(et_login_mobile.getEditableText())) {
            ToastUtils.showShort("请输入有效手机号码");
            return;
        }

        if (et_login_pwd.length() < 1) {
            ToastUtils.showShort("请输入有效密码");
            return;
        }

        KVLogin login = new KVLogin(
                et_login_mobile.getEditableText().toString(),
                HRConst.VERSION,
                et_login_pwd.getEditableText().toString(),
                HRConst.PHONE_IMEI,
                HRConst.PLATFORM);
        Map<String, Object> map = login.toMap();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getLoginUrl())
                .activity(this)
                .isShowDialog(true)
                .params(map)
                .isInterceptErrorCode(false)
                .clazz(BLogin.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<BLogin>(netOption) {
            @Override
            public void onSuccess(Response<BLogin> response) {
                if (response.code() != 200) {
                    return;
                }

                BLogin body = response.body();
                if (body == null) {
                    return;
                }

                if (!"200".equals(body.code)) {
                    return;
                }

                // oastUtils.showShort("登录成功");

                /* 保存用户信息 */
                SharedPreferencesUtil.saveDataBean(HRConst.CONTEXT, HRConst.XML_FILE_NAME_USER_INFO, body);
                startVerifyActivity();
            }
        });
    }

    /* 开启忘记密码界面 */
    private void startForgetPwdActivity() {
        ModifyPwdActivity.start(getContext());
    }

    /* 开启设备认证页面 */
    private void startVerifyActivity() {
        VerifyDeviceActivity.start(getContext());
    }

    private void startTimer() {
        tv_verify_code.setEnabled(false);

        if (timer == null) {
            timer = new SecondsTimer(60L, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    tv_verify_code.setText(secondsUntilFinished + "s后重发");
                }

                @Override
                public void onFinish() {
                    tv_verify_code.setText("获取验证码");
                    tv_verify_code.setEnabled(true);
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
        et_login_mobile.addTextChangedListener(new OnTextChangedListener(et_login_mobile));
        et_login_pwd.addTextChangedListener(new OnTextChangedListener(et_login_pwd));
        cb_login_eye.setOnCheckedChangeListener(this);
        tv_forget_pwd.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        cb_login_eye.setChecked(false);

        et_register_mobile.setOnFocusChangeListener(this);
        et_register_verify_code.setOnFocusChangeListener(this);
        et_register_pwd.setOnFocusChangeListener(this);
        et_register_invite_code.setOnFocusChangeListener(this);
        et_register_mobile.addTextChangedListener(new OnTextChangedListener(et_register_mobile));
        et_register_verify_code.addTextChangedListener(new OnTextChangedListener(et_register_verify_code));
        tv_verify_code.setOnClickListener(this);
        et_register_pwd.addTextChangedListener(new OnTextChangedListener(et_register_pwd));
        cb_register_eye.setOnCheckedChangeListener(this);
        et_register_invite_code.addTextChangedListener(new OnTextChangedListener(et_register_invite_code));
        tv_register.setOnClickListener(this);
        cb_register_eye.setChecked(true);
    }

    class OnTextChangedListener implements TextWatcher {
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
