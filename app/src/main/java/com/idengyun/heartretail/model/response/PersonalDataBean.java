package com.idengyun.heartretail.model.response;

/**
 * 个人资料API 响应体
 *
 * @author aLang
 */
public final class PersonalDataBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public String nickName;
        public String headPic;
        public String invitationCode;
    }
}
