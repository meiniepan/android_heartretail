package com.idengyun.heartretail.model.request;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.Nullable;

/**
 * 商品详情API 请求体
 *
 * @author aLang
 */
public final class KVGoodsDetail {
    private HashMap<String, Object> map = new HashMap<>();

    public String version;
    public int goodsId;
    @Nullable
    public String userId;
    public String platform;
    public int goodsType;

    public KVGoodsDetail() {
        super();
    }

    public KVGoodsDetail(String version,
                         int goodsId,
                         String userId,
                         String platform,
                         int goodsType) {
        this.version = version;
        this.goodsId = goodsId;
        this.userId = userId;
        this.platform = platform;
        this.goodsType = goodsType;
    }

    public Map<String, Object> toMap() {
        map.put("version", version);
        map.put("goodsId", goodsId);
        map.put("userId", userId);
        map.put("platform", platform);
        map.put("goodsType", goodsType);
        return new HashMap<>(map);
    }
}
