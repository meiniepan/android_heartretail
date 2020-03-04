package com.idengyun.usermodule.beans;


import com.idengyun.usermodule.HRNetUrlForGet;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取手机验证码API 请求参数
 *
 * @author aLang
 */
public final class KVVerify {
    private HashMap<String, Object> map = new HashMap<>();

    public String mobile;
    public String identifyType;
    public String version;
    public String platform;

    public KVVerify() {
        super();
    }

    public KVVerify(String mobile, String identifyType, String version, String platform) {
        this.mobile = mobile;
        this.identifyType = identifyType;
        this.version = version;
        this.platform = platform;
    }

    public Map<String, Object> toMap() {
        map.put("mobile", mobile);
        map.put("identifyType", identifyType);
        map.put("version", version);
        map.put("platform", platform);
        return new HashMap<>(map);
    }

    public String toQuery() {
        return HRNetUrlForGet.getDealUrl(map);
    }
}
