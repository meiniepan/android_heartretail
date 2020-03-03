package com.idengyun.heartretail;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;

/**
 * 用户信息查询
 *
 * @author aLang
 */
public final class HRUserUtil {

    public static int getId() {
        return getValueFromUserInfo("id");
    }

    public static String getMobile() {
        return getValueFromUserInfo("mobile");
    }

    public static String getInvitationCode() {
        return getValueFromUserInfo("invitationCode");
    }

    public static String getHeadUrl() {
        return getValueFromUserInfo("headUrl");
    }

    public static String getNickName() {
        return getValueFromUserInfo("nickName");
    }

    private static <T extends Object> T getValueFromUserInfo(String key) {
        return SharedPreferencesUtil.getData(HRConst.CONTEXT, HRConst.XML_FILE_NAME_USER_INFO, key, "");
    }

}
