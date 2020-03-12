package com.dengyun.baselibrary.utils.ocr;

/**
 * @Title ocr银行卡的实体
 * @Author: zhoubo
 * @CreateDate: 2020-03-12 09:58
 */
public class BankCardBean {
    /**
     * card_num : 1234567890123456
     * request_id : 20170301160849_918cfcae128fc919ad6d6e3b865d2973
     * success : true
     */

    private String card_num; //#银行卡卡号，可能为空
    private String request_id; //#请求唯一标识，用于错误追踪
    private boolean success; //#识别成功与否 true/false

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
