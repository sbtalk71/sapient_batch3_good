package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.DeptDTO;
import com.demo.spring.EmpDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/hr")
public class HrRestController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("restTemplate2")
	RestTemplate restTemplate2;
	
	@Autowired
	LoadBalancerClient lbClient;
	
	@GetMapping("/info")
	public String info() {
		return "this is HR Application";
	}

	@GetMapping(path = "/dept/{dno}")
	@CircuitBreaker(name="config-hr",fallbackMethod = "fallbackGetEmpInDept")
	public ResponseEntity getEmpInDept(@PathVariable("dno") Integer deptNo) {
		
		System.out.println(restTemplate.getClass().getName());
		ServiceInstance si=lbClient.choose("dept-service");
		
		System.out.println(si.getHost()+":"+si.getPort());
		DeptDTO deptDto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> req = new HttpEntity<>(headers);

		// get the dept for dno
		ResponseEntity<DeptDTO> response1 = restTemplate.exchange("http://localhost:8184/dept/" + deptNo,
				HttpMethod.GET, req, DeptDTO.class);

		deptDto = response1.getBody();

		// get all the employees in above dno
		ResponseEntity<List<EmpDTO>> response2 = restTemplate2.exchange("http://localhost:8181/employee/list/" + deptNo,
				HttpMethod.GET, req, new ParameterizedTypeReference<List<EmpDTO>>() {
				});

		List<EmpDTO> empList = response2.getBody();
		deptDto.setEmpList(empList);
		return ResponseEntity.ok(deptDto);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity handle404(Exception ex) {

		return ResponseEntity.status(404).body(ex.getMessage());

	}
	
	public ResponseEntity fallbackGetEmpInDept(Integer deptNo,Exception ex) {
		
		return ResponseEntity.ok("Service Down, Try after sometime...");
	}
}
