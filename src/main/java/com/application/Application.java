package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	GrantedAuthorityDefaults grantedAuthorityDefaults() {
//		return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
//	}
}

