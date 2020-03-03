package com.idengyun.heartretail.login;

import android.view.View;
import android.widget.EditText;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRNetUrlForGet;
import com.idengyun.heartretail.model.response.BVerify;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

@Deprecated
public final class VerifySender {
    private boolean isSending;
    private EditText editText;
    private View verifyCodeSender;

    public VerifySender(EditText editText, View verifyCodeSender) {
        SecondsTimer timer = new SecondsTimer(60L, null);
        timer.start();

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /* 发送手机验证码 */
    public void sendVerifyCode() {
        if (isSending) return;
        isSending = true;

        if (editText.length() <= 0) {
            isSending = false;
            ToastUtils.showShort("请输入手机号");
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", "16601108200");
        map.put("identifyType", 0);
        map.put("version", "1.0.0");
        map.put("platform", "Android");
        String dealUrl = HRNetUrlForGet.getDealUrl(map);
        String url = "http://10.10.8.22:3000/mock/39/user/send/msg/" + dealUrl;

        NetOption netOption = NetOption.newBuilder(url)
                .isShowDialog(true)
                .clazz(BVerify.class)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<BVerify>(netOption) {
            @Override
            public void onSuccess(Response<BVerify> response) {
                if (response.code() != 200) {
                    isSending = false;
                    return;
                }

                BVerify body = response.body();
                if (body == null) {
                    isSending = false;
                    return;
                }

                if (!"200".equals(body.code)) {
                    isSending = false;
                    return;
                }

                isSending = true;
                ToastUtils.showShort("验证码已发出");
            }
        });
    }
}
