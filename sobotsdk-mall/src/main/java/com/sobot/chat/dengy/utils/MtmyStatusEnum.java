package com.sobot.chat.dengy.utils;

/**
 * Created by liupeng on 2017/12/1.
 */

public enum MtmyStatusEnum {
    //订单状态
    VALUE1(-2, "取消订单"),
    VALUE2(-1, "待付款"),
    VALUE3(1, "待发货"),
    VALUE4(2, "待收货"),
    VALUE5(3, "已退款"),
    VALUE6(4, "已完成"),
    //售后状态
    VALUE7(-20, "拒绝换货"),
    VALUE8(-10, "拒绝退货"),
    VALUE9(11, "申请退货"),
    VALUE10(12, "同意退货"),
    VALUE11(13, "退货中"),
    VALUE12(14, "退货完成"),
    VALUE13(15, "退款中"),
    VALUE14(16, "已退款"),
    VALUE15(21, "申请换货"),
    VALUE16(22, "同意换货"),
    VALUE17(23, "换货退货中"),
    VALUE18(24, "换货退货完成"),
    VALUE19(25, "换货中"),
    VALUE20(26, "换货完成");

    private String name;
    private int index;

    MtmyStatusEnum(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (MtmyStatusEnum c : MtmyStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}

