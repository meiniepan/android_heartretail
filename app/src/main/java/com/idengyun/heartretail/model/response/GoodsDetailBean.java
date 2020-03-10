package com.idengyun.heartretail.model.response;

import java.util.List;

/**
 * 商品详情API 响应体
 *
 * @author aLang
 */
public final class GoodsDetailBean {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public int goodsId;
        public int categoryId;
        public String goodsName;
        public int totalCount;
        public String retailPrice;
        public String wholesalePrice;
        public String goodsTitle;
        public int soldCounts;
        public int goodsType;
        public int shopId;
        public String shopName;
        public int wholesaleFlag;
        public List<Banner> imageList;
        public List<Protocol> protocolList;
        public List<GoodsSpec> goodsSpecList;
        public List<GoodsSku> goodsSkuList;
        public List<String> goodsDetailList;

        public static class Banner {
            public String imgUrl;
            public String imgSort;
        }

        public static class GoodsSpec {
            public String skuName;
            public List<SkuValue> skuValueList;

            public static class SkuValue {
                public int specItemId;
                public String specItemName;
            }
        }

        public static class GoodsSku {
            public String skuCombinationCode;
            public int isDefault;
            public int goodsCount;
            public int goodsSkuId;
            public String goodsPrice;
            public int canBuyCount;
            public String skuImgUrl;
        }

        public static class Protocol {
            public int protocolId;
            public String protocolName;
            public int protocolSort;
        }
    }
}