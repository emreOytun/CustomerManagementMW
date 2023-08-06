package com.emreoytun.customermanagementmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EntityScan("com.emreoytun.customermanagementdata.entities")
@ComponentScan(basePackages = "com.emreoytun.customermanagementdata")
@ComponentScan(basePackages = "com.emreoytun.customermanagementmw")
@EnableAspectJAutoProxy
//@EnableDiscoveryClient
public class CustomerManagementMwApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementMwApplication.class, args);
	}

}
