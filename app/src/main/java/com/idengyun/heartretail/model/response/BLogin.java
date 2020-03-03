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
        public User user;

        public static class User {
            public int id;
            public String mobile;
            public String invitationCode;
            public String headUrl;
            public String nickName;
        }
    }
}
