package com.whale;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})//禁用ErrorMvcAutoConfiguration
public class WhaleApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(WhaleApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WhaleApplication.class, args);
	}

}
