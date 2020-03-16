package com.idengyun.heartretail;

import android.arch.lifecycle.Observer;
import android.support.v4.app.Fragment;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.model.response.BalanceBean;
import com.idengyun.heartretail.model.response.MobileBindBean;
import com.idengyun.heartretail.model.response.PersonalDataBean;
import com.idengyun.heartretail.model.response.ProtocolsBean;
import com.idengyun.heartretail.model.response.PwdModifyBean;
import com.idengyun.heartretail.model.response.UserAvatarBean;
import com.idengyun.heartretail.model.response.UserNickBean;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.lzy.okgo.model.Response;

import java.util.List;


public final class HRSession {

    /* 查询用户信息 */
    public static void session_01(Fragment fragment, Observer<PersonalDataBean.Data> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryUserId())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(PersonalDataBean.class)
                .params("userId", HRUser.getId())
                .build();
        NetApi.<PersonalDataBean>getData(RequestMethod.GET, netOption, new JsonCallback<PersonalDataBean>(netOption) {
            @Override
            public void onSuccess(Response<PersonalDataBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }

    /* 查询我的余额信息 */
    public static void session_02(Fragment fragment, Observer<BalanceBean.Data> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.balanceInfo())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(BalanceBean.class)
                .params("userId", HRUser.getId())
                .build();
        NetApi.<BalanceBean>getData(RequestMethod.GET, netOption, new JsonCallback<BalanceBean>(netOption) {
            @Override
            public void onSuccess(Response<BalanceBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }

    /* 修改用户头像 */
    public static void session_03(Fragment fragment,
                                  String avatarUrl,
                                  Observer<UserAvatarBean.Data> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeIcon())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(UserAvatarBean.class)
                .params("url", avatarUrl)
                .params("userId", HRUser.getId())
                .build();
        NetApi.<UserAvatarBean>getData(netOption, new JsonCallback<UserAvatarBean>(netOption) {
            @Override
            public void onSuccess(Response<UserAvatarBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }

    /* 修改用户昵称 修改用户好友邀请码 */
    public static void session_04(Fragment fragment,
                                  String nickname,
                                  String inviteCode,
                                  Observer<UserNickBean.Data> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeNick())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(UserNickBean.class)
                .params("userId", HRUser.getId())
                .params("nickName", nickname)
                .params("invitationCode", inviteCode)
                .build();
        NetApi.<UserNickBean>getData(netOption, new JsonCallback<UserNickBean>(netOption) {
            @Override
            public void onSuccess(Response<UserNickBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }

    /* 修改用户密码 */
    public static void session_05(Fragment fragment,
                                  String verifyCode,
                                  String pwd,
                                  Observer<PwdModifyBean.Data> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changePwd())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(PwdModifyBean.class)
                .params("mobile", HRUser.getMobile())
                .params("identifyCode", verifyCode)
                .params("pwd", pwd)
                .build();
        NetApi.<PwdModifyBean>getData(netOption, new JsonCallback<PwdModifyBean>(netOption) {
            @Override
            public void onSuccess(Response<PwdModifyBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }

    /* 发送验证码 */
    public static void session_06(Fragment fragment,
                                  String identifyType,
                                  Observer<VerifyCodeBean.Data> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.getIdentifyCode())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(VerifyCodeBean.class)
                .params("mobile", HRUser.getMobile())
                .params("identifyType", identifyType)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<VerifyCodeBean>(netOption) {
            @Override
            public void onSuccess(Response<VerifyCodeBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }

    /* 绑定新手机号码 */
    public static void session_07(Fragment fragment,
                                  String mobile,
                                  String verifyCode,
                                  Observer<MobileBindBean.Data> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeMobile())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(MobileBindBean.class)
                .params("mobile", mobile)
                .params("identifyCode", verifyCode)
                .build();

        NetApi.<MobileBindBean>getData(netOption, new JsonCallback<MobileBindBean>(netOption) {
            @Override
            public void onSuccess(Response<MobileBindBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }

    /* 查询协议 */
    public static void session_08(Fragment fragment, int[] protocols, Observer<List<ProtocolsBean.Data>> callback) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.protocolDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(ProtocolsBean.class)
                .params("protocolIds", protocols)
                .build();
        NetApi.<ProtocolsBean>getData(netOption, new JsonCallback<ProtocolsBean>(netOption) {
            @Override
            public void onSuccess(Response<ProtocolsBean> response) {
                callback.onChanged(response.body().data);
            }
        });
    }
}
