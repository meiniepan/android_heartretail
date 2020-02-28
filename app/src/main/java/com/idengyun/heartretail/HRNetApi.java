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

public final class HRNetApi {
    public static void a() {
//        NetOption build = NetOption.newBuilder("")
//                .type(type)
//                .fragment(this)
//                .isShowDialog(true)
//                .params("goods_id", goods_id)
//                .params("spec_key", spec_key)
//                .build();
//        NetApi.getString(build, new JsonCallback<String>(build) {
//            @Override
//            public void onSuccess(Response<String> response) {
//
//            }
//        });
//        NetApi.getData(build, new JsonCallback<Object>(build) {
//            @Override
//            public void onSuccess(Response<Object> response) {
//
//            }
//        });
    }

    public static String createBody(Map<String, Object> bodyMap) throws JSONException {
        String parameters = new JSONObject(bodyMap).toString();

        // 加密字段拼接在请求json前后，得md5值
        String signMd5 = EncryptUtils.stringToMD5(parameters + "secret");

        JSONObject content = new JSONObject();
        content.put("jsonStr", parameters);
        content.put("sign", signMd5);

        return content.toString();
    }

    public static String createQuery(Map<String, Object> queryMap) {
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
