/**
 * 
 */
package com.whale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
//	@RequestMapping("/")
//	public String index() {
//		return "index";
//	}
	@RequestMapping("/back/index")
	public String backIndex() {
		return "back/index";
	}
}
