/**
 * 
 */
package com.whale.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author Administrator
 *
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.AuthenticationEntryPoint#commence(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
//		  response.setContentType("application/json;charset=utf-8");
//        PrintWriter out = response.getWriter();
//        StringBuffer sb = new StringBuffer();
//        sb.append("{\"status\":\"error\",\"msg\":\"");
//        sb.append("请登陆!");
//        sb.append("\"}");
//        out.write(sb.toString());
//        out.flush();
//        out.close();

//		response.sendRedirect("/");
//		if (isAjaxRequest(request)) {
			response.sendError(HttpServletResponse.SC_OK, authException.getMessage());
//			response.sendRedirect("/"); 
//		} else {
//			response.sendRedirect("/");
//		}

	}

	// 判断是否为ajax请求
//	public static boolean isAjaxRequest(HttpServletRequest request) {
//		String ajaxFlag = request.getHeader("X-Requested-With");
//		return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
//	}

}
