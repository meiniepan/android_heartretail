package com.idengyun.usermodule;

import android.app.Application;
import android.text.TextUtils;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.idengyun.usermodule.beans.LoginBean;

/**
 * 用户信息查询
 *
 * @author aLang
 */
public final class HRUser {
    private static Application context = Utils.getApp();
    private static String fileName = HRConst.XML_FILE_NAME_USER_INFO;

    /* 用户是否实名认证 */
    public static boolean isAuthenticated() {
        int authIdentity = SharedPreferencesUtil.getData(context, HRConst.XML_FILE_NAME_USER_INFO, "authIdentity", 0);
        return authIdentity == 1;
    }

    /* 是否是新手机登录 */
    public static boolean isNewDevice() {
        int isnewPhoneImei = SharedPreferencesUtil.getData(context, HRConst.XML_FILE_NAME_USER_INFO, "isnewPhoneImei", 0);
        return isnewPhoneImei == 1;
    }

    /* 用户是否登录 */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getId()) && !TextUtils.isEmpty(getToken());
    }

    /* 获取用户token */
    public static String getToken() {
        return getValueFromUserInfo("token");
    }

    /* 获取用户ID */
    public static String getId() {
        return getValueFromUserInfo("id");
    }

    /* 获取用户手机号码 */
    public static String getMobile() {
        return getValueFromUserInfo("mobile");
    }

    /* 获取用户好友邀请码 */
    public static String getInviteCode() {
        return getValueFromUserInfo("invitationCode");
    }

    /* 获取用户头像地址 */
    public static String getAvatar() {
        return getValueFromUserInfo("headUrl");
    }

    /* 获取用户昵称 */
    public static String getNickname() {
        return getValueFromUserInfo("nickName");
    }

    /* 不要使用这个 */
    private static String getValueFromUserInfo(String key) {
        return SharedPreferencesUtil.getData(Utils.getApp(), HRConst.XML_FILE_NAME_USER_INFO, key, "");
    }

    /* 保存用户手机号 */
    public static void saveMobile(String mobile) {
        SharedPreferencesUtil.saveData(context, fileName, "mobile", mobile);
    }

    /* 保存用户昵称 */
    public static void saveNickname(String nickname) {
        SharedPreferencesUtil.saveData(context, fileName, "nickName", nickname);
    }

    /* 保存用户头像地址 */
    public static void saveAvatar(String avatar) {
        SharedPreferencesUtil.saveData(context, fileName, "headUrl", avatar);
    }

    /**
     * 清空用户登录信息
     */
    public static void clear() {
        SharedPreferencesUtil.removeAll(context, HRConst.XML_FILE_NAME_USER_INFO);
    }

    static void saveLoginBean(LoginBean loginBean) {
        LoginBean.Data data = loginBean.data;
        LoginBean.Data.User user = data.user;
        SharedPreferencesUtil.saveData(context, fileName, "token", data.token);
        SharedPreferencesUtil.saveData(context, fileName, "isnewPhoneImei", data.isnewPhoneImei);
        SharedPreferencesUtil.saveData(context, fileName, "id", user.id);
        SharedPreferencesUtil.saveData(context, fileName, "mobile", user.mobile);
        SharedPreferencesUtil.saveData(context, fileName, "invitationCode", user.invitationCode);
        SharedPreferencesUtil.saveData(context, fileName, "headUrl", user.headUrl);
        SharedPreferencesUtil.saveData(context, fileName, "nickName", user.nickName);
        SharedPreferencesUtil.saveData(context, fileName, "authIdentity", user.authIdentity);
    }

    private HRUser() {
    }
}
