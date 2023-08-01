package com.emreoytun.customermanagementmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.emreoytun.customermanagementdata.entities")
@ComponentScan(basePackages = "com.emreoytun.customermanagementdata")
@ComponentScan(basePackages = "com.emreoytun.customermanagementmw")
@EnableJpaRepositories("com.emreoytun.customermanagementdata.repository")
//@EnableDiscoveryClient
public class CustomerManagementMwApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementMwApplication.class, args);
	}

}
