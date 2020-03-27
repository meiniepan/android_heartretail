package com.idengyun.heartretail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.beans.KVRealVerify;
import com.idengyun.heartretail.beans.MobileBindBean;
import com.idengyun.heartretail.beans.PersonalDataBean;
import com.idengyun.heartretail.beans.PwdModifyBean;
import com.idengyun.heartretail.beans.RealVerifyBean;
import com.idengyun.heartretail.beans.UserAvatarBean;
import com.idengyun.heartretail.beans.UserNickBean;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.lzy.okgo.model.Response;

/**
 * 用户模块API
 *
 * @author aLang
 */
public final class SettingViewModel extends ViewModel {

    public static SettingViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(SettingViewModel.class);
    }

    private final MutableLiveData<VerifyCodeBean> verifyCodeLiveData;
    private final MutableLiveData<PersonalDataBean> userInfoLiveData;

    /* 个人资料 */
    private final MutableLiveData<UserAvatarBean> avatarModifyLiveData;
    private final MutableLiveData<UserNickBean> nicknameModifyLiveData;

    /* 账号管理 */
    private final MutableLiveData<PwdModifyBean> pwdModifyLiveData;
    private final MutableLiveData<MobileBindBean> idVerifyLiveData;
    private final MutableLiveData<MobileBindBean> mobileBindLiveData;
    private final MutableLiveData<RealVerifyBean> realVerifyLiveData;

    public SettingViewModel() {
        super();
        verifyCodeLiveData = new MutableLiveData<>();
        userInfoLiveData = new MutableLiveData<>();
        avatarModifyLiveData = new MutableLiveData<>();
        nicknameModifyLiveData = new MutableLiveData<>();
        pwdModifyLiveData = new MutableLiveData<>();
        idVerifyLiveData = new MutableLiveData<>();
        mobileBindLiveData = new MutableLiveData<>();
        realVerifyLiveData = new MutableLiveData<>();
    }

    public LiveData<VerifyCodeBean> getVerifyCode() {
        return verifyCodeLiveData;
    }

    public LiveData<PersonalDataBean> getUserInfo() {
        return userInfoLiveData;
    }

    public LiveData<UserAvatarBean> getModifyAvatar() {
        return avatarModifyLiveData;
    }

    public LiveData<UserNickBean> getModifyNickname() {
        return nicknameModifyLiveData;
    }

    public LiveData<PwdModifyBean> getModifyPwd() {
        return pwdModifyLiveData;
    }

    public LiveData<MobileBindBean> getIdentityVerify() {
        return idVerifyLiveData;
    }

    public LiveData<MobileBindBean> getBindMobile() {
        return mobileBindLiveData;
    }

    public LiveData<RealVerifyBean> getRealVerify() {
        return realVerifyLiveData;
    }

    /* 发送验证码 */
    public void requestVerifyCode(Fragment fragment, String identifyType) {
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
                verifyCodeLiveData.setValue(response.body());
            }
        });
    }

    /* 查询用户信息 */
    public void requestUserInfo(Fragment fragment) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryUserId())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(PersonalDataBean.class)
                .params("userId", HRUser.getId())
                .build();
        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<PersonalDataBean>(netOption) {
            @Override
            public void onSuccess(Response<PersonalDataBean> response) {
                userInfoLiveData.setValue(response.body());
            }
        });
    }

    /* 修改用户头像 */
    public void requestModifyAvatar(Fragment fragment, String avatarUrl) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeIcon())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(UserAvatarBean.class)
                .params("url", avatarUrl)
                .params("userId", HRUser.getId())
                .build();
        NetApi.getData(netOption, new JsonCallback<UserAvatarBean>(netOption) {
            @Override
            public void onSuccess(Response<UserAvatarBean> response) {
                avatarModifyLiveData.setValue(response.body());
            }
        });
    }

    /* 修改用户昵称 修改用户好友邀请码 */
    public void requestModifyNickname(Fragment fragment, String nickname, String inviteCode) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeNick())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(UserNickBean.class)
                .params("userId", HRUser.getId())
                .params("nickName", nickname)
                .params("invitationCode", inviteCode)
                .build();
        NetApi.getData(netOption, new JsonCallback<UserNickBean>(netOption) {
            @Override
            public void onSuccess(Response<UserNickBean> response) {
                nicknameModifyLiveData.setValue(response.body());
            }
        });
    }

    /* 修改用户密码 */
    public void requestModifyPwd(Fragment fragment, String verifyCode, String pwd) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changePwd())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(PwdModifyBean.class)
                .params("mobile", HRUser.getMobile())
                .params("identifyCode", verifyCode)
                .params("pwd", pwd)
                .build();
        NetApi.getData(netOption, new JsonCallback<PwdModifyBean>(netOption) {
            @Override
            public void onSuccess(Response<PwdModifyBean> response) {
                pwdModifyLiveData.setValue(response.body());
            }
        });
    }

    /* 身份验证 */
    public void requestIdentityVerify(Fragment fragment, String identifyCode) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.smsIdentity())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(MobileBindBean.class)
                .params("mobile", HRUser.getMobile())
                .params("identifyCode", identifyCode)
                .params("userId", HRUser.getId())
                .params("identifyType", 4)
                .build();
        NetApi.getData(netOption, new JsonCallback<MobileBindBean>(netOption) {
            @Override
            public void onSuccess(Response<MobileBindBean> response) {
                idVerifyLiveData.setValue(response.body());
            }
        });
    }

    /* 绑定新手机号码 */
    public void requestBindMobile(Fragment fragment, String mobile, String verifyCode) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeMobile())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(MobileBindBean.class)
                .params("userId", HRUser.getId())
                .params("mobile", mobile)
                .params("identifyCode", verifyCode)
                .build();

        NetApi.getData(netOption, new JsonCallback<MobileBindBean>(netOption) {
            @Override
            public void onSuccess(Response<MobileBindBean> response) {
                mobileBindLiveData.setValue(response.body());
            }
        });
    }

    /* 实名认证 */
    public void requestRealVerify(Fragment fragment, KVRealVerify kvRealVerify) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.authIdentity())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(RealVerifyBean.class)
                .params(kvRealVerify.toMap())
                .build();
        NetApi.getData(netOption, new JsonCallback<RealVerifyBean>(netOption) {
            @Override
            public void onSuccess(Response<RealVerifyBean> response) {
                realVerifyLiveData.setValue(response.body());
            }
        });
    }
}
