package com.orderhippo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class OrderhippoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OrderhippoApplication.class, args);
	}

   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
           return builder.sources(OrderhippoApplication.class);
   }
} 
