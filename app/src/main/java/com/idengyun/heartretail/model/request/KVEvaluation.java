package com.idengyun.heartretail.model.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品评价API 请求体
 *
 * @author aLang
 */
public final class KVEvaluation {
    private HashMap<String, Object> map = new HashMap<>();

    public String version;
    public String goodsId;
    public int page;
    public int pageSize;
    public String platform;

    public KVEvaluation() {
        super();
    }

    public KVEvaluation(String version,
                        String goodsId,
                        int page,
                        int pageSize,
                        String platform) {
        this.version = version;
        this.goodsId = goodsId;
        this.page = page;
        this.pageSize = pageSize;
        this.platform = platform;
    }

    public Map<String, Object> toMap() {
        map.put("version", version);
        map.put("goodsId", goodsId);
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("platform", platform);
        return new HashMap<>(map);
    }
}
