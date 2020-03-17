package com.sobot.chat.dengy.utils;

/**
 * Created by liupeng on 2017/12/1.
 */

public enum BllnStatusEnum {
    //订单状态
    VALUE1(-2, "取消订单"),
    VALUE2(-1, "待支付"),
    VALUE3(1, "待量体"),
    VALUE4(2, "量体完成"),
    VALUE5(3, "待审核"),
    VALUE6(4, "打版"),
    VALUE7(5, "裁剪"),
    VALUE8(6, "制作"),
    VALUE9(7, "质检"),
    VALUE10(8, "快递"),
    VALUE11(9, "试穿"),
    VALUE12(10, "待评价"),
    VALUE13(11, "完成");


    private String name;
    private int index;

    BllnStatusEnum(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (BllnStatusEnum c : BllnStatusEnum.values()) {
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

