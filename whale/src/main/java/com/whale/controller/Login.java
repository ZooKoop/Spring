package com.whale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {
	@RequestMapping(value="/loging")
	public String login(String name,String pass) {
		if (name!=""&&name!=null) {
			return "redirect:/list";
		}
		return "loging";
	}
}
