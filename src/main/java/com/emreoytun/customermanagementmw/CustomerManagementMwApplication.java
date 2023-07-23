package com.emreoytun.customermanagementmw;

import com.emreoytun.customermanagementdata.dto.IModelMapperService;
import com.emreoytun.customermanagementdata.dto.ModelMapperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan("com.emreoytun.customermanagementdata.entities")
@ComponentScan(basePackages = "com.emreoytun.customermanagementdata")
@ComponentScan(basePackages = "com.emreoytun.customermanagementmw")
@EnableJpaRepositories("com.emreoytun.customermanagementdata.repository")
public class CustomerManagementMwApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementMwApplication.class, args);
	}


	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	IModelMapperService getModelMapperService() {
		return new ModelMapperService();
	}
}
