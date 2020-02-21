package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 用户报货数据
 * @Author: zhoubo
 * @CreateDate: 2019-09-16 11:44
 */
public class SpUserGoodsConstants {
    private static String FILE_NAME = "user_daily_good";
    private static String USER_OFFICES = "userOffices";
    private static String USER_AUDIT_OFFICES = "userAuditOffices";
    private static String USER_PROXY_OFFICES = "userProxyOffices";

    public static String getUserOffices() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, USER_OFFICES, "");
    }

    public static String getUserAuditOffices() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, USER_AUDIT_OFFICES, "");
    }

    public static String getUserProxyOffices() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, USER_PROXY_OFFICES, "");
    }
    public static void removeAll(){
        SharedPreferencesUtil.removeAll(Utils.getApp(),FILE_NAME);
    }

    public static void saveUserGoodsBean(Object authBean){
        SharedPreferencesUtil.saveDataBean(Utils.getApp(), FILE_NAME, authBean);
    }
}
