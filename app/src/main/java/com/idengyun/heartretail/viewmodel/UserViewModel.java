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
import com.idengyun.heartretail.model.response.MobileBindBean;
import com.idengyun.heartretail.model.response.PersonalDataBean;
import com.idengyun.heartretail.model.response.PwdModifyBean;
import com.idengyun.heartretail.model.response.RealVerifyBean;
import com.idengyun.heartretail.model.response.UserAvatarBean;
import com.idengyun.heartretail.model.response.UserNickBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 用户模块API
 *
 * @author aLang
 */
public final class UserViewModel extends ViewModel {

    public static UserViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(UserViewModel.class);
    }

    private final MutableLiveData<PersonalDataBean> userInfoLiveData;
    private final MutableLiveData<UserAvatarBean> avatarModifyLiveData;
    private final MutableLiveData<UserNickBean> nicknameModifyLiveData;
    private final MutableLiveData<PwdModifyBean> pwdModifyLiveData;
    private final MutableLiveData<MobileBindBean> mobileBindLiveData;
    private final MutableLiveData<RealVerifyBean> realVerifyLiveData;

    public UserViewModel() {
        super();
        userInfoLiveData = new MutableLiveData<>();
        avatarModifyLiveData = new MutableLiveData<>();
        nicknameModifyLiveData = new MutableLiveData<>();
        pwdModifyLiveData = new MutableLiveData<>();
        mobileBindLiveData = new MutableLiveData<>();
        realVerifyLiveData = new MutableLiveData<>();
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
    public void requestRealVerify(Fragment fragment, String mobile, String nationality, String certificateType, String certificateCode, String bankCode, String verifyCode) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.authIdentity())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(RealVerifyBean.class)
                .params("mobile", mobile)
                .params("nationality", nationality)
                .params("certificateType", certificateType)
                .params("certificateCode", certificateCode)
                .params("bankCode", bankCode)
                .params("identifyCode", verifyCode)
                .params("userId", HRUser.getId())
                .build();
        NetApi.getData(netOption, new JsonCallback<RealVerifyBean>(netOption) {
            @Override
            public void onSuccess(Response<RealVerifyBean> response) {
                realVerifyLiveData.setValue(response.body());
            }
        });
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

    public LiveData<MobileBindBean> getBindMobile() {
        return mobileBindLiveData;
    }

    public LiveData<RealVerifyBean> getRealVerify() {
        return realVerifyLiveData;
    }
}
