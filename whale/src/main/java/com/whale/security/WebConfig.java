/**
 * 
 */
package com.whale.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	/**
	 *  配置静态访问资源
	 *  registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
	 *  指定外部的目录也很简单，直接addResourceLocations指定即可addResourceLocations("file:D:/whale/")
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/whale/**").addResourceLocations("file:D:/whale/");
		WebMvcConfigurer.super.addResourceHandlers(registry);

	}

}
