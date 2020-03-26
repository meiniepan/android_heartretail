package com.idengyun.heartretail.model.response;

import java.util.List;

/**
 * 红包API 响应体
 *
 * @author aLang
 */
public final class RedPacketBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public String withdrawableCash;
        public String total;
        public String withdrawnCash;
        public String caseTobeIssued;
        public String levelName;
        public String percent;

        /*public int current;
        public int pages;
        public int total;
        public int friendsCount;
        public Packet packet;
        public List<Friend> friends;

        public static class Friend {
            public int recordId;
            public int rewardType;
            public int status;
            public int friendId;
            public String friendName;
            public String friendHeadImg;
            public String inviteTime;
            public String businessMoney;
            public String consumeMoney;
        }

        public static class Packet {
            public String canExchange;
            public String total;
            public String hasExchange;
            public String willSend;
            public int level;
            public String percent;
        }*/
    }
}
