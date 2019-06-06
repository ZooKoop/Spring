package com.whale.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whale.model.WorkOpction;
import com.whale.services.WorkOpctionServices;
@Controller
@RequestMapping("/back/workopction")
public class WorkOpctionController {
	@Autowired
	private WorkOpctionServices w;
	
	@GetMapping("/toWorkOpction")
	public String toWork(HttpServletRequest request , Model model) {
		model.addAttribute("F_O", "one");
		model.addAttribute("queryUrl", request.getRequestURI());
		return "back/work/workopction";
	}
	
	@GetMapping("/queryAll")
	@ResponseBody
	public Page<WorkOpction> queryAll(@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size) {
		Page<WorkOpction> queryAll = w.queryAll(page,size);
		return queryAll;
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(WorkOpction work,Authentication authentication) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		if (work.getOpctionCode()!=null) {
			if (w.findByOpctionCode(work.getOpctionCode())) {
				hashMap.put("repeat", "222");
			}else {
				boolean add = w.add(work);
				if (add) {
					hashMap.put("success", "200");
				}
			}
		}else {
			hashMap.put("fail", "400");
		}
		return hashMap;
	}
	@GetMapping("/delete")
	@ResponseBody
	public String del(String id) {
		try {
			w.del(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "400";
		}
		return "200";
	}
};
