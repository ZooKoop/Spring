package com.whale.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception { // 使用表单登录:指定了身份认证的方式
		http.formLogin() // http.httpBasic() //使用回之前的认证方式
			.loginPage("/login.html")
			.loginProcessingUrl("/lg")//前端from表单自定义登陆校验路径
			.defaultSuccessUrl("/index")
			.and()
			.authorizeRequests()// 表示以下都是授权的配置
			.antMatchers("/login.html").permitAll()
			.anyRequest()// 任何请求
			.authenticated()// 都需要身份认证
			.and()
			.csrf() // 关闭csrf 不然不支持post
			.disable();

	}
	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.authorizeRequests()
	// // .antMatchers("/", "/home").permitAll()
	// // 定义哪些URL需要被保护、哪些不需要被保护。例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
	// .antMatchers("/").permitAll()//
	// .anyRequest()
	// .authenticated()
	// .and()
	// .formLogin()
	// .loginPage("/login.html")
	// .defaultSuccessUrl("/list")
	// .failureUrl("/login?error").permitAll().and().rememberMe() // rememberMe
	// .and() // 注销行为任意访问
	// .logout().permitAll()
	// .and().csrf() // 关闭csrf 不然不支持post
	// .disable();
	// }

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.inMemoryAuthentication().passwordEncoder(new
	// BCryptPasswordEncoder()).withUser("user")
	// .password(new BCryptPasswordEncoder().encode("123")).roles("USER");
	// }

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 不加页面无样式，报错
		web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
	}

}