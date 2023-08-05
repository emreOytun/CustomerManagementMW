package com.emreoytun.customermanagementmw.listener;

import com.emreoytun.customermanagementmw.service.cache.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationReadyListener {

    private final CacheService cacheService;

    // When the application ready, clear the redis cache.
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        cacheService.clearCache();
    }
}
