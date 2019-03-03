/**
 * 
 */
package com.whale.security.authenticationhandler;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whale.security.properties.LoginType;
import com.whale.security.properties.MySecurityproperties;

/**
 * @author Administrator
 *
 */
@Component("myAuthenticationFalureHandler")
public class MyAuthenticationFalureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private MySecurityproperties mySecurityproperties;
	@Autowired
	private ObjectMapper objectMapper;// 对象转为json

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println("----------------"+request.getParameter("username"));
		if (LoginType.JSON.equals(mySecurityproperties.getBrowserProperties().getLoginType())) {
			Logger logger = Logger.getLogger("登陆失败");
			logger.info("登陆失败");
//			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//			response.setContentType("application/json;Charset=UTF-8");
//			response.getWriter().write(objectMapper.writeValueAsString(exception));
			 Map<String, Object> map = new HashMap<>();
	            map.put("code","1002");
	            map.put("msg","用户名或密码错误，登录失败！");
	            map.put("data",exception.getMessage());
	            response.setContentType("application/json;charset=UTF-8");
	            response.getWriter().write(objectMapper.writeValueAsString(map));
		}else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
