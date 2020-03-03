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

}
