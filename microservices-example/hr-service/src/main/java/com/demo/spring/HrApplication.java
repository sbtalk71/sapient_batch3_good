package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class HrApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);

	}

	@Bean
	//@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	@Bean
	//@LoadBalanced
	public RestTemplate restTemplate2() {
		return new RestTemplate();
	}
}
