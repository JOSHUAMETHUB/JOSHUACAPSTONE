package com.menin.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SesSecurityServiceV1Application {

	public static void main(String[] args) {
		SpringApplication.run(SesSecurityServiceV1Application.class, args);
	}

}
