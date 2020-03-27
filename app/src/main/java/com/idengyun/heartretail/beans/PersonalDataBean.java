package com.idengyun.heartretail.beans;

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
        public String headUrl;
        public String invitationCode;
        public String ownerInvitationCode;
        public String userCertificateType;
        public String bankCardNo;
        public String bankMobile;
        public String userBankName;
        public String bankCardType;
        public String cardType;
        public String number;
        public String address;
        public String nation;
        public String userBirthday;
        public String sex;
        public String name;
        public String authority;
        public String timeLimit;
        public String frontUrl;
        public String backUrl;
        public String platform;
        public String userId;
        public String userPayPwdSetFlag;
    }
}
