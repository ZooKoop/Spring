package com.whale;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class,ErrorMvcAutoConfiguration.class })

//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})//@SpringBootApplication包含3个注解禁用ErrorMvcAutoConfiguration
public class WhaleApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(WhaleApplication.class);
	}

	public static void main(String[] args) {
		//新的启动方式
//		SpringApplication springApplication = new SpringApplication(WhaleApplication.class);
//		//隐藏banner
//		springApplication.setBannerMode(Banner.Mode.OFF);
//		springApplication.run(args);

//		原来的启动方式，显示banner
		SpringApplication.run(WhaleApplication.class, args);
	}

}
