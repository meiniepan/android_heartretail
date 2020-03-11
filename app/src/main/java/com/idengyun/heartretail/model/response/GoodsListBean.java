package com.idengyun.heartretail.model.response;

import java.util.List;

/**
 * 商品列表API 响应体
 *
 * @author aLang
 */
public final class GoodsListBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public List<Goods> goods;
        public int total;
        public int current;
        public int pages;

        public static class Goods {
            public int goodsId;
            public String goodsName;
            public String retailPrice;
            public String wholesalePrice;
            public String goodsImgUrl;
            public int quantitySold;
            public int isReal;
            public String goodsRemark;
            public int goodsType;
        }
    }
}
