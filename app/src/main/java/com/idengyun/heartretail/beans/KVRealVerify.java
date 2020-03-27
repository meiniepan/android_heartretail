package com.idengyun.heartretail.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证请求字段集
 *
 * @author aLang
 */
public final class KVRealVerify {

    /* 姓名 性别 民族 出生 年 月 日 住址 公民身份证号 中华人民共和国 居民身份证 签发机关 有效期限 */
    public String name;
    public String sex;
    public String nation;
    public String userBirthday;
    public String address;
    public String number;
    public String authority;
    public String timeLimit;

    /* 银行卡号 银行预留手机号 银行名称 银行全称 银行所在市 银行所在省 银行卡类型0不能识别1借记卡 银行卡类型名称 */
    public String bankCardNo;
    public String bankMobile;
    public String userBankName;
    public String userBankFullName;
    public String userBankCity;
    public String userBankProvince;
    public String bankCardType;
    public String cardType;

    /* 用户ID 身份证URL正面 身份证URL反面 银行卡URL正面 证件类型 正面订单号 反面订单号 验证码 */
    public String userId;
    public String frontUrl;
    public String backUrl;
    public String bankUrl;
    public String userCertificateType;
    public String frontOrderNo;
    public String backOrderNo;
    public String identifyCode;

    public KVRealVerify() {
    }

    public Map<String, String> toMap() {
        return new HashMap<String, String>() {
            {
                put("userCertificateType", userCertificateType);
                put("bankCardNo", bankCardNo);
                put("bankMobile", bankMobile);
                put("userBankName", userBankName);
                put("userBankFullName", userBankFullName);
                put("userBankCity", userBankCity);
                put("userBankProvince", userBankProvince);
                put("bankCardType", bankCardType);
                put("cardType", cardType);
                put("frontOrderNo", frontOrderNo);
                put("number", number);
                put("address", address);
                put("nation", nation);
                put("userBirthday", userBirthday);
                put("sex", sex);
                put("name", name);
                put("backOrderNo", backOrderNo);
                put("authority", authority);
                put("timeLimit", timeLimit);
                put("bankUrl", bankUrl);
                put("frontUrl", frontUrl);
                put("backUrl", backUrl);
                put("identifyCode", identifyCode);
                put("userId", userId);
            }
        };
    }

    /**
     * 已绑定银行卡修改请求体
     *
     * @author aLang
     */
    public static class BankCard {
        public String bankCardNo;
        public String bankMobile;
        public String userBankName;
        public String userBankFullName;
        public String userBankCity;
        public String userBankProvince;
        public String bankCardType;
        public String cardType;
        public String name;
        public String bankUrl;
        public String identifyCode;
    }
}
