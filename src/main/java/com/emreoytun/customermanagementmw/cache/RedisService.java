package com.emreoytun.customermanagementmw.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService implements CacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void cacheValueWithTTL(String key, Object value, long ttlInSecond) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttlInSecond));
    }

    @Override
    public Object getCacheValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // @param Object value should implement Serializable interface.
    @Override
    public void cacheWithHashKeyTTL(String mapKey, String key, Object value, long ttlInSeconds) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        hashOps.put(mapKey, key, value);
        redisTemplate.expire(mapKey, ttlInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void cache(String mapKey, String key, Object value) {
        cacheWithHashKeyTTL(mapKey, key, value, 0L);
    }

    // Gets the keys of the given mapKey.
    @Override
    public List<Object> getValuesFromCache(String mapKey) {
        return redisTemplate.opsForHash().values(mapKey);
    }

    @Override
    public void deleteKeyFromCache(String mapKey, String key) {
        redisTemplate.opsForHash().delete(mapKey, key);
    }

    @Override
    public Object getValueFromCache(String mapKey, String key) {
        return redisTemplate.opsForHash().get(mapKey, key);
    }

    @Override
    public void clearCache() {
        Set<String> mapKeys = redisTemplate.keys("*");
        redisTemplate.delete(mapKeys);
    }
}
