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
		String os = System.getProperty("os.name");

		   if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
		      registry.addResourceHandler("/whale_resoruce/**")
		            // /apple/**表示在磁盘apple目录下的所有资源会被解析为以下的路径
		            .addResourceLocations("file:D:/whale_resoruce/"); //媒体资源
//		            .addResourceLocations("classpath:/META-INF/whale_resoruce/");  //swagger2页面
		   } else {  //linux 和mac
		      registry.addResourceHandler("/whale_resoruce/**")
		            .addResourceLocations("file:/resources/whale_resoruce/");   //媒体资源
//		            .addResourceLocations("classpath:/META-INF/whale_resoruce/");  //swagger2页面;
		   }

	}

}
