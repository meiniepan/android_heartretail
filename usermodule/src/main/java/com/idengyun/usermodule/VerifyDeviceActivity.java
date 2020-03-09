package com.idengyun.usermodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiSimpleBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.idengyun.usermodule.beans.KVVerifyDevice;
import com.idengyun.usermodule.utils.SecondsTimer;
import com.idengyun.usermodule.utils.TransformPhoneNumUtil;
import com.lzy.okgo.model.Response;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020/3/3 0003 10:39
 */
public class VerifyDeviceActivity extends BaseActivity {
    @BindView(R2.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R2.id.et_v_code)
    EditText etVCode;
    @BindView(R2.id.tv_get_v_code)
    TextView tvGetVCode;
    @BindView(R2.id.confirm_verify_device)
    TextView confirmVerifyDevice;
    private SecondsTimer timer;

    public static void start(Context context) {
        Intent starter = new Intent(context, VerifyDeviceActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_device;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvPhoneNum.setText(TransformPhoneNumUtil.transform(HRUser.getMobile()));
    }


    @OnClick({R2.id.tv_get_v_code, R2.id.confirm_verify_device})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_get_v_code) {
            sendVerifyCode();
        } else if (i == R.id.confirm_verify_device) {
            verifyDevice();
        }
    }

    /* 发送手机验证码 */
    private void sendVerifyCode() {

        startTimer();

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getIdentifyCode())
                .activity(this)
                .params("mobile", HRUser.getMobile())
                .params("identifyType", HRConst.IDENTIFY_TYPE_1)
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

    /* 新设备验证 */
    private void verifyDevice() {

        if (etVCode.length() < 1) {
            ToastUtils.showShort("请输入有效验证码");
            return;
        }

        KVVerifyDevice mapV = new KVVerifyDevice(
                HRConst.PHONE_IMEI,
                etVCode.getEditableText().toString(),
                AppUtils.getAppVersionName(),
                HRUser.getId(),
                HRConst.PHONE_TYPE,
                HRConst.PLATFORM);
        Map<String, Object> map = mapV.toMap();

        NetOption netOption = NetOption.newBuilder("http://10.10.8.22:3000/mock/39/user/add/phone")
                .activity(this)
                .isShowDialog(true)
                .params(map)
                .clazz(ApiSimpleBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiSimpleBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiSimpleBean> response) {
                ToastUtils.showShort("验证新设备成功");
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        //MainActivity.start(getContext());
    }

    private void startTimer() {
        tvGetVCode.setEnabled(false);

        if (timer == null) {
            timer = new SecondsTimer(60L, new SecondsTimer.Callback() {
                @Override
                public void onTick(long secondsUntilFinished) {
                    tvGetVCode.setText(secondsUntilFinished + "s后重发");
                }

                @Override
                public void onFinish() {
                    tvGetVCode.setText("再次获取");
                    tvGetVCode.setEnabled(true);
                }
            });
        }

        timer.start();
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
