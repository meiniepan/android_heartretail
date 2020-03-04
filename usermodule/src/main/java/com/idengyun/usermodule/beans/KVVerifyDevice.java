package com.idengyun.usermodule.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证新设备 请求体
 *
 * @author Burning
 */
public final class KVVerifyDevice {
    private HashMap<String, Object> map = new HashMap<>();

    public String phoneImei;
    public String identifyCode;
    public String version;
    public int userId;
    public String phoneType;
    public String platform;

    public KVVerifyDevice() {
        super();
    }

    public KVVerifyDevice(String phoneImei,
                          String identifyCode,
                          String version,
                          int userId,
                          String phoneType,
                          String platform) {
        this.phoneImei = phoneImei;
        this.identifyCode = identifyCode;
        this.version = version;
        this.userId = userId;
        this.phoneType = phoneType;
        this.platform = platform;
    }

    public Map<String, Object> toMap() {
        map.put("phoneImei", phoneImei);
        map.put("identifyCode", identifyCode);
        map.put("version", version);
        map.put("userId", userId);
        map.put("phoneType", phoneType);
        map.put("platform", platform);
        return new HashMap<>(map);
    }
}
