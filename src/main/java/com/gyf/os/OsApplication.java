package com.gyf.os;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableSwagger2
@EnableCircuitBreaker
@EnableAutoConfiguration
@EnableConfigurationProperties
public class OsApplication {
	public static void main(String[] args) {
		SpringApplication.run(OsApplication.class, args);
	}
}
