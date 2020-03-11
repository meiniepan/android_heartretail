package com.idengyun.heartretail.model.response;

/**
 * 我的余额API 响应体
 *
 * @author aLang
 */
public final class BalanceBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public Balance balance;

        public static class Balance {
            public String canExchange;
            public String total;
            public String willSend;
        }
    }
}
