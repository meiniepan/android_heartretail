package com.idengyun.heartretail.model.response;

import java.util.List;

/**
 * 协议详情查询API 响应体
 *
 * @author aLang
 */
public final class ProtocolsBean {
    public String code;
    public String msg;
    public List<Data> data;

    public final static class Data {
        public int protocolId;
        public String protocolName;
        public String protocolContent;
    }
}
