package com.demo.spring;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Java11HttpClientDemo {

	public static void main(String[] args) throws Exception{
		HttpClient httpCLient = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.connectTimeout(Duration.ofSeconds(10))
				.build();
		
	HttpRequest request=HttpRequest.newBuilder()
			.uri(URI.create("http://localhost:8181/find/104"))
			.GET()
			.header("Accept", "application/json")
			.build();
	HttpResponse<String> empData=httpCLient.send(request, HttpResponse.BodyHandlers.ofString());
	System.out.println(empData.body());
	

	}

}
