package com.dengyun.splashmodule.config;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 主配置返回的协议本地存储
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 15:38
 */
public class SpProtocol {

    public static final String spFileName = "SpProtocol";

    /* 不要使用 */
    private static int getProtocolId(String key) {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, key, 0);
    }


    /* ================================== 公共方法 获取协议id ================================== */

    /* 用户协议 */
    public static int getUserProtocolId() {
        return getProtocolId("user");
    }

    /* 代签协议 */
    public static int getAllographProtocolId() {
        return getProtocolId("allograph");
    }

    /* 代销协议 */
    public static int getProxySalesProtocolId() {
        return getProtocolId("proxySales");
    }

}
