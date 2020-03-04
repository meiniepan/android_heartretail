package com.idengyun.usermodule;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;

/**
 * 查询主配置API返回的特定URL
 *
 * @author aLang
 */
public final class HRConfig {

    /* 获取商品评价URL */
    public static String getGoodsEvaluationUrl() {
        return getMainConfigUrl("evaluationList");
    }

    /* 获取登录URL */
    public static String getLoginUrl() {
        return getMainConfigUrl("login");
    }

    /* 获取注册URL */
    public static String getRegisterUrl() {
        return getMainConfigUrl("register");
    }

    /* 获取手机验证码URL */
    public static String getVerifyUrl() {
        return getMainConfigUrl("getIdentifyCode");
    }

    /* 不要使用 */
    private static String getMainConfigUrl(String key) {
        return SharedPreferencesUtil.getData(HRConst.CONTEXT, HRConst.XML_FILE_NAME_MAIN_CONFIG, key, "");
    }

}
