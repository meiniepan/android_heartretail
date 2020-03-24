package com.idengyun.heartretail.model.response;

import java.util.List;

/**
 * 协议详情查询API 响应体
 *
 * @author aLang
 */
public final class ProtocolBean {
    public String code;
    public String msg;
    public List<Protocol> data;

    public final static class Protocol {
        public String protocolKey;
        public String protocolName;
        public String protocolContent;
    }
}
