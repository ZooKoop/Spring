package com.whale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {
	@RequestMapping(value="/login")
	public String login() {
		return "user/list";
	}
}
