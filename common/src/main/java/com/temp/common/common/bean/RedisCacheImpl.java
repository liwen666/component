package com.temp.common.common.bean;

import com.temp.common.common.RedisCacheInterface;

public class RedisCacheImpl  implements RedisCacheInterface<RedisCacheImpl>{
    private  String idCacheKey;
    public String findIdKey(RedisCacheImpl redisCache) {
        return redisCache.getIdCacheKey();
    }

    public String getIdCacheKey() {
        return idCacheKey;
    }

    public void setIdCacheKey(String idCacheKey) {
        this.idCacheKey = idCacheKey;
    }
}
