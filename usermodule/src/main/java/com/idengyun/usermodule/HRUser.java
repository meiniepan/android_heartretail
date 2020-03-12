package com.idengyun.usermodule;

import android.text.TextUtils;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * 用户信息查询
 *
 * @author aLang
 */
public final class HRUser {

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
    public static String getInvitationCode() {
        return getValueFromUserInfo("invitationCode");
    }

    /* 获取用户头像地址 */
    public static String getHeadUrl() {
        return getValueFromUserInfo("headUrl");
    }

    /* 获取用户昵称 */
    public static String getNickName() {
        return getValueFromUserInfo("nickName");
    }

    /* 不要使用这个 */
    private static String getValueFromUserInfo(String key) {
        return SharedPreferencesUtil.getData(Utils.getApp(), HRConst.XML_FILE_NAME_USER_INFO, key, "");
    }

    private HRUser() {
    }
}
