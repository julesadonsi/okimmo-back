package com.example.backendokimmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendokimmoApplication {

	public static void main(String[] args) {

        SpringApplication.run(BackendokimmoApplication.class, args);
	}

}
