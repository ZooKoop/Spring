package com.whale.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whale.model.WorkOpction;
import com.whale.services.WorkOpctionServices;
@Controller
@RequestMapping("/workOpction")
public class WorkOpctionController {
	@Autowired
	private WorkOpctionServices w;
	@GetMapping("/toWorkOpction")
	public String toWork() {
		
		return "work/sj/work";
	}
	
	@GetMapping("/queryAll")
	@ResponseBody
	public Page<WorkOpction> queryAll(@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size,
			@RequestParam(value = "order") String order
			) {
		Page<WorkOpction> queryAll = w.queryAll(page,size,order);
		return queryAll;
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(WorkOpction work,Authentication authentication) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		w.add(work);
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
