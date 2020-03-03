package com.idengyun.heartretail;

import android.app.Application;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * 查询主配置API返回的特定URL
 *
 * @author aLang
 */
public final class MCHelper {
    private static final Application context = Utils.getApp();
    private static final String FILE_NAME = "main_config";

    public static final String key_0 = "";
    public static final String key_1 = "";
    public static final String key_2 = "";
    public static final String key_3 = "";

    public static String getUrl(String key) {
        return SharedPreferencesUtil.getData(context, FILE_NAME, key, "");
    }

}
