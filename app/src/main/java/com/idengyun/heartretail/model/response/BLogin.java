package com.idengyun.heartretail.model.response;

/**
 * 登录API 响应体
 *
 * @author aLang
 */
public final class BLogin {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public String token;
    }
}
