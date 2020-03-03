package com.idengyun.heartretail.model.request;

import com.idengyun.heartretail.HRNetUrlForGet;

import java.util.HashMap;
import java.util.Map;

/**
 * 主配置API 请求参数
 *
 * @author aLang
 */
public final class KVMainConfig {
    private HashMap<String, Object> map = new HashMap<>();

    public String version;
    public String platform;
    public String type;

    public KVMainConfig() {
        super();
    }

    public KVMainConfig(String version, String platform, String type) {
        this.version = version;
        this.platform = platform;
        this.type = type;
    }

    public Map<String, Object> toMap() {
        map.put("version", version);
        map.put("platform", platform);
        map.put("type", type);
        return new HashMap<>(map);
    }

    public String toQuery() {
        return HRNetUrlForGet.getDealUrl(map);
    }
}
