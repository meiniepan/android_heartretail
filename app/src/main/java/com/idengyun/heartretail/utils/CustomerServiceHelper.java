package com.idengyun.heartretail.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.idengyun.usermodule.HRUser;
import com.sobot.chat.utils.InformationUtil;

/**
 * 跳转到联系客服界面 助手类
 */
public final class CustomerServiceHelper {

    /* 开启智齿SDK */
    public static void start(@Nullable Context context) {
        if (context == null) return;
        if (!HRUser.isLogin()) return;
        new InformationUtil(context).startSobot();
    }

    private CustomerServiceHelper() {
    }
}
