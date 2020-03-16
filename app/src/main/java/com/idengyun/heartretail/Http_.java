package com.idengyun.heartretail;

import android.support.v4.app.Fragment;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.model.response.UserNickBean;
import com.idengyun.usermodule.HRUser;

public final class Http_ {
    public static void changeNick(Fragment fragment, final String nickname, final String inviteCode, JsonCallback<UserNickBean> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeNick())
                .fragment(fragment)
                .clazz(UserNickBean.class)
                .params("userId", HRUser.getId())
                .params("nickName", nickname)
                .params("invitationCode", inviteCode)
                .build();
        NetApi.<UserNickBean>getData(netOption, callback);
    }
}
