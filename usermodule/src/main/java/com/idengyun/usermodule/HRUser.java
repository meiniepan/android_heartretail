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

    /* 用户是否实名认证 */
    public static boolean isAuthenticated() {
        return getInt("authIdentity") == 1;
    }

    /* 是否是新手机登录 */
    public static boolean isNewDevice() {
        return getInt("isnewPhoneImei") == 1;
    }

    /* 用户是否登录 */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getId()) && !TextUtils.isEmpty(getToken());
    }

    /* 获取用户token */
    public static String getToken() {
        return getString("token");
    }

    /* 获取用户ID */
    public static String getId() {
        return getString("id");
    }

    /* 获取用户手机号码 */
    public static String getMobile() {
        return getString("mobile");
    }

    /* 获取用户好友邀请码 */
    public static String getInviteCode() {
        return getString("invitationCode");
    }

    /* 获取用户头像地址 */
    public static String getAvatar() {
        return getString("headUrl");
    }

    /* 获取用户昵称 */
    public static String getNickname() {
        return getString("nickName");
    }

    /* 保存用户好友邀请码 */
    public static void saveInviteCode(String inviteCode) {
        putValue("invitationCode", inviteCode);
    }

    /* 保存用户手机号 */
    public static void saveMobile(String mobile) {
        putValue("mobile", mobile);
    }

    /* 保存用户昵称 */
    public static void saveNickname(String nickname) {
        putValue("nickName", nickname);
    }

    /* 保存用户头像地址 */
    public static void saveAvatar(String avatar) {
        putValue("headUrl", avatar);
    }

    /**
     * 清空用户登录信息
     */
    public static void clear() {
        SharedPreferencesUtil.removeAll(context, fileName);
    }

    static void saveLoginBean(LoginBean loginBean) {
        LoginBean.Data data = loginBean.data;
        LoginBean.Data.User user = data.user;
        putValue("token", data.token);
        putValue("isnewPhoneImei", data.isnewPhoneImei);
        putValue("id", user.id);
        putValue("mobile", user.mobile);
        putValue("invitationCode", user.invitationCode);
        putValue("ownerInvitationCode", user.ownerInvitationCode);
        putValue("headUrl", user.headUrl);
        putValue("nickName", user.nickName);
        putValue("authIdentity", user.authIdentity);
    }

    private static Application context = Utils.getApp();
    private static String fileName = HRConst.XML_FILE_NAME_USER_INFO;

    /* 不要使用这个 */
    private static String getString(String key) {
        return SharedPreferencesUtil.getData(context, fileName, key, "");
    }

    /* 不要使用这个 */
    private static int getInt(String key) {
        return SharedPreferencesUtil.getData(context, fileName, key, 0);
    }

    /* 不要使用这个 */
    private static void putValue(String key, Object value) {
        SharedPreferencesUtil.saveData(context, fileName, key, value);
    }

    private HRUser() {
    }
}
