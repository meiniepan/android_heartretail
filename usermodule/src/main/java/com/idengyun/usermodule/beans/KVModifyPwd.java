package com.idengyun.usermodule.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码API 请求体
 *
 * @author Burning
 */
public final class KVModifyPwd {
    private HashMap<String, Object> map = new HashMap<>();

    public String mobile;
    public String identifyCode;
    public String pwd;
    public String version;
    public String platform;

    public KVModifyPwd() {
        super();
    }

    public KVModifyPwd(String mobile,
                       String identifyCode,
                       String pwd,
                       String version,
                       String platform
                       ) {
        this.mobile = mobile;
        this.version = version;
        this.identifyCode = identifyCode;
        this.pwd = pwd;
        this.platform = platform;
    }

    public Map<String, Object> toMap() {
        map.put("mobile", mobile);
        map.put("version", version);
        map.put("identifyCode", identifyCode);
        map.put("pwd", pwd);
        map.put("platform", platform);
        return new HashMap<>(map);
    }
}
