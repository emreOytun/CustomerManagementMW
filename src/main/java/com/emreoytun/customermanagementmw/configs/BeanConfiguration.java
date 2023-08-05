package com.emreoytun.customermanagementmw.configs;

import com.emreoytun.customermanagementdata.dto.IModelMapperService;
import com.emreoytun.customermanagementdata.dto.ModelMapperService;
import com.emreoytun.customermanagementmw.handler.RestTemplateResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final RestTemplateResponseErrorHandler responseErrorHandler;

    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(responseErrorHandler)
                .build();
    }

    @Bean
    IModelMapperService getModelMapperService() {
        return new ModelMapperService();
    }
}
