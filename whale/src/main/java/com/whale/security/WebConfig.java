/**
 * 
 */
package com.whale.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
//		WebMvcConfigurer.super.addViewControllers(registry);
		registry.addViewController("/images").setViewName(fileSrc+"/user_head _Img");
	}

}
