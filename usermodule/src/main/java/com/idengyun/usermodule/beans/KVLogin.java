package com.idengyun.usermodule.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录API 请求体
 *
 * @author aLang
 */
public final class KVLogin {
    private HashMap<String, Object> map = new HashMap<>();

    public String mobile;
    public String version;
    public String pwd;
    public String phoneImei;
    public String platform;

    public KVLogin() {
        super();
    }

    public KVLogin(String mobile,
                   String version,
                   String pwd,
                   String phoneImei,
                   String platform) {
        this.mobile = mobile;
        this.version = version;
        this.pwd = pwd;
        this.phoneImei = phoneImei;
        this.platform = platform;
    }

    public Map<String, Object> toMap() {
        map.put("mobile", mobile);
        map.put("version", version);
        map.put("pwd", pwd);
        map.put("phoneImei", phoneImei);
        map.put("platform", platform);
        return new HashMap<>(map);
    }
}
