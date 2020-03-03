package com.idengyun.heartretail;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;

/**
 * 查询主配置API返回的特定URL
 *
 * @author aLang
 */
public final class MCHelper {

    public static final String key_0 = "";
    public static final String key_1 = "";
    public static final String key_2 = "";
    public static final String key_3 = "";

    public static String getHRUrl(String key) {
        return SharedPreferencesUtil.getData(HRConst.CONTEXT, HRConst.XML_FILE_NAME_MAIN_CONFIG, key, "");
    }

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
