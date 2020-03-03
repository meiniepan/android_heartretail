package com.idengyun.heartretail;

import android.app.Application;

import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;

/**
 * 心零售 常量字段集合
 *
 * @author aLang
 */
public final class HRConst {
    public static final Application CONTEXT = Utils.getApp();

    public static final String XML_FILE_NAME_MAIN_CONFIG = "hr_main_config";
    public static final String XML_FILE_NAME_USER_INFO = "hr_user_info";

    public static final String VERSION = AppUtils.getAppVersionName();
    public static final String PLATFORM = "Android";
    public static final String APP_NAME = "app";
    //验证码类型0注册1换新设备2修改密码3忘记密码4绑定新手机号
    public static final String IDENTIFY_TYPE_0 = "0";
    public static final String IDENTIFY_TYPE_1 = "1";
    public static final String IDENTIFY_TYPE_2 = "2";
    public static final String IDENTIFY_TYPE_3 = "3";
    public static final String IDENTIFY_TYPE_4 = "4";
    public static final String PHONE_IMEI = "";
    public static final String PHONE_TYPE = "";

}
