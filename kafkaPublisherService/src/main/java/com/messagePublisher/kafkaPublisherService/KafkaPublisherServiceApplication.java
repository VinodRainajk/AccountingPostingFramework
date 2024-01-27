package com.messagePublisher.kafkaPublisherService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KafkaPublisherServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaPublisherServiceApplication.class, args);
	}


}
