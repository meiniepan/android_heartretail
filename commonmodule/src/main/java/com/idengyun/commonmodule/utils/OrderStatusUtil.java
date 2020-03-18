package com.idengyun.commonmodule.utils;

/**
 * @Title 订单状态的工具类
 * @Author: zhoubo
 * @CreateDate: 2020-03-18 13:39
 */
public class OrderStatusUtil {

    /**
     * 获取订单状态的名称
     *
     * @param orderStatus 订单状态类型
     */
    public static String getStatusName(int orderStatus) {
        //0:待付款、1:待发货:2:已发货、3:代销中、4:待提货、5:已完成、6:已关闭、7:已评价
        switch (orderStatus) {
            case 0:
                return "待付款";
            case 1:
                return "待发货";
            case 2:
                return "已发货";
            case 3:
                return "代销中";
            case 4:
                return "待提货";
            case 5:
                return "已完成";
            case 6:
                return "已关闭";
            case 7:
                return "已评价";
        }
        return "";
    }
}
