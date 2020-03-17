package com.sobot.chat.dengy.bean;

import java.util.List;

/**
 * Created by liupeng on 2017/11/2.
 */

public class BllnOrderListBean {

    /**
     * code : 200
     * data : [{"createDate":"2017-09-29","customStatus":10,"goodsList":[{"firstImg":"http://10.10.8.22:9377/blln/resource/images/2017/09/a5f7b724-a7aa-4628-8701-1f9f6511123c.jpg","goodsName":"亲肤系列#套装#（预付）","id":43,"isAddToCar":false,"price":28999},{"firstImg":"http://10.10.8.22:9377/blln/resource/images/2017/09/2309af46-876e-47a5-ab24-5e9be4f18453.jpg","goodsName":"薄款夏性感无痕聚拢调整#套装#少女型小胸罩","id":12,"isAddToCar":false,"price":1999.99}],"orderId":"00201709291404549141019728","orderNo":"DZXS2017092999293"}]
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * "createDate": "2017-09-29",   订单日期
         * "customStatus": 10,  订单状态
         * "goodsList": [
         *  {
         *      "firstImg": "http://10.10.8.22:9377/blln/resource/images/2017/09/a5f7b724-a7aa-4628-8701-1f9f6511123c.jpg",   商品首图
         *      "goodsName": "亲肤系列#套装#（预付）",
         *      "id": 43,           商品编号
         *      "isAddToCar": false,
         *      "price": 28999    商品金额
         *  },
         *  {
         *      "firstImg": "http://10.10.8.22:9377/blln/resource/images/2017/09/2309af46-876e-47a5-ab24-5e9be4f18453.jpg",
         *      "goodsName": "薄款夏性感无痕聚拢调整#套装#少女型小胸罩",
         *      "id": 12,
         *      "isAddToCar": false,
         *      "price": 1999.99
         *  }
         * ],
         * "orderId": "00201709291404549141019728",  订单id
         * "orderNo": "DZXS2017092999293"  订单编号
         */

        private String createDate;
        private int customStatus;
        private String orderId;
        private String orderNo;
        private List<GoodsListBean> goodsList;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getCustomStatus() {
            return customStatus;
        }

        public void setCustomStatus(int customStatus) {
            this.customStatus = customStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * firstImg : http://10.10.8.22:9377/blln/resource/images/2017/09/a5f7b724-a7aa-4628-8701-1f9f6511123c.jpg
             * goodsName : 亲肤系列#套装#（预付）
             * id : 43
             * isAddToCar : false
             * price : 28999
             */

            private String firstImg;
            private String goodsName;
            private int id;
            private boolean isAddToCar;
            private double price;

            public String getFirstImg() {
                return firstImg;
            }

            public void setFirstImg(String firstImg) {
                this.firstImg = firstImg;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIsAddToCar() {
                return isAddToCar;
            }

            public void setIsAddToCar(boolean isAddToCar) {
                this.isAddToCar = isAddToCar;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }
}
