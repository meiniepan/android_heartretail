package com.sobot.chat.dengy.bean;

import java.util.List;

/**
 * Created by liupeng on 2017/11/30.
 */

public class AfterOrderBean {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":"01201711291357149831062830","order_id":"00201711271445517711062830","goods_mapping_id":87252,"goods_num":0,"user_id":1062830,"is_real":1,"apply_type":0,"apply_date":"2017-11-29 13:57:15","return_reason":"啊啊","total_amount":100,"order_amount":300,"order_arrearage":0,"return_amount":20,"warehouse_id":0,"is_storage":-1,"return_status":16,"remarks":"","refusal_cause":null,"shipping_code":null,"service_times":7,"return_num":5,"problem_desc":"啊噶","shipper_date":null,"financial_date":null,"governor":null,"phone":null,"address":null,"postalcode":null,"goods_id":1381,"goods_name":"虚拟_老_有预约金&gt;sku","spec_key_name":"7次","original_img":"http://resource.idengyun.com/resource/images/2017/11/0ed1ba51-16ba-4d97-a6af-adc5fa8f046b.jpg","integral":0,"action_type":0,"imgs":[],"account_arrearage":0,"returnedGoodsCards":[],"expiring_date":9},{"id":"01201711281014074681062830","order_id":"00201711271445517711062830","goods_mapping_id":87252,"goods_num":0,"user_id":1062830,"is_real":1,"apply_type":0,"apply_date":"2017-11-28 10:14:07","return_reason":"赶紧看看","total_amount":100,"order_amount":300,"order_arrearage":0,"return_amount":20,"warehouse_id":0,"is_storage":-1,"return_status":-10,"remarks":"","refusal_cause":null,"shipping_code":null,"service_times":7,"return_num":7,"problem_desc":"阿嘎啊","shipper_date":null,"financial_date":null,"governor":null,"phone":null,"address":null,"postalcode":null,"goods_id":1381,"goods_name":"虚拟_老_有预约金&gt;sku","spec_key_name":"7次","original_img":"http://resource.idengyun.com/resource/images/2017/11/0ed1ba51-16ba-4d97-a6af-adc5fa8f046b.jpg","integral":0,"action_type":0,"imgs":[],"account_arrearage":0,"returnedGoodsCards":[],"expiring_date":9}]
     */

    public String code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 01201711291357149831062830
         * apply_date : 2017-11-29 13:57:15
         * return_amount : 20
         * return_status : 16
         * goods_name : 虚拟_老_有预约金&gt;sku
         * original_img : http://resource.idengyun.com/resource/images/2017/11/0ed1ba51-16ba-4d97-a6af-adc5fa8f046b.jpg
         */
        public String id;
        public double return_amount;
        public String apply_date;
        public int return_status;
        public String original_img;
        public String goods_name;
    }
}
