package com.spring.miniposbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MiniPosBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniPosBackendApplication.class, args);
	}

}
