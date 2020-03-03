package com.idengyun.heartretail.login;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.RegexUtils;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRConst;
import com.idengyun.heartretail.MainActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.request.KVLogin;
import com.idengyun.heartretail.model.request.KVRegister;
import com.idengyun.heartretail.model.request.KVVerify;
import com.idengyun.heartretail.model.response.BLogin;
import com.idengyun.heartretail.model.response.BRegister;
import com.idengyun.heartretail.model.response.BVerify;
import com.lzy.okgo.model.Response;

import java.util.Map;

/**
 * 登录页面
 * 注册页面
 *
 * @author aLang
 */
public final class LoginFragment extends BaseFragment
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener {

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
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
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
        switch (id) {
            case R.id.tv_forget_pwd:
                startForgetPwdActivity();
                break;
            case R.id.tv_login:
                requestLogin();
                break;
            case R.id.tv_verify_code:
                sendVerifyCode();
                break;
            case R.id.tv_register:
                requestRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        TransformationMethod method = isChecked ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance();
        switch (id) {
            case R.id.cb_login_eye:
                et_login_pwd.setTransformationMethod(method);
                break;
            case R.id.cb_register_eye:
                et_register_pwd.setTransformationMethod(method);
                break;
            default:
                break;
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

        NetOption netOption = NetOption.newBuilder("http://10.10.8.22:3000/mock/39/user/register")
                .activity(getActivity())
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
        String url = "http://10.10.8.22:3000/mock/39/user/send/msg" + query;

        NetOption netOption = NetOption.newBuilder(url)
                .activity(getActivity())
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

        NetOption netOption = NetOption.newBuilder("http://10.10.8.22:3000/mock/39/user/login")
                .activity(getActivity())
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

                SharedPreferencesUtil.saveDataBean(HRConst.CONTEXT, HRConst.XML_FILE_NAME_USER_INFO, body);
                // oastUtils.showShort("登录成功");
                startMainActivity();
            }
        });
    }

    /* 开启忘记密码界面 */
    private void startForgetPwdActivity() {
        MainActivity.start(getContext());
    }

    private void startMainActivity() {
        MainActivity.start(getContext());
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
        et_register_invite_code = view.findViewById(R.id.et_register_invite_code);
        tv_register = view.findViewById(R.id.tv_register);

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
            switch (id) {
                case R.id.et_login_mobile:
                    loginMobile = s;
                    break;
                case R.id.et_login_pwd:
                    loginPwd = s;
                    break;
                case R.id.et_register_mobile:
                    registerMobile = s;
                    break;
                case R.id.et_register_verify_code:
                    registerVerifyCode = s;
                    break;
                case R.id.et_register_pwd:
                    registerPwd = s;
                    break;
                case R.id.et_register_invite_code:
                    registerInviteCode = s;
                    break;
                default:
                    break;
            }
        }

    }
}
