package com.sobot.chat.dengy.bean;

import java.util.List;

/**
 * Created by liupeng on 2017/11/8.
 */

public class MtmyOrderListBean {

    /**
     * code : 200
     * msg : 成功
     * data : [{"order_status":-2,"add_time":"2017-10-12 10:58:22","orderGoods":[{"order_id":"00201710121058219521063010","goods_id":1341,"goods_name":"套卡套卡","original_img":"http://resource.idengyun.com/resource/images/2017/09/f5635db7-0ae9-4b33-8c79-73d6db49a763.jpg","goods_price":1439.04}]}]
     */

    public String code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * order_status : -2
         * add_time : 2017-10-12 10:58:22
         * orderGoods : [{"order_id":"00201710121058219521063010","goods_id":1341,"goods_name":"套卡套卡","original_img":"http://resource.idengyun.com/resource/images/2017/09/f5635db7-0ae9-4b33-8c79-73d6db49a763.jpg","goods_price":1439.04}]
         */

        public int order_status;
        public String add_time;
        public List<OrderGoodsBean> orderGoods;
        public static class OrderGoodsBean {
            /**
             * order_id : 00201710121058219521063010
             * goods_id : 1341
             * goods_name : 套卡套卡
             * original_img : http://resource.idengyun.com/resource/images/2017/09/f5635db7-0ae9-4b33-8c79-73d6db49a763.jpg
             * goods_price : 1439.04
             */

            public String order_id;
            public int goods_id;
            public String goods_name;
            public String original_img;
            public double goods_price;
        }
    }
}
