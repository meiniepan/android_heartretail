package com.sobot.chat.dengy.utils;

import android.util.SparseArray;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by liupeng on 2017/11/9.
 * <p>
 * 拼接数据工具类
 */

public class SpliceUtil {

    /**
     * @param name         订单/售后
     * @param orderNo      订单编号
     * @param orderType    订单状态  售后状态
     * @param date         下单时间  申请时间
     * @param productTitle 商品名称
     * @param price        商品价格  退款金额
     * @param img          商品首图
     *                     <p>
     *                     [消息类型]:[售后]
     *                     [订单编号]:[547]
     *                     [售后状态]:[已退款]
     *                     [申请时间]:[2017-11-30 16:41:20]
     *                     [退款金额]:[9600.0]
     *                     [商品名称]:[泰妍魅力套套盒]
     *                     [商品首图]:[http://resource.idengyun.com/resource/images/2017/05/c2413a60-6e47-4082-8e33-e6edc4b3f92b.jpg]
     */
    public static String getOrderSplice(String name, String orderNo, String orderType, String date, String productTitle,
                                        double price, String img) {
        if ("订单".equals(name)) {
            return "[消息类型]:[" + name + "]\n"
                    + "[订单编号]:[" + orderNo + "]\n"
                    + "[订单状态]:[" + orderType + "]\n"
                    + "[下单时间]:[" + date + "]\n"
                    + "[商品名称]:[" + productTitle + "]\n"
                    + "[商品价格]:[" + getTwoDouble(price) + "]\n"
                    + "[商品首图]:[" + img + "]";
        } else {
            return "[消息类型]:[" + name + "]\n"
                    + "[售后编号]:[" + orderNo + "]\n"
                    + "[售后状态]:[" + orderType + "]\n"
                    + "[申请时间]:[" + date + "]\n"
                    + "[商品名称]:[" + productTitle + "]\n"
                    + "[退款金额]:[" + getTwoDouble(price) + "]\n"
                    + "[商品首图]:[" + img + "]";
        }

    }

    /**
     * @param productN     商品id
     * @param productTitle 商品名称
     * @param price        商品价格
     * @param img          商品首图
     *                     <p>
     */
    public static String getProductSplice(String productN, String productTitle, String img, double price) {
        return "[消息类型]:[商品]\n"
                + "[商品id]:[" + productN + "]\n"
                + "[商品名称]:[" + productTitle + "]\n"
                + "[商品价格]:[" + getTwoDouble(price) + "]\n"
                + "[商品首图]:[" + img + "]";
    }

    /**
     * @param price 商品价格
     *              <p>
     *              此方法返回两位小数的数字
     */
    public static String getTwoDouble(double price) {
        return new DecimalFormat("#########0.00").format(price);
    }

}
