/**
 * 
 */
package com.whale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
}
