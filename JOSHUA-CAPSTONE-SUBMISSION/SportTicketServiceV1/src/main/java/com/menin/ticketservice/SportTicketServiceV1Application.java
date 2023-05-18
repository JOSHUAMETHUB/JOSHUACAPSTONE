package com.menin.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SportTicketServiceV1Application {

	public static void main(String[] args) {
		SpringApplication.run(SportTicketServiceV1Application.class, args);
	}

}
