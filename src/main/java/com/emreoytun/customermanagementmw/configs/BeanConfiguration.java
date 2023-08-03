package com.emreoytun.customermanagementmw.configs;

import com.emreoytun.customermanagementdata.dto.IModelMapperService;
import com.emreoytun.customermanagementdata.dto.ModelMapperService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    IModelMapperService getModelMapperService() {
        return new ModelMapperService();
    }
}
