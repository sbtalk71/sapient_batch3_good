package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Employee;
import com.demo.spring.repositories.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmpRestControllerMockMvcTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	EmployeeRepository empRepository;
	
	@Test
	public void testFindSuccess() throws Exception{
		Employee emp= new Employee(130, "Preetam", "Noida", 90000.0,10);
		when(empRepository.findById(130)).thenReturn(Optional.of(emp));
		
		mvc.perform(get("/employee/130"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.name").value("Preetam"));
		
	}
	
	@Test
	public void testSave() throws Exception{
		Employee emp= new Employee(130, "Preetam", "Noida", 90000.0,10);
		ObjectMapper mapper=new ObjectMapper();
		String empJson=mapper.writeValueAsString(emp);
		
		when(empRepository.existsById(130)).thenReturn(false);
				
		mvc.perform(post("/employee/").content(empJson).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("Emp saved"));
		
	}
}
