package com.idengyun.heartretail.beans;

import java.util.List;

/**
 * @类名称: CLASS
 * @类描述:
 * @创建人：Burning
 * @创建时间：2020/3/12 16:50
 * @备注：
 */
public class ShippingListBean {
    public List<ShippingBean> shippings;

    public class ShippingBean {
        public int shippingId;
        public int shippingStatus;
        public String shippingCode;
        public String shippingTime;
        public String shippingName;
        public String pickupCode;
        public List<Goods> goods;
        public List<LogisticsTraces> logisticsTraces;
    }

    public class Goods {
        public int goodsId;
        public String goodsName;
        public int goodsNum;
        public String originalImg;
        public String specKey;
        public String specKeyName;
        public String goodsPrice;
    }

    public class LogisticsTraces {
        public String Action;
        public String AcceptStation;
        public String AcceptTime;
        public String Location;
    }


}
