package com.lavu.library.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.lavu.library.service.StorageService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableConfigurationProperties(StorageProperties.class)
public class BeanConfig {
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args ->{
			storageService.init();
		});
	}
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}


}
