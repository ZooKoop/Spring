package com.whale.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler myAuthenticationFalureHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // 使用表单登录:指定了身份认证的方式
		http.formLogin() // http.httpBasic() //使用回之前的认证方式
				.successHandler(myAuthenticationSuccessHandler)// 加自己定义的登陆成功hadler
				.loginPage("/login")
				.loginProcessingUrl("/lg")// 前端from表单自定义登陆校验路径
				.defaultSuccessUrl("/")
				.failureHandler(myAuthenticationFalureHandler)
				.failureUrl("/login?error=true")
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.and()
				.cors()
				.and()
				.authorizeRequests()// 表示以下都是授权的配置
				.antMatchers("/login","/**").permitAll()
				.anyRequest()// 任何请求
				.authenticated()// 都需要身份认证
				.and()
				.headers().frameOptions().disable()//iframe不禁止
//				.and()//访问受保护页面，跳转
//				.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint())
				.and().csrf() // 关闭csrf 不然不支持post
				.disable();

	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 不加页面无样式，报错
		web.ignoring().antMatchers("/js/**", "/css/**", "/img/**","/fonts/**","/layer/**");
	}

}