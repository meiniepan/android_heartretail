package com.idengyun.usermodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiSimpleBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.RegexUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.beans.KVModifyPwd;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.lzy.okgo.model.Response;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020/3/3 0003 10:38
 */
public class ModifyPwdActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R2.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R2.id.et_v_code1)
    EditText etVCode1;
    @BindView(R2.id.tv_get_v_code1)
    TextView tvGetVCode1;
    @BindView(R2.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R2.id.cb_pwd_eye)
    CheckBox cbPwdEye;
    @BindView(R2.id.tv_modify_pwd)
    TextView tvModifyPwd;
    private SecondsTimer timer;

    public static void start(Context context) {
        Intent starter = new Intent(context, ModifyPwdActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        cbPwdEye.setOnCheckedChangeListener(this);
    }


    @OnClick({R2.id.tv_get_v_code1, R2.id.tv_modify_pwd})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_get_v_code1) {
            sendVerifyCode();
        } else if (id == R.id.tv_modify_pwd) {
            modifyPwd();
        }
    }

    /* 发送手机验证码 */
    private void sendVerifyCode() {
        if (!RegexUtils.isMobileSimple(etPhoneNum.getEditableText())) {
            ToastUtils.showShort("请输入有效手机号码");
            return;
        }
        startTimer();

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getIdentifyCode())
                .activity(this)
                .params("mobile", etPhoneNum.getText().toString())
                .params("identifyType", HRConst.IDENTIFY_TYPE_2)
                .params("version", AppUtils.getAppVersionName())
                .params("platform", HRConst.PLATFORM)
                .isShowDialog(true)
                .clazz(VerifyCodeBean.class)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<VerifyCodeBean>(netOption) {
            @Override
            public void onSuccess(Response<VerifyCodeBean> response) {
                if (200 != response.code()) {
                    ToastUtils.showLong("验证码发送失败");
                    return;
                }

                VerifyCodeBean body = response.body();
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

    /* 修改密码api */
    private void modifyPwd() {
        if (!RegexUtils.isMobileSimple(etPhoneNum.getEditableText())) {
            ToastUtils.showShort("请输入有效手机号码");
            return;
        }

        if (etVCode1.length() < 1) {
            ToastUtils.showShort("请输入有效手机验证码");
            return;
        }

        if (etNewPwd.length() < 1) {
            ToastUtils.showShort("请输入有效密码");
            return;
        }

        Map<String, Object> map = new KVModifyPwd(
                etPhoneNum.getEditableText().toString(),
                etVCode1.getEditableText().toString(),
                etNewPwd.getEditableText().toString(),
                AppUtils.getAppVersionName(),
                HRConst.PLATFORM
        ).toMap();

        NetOption netOption = NetOption.newBuilder("http://10.10.8.22:3000/mock/39/user/change/pwd")
                .activity(this)
                .isShowDialog(true)
                .params(map)
                .clazz(ApiSimpleBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiSimpleBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiSimpleBean> response) {
                ToastUtils.showShort("修改密码成功");

            }
        });
    }

    private void startTimer() {
        tvGetVCode1.setEnabled(false);

        if (timer == null) {
            timer = new SecondsTimer(60L, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    tvGetVCode1.setText(secondsUntilFinished + "s后重发");
                }

                @Override
                public void onFinish() {
                    tvGetVCode1.setText("再次获取");
                    tvGetVCode1.setEnabled(true);
                }
            });
        }

        timer.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        TransformationMethod method = isChecked ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance();
        if (id == R.id.cb_pwd_eye) {
            etNewPwd.setTransformationMethod(method);
        }
    }
    private void cancelTimer() {
        if (timer != null) timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
