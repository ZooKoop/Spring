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

import com.whale.model.Work;
import com.whale.security.repository.SecurityUserRepository;
import com.whale.services.WorkServices;
@Controller
@RequestMapping("/work")
public class WorkController {
	@Autowired
	private WorkServices workServices;
	@Autowired
	private SecurityUserRepository securityUserRepository;
	@GetMapping("/toWork")
	public String toWork() {
		
		return "work/sj/work";
	}
	
	@GetMapping("/queryAll")
	@ResponseBody
	public Page<Work> queryAll(@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size) {
		Page<Work> queryAll = workServices.queryAll(page,size);
		return queryAll;
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Work work,Authentication authentication) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		if (work.getTicketNumber()!=null) {
			if (workServices.findByTicketNumber(work.getTicketNumber())) {
				hashMap.put("repeat", "222");
			}else {
				work.setSecurityUser(securityUserRepository.findByuserName(authentication.getName()));
				boolean add = workServices.add(work);
				if (add) {
					hashMap.put("success", "200");
				}
			}
		}else {
			hashMap.put("fail", "400");
		}
		return hashMap;
	}
	
}
