package com.idengyun.heartretail.model.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证请求字段集
 *
 * @author aLang
 */
public final class KVRealVerify {

    public String userCertificateType;
    public String bankCardNo;
    public String bankMobile;
    public String userBankName;
    public String userBankFullName;
    public String userBankCity;
    public String userBankProvince;
    public String bankCardType;
    public String cardType;
    public String frontOrderNo;
    public String number;
    public String address;
    public String nation;
    public String userBirthday;
    public String sex;
    public String name;
    public String backOrderNo;
    public String authority;
    public String timeLimit;
    public String bankUrl;
    public String frontUrl;
    public String backUrl;
    public String identifyCode;
    public String userId;

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
}
