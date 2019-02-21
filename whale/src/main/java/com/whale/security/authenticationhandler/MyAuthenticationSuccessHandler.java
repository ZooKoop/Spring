/**
 * 
 */
package com.whale.security.authenticationhandler;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whale.security.properties.LoginType;
import com.whale.security.properties.MySecurityproperties;

/**
 * @author Administrator
 *
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private ObjectMapper objectMapper;// 对象转为json
	@Autowired
	private MySecurityproperties mySecurityproperties;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// Authentication包含session、登陆等信息
		if (LoginType.JSON.equals(mySecurityproperties.getBrowserProperties().getLoginType())) {
			Logger logger = Logger.getLogger("登陆成功");
			logger.info("登陆成功");
			response.setContentType("application/json;Charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		}else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

}
