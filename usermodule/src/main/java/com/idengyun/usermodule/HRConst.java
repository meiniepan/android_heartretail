package com.idengyun.usermodule;

import com.dengyun.baselibrary.utils.phoneapp.PhoneUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

/**
 * 心零售 常量字段集合
 *
 * @author aLang
 */
public final class HRConst {

    /* 主配置.xml 登录API.xml */
    public static final String XML_FILE_NAME_USER_INFO = "hr_user_info";

    // public static final String PLATFORM = "Android";
    // public static final String APP_NAME = "app";
    // 验证码类型0注册1换新设备2修改密码3忘记密码4绑定新手机号
    // 验证码类型0注册1换新设备2修改密码3忘记密码4绑定新手机号5旧手机号身份验证6（提现审核）实名认证 7支付密码设置8忘记支付密码
    public static final String IDENTIFY_TYPE_0 = "0";
    public static final String IDENTIFY_TYPE_1 = "1";
    public static final String IDENTIFY_TYPE_2 = "2";
    public static final String IDENTIFY_TYPE_3 = "3";
    public static final String IDENTIFY_TYPE_4 = "4";
    public static final String PHONE_IMEI = SensorsDataAPI.sharedInstance().getAnonymousId();
    public static final String PHONE_TYPE = PhoneUtils.getModel();

}
