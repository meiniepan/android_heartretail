package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * 规则API 响应体
 *
 * @author aLang
 */
public final class RuleBean {
    public String code;
    public String msg;
    public List<Rule> data;

    public final static class Rule {
        public String ruleKey;
        public String title;
        public String content;
    }
}
