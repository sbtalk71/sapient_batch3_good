package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(MyServerCOnfiguration.class)
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);

	}

	@Bean
	public RouteLocator appRoutes(RouteLocatorBuilder routeBuilder, MyServerCOnfiguration config) {
		String empserver = config.getEmpServer();
		String deptserver = config.getDeptServer();
		String hrserver = config.getHrServer();
		return routeBuilder
				.routes()
				.route(p -> p.path("/hr/**").uri(hrserver))
				.route(p -> p.path("/employee/**").uri(empserver))
				.route(p -> p.path("/dept/**").uri(deptserver))
				.build();
	}
}

@ConfigurationProperties
class MyServerCOnfiguration {
	private String empServer = "http://localhost:8181";
	private String deptServer = "http://localhost:8184";
	private String hrServer = "http://localhost:8090";

	public String getEmpServer() {
		return empServer;
	}

	public void setEmpServer(String empServer) {
		this.empServer = empServer;
	}

	public String getDeptServer() {
		return deptServer;
	}

	public void setDeptServer(String deptServer) {
		this.deptServer = deptServer;
	}

	public String getHrServer() {
		return hrServer;
	}

	public void setHrServer(String hrServer) {
		this.hrServer = hrServer;
	}

}
