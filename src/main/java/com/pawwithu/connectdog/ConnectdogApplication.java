package com.pawwithu.connectdog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "https://dev-api.connectdog.site")})
public class ConnectdogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectdogApplication.class, args);
	}

}
