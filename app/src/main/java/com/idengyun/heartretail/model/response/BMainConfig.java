package com.idengyun.heartretail.model.response;

import java.util.List;

/**
 * 主配置API 响应体
 *
 * @author aLang
 */
public final class BMainConfig {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public List<UrlConfig> urlConfigList;

        public static class UrlConfig {
            public int urlId;
            public String urlName;
            public String urlCode;
            public String urlHead;
            public String urlTail;
            public String createTime;
            public String urlVersion;
            public int checkLogin;
        }
    }
}
