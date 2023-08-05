package com.emreoytun.customermanagementmw.constants.cache;

import com.emreoytun.customermanagementmw.cache.UserCache;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheConstants {
    public static final Map<Class, String> classMapkeyMap;
    static {
        classMapkeyMap = new HashMap<>();
        classMapkeyMap.put(UserCache.class, "usermap");
    }
}
