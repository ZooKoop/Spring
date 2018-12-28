package com.whale.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whale.model.UserInfor;
import com.whale.param.UserParam;
import com.whale.repostitory.UserInforRepostitory;

@Controller
public class UserInforController {
	@Autowired
	private UserInforRepostitory userInforRepostitory;
	@Autowired
	private EntityManager entityManager; //自动启用一级缓存

	@RequestMapping("/")
	public String index() {
		return "redirect:/list";
	}

	@RequestMapping("/list")
	public String list(ModelMap model, @RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size) {
//		Sort sort = new Sort(Sort.Direction.DESC, "id");
//		PageRequest pageable = new PageRequest(page, size, sort);
//		Page<UserInfor> findList = userInforRepostitory.findList(pageable);
		if (page<0) {
			return "redirect:/list";
		}
		PageRequest pageable = PageRequest.of(page, size);
		Page<UserInfor> findList = userInforRepostitory.findAll(pageable);
		int totalPages = findList.getTotalPages()-1;
		model.addAttribute("list", findList);
		if (page>=totalPages) {
			page=totalPages;
			PageRequest pageEnd = PageRequest.of(page, size);
			Page<UserInfor> findEnd = userInforRepostitory.findAll(pageEnd);
			model.addAttribute("list", findEnd);
//			return "user/list";
		}
		return "user/list";
	}

	/**
	 * 转到添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "user/userInforAdd";
	}

	@RequestMapping("/add")
	// @ResponseBody 返回json数据
	public String add(@Valid UserParam userParam, BindingResult result, ModelMap model) {
		String errorMsg = "";
		boolean hasErrors = result.hasErrors();
		if (hasErrors) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError e : list) {
				errorMsg = errorMsg + e.getCode() + ":" + e.getDefaultMessage();
			}
			model.addAttribute("errorMsg", errorMsg);
			return "user/userInforAdd";
		}
		UserInfor u = userInforRepostitory.findByUserName(userParam.getUserName());
		if (u != null) {
			model.addAttribute("errorMsg", "用户名已存在！");
			return "user/userInforAdd";
		}
		UserInfor userInfor = new UserInfor();
		BeanUtils.copyProperties(userParam, userInfor);
		userInforRepostitory.save(userInfor);
		return "redirect:/list";
	}

	@RequestMapping("/delete")
	public String delete(String id) {
		userInforRepostitory.deleteById(id);
		return "redirect:/list";
	}

	@RequestMapping("/toUserUpdate")
	@ResponseBody
	public UserInfor toUpdate(String id, Model model) {
		UserInfor userInfor = userInforRepostitory.findById(id).get();
		model.addAttribute("user", userInfor);
		return userInfor;
	}
	
	@Transactional
	@RequestMapping("/userUpdate")
	public String update(@Valid UserParam userParam,BindingResult result,Model model ) {
		String errorMsg = "";
		boolean hasErrors = result.hasErrors();
		if (hasErrors) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError e : list) {
				errorMsg = errorMsg + e.getCode() + ":" + e.getDefaultMessage();
			}
			model.addAttribute("errorMsg", errorMsg);
			return "user/userInforAdd";
		}
		
		UserInfor userInfor = new UserInfor();
		BeanUtils.copyProperties(userParam, userInfor);
		entityManager.merge(userInfor);
		return "redirect:/list";
	}
}
