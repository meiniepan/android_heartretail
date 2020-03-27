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
import com.idengyun.heartretail.beans.Bean;
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

    public static SettingViewModel getInstance(@NonNull Fragment fragment) {
        return ViewModelProviders.of(fragment).get(SettingViewModel.class);
    }

    /* 查询用户信息 */
    private final MutableLiveData<PersonalDataBean> userInfoLiveData;

    /* 个人资料 */
    private final MutableLiveData<UserAvatarBean> avatarModifyLiveData;
    private final MutableLiveData<UserNickBean> nicknameModifyLiveData;

    /* 账号管理 */
    private final MutableLiveData<PwdModifyBean> pwdModifyLiveData;
    private final MutableLiveData<MobileBindBean> idVerifyLiveData;
    private final MutableLiveData<MobileBindBean> mobileBindLiveData;
    private final MutableLiveData<RealVerifyBean> realVerifyLiveData;
    private final MutableLiveData<Bean> bankCardChangeLiveData;

    /* 支付设置 */
    private final MutableLiveData<Bean> payPwdCheckLiveData;
    private final MutableLiveData<Bean> payPwdChangeLiveData;

    /* 获取验证码 */
    private final MutableLiveData<VerifyCodeBean> verifyCodeLiveData;

    public SettingViewModel() {
        super();
        userInfoLiveData = new MutableLiveData<>();
        verifyCodeLiveData = new MutableLiveData<>();

        avatarModifyLiveData = new MutableLiveData<>();
        nicknameModifyLiveData = new MutableLiveData<>();

        pwdModifyLiveData = new MutableLiveData<>();
        idVerifyLiveData = new MutableLiveData<>();
        mobileBindLiveData = new MutableLiveData<>();
        realVerifyLiveData = new MutableLiveData<>();
        bankCardChangeLiveData = new MutableLiveData<>();

        payPwdCheckLiveData = new MutableLiveData<>();
        payPwdChangeLiveData = new MutableLiveData<>();
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

    public LiveData<Bean> getBankCardChange() {
        return bankCardChangeLiveData;
    }

    public LiveData<Bean> getPayPwdCheck() {
        return payPwdCheckLiveData;
    }

    public LiveData<Bean> getPayPwdChange() {
        return payPwdChangeLiveData;
    }

    public LiveData<VerifyCodeBean> getVerifyCode() {
        return verifyCodeLiveData;
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

    /* 已绑定银行卡修改 */
    public void requestChangeBankCard(Fragment fragment, KVRealVerify.BankCard bankCard) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeBankCard())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(Bean.class)
                .params("userId", HRUser.getId())
                .params("bankCardNo", bankCard.bankCardNo)
                .params("bankMobile", bankCard.bankMobile)
                .params("userBankName", bankCard.userBankName)
                .params("userBankFullName", bankCard.userBankFullName)
                .params("userBankCity", bankCard.userBankCity)
                .params("userBankProvince", bankCard.userBankProvince)
                .params("bankCardType", bankCard.bankCardType)
                .params("cardType", bankCard.cardType)
                .params("name", bankCard.name)
                .params("bankUrl", bankCard.bankUrl)
                .params("identifyCode", bankCard.identifyCode)
                .build();
        NetApi.getData(netOption, new JsonCallback<Bean>(netOption) {
            @Override
            public void onSuccess(Response<Bean> response) {
                payPwdChangeLiveData.setValue(response.body());
            }
        });
    }

    /* 支付密码校验 */
    public void requestCheckPayPwd(Fragment fragment, String pwd, int type) {
        /* type:0提现1购买2修改支付密码 */
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.checkPayPwd())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(Bean.class)
                .params("userId", HRUser.getId())
                .params("pwd", pwd)
                .params("type", type)
                .build();
        NetApi.getData(netOption, new JsonCallback<Bean>(netOption) {
            @Override
            public void onSuccess(Response<Bean> response) {
                payPwdCheckLiveData.setValue(response.body());
            }
        });
    }

    /* 支付密码设置 */
    public void requestChangePayPwd(Fragment fragment, String pwd, int type) {
        /* type:0新增支付密码1修改支付密码2忘记支付密码 */
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changePayPwd())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(Bean.class)
                .params("userId", HRUser.getId())
                .params("pwd", pwd)
                .params("type", type)
                .build();
        NetApi.getData(netOption, new JsonCallback<Bean>(netOption) {
            @Override
            public void onSuccess(Response<Bean> response) {
                payPwdChangeLiveData.setValue(response.body());
            }
        });
    }

    /* 发送验证码 */
    public void requestVerifyCode(Fragment fragment, String identifyType) {
        /* 验证码类型0注册1换新设备2修改密码3忘记密码4绑定新手机号5旧手机号身份验证6（提现审核）实名认证 7支付密码设置8忘记支付密码 */
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
}
