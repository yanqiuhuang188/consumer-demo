package com.nimble.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
// @RibbonClient(name = "provider-1", configuration = RibbonConfig.class)
// 下面注解作用是排除扫描，需要在RibbonConfig上添加@ExcludeFromComponentScan标识注解
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromComponentScan.class)})
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
