package com.idengyun.heartretail;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.utils.encode.EncryptUtils;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

/**
 * 获取处理过的get请求方式的url（拼接参数）
 */
public final class HRNetUrlForGet {

    /**
     * 获取处理过的get请求方式的url（拼接参数）
     * @param queryMap 参数
     */
    public static String getDealUrl(Map<String, Object> queryMap) {
        if (queryMap.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();

        /* 真实请求参数 */
        Object[] array = queryMap.keySet().toArray();
        Arrays.sort(array);
        for (Object key : array) {
            Object value = queryMap.get(key);
            builder.append(key).append("=").append(value).append("&");
        }

        /* MD5加密参数 */
        String parameters = builder.substring(0, builder.length() - 1);
        String sign = EncryptUtils.stringToMD5(parameters + "secret");
        builder.append("sign").append("=").append(sign);

        return builder.toString();
    }
}
