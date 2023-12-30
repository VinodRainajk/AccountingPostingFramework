package com.accountingProcessor.accountingProcessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AccountingProcessorApplication {
	private static final Logger LOGGER = LogManager.getLogger(AccountingProcessorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AccountingProcessorApplication.class, args);

	}

}
