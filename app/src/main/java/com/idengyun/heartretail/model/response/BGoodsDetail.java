package com.idengyun.heartretail.model.response;

import java.util.List;

/**
 * 商品详情API 响应体
 *
 * @author aLang
 */
public final class BGoodsDetail {
    public String code;
    public String msg;
    public Data data;

    public static class Data {
        public int goodsId;
        public int categoryId;
        public String goodsName;
        public String totalCount;
        public List<Serves> serves;
        public int retailPrice;
        public int wholesalePrice;
        public int goodsTitle;
        public List<Images> images;
        public int soldCounts;
        public List<GoodsSku> goodsSku;
        public int goodsType;
        public List<Protocol> protocol;

        public static class Serves {
            public int server_id;
            public String server_name;
            public int server_sort;
        }

        public static class Images {
            public String imgUrl;
            public String imgSort;
        }

        public static class GoodsSku {
            public List<SkuCompose> skuCompose;
            public String skuCombinationCode;
            public String isDefault;
            public String goodsCount;
            public String canBuyCount;
            public String goodsPrice;

            public static class SkuCompose {
                public int sku_id;
                public String sku_no;
                public String sku_name;
                public String sku_value;
            }
        }

        public static class Protocol {
            public int protocolId;
            public String protocolName;
            public String protocolKey;
        }
    }
}
