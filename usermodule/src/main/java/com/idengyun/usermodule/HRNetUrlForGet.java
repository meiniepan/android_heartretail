package com.idengyun.usermodule;

import com.dengyun.baselibrary.utils.encode.EncryptUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * 获取处理过的get请求方式的url（拼接参数）
 */
public final class HRNetUrlForGet {

    /**
     * 获取处理过的get请求方式的url（拼接参数）
     *
     * @param queryMap 参数
     */
    public static String getDealUrl(Map<String, Object> queryMap) {
        if (queryMap.isEmpty()) return "";
        if (queryMap.get("sign") != null) queryMap.remove("sign");

        StringBuilder builder = new StringBuilder().append("?");

        /* 真实请求参数 */
        Object[] array = queryMap.keySet().toArray();
        Arrays.sort(array);
        for (Object key : array) {
            Object value = queryMap.get(key);
            builder.append(key).append("=").append(value).append("&");
        }

        /* MD5加密参数 */
        String parameters = builder.substring(0, builder.length() - 1);
        String sign = EncryptUtils.stringToMD5(parameters + "xls");
        builder.append("sign").append("=").append(sign);

        return builder.toString();
    }
}
