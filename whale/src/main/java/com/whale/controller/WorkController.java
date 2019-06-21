package com.whale.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.whale.model.Work;
import com.whale.model.WorkConcent;
import com.whale.security.model.SecurityUser;
import com.whale.security.repository.SecurityUserRepository;
import com.whale.services.WorkConcentServices;
import com.whale.services.WorkServices;
import com.whale.tools.UploadUntils;

@Controller
@RequestMapping("/back/work")
public class WorkController {
	/**
	 * 本地字段,只判断是否创建文件夹不写入库
	 */
	@Value("${whale.workSql_D}")
	private String workSql_D;
	/**
	 * 浏览器字段写入库
	 */
	@Value("${whale.workSql_L}")
	private String workSql_L;

	@Autowired
	private WorkServices workServices;
	@Autowired
	private SecurityUserRepository securityUserRepository;
	@Autowired
	private WorkConcentServices workConcentServices;

	@GetMapping("/toWork")
	public String toWork(HttpServletRequest httpServletRequest, Model model) {
		String queryUrl = httpServletRequest.getRequestURI();
		model.addAttribute("queryUrl", queryUrl);
		return "back/work/work";
	}

	@GetMapping("/queryAll")
	@ResponseBody
	// public Page<Work> queryAll(@RequestParam(value = "page") Integer page,
	// @RequestParam(value = "size", defaultValue = "5") Integer size,
	// Authentication authentication,
	// Work work
	// ) {
	public Map<String, Object> queryAll(Authentication authentication, Work work) {
		String userName = authentication.getName();
		SecurityUser byuserName = securityUserRepository.findByuserName(userName);
		work.setSecurityUser(byuserName);
		if (null != byuserName) {
			List<Work> queryAll = workServices.quAll(work);
			HashMap<String, Object> m = new HashMap<>();
			m.put("data", queryAll);
			return m;
		}
		return null;
	}

	/**
	 * 模态框跳转用
	 * 
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "/back/work/work_add";
	}

	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Work work,Authentication authentication) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		if (work.getTicketNumber() != null) {
			if (workServices.findByTicketNumber(work.getTicketNumber())!=null) {
				json.put("repeat", "222");
			} else {
				work.setSecurityUser(securityUserRepository.findByuserName(authentication.getName()));
				if (workServices.add(work)) {
					json.put("success", "200");
				}
			}
		} else {
			json.put("fail", "400");
		}
		return json;
	}

	@GetMapping("/delete")
	@ResponseBody
	public String del(String id) {
		Work work = workServices.findById(id);
		Set<WorkConcent> workConcent = work.getWorkConcentList();
		String fileNmae ="";
		try {
			for (WorkConcent w : workConcent) {
				if (!StringUtils.isBlank(w.getSqlUrls())) {
				fileNmae = w.getSqlUrls().substring(w.getSqlUrls().lastIndexOf("/")+1, w.getSqlUrls().length());
				boolean delete = new File(workSql_D+fileNmae).delete();
				System.out.println("删除结果：" + delete);
				}
			}
			workServices.del(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "400";
		}
		return "200";
	}
//
//	@PostMapping("/delete_All")
//	@ResponseBody
//	public String delete_All(String ids) {
//		try {
//			List<String> idList = Arrays.asList(ids.split(","));
//			for (String id : idList) {// 放置报空指针异常
//				Work findById = workServices.findById(id);
//				if (!StringUtils.isBlank(findById.getSqlUrls())) {
//					String[] sqlUrlLit = findById.getSqlUrls().split(",");
//					for (String f : sqlUrlLit) {
//						File file = new File(f);
//						if (file.exists() && file.isFile()) {
//							File absoluteFile = file.getAbsoluteFile();
//							absoluteFile.delete();
//						}
//					}
//				}
//				workServices.delete_All(idList);
//			}
//			return "200";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "400";
//		}
//
//	}

	@RequestMapping("/toUpdate")
	public String toUpdate() {
		return "/back/work/work_edit";
	}

	/**
	 * 获取要编辑的信息回显用
	 * @param id
	 * @return
	 */
	@GetMapping("/toUpdateInfo")
	@ResponseBody
	public Work toUpdateInfo(String id) {
		Work work = workServices.findById(id);
		return work;
	}

	@RequestMapping("/Update")
	@ResponseBody
	public String update(Work work, Authentication authentication) {
		work.setSecurityUser(securityUserRepository.findByuserName(authentication.getName()));
		if (workServices.update(work)) {
			return "200";
		}
		return "400";
	}

	@PostMapping("/sqlUpload")
	@ResponseBody
	public Map<String, Object> sqlUpload(String ticketNumber,MultipartFile sqlurl_font) {
		WorkConcent workConcent = null;
		if(!StringUtils.isBlank(ticketNumber)) {
			workConcent = new WorkConcent();
			Work findByTicketNumber = workServices.findByTicketNumber(ticketNumber);
			workConcent.setSqlUrls(workSql_L+sqlurl_font.getOriginalFilename());
			workConcent.setWork(findByTicketNumber);
		}
		Map<String, Object> upload = UploadUntils.upload(workSql_D,sqlurl_font);
		System.out.println(upload.get("namesame") + "查找文件夹下相同的名字的文件");
		if(workConcentServices.save(workConcent)!=null) {
			return upload;
		} ;
		return null;
	}
};
