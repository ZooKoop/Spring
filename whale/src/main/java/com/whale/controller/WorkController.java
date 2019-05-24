package com.whale.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whale.model.Work;
import com.whale.param.UserParam;
import com.whale.security.model.SecurityUser;
import com.whale.security.repository.SecurityUserRepository;
import com.whale.services.WorkServices;
@Controller
@RequestMapping("/back/work")
public class WorkController {
	@Autowired
	private WorkServices workServices;
	@Autowired
	private SecurityUserRepository securityUserRepository;
	@GetMapping("/toWork")
	public String toWork(HttpServletRequest httpServletRequest,Model model) {
		String queryUrl = httpServletRequest.getRequestURI();
		model.addAttribute("queryUrl",queryUrl);
		return "back/work/work";
	}
	
	@GetMapping("/queryAll")
	@ResponseBody
	public Page<Work> queryAll(@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size,
			@RequestParam(value = "order") String order,
			Authentication authentication,
			Work work
			) {
		String userName = authentication.getName();
		SecurityUser byuserName = securityUserRepository.findByuserName(userName);
		work.setSecurityUser(byuserName);
		if (null != byuserName) {
			Page<Work> queryAll = workServices.queryAll(page,size,order,work);
			return queryAll;
		}
		return null;
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
	@GetMapping("/delete")
	@ResponseBody
	public String del(String id) {
		try {
			workServices.del(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "400";
		}
		return "200";
	}
	
	@RequestMapping("/toUpdate")
//	@ResponseBody
	public String toUpdate() {
		return "/back/work/work_edit";
	}

	@Transactional
	@RequestMapping("/Update")
	public String update(@Valid UserParam userParam, BindingResult result, Model model) {
		String errorMsg = "";
		boolean hasErrors = result.hasErrors();
		if (hasErrors) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError e : list) {
				errorMsg = errorMsg + e.getCode() + ":" + e.getDefaultMessage();
			}
			model.addAttribute("errorMsg", errorMsg);
			return "back/user/userInforAdd";
		}

		SecurityUser userInfor = new SecurityUser();
		BeanUtils.copyProperties(userParam, userInfor);
//		entityManager.merge(userInfor);
		return "redirect:back//user/list";
	}
};
