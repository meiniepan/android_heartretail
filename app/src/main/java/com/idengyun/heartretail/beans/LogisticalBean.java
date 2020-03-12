package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @类名称: CLASS
 * @类描述:
 * @创建人：Burning
 * @创建时间：2020/3/12 16:50
 * @备注：
 */
public class LogisticalBean {

    /**
     * courierList : [{"processinfo":"【广东省广州市黄埔区东区永和公司】 已收件","upload_time":"2017-12-28 18:59:55","waybill_no":"887880632749418349"},{"processinfo":"【广东省广州市黄埔区东区永和公司】 已打包","upload_time":"2017-12-28 19:06:58","waybill_no":"887880632749418349"},{"processinfo":"【广州转运中心】 已收入","upload_time":"2017-12-29 2:26:58","waybill_no":"887880632749418349"},{"processinfo":"【广州转运中心】 已发出 下一站 【江门转运中心】","upload_time":"2017-12-29 2:31:07","waybill_no":"887880632749418349"},{"processinfo":"【江门转运中心】 已收入","upload_time":"2017-12-29 5:30:24","waybill_no":"887880632749418349"},{"processinfo":"【江门转运中心】 已发出 下一站 【广东省湛江市公司】","upload_time":"2017-12-29 6:18:29","waybill_no":"887880632749418349"},{"processinfo":"【广东省湛江市公司】 已发出 下一站 【广东省湛江市市区公司】","upload_time":"2017-12-29 15:32:56","waybill_no":"887880632749418349"},{"processinfo":"【广东省湛江市市区公司】 派件人 :郭介中 派件中 派件员电话13828285140","upload_time":"2017-12-30 13:18:32","waybill_no":"887880632749418349"},{"processinfo":"客户 签收人 :null 已签收 感谢使用圆通速递，期待再次为您服务","upload_time":"2017-12-30 13:25:12","waybill_no":"887880632749418349"}]
     * express_company : null
     * goods_num : 0
     * id : 0
     * original_img : null
     * shipping_code : null
     * shipping_customer_phone : null
     * shipping_status : 1
     */

    public String express_company;
    public int goods_num;
    public int id;
    public String original_img;
    public String shipping_code;
    public String shipping_customer_phone;
    public int shipping_status;
    public List<LogisticalMessageBean> courierList;

}
