package com.niit.ArchieveService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ArchieveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchieveServiceApplication.class, args);
	}

}
