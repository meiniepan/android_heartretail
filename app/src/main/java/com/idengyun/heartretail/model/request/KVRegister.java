package com.idengyun.heartretail.model.request;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.Nullable;

/**
 * 注册API 请求体
 *
 * @author aLang
 */
public final class KVRegister {
    private HashMap<String, Object> map = new HashMap<>();

    public String mobile;
    public String version;
    public String identifyCode;
    public String pwd;
    @Nullable
    public String invitationCode;
    public String phoneImei;
    public String phoneType;
    public String platform;
    public String appName;

    public KVRegister() {
        super();
    }

    public KVRegister(String mobile,
                      String version,
                      String identifyCode,
                      String pwd,
                      String invitationCode,
                      String phoneImei,
                      String phoneType,
                      String platform,
                      String appName) {
        this.mobile = mobile;
        this.version = version;
        this.identifyCode = identifyCode;
        this.pwd = pwd;
        this.invitationCode = invitationCode;
        this.phoneImei = phoneImei;
        this.phoneType = phoneType;
        this.platform = platform;
        this.appName = appName;
    }

    public Map<String, Object> toMap() {
        map.put("mobile", mobile);
        map.put("version", version);
        map.put("identifyCode", identifyCode);
        map.put("pwd", pwd);
        map.put("invitationCode", invitationCode);
        map.put("phoneImei", phoneImei);
        map.put("phoneType", phoneType);
        map.put("platform", platform);
        map.put("appName", appName);
        return new HashMap<>(map);
    }
}
