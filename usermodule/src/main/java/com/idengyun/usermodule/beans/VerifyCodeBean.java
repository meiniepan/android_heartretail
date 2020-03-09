package com.idengyun.usermodule.beans;

/**
 * 发送手机验证码API 响应体
 *
 * @author aLang
 */
public final class VerifyCodeBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
    }
}
