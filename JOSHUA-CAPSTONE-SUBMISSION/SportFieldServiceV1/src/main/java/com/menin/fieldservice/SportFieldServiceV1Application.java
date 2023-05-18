package com.menin.fieldservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SportFieldServiceV1Application {

	public static void main(String[] args) {
		SpringApplication.run(SportFieldServiceV1Application.class, args);
	}

}
