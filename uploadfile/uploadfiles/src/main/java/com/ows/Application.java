package com.ows;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.ows.uploadfiles.properties.StorageProperties;
import com.ows.uploadfiles.service.IStorageService;



@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(IStorageService istorageService) {
		return (args) -> {
            istorageService.deleteAll();
            istorageService.init();
		};
	}
}
