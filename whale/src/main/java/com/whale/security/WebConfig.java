/**
 * 
 */
package com.whale.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Value("${img.src}")
	private String fileSrc;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/images/**").addResourceLocations("file:D:/imgs/");
//		WebMvcConfigurer.super.addResourceHandlers(registry);
		
	}
	

}
