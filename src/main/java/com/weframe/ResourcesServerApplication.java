package com.weframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.weframe")
@EnableAutoConfiguration
public class ResourcesServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourcesServerApplication.class, args);
	}
	
}
