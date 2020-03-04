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
//        return getMainConfigUrl("evaluationList");
        return "http://10.10.8.22:3000/mock/39/evaluation/query/list";
    }

    /* 获取登录URL */
    public static String getLoginUrl() {
//        return getMainConfigUrl("login");
        return "http://10.10.8.22:3000/mock/39/user/login";
    }

    /* 获取注册URL */
    public static String getRegisterUrl() {
//        return getMainConfigUrl("register");
        return "http://10.10.8.22:3000/mock/39/user/register";
    }

    /* 获取手机验证码URL */
    public static String getVerifyUrl() {
//        return getMainConfigUrl("getIdentifyCode");
        return "http://10.10.8.22:3000/mock/39/user/send/msg";
    }


}
