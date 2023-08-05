package com.emreoytun.customermanagementmw.cache;


import java.util.List;

public interface CacheService {


    void cacheValueWithTTL(String key, Object value, long ttlInSecond);

    Object getCacheValue(String key);

    // @param Object value should implement Serializable interface.
    void cacheWithHashKeyTTL(String mapKey, String key, Object value, long ttlInSeconds);

    void cache(String mapKey, String key, Object value);

    // Gets the keys of the given mapKey.
    List<Object> getValuesFromCache(String mapKey);

    void deleteKeyFromCache(String mapKey, String key);

    Object getValueFromCache(String mapKey, String key);

    void clearCache();
}
