package com.dengyun.splashmodule.config;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 主配置接口的sp常量
 * @Author: zhoubo
 * @CreateDate: 2020-03-04 09:18
 */
public class SpMainConfigConstants {
    public static final String spFileName = "main";

    /* 不要使用 */
    private static String getMainConfigUrl(String key) {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, key, "");
    }

    public static String index() {
        return getMainConfigUrl("index");
    }

    /* 获取商品评价URL */
    public static String evaluationList(){
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


}
