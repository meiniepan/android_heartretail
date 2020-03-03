package com.idengyun.heartretail;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;

/**
 * 用户信息查询
 *
 * @author aLang
 */
public final class HRUser {

    /* 获取用户ID */
    public static int getId() {
        return SharedPreferencesUtil.getData(HRConst.CONTEXT, HRConst.XML_FILE_NAME_USER_INFO, "id", -1);
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
    private static <T> T getValueFromUserInfo(String key) {
        return SharedPreferencesUtil.getData(HRConst.CONTEXT, HRConst.XML_FILE_NAME_USER_INFO, key, "");
    }

}
