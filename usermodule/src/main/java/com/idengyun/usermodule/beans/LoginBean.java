package com.idengyun.usermodule.beans;

/**
 * 登录API 响应体
 *
 * @author aLang
 */
public final class LoginBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public String token;
        public User user;
        public int isnewPhoneImei;

        public static class User {
            public String id;
            public String mobile;
            public String invitationCode;
            public String headUrl;
            public String nickName;
            public int authIdentity;
        }
    }
}
